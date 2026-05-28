package ua.karazin.PerfectFit.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ua.karazin.PerfectFit.repository.ScheduleRepository;



@Service
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;

    @Autowired
    ScheduleService(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }







}
