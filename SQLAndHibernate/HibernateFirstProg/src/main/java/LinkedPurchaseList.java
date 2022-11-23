import jakarta.persistence.*;

import java.util.Date;

@Entity
public class LinkedPurchaseList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "student_id")
    private int studentsId;


    @Column(name = "course_id")
    private int courseId;


    @Column(name = "subscription_date")
    private Date subscriptionDate;

    private int price;
}
