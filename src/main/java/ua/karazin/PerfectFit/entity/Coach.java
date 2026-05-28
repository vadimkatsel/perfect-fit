package ua.karazin.PerfectFit.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.springframework.stereotype.Component;
import ua.karazin.PerfectFit.constants.tables.CoachConstants;

import java.util.Objects;

@Entity
@Component
@Table(name = CoachConstants.TABLE_NAME)
public class Coach {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = CoachConstants.COLUMN_ID_NAME)
    private int id;
    @Column(name = CoachConstants.COLUMN_FIO_NAME)
    private String fio;
    @Pattern(regexp = "\\+\\d{1,3}\\d{9}|0\\d{9}", message = "Invalid phone number format!")
    @Column(name = CoachConstants.COLUMN_PHONE_NUMBER_NAME)
    private String phoneNumber;
    @NotEmpty(message = "Email is required!")
    @Email(message = "Invalid email format!")
    @Column(name = CoachConstants.COLUMN_MAIL_NAME)
    private String mail;
    @Size(min = 6, max = 60, message = "Password can't be saved in this size!")
    @Column(name = CoachConstants.COLUMN_PASSWORD_NAME)
    private String password;

    public Coach() {
        // default constructor
    }

    public Coach(int id, String fio, String phoneNumber, String mail, String password) {
        this.id = id;
        this.fio = fio;
        this.phoneNumber = phoneNumber;
        this.mail = mail;
        this.password = password;
    }

    // getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coach coach = (Coach) o;
        return id == coach.id && Objects.equals(fio, coach.fio) && Objects.equals(phoneNumber, coach.phoneNumber) && Objects.equals(mail, coach.mail) && Objects.equals(password, coach.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fio, phoneNumber, mail, password);
    }

    @Override
    public String toString() {
        return "Coach{" +
                "id=" + id +
                ", fio='" + fio + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", mail='" + mail + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
