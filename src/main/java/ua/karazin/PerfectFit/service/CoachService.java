package ua.karazin.PerfectFit.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.karazin.PerfectFit.entity.Client;
import ua.karazin.PerfectFit.entity.Coach;
import ua.karazin.PerfectFit.repository.CoachRepository;
import ua.karazin.PerfectFit.repository.CourseRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.TreeMap;

@Service
public class CoachService {

    private final CoachRepository coachRepository;
    public final CourseRepository courseRepository;

    @Autowired
    public CoachService(CoachRepository coachRepository, CourseRepository courseRepository) {
        this.coachRepository = coachRepository;
        this.courseRepository = courseRepository;
    }

    public Coach findCoachByMail(String mail) {return coachRepository.findCoachByMail(mail);}



    public List<Coach> getAllCoaches() {
        return coachRepository.findAll();
    }

    public Coach getCoachById(Long id) {
        Optional<Coach> optionalCoach = coachRepository.findById(id);
        return optionalCoach.orElse(null);
    }
    public TreeMap<LocalDate, TreeMap<String, String>> getSchedule(Integer dateIndex, Coach coach) {

        if (dateIndex < 0) {
            dateIndex = 0;
        }

        LocalDate currentDate = LocalDate.now().plusDays(dateIndex);
        TreeMap<LocalDate, TreeMap<String, String>> schedule = new TreeMap<>();
        int countOfCols = 6;
        int startIndex = (dateIndex == 0) ? 0 : countOfCols*dateIndex;
        int endIndex = (dateIndex == 0) ? countOfCols : countOfCols * (dateIndex + 1);


        for (int i = startIndex; i < endIndex; i++) {
            LocalDate dateIterator = currentDate.plusDays(i - dateIndex);
            List<Object[]> result = courseRepository.findCoursesByDateAndCoach(dateIterator,
                    dateIterator.getDayOfWeek().getValue(), coach);
            if (!result.isEmpty()) {
                TreeMap<String, String> timeSchedule = new TreeMap<>();
                for (Object[] row : result) {
                    String courseName = (String) row[0];
                    LocalTime startTime = (LocalTime) row[1];


                    timeSchedule.put(startTime.toString(), courseName);

                }
                schedule.put(dateIterator, timeSchedule);
            } else {
                schedule.put(dateIterator, null);
            }

        }

        return schedule;
    }
    public Coach createCoach(Coach coach) {
        return coachRepository.save(coach);
    }


    public Coach updateCoach(Coach coach) {
        Optional<Coach> optionalCoach = coachRepository.findById(Long.valueOf(coach.getId()));

        if(optionalCoach.isPresent()) {
            Coach existingCoach = optionalCoach.get();
            existingCoach.setFio(coach.getFio());
            existingCoach.setMail(coach.getMail());
            existingCoach.setPassword(coach.getPassword());
            existingCoach.setPhoneNumber(coach.getPhoneNumber());
            return coachRepository.save(existingCoach);
        }
        return null;
    }

    public void deleteCoach(Long id) {
        coachRepository.deleteById(id);
    }
}

