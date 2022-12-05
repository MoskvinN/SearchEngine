package main;

import main.model.Task;
import main.model.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class TaskController {
    @Autowired
    private TaskRepository taskRepository;

    @GetMapping("/tasks")
    public List<Task> tasks(){
        Iterable<Task> taskIterable = taskRepository.findAll();
        ArrayList<Task> tasks = new ArrayList<>();
        for (Task task : taskIterable) {
            tasks.add(task);
        }
        return tasks;
    }

    @PostMapping("/tasks")
    public ResponseEntity add(@RequestParam String title, @RequestParam String description){
        Task task = new Task();
        task.setTitle(title);
        task.setDescription(description);
        taskRepository.save(task);
        return ResponseEntity.status(HttpStatus.CREATED).body(null);
    }

    @GetMapping("/tasks/{id}")
    public ResponseEntity get(@PathVariable int id){
        Optional<Task> optionalTask = taskRepository.findById(id);
        if(!optionalTask.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return new ResponseEntity(optionalTask.get(), HttpStatus.OK);
    }

    @DeleteMapping("/tasks/{id}")
    public ResponseEntity delete(@PathVariable int id){
        Iterable<Task> taskIterable = taskRepository.findAll();
        for (Task task : taskIterable){
            if(task.getId() == id){
                taskRepository.deleteById(id);
                return ResponseEntity.status(HttpStatus.OK).body(null);
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    @PatchMapping("/tasks/{id}")
    public ResponseEntity patch(@PathVariable int id, @RequestParam(required = false) boolean isDone, @RequestParam(required = false) String title, @RequestParam(required = false) String description){
        Optional<Task> optionalTask = taskRepository.findById(id);
        if(!optionalTask.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }else {
            if(isDone && title != null && description != null){
                optionalTask.get().setDone(true);
                optionalTask.get().setTitle(title);
                optionalTask.get().setDescription(description);
            } else if(isDone && title != null){
                optionalTask.get().setDone(true);
                optionalTask.get().setTitle(title);
            } else if (isDone && description != null) {
                optionalTask.get().setDone(true);
                optionalTask.get().setDescription(description);
            } else if(title != null && description != null){
                optionalTask.get().setTitle(title);
                optionalTask.get().setDescription(description);
            } else if (isDone) {
                optionalTask.get().setDone(true);
            } else if (title != null) {
                optionalTask.get().setTitle(title);
            } else if (description != null) {
                optionalTask.get().setDescription(description);
            }
            taskRepository.save(optionalTask.get());
        }
        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
}
