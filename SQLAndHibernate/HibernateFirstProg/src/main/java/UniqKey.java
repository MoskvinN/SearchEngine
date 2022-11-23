import jakarta.persistence.*;
import java.io.Serializable;

@Embeddable
public class UniqKey implements Serializable {

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "student_id")
    private Students studentId;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "course_id")
    private Courses courseId;
}
