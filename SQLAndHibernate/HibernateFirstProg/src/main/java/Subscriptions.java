import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "subscriptions")
public class Subscriptions implements Serializable {

    @EmbeddedId
    @Column(name = "course_id")
    private UniqKey courseId;

    @Column(name = "subscription_date")
    private Date subscriptionDate;

    public UniqKey getCourseId() {
        return courseId;
    }

    public void setCourseId(UniqKey courseId) {
        this.courseId = courseId;
    }

    public Date getSubscriptionDate() {
        return subscriptionDate;
    }

    public void setSubscriptionDate(Date subscriptionDate) {
        this.subscriptionDate = subscriptionDate;
    }
}
