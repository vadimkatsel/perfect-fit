package ua.karazin.PerfectFit.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.karazin.PerfectFit.entity.AbsenceRecordCoach;

@Repository
@Transactional
public interface AbsenceRecordCoachRepository extends JpaRepository<AbsenceRecordCoach, Long> {
}
