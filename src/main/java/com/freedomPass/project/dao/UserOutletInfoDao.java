package com.freedomPass.project.dao;

import com.freedomPass.project.model.UserOutletInfo;
import java.util.List;

public interface UserOutletInfoDao {

    List<UserOutletInfo> getUserOutletInfos();

    UserOutletInfo getUserOutletInfo(Long id);
}
