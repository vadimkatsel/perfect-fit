package ua.karazin.PerfectFit.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.karazin.PerfectFit.entity.AbsenceRecordClient;

@Repository
@Transactional
public interface AbsenceRecordClientRepository extends JpaRepository<AbsenceRecordClient, Long> { }
