package ua.karazin.PerfectFit.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.springframework.stereotype.Component;
import ua.karazin.PerfectFit.constants.tables.AdminConstants;

import java.util.Objects;

@Entity
@Component
@Table(name = AdminConstants.TABLE_NAME)
public class Admin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column( name = AdminConstants.COLUMN_ID_NAME)
    private int id;
    @Column(name = AdminConstants.COLUMN_FIO_NAME)
    private String fio;
    @Column(name = AdminConstants.COLUMN_PASSWORD_NAME)
    private String password;

    public Admin() {
        // default constructor
    }

    public Admin(int id, String fio, String password) {
        this.id = id;
        this.fio = fio;
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
        Admin admin = (Admin) o;
        return id == admin.id && Objects.equals(fio, admin.fio) && Objects.equals(password, admin.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fio, password);
    }
}


