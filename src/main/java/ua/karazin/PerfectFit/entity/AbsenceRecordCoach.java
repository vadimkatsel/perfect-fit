package ua.karazin.PerfectFit.entity;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import org.springframework.stereotype.Component;

import ua.karazin.PerfectFit.constants.tables.AbsenceRecordCoachConstants;


import java.time.LocalDate;


@Entity
@Component
@Table(name = AbsenceRecordCoachConstants.TABLE_NAME)
public class AbsenceRecordCoach {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = AbsenceRecordCoachConstants.COLUMN_ID_NAME)
    private int id;
    @Column(name = AbsenceRecordCoachConstants.COLUMN_ABSENCE_DATE_BEGIN_NAME)
    private LocalDate absenceDateBegin;

    @Column(name = AbsenceRecordCoachConstants.COLUMN_ABSENCE_DATE_END_NAME)
    private LocalDate absenceDateEnd;
    @Column(name = AbsenceRecordCoachConstants.COLUMN_REASON_NAME)
    private String reason;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = AbsenceRecordCoachConstants.COLUMN_FK_COACH_NAME)
    private Coach coach;

    public AbsenceRecordCoach() {
        // default constructor
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getAbsenceDateBegin() {
        return absenceDateBegin;
    }

    public void setAbsenceDateBegin(LocalDate absenceDateBegin) {
        this.absenceDateBegin = absenceDateBegin;
    }

    public LocalDate getAbsenceDateEnd() {
        return absenceDateEnd;
    }

    public void setAbsenceDateEnd(LocalDate absenceDateEnd) {
        this.absenceDateEnd = absenceDateEnd;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Coach getCoach() {
        return coach;
    }

    public void setCoach(Coach coach) {
        this.coach = coach;
    }
}