package com.freedomPass.project.dao;

import com.freedomPass.project.model.AdminPasses;
import java.util.List;

public interface AdminPassesDao {

    List<AdminPasses> getAdminPasses();

    AdminPasses getAdminPasse(Long id);
}
