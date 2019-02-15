package com.freedomPass.project.service;

import com.freedomPass.project.dao.AdminPassesDao;
import com.freedomPass.project.model.AdminPasses;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("adminPassesService")
@Transactional
public class AdminPassesServiceImpl extends AbstractService implements AdminPassesService {

    @Autowired
    AdminPassesDao adminPassesDao;

    @Override
    public List<AdminPasses> getAdminPasses() {
        return adminPassesDao.getAdminPasses();
    }

    @Override
    public AdminPasses getAdminPasse(Long id) {
        return adminPassesDao.getAdminPasse(id);
    }

}
