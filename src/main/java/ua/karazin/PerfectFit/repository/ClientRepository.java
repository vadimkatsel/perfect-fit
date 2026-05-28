package ua.karazin.PerfectFit.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ua.karazin.PerfectFit.entity.Client;
import ua.karazin.PerfectFit.entity.SaleSubscription;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    public Client findClientByFio(String fio);
    public Client findClientByMail(String mail);

    @Modifying
    @Transactional
    @Query("DELETE FROM SaleCourse sc WHERE sc.course.id = :id")
    void deleteCourse(@Param("id") Long id);



    @Modifying
    @Transactional
    @Query("DELETE FROM SaleSubscription ss WHERE ss.client = :clP")
    public void deleteSubscriptionForClient(@Param("clP") Client client);


    @Modifying
    @Transactional
    @Query("DELETE FROM SaleCourse sc WHERE sc.client = :clP")
    void deleteAllCoursesForClient(@Param("clP") Client client);




}
