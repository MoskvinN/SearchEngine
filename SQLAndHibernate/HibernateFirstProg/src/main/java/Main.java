import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
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
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure("hibernate.cfg.xml").build();
        Metadata metadata = new MetadataSources(registry).getMetadataBuilder().build();
        SessionFactory sessionFactory = metadata.getSessionFactoryBuilder().build();

        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        sessionFactory.close();

        String url = "jdbc:mysql://127.0.0.1:3306/hibernate_prog";
        String user = "root";
        String pass = "8916Moskva773122";


        try {
            Connection connection = DriverManager.getConnection(url, user, pass);
            Statement statement = connection.createStatement();
            statement.executeUpdate("INSERT INTO linkedpurchaselist (student_id, course_id, price, subscription_date) \n" +
                    "SELECT student_id, course_id, purchaselist.price, purchaselist.subscription_date \n" +
                    "FROM purchaselist \n" +
                    "INNER JOIN students ON purchaselist.student_name = students.name \n" +
                    "INNER JOIN subscriptions ON students.id = subscriptions.student_id \n" +
                    "INNER JOIN courses ON subscriptions.course_id = courses.id \n" +
                    "GROUP BY student_id, course_id;");

            statement.close();
            connection.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
