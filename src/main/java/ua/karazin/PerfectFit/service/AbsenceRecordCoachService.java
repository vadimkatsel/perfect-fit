package ua.karazin.PerfectFit.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.karazin.PerfectFit.entity.AbsenceRecordCoach;
import ua.karazin.PerfectFit.repository.AbsenceRecordCoachRepository;

@Service
public class AbsenceRecordCoachService {
    AbsenceRecordCoachRepository absenceRecordCoachRepository;

    @Autowired
    public AbsenceRecordCoachService(AbsenceRecordCoachRepository absenceRecordCoachRepository) {
        this.absenceRecordCoachRepository = absenceRecordCoachRepository;
    }

    public void save(AbsenceRecordCoach absenceRecordCoach) {
        absenceRecordCoachRepository.save(absenceRecordCoach);
    }
}
