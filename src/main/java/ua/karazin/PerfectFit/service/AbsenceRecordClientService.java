package ua.karazin.PerfectFit.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.karazin.PerfectFit.entity.AbsenceRecordClient;
import ua.karazin.PerfectFit.repository.AbsenceRecordClientRepository;

@Service
public class AbsenceRecordClientService {

    AbsenceRecordClientRepository absenceRecordClientRepository;

    @Autowired
    public AbsenceRecordClientService(AbsenceRecordClientRepository absenceRecordClientRepository) {
        this.absenceRecordClientRepository = absenceRecordClientRepository;
    }

    public void save(AbsenceRecordClient ar) {absenceRecordClientRepository.save(ar);}


}
