package com.freedomPass.project.dao;

import com.freedomPass.project.helpermodel.AdminPassesPagination;
import com.freedomPass.project.model.AdminPasses;
import java.util.List;

public interface AdminPassesDao {

    List<AdminPasses> getAdminPasses();

    AdminPassesPagination getAdminPassesPagination(int pageNumber, int maxRes);

    AdminPasses getAdminPasse(Long id);

    void addAdminPasse(AdminPasses adminPasse);
}
