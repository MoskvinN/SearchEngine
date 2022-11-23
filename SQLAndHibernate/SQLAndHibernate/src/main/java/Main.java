import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) {

        String url = "jdbc:mysql://127.0.0.1:3306/skillbox";
        String user = "root";
        String pass = "8916Moskva773122";


        try {
            Connection connection = DriverManager.getConnection(url, user, pass);
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(
                    "SELECT pl.course_name, pl.subscription_date, ROUND(COUNT(pl.course_name) / (MAX(MONTH(pl.subscription_date)) - MIN(MONTH(pl.subscription_date)) + 1), 2) AS average_quantity FROM skillbox.purchaselist pl GROUP BY pl.course_name ORDER BY pl.course_name;");
            System.out.println("course_name / average_quantity" );
            while (resultSet.next()){
                System.out.println(resultSet.getString("course_name") + " / " + resultSet.getString("average_quantity"));
            }

            resultSet.close();
            statement.close();
            connection.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
