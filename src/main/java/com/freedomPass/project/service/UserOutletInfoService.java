package com.freedomPass.project.service;

import com.freedomPass.project.model.UserOutletInfo;
import java.util.List;

public interface UserOutletInfoService {

    List<UserOutletInfo> getUserOutletInfos();

    List<UserOutletInfo> getUserOutletInfosByCategory(Long id);

    UserOutletInfo getUserOutletInfo(Long id);

}
