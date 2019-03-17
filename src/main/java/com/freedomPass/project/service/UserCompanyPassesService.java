package com.freedomPass.project.service;

import com.freedomPass.project.helpermodel.ResponseBodyEntity;
import com.freedomPass.project.helpermodel.UserCompanyPassPagination;
import com.freedomPass.project.model.UserCompanyPasses;
import java.util.List;

public interface UserCompanyPassesService {

    List<UserCompanyPasses> getUserCompanyPasses();

    List<UserCompanyPasses> getUserCompanyPassesByCompanyUserId(Long id);

    UserCompanyPasses getUserCompanyPasse(Long id);

    UserCompanyPassPagination getUserCompanyPassesPagination(int pageNumber, int maxRes);

    ResponseBodyEntity addUserCompanyPasses(UserCompanyPasses userCompanyPasses);

}
