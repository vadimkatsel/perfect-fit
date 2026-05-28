package ua.karazin.PerfectFit.entity;

import jakarta.persistence.*;
import org.springframework.stereotype.Component;
import ua.karazin.PerfectFit.constants.tables.DayOfWeekConstants;


@Entity
@Component
@Table(name = DayOfWeekConstants.TABLE_NAME)
public class DayOfWeek {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = DayOfWeekConstants.COLUMN_ID_NAME)
    int id;
    @Column(name = DayOfWeekConstants.COLUMN_DAY_NAME_NAME)
    String dayName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDayName() {
        return dayName;
    }

    public void setDayName(String dayName) {
        this.dayName = dayName;
    }
}
