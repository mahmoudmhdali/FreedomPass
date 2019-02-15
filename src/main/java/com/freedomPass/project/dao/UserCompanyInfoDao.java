package com.freedomPass.project.dao;

import com.freedomPass.project.model.UserCompanyInfo;
import java.util.List;

public interface UserCompanyInfoDao {

    List<UserCompanyInfo> getUserCompanyInfos();

    UserCompanyInfo getUserCompanyInfo(Long id);
}
