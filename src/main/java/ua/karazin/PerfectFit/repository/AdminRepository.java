package ua.karazin.PerfectFit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.karazin.PerfectFit.entity.Admin;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {
    public Admin findAdminByFio(String username);
}
