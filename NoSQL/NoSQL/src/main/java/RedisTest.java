import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class RedisTest {
    public static void main(String[] args) throws InterruptedException {
        RedisStorage redis = new RedisStorage();
        redis.init();
        for (int i = 0; i < 20; i++) {
            redis.logPageVisit(i);
            Thread.sleep(10);
        }
        while (true){
            List<String> userList = new ArrayList<>();
            int j = 0;
            for (String user : redis.getUser().stream().collect(Collectors.toList())) {
                if(!userList.contains(user)){
                    System.out.println("-пользователь " + user);
                    redis.add(user);
                    if(j == 4){
                        int user_id = new Random().ints(j, 20).findFirst().getAsInt();
                        while (user_id == 4 || userList.contains(String.valueOf(user_id))){
                            user_id = new Random().ints(j, 20).findFirst().getAsInt();
                        }
                        redis.add(String.valueOf(user_id));
                        userList.add(String.valueOf(user_id));
                        System.out.println(">пользователю " + user_id + " доступна платная услуга \n-пользователь " + user_id);
                    }else if(j == 19){
                        j = 0;
                        Thread.sleep(1000);
                    }
                }
                j++;
            }
        }
    }
}
