package ua.karazin.PerfectFit.entity;

import jakarta.persistence.*;
import org.springframework.stereotype.Component;
import ua.karazin.PerfectFit.constants.tables.SubscriptionConstants;


import java.util.Objects;

@Entity
@Component
@Table(name = SubscriptionConstants.TABLE_NAME)
public class Subscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = SubscriptionConstants.COLUMN_ID_NAME)
    private int id;

    @Column(name = SubscriptionConstants.COLUMN_NAME_NAME)
    private String name;

    @Column(name = SubscriptionConstants.COLUMN_DESCRIPTION_NAME)
    private String description;

    @Column(name = SubscriptionConstants.COLUMN_PRICE_NAME)
    private float price;

    @Column(name = SubscriptionConstants.COLUMN_MAX_COURSES_NAME)
    private int maxCoursesCapacity;

    public Subscription() {
        // default constructor
    }

    public Subscription(int id, String name, String description, float price, int maxCoursesCapacity) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.maxCoursesCapacity = maxCoursesCapacity;
    }

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getMaxCoursesCapacity() {
        return maxCoursesCapacity;
    }

    public void setMaxCoursesCapacity(int maxCoursesCapacity) {
        this.maxCoursesCapacity = maxCoursesCapacity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Subscription that = (Subscription) o;
        return id == that.id && Float.compare(that.price, price) == 0 && maxCoursesCapacity == that.maxCoursesCapacity && Objects.equals(name, that.name) && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, price, maxCoursesCapacity);
    }
}