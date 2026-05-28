package ua.karazin.PerfectFit.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import org.springframework.stereotype.Component;
import ua.karazin.PerfectFit.constants.tables.AbsenceRecordClientConstants;

import java.time.LocalDate;
import java.util.Objects;


@Entity
@Component
@Table(name = AbsenceRecordClientConstants.TABLE_NAME)
public class AbsenceRecordClient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = AbsenceRecordClientConstants.COLUMN_ID_NAME)
    private int id;
    @Column(name = AbsenceRecordClientConstants.COLUMN_ABSENCE_DATE_BEGIN_NAME)
    private LocalDate absenceDateBegin;

    @Column(name = AbsenceRecordClientConstants.COLUMN_ABSENCE_DATE_END_NAME)
    private LocalDate absenceDateEnd;
    @Column(name = AbsenceRecordClientConstants.COLUMN_REASON_NAME)
    @Size(max = 500, message = "Too big text for reason!")
    private String reason;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = AbsenceRecordClientConstants.COLUMN_FK_CLIENT_NAME)
    private Client client;

    public AbsenceRecordClient() {
        // default constructor
    }

    public AbsenceRecordClient(int id, LocalDate absenceDateBegin, LocalDate absenceDateEnd, String reason, Client client) {
        this.id = id;
        this.absenceDateBegin = absenceDateBegin;
        this.absenceDateEnd = absenceDateEnd;
        this.reason = reason;
        this.client = client;
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

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbsenceRecordClient that = (AbsenceRecordClient) o;
        return id == that.id && Objects.equals(absenceDateBegin, that.absenceDateBegin) && Objects.equals(absenceDateEnd, that.absenceDateEnd) && Objects.equals(reason, that.reason) && Objects.equals(client, that.client);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, absenceDateBegin, absenceDateEnd, reason, client);
    }
}