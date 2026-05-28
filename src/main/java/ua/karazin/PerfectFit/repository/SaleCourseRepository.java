package ua.karazin.PerfectFit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;
import ua.karazin.PerfectFit.entity.Client;
import ua.karazin.PerfectFit.entity.Course;
import ua.karazin.PerfectFit.entity.SaleCourse;

@Repository
public interface SaleCourseRepository extends JpaRepository<SaleCourse, Long> {


    @Query("SELECT COUNT(DISTINCT sc) FROM SaleCourse sc WHERE sc.client = :clP")
    Long getCountOfCoursesForClient(@Param("clP")Client client);


    @Query("SELECT COUNT(DISTINCT sc) FROM SaleCourse sc WHERE sc.client = :clP AND sc.course = :coP")
    Long checkIsVisiting(@Param("clP")Client client, @Param("coP")Course course);

    @Query("SELECT COUNT(DISTINCT sc) FROM SaleCourse sc WHERE sc.client = :coP")
    Long countNumberOfVisitors(@Param("coP")Course course);



}
