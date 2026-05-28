package ua.karazin.PerfectFit.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import ua.karazin.PerfectFit.constants.tables.ClientConstants;


import java.util.Objects;

@Entity(name = ClientConstants.TABLE_NAME)
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = ClientConstants.COLUMN_ID_NAME)
    private Long id;
    @NotEmpty(message = "Name and surname is required!")
    @Column(name = ClientConstants.COLUMN_FIO_NAME)
    private String fio;

    @Column(name = ClientConstants.COLUMN_PHONE_NUMBER_NAME)
    @Pattern(regexp = "\\+\\d{1,3}\\d{9}|0\\d{9}", message = "Invalid phone number format!")
    private String phoneNumber;
    @NotEmpty(message = "Email is required!")
    @Email(message = "Invalid email format!")
    @Column(name = ClientConstants.COLUMN_MAIL_NAME)
    private String mail;
    @Size(min = 6, max = 60, message = "Password can't be saved in this size!")
    @NotEmpty(message = "Password is required!")
    @Column(name = ClientConstants.COLUMN_PASSWORD_NAME)
    private String password;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = ClientConstants.COLUMN_SUBSCRIPTION_NAME)
    private Subscription subscription;



    public Client() {
        // default constructor
    }

    public Client(Long id, String fio, String phoneNumber, String mail, String password, Subscription subscription) {
        this.id = id;
        this.fio = fio;
        this.phoneNumber = phoneNumber;
        this.mail = mail;
        this.password = password;
        this.subscription = subscription;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public Subscription getSubscription() {
        return subscription;
    }

    public void setSubscription(Subscription subscription) {
        this.subscription = subscription;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return Objects.equals(id, client.id) && Objects.equals(fio, client.fio) && Objects.equals(phoneNumber, client.phoneNumber) && Objects.equals(mail, client.mail) && Objects.equals(password, client.password) && Objects.equals(subscription, client.subscription);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id , fio, phoneNumber, mail, password, subscription);
    }
}

