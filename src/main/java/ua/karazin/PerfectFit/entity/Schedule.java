package ua.karazin.PerfectFit.entity;

import jakarta.persistence.*;
import org.springframework.stereotype.Component;
import ua.karazin.PerfectFit.constants.tables.ScheduleConstants;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;
import java.util.Set;

@Entity
@Component
@Table(name = ScheduleConstants.TABLE_NAME)
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = ScheduleConstants.COLUMN_ID_NAME)
    private int id;
    @Column(name = ScheduleConstants.COLUMN_BEGIN_TIME_NAME)
    private LocalTime beginTime;
    @Column(name = ScheduleConstants.COLUMN_END_TIME_NAME)
    private LocalTime endTime;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = ScheduleConstants.COLUMN_FK_COACH_NAME)
    private Coach coach;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = ScheduleConstants.COLUMN_FK_COURSE_NAME)
    private Course course;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = ScheduleConstants.COLUMN_DAYS_OF_WEEK_NAME)
    private DayOfWeek dayOfWeek;

    public Schedule() {
        // default constructor
    }

    public Schedule(int id, LocalTime beginTime, LocalTime endTime, LocalDate beginDate,
                    LocalDate deadlineCourse, Coach coach, Course course ) {
        this.id = id;
        this.beginTime = beginTime;
        this.endTime = endTime;
        this.coach = coach;
        this.course = course;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalTime getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(LocalTime beginTime) {
        this.beginTime = beginTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public Coach getCoach() {
        return coach;
    }

    public void setCoach(Coach coach) {
        this.coach = coach;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Schedule schedule = (Schedule) o;
        return id == schedule.id && coach == schedule.coach && course == schedule.course  && Objects.equals(beginTime, schedule.beginTime) && Objects.equals(endTime, schedule.endTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, beginTime, endTime, coach, course);
    }
}


