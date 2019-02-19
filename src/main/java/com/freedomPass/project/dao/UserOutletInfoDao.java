package com.freedomPass.project.dao;

import com.freedomPass.project.model.UserOutletInfo;
import java.util.List;

public interface UserOutletInfoDao {

    List<UserOutletInfo> getUserOutletInfos();

    List<UserOutletInfo> getUserOutletInfosByCategory(Long id);

    UserOutletInfo getUserOutletInfo(Long id);
}
