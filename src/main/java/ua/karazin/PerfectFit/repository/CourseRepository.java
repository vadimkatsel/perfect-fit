package ua.karazin.PerfectFit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ua.karazin.PerfectFit.entity.*;


import java.time.LocalDate;
import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    @Query("SELECT sc.course FROM SaleCourse sc WHERE sc.client = :client")
    List<Course> findCoursesByClient(@Param("client") Client client);

    @Query("SELECT c.name, s.beginTime " +
            "FROM SaleCourse sc " +
            "JOIN client cl ON cl = sc.client " +
            "JOIN Course c ON c = sc.course " +
            "JOIN Schedule s ON s.course = c " +
            "WHERE (:date BETWEEN c.beginDate AND c.endDate) " + "AND :clParam = cl " + " AND s.dayOfWeek.id = :dayOfWeek " +
            "AND s.coach.id NOT IN (SELECT arc.coach.id FROM AbsenceRecordCoach arc WHERE :date BETWEEN arc.absenceDateBegin AND arc.absenceDateEnd) " +
            "AND :clParam NOT IN (SELECT arcl.client FROM AbsenceRecordClient arcl WHERE :date BETWEEN arcl.absenceDateBegin AND  arcl.absenceDateEnd)")
    List<Object[]> findCoursesByDateAndClient(@Param("date") LocalDate date, @Param("dayOfWeek") int dayOfWeek, @Param("clParam") Client client);


    @Query("SELECT c.name, sh.beginTime FROM Schedule sh " +
            "JOIN Course c ON sh.course = c " +
            "JOIN Coach co ON sh.coach = co " +
            "WHERE (:date BETWEEN c.beginDate AND c.endDate) " + "AND :coP = co " + " AND sh.dayOfWeek.id = :dayOfWeek " +
            "AND co NOT IN (SELECT arc.coach FROM AbsenceRecordCoach arc WHERE :date BETWEEN arc.absenceDateBegin AND arc.absenceDateEnd)")
    List<Object[]> findCoursesByDateAndCoach(@Param("date") LocalDate date, @Param("dayOfWeek") int dayOfWeek, @Param("coP")Coach coach);


    @Query("SELECT DISTINCT co, c.fio " +
            "FROM Course co " +
            "JOIN SaleCourse sc on sc.course = co " +
            "JOIN Schedule sch on sch.course = co " +
            "JOIN Coach c on sch.coach = c " +
            "WHERE (current_date BETWEEN co.beginDate AND co.endDate) AND sc.client = :clP")
    List<Object[]> getListOfCoursesForClient(@Param("clP") Client client);


    @Query( "SELECT DISTINCT ar FROM AbsenceRecordCoach ar " +
    "JOIN Coach  c  ON c = ar.coach " +
    "JOIN Schedule sh ON sh.coach = c " +
    "WHERE (GETDATE() < ar.absenceDateEnd) AND (GETDATE() BETWEEN sh.course.beginDate and sh.course.endDate)" +
    "AND sh.course = :coP")
    List<AbsenceRecordCoach> checkCoachAbsence(@Param("coP")Course course);


    @Query("SELECT c FROM Course c " +
    "WHERE GETDATE() < c.endDate  AND " +
            "c.capacity > (SELECT COUNT ( DISTINCT sc) FROM SaleCourse sc WHERE sc.course = c ) AND " +
    "c IN (SELECT sh.course FROM Schedule sh)")
    List<Course> findAllAvailableCourses();


    @Query("SELECT s FROM Schedule s WHERE s.course = :coP")
    List<Schedule> getAllDaysOfWeekForCourse(@Param("coP")Course course);


    @Query("SELECT c, ar FROM Course c " +
    "JOIN Schedule sh on sh.course = c " +
    "JOIN Coach co on co = sh.coach " +
    "JOIN AbsenceRecordCoach ar ON ar.coach = co " +
    "JOIN SaleCourse sc ON sc.course = c WHERE (GETDATE() BETWEEN ar.absenceDateBegin AND ar.absenceDateEnd) AND sc.client = :clP ")
    List<Object[]> getDeclinedCourses(@Param("clP") Client client);

}
