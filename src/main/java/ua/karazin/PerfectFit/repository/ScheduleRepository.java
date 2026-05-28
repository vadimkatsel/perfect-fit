package ua.karazin.PerfectFit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ua.karazin.PerfectFit.entity.Client;
import ua.karazin.PerfectFit.entity.Course;
import ua.karazin.PerfectFit.entity.Schedule;
import ua.karazin.PerfectFit.entity.Subscription;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;


@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
}
