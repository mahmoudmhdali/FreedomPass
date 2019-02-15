package com.freedomPass.project.service;

import com.freedomPass.project.model.AdminPasses;
import java.util.List;

public interface AdminPassesService {

    List<AdminPasses> getAdminPasses();

    AdminPasses getAdminPasse(Long id);

}
