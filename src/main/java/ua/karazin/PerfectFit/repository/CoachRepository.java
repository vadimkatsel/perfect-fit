package ua.karazin.PerfectFit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.karazin.PerfectFit.entity.Coach;

@Repository
public interface CoachRepository extends JpaRepository<Coach, Long> {

     public Coach findCoachByMail(String username);
}
