package ua.karazin.PerfectFit.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.karazin.PerfectFit.repository.AdminRepository;

import ua.karazin.PerfectFit.entity.Admin;
import java.util.List;
import java.util.Optional;


@Service
public class AdminService {
    private final AdminRepository adminRepository;

    @Autowired
    public AdminService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    public List<Admin> getAllAdmins() {return adminRepository.findAll();}

    public Admin getAdminById(Long id) {
        Optional<Admin> optionalAdmin = adminRepository.findById(id);
        return optionalAdmin.orElse(null);
    }

    public Admin createAdmin(Admin admin) { return adminRepository.save(admin);}

    public Admin updateAdmin(Admin admin) {
        Optional<Admin> optionalAdmin = adminRepository.findById(Long.valueOf(admin.getId()));
        if(optionalAdmin.isPresent()) {
            Admin existingAdmin = optionalAdmin.get();
            existingAdmin.setFio(admin.getFio());
            existingAdmin.setPassword(admin.getPassword());
            return adminRepository.save(existingAdmin);
        }
        return null;
    }

    public void deleteAdmin(Long id) {adminRepository.deleteById(id);}

}

