package ua.karazin.PerfectFit.entity;


import jakarta.persistence.*;
import org.springframework.stereotype.Component;
import ua.karazin.PerfectFit.constants.tables.CourseConstants;
import ua.karazin.PerfectFit.constants.tables.ScheduleConstants;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Component
@Table(name = CourseConstants.TABLE_NAME)
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = CourseConstants.COLUMN_ID_NAME)
    private int id;

    @Column(name = CourseConstants.COLUMN_NAME_NAME)
    private String name;

    @Column(name = CourseConstants.COLUMN_CAPACITY_NAME)
    private int capacity;
    @Column(name = "description")
    private String description;

    @Column(name = CourseConstants.COLUMN_BEGIN_NAME)
    private LocalDate beginDate;
    @Column(name = CourseConstants.COLUMN_END_NAME)
    private LocalDate endDate;

    public Course() {
        // default constructor
    }

    public Course(int id, String name, int capacity, LocalDate beginDate, LocalDate endCourse) {
        this.id = id;
        this.name = name;
        this.capacity = capacity;
        this.beginDate = beginDate;
        this.endDate = endCourse;
    }

    // getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getDescription() {return description;}

    public void setDescription(String description) {this.description = description;}

    public LocalDate getBeginDate() {return beginDate;}

    public void setBeginDate(LocalDate beginDate) {this.beginDate = beginDate;}

    public LocalDate getEndDate() {return endDate;}

    public void setEndDate(LocalDate endDate) {this.endDate = endDate;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return id == course.id && capacity == course.capacity && Objects.equals(name, course.name) && Objects.equals(description, course.description) && Objects.equals(beginDate, course.beginDate) && Objects.equals(endDate, course.endDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, capacity, description, beginDate, endDate);
    }
}
