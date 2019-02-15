package com.freedomPass.project.service;

import com.freedomPass.project.model.UserCompanyPasses;
import java.util.List;

public interface UserCompanyPassesService {

    List<UserCompanyPasses> getUserCompanyPasses();

    UserCompanyPasses getUserCompanyPasse(Long id);

}
