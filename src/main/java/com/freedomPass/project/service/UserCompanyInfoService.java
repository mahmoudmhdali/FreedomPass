package com.freedomPass.project.service;

import com.freedomPass.project.model.UserCompanyInfo;
import java.util.List;

public interface UserCompanyInfoService {

    List<UserCompanyInfo> getUserCompanyInfos();

    UserCompanyInfo getUserCompanyInfo(Long id);

}
