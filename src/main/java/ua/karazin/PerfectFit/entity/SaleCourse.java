package ua.karazin.PerfectFit.entity;

import jakarta.persistence.*;
import org.springframework.stereotype.Component;
import ua.karazin.PerfectFit.constants.tables.SaleCourseConstants;

import java.util.Objects;

@Entity
@Component
@Table(name = SaleCourseConstants.TABLE_NAME)
public class SaleCourse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = SaleCourseConstants.COLUMN_ID_NAME)
    private int id;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = SaleCourseConstants.COLUMN_FK_CLIENT_NAME)
    private Client client;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = SaleCourseConstants.COLUMN_FK_COURSE_NAME)
    private Course course;

    public SaleCourse() {}
    public SaleCourse(int id, Client client, Course course) {
        this.id = id;
        this.client = client;
        this.course = course;
    }

    public int getId() {
        return id;
    }

    public Client getClient() {
        return client;
    }

    public Course getCourse() {
        return course;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SaleCourse that = (SaleCourse) o;
        return id == that.id && Objects.equals(client, that.client) && Objects.equals(course, that.course);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, client, course);
    }
}
