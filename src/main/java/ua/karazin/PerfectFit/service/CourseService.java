package ua.karazin.PerfectFit.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.karazin.PerfectFit.entity.Course;
import ua.karazin.PerfectFit.entity.DayOfWeek;
import ua.karazin.PerfectFit.entity.Schedule;
import ua.karazin.PerfectFit.repository.CourseRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class CourseService {
    private final CourseRepository courseRepository;

    @Autowired
    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public List<Course> getAllAvailableCourses() {
        return courseRepository.findAllAvailableCourses();
    }

    public List<Schedule> getAllDaysOfWeekForCourse(Course course) {

        return courseRepository.getAllDaysOfWeekForCourse(course);
    }

    public Course getCourseById(Long courseId) {
        Optional<Course> optionalCourse = courseRepository.findById(courseId);
        return optionalCourse.orElse(null);
    }

    public Course createCourse(Course course) {
        return courseRepository.save(course);
    }

    public Course updateCourse(Course course) {
        Optional<Course> optionalCourse = courseRepository.findById(Long.valueOf(course.getId()));
        if (optionalCourse.isPresent()) {
            Course existingCourse = optionalCourse.get();
            existingCourse.setName(course.getName());
            existingCourse.setCapacity(course.getCapacity());
            return courseRepository.save(existingCourse);
        }
        return null;
    }

    public void deleteCourse(Long courseId) {
        courseRepository.deleteById(courseId);
    }
}
