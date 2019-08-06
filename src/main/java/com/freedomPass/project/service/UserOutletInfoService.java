package com.freedomPass.project.service;

import com.freedomPass.project.model.UserOutletInfo;
import java.util.List;

public interface UserOutletInfoService {

    List<UserOutletInfo> getUserOutletInfos();

    UserOutletInfo getUserOutletInfo(Long id);
    
    UserOutletInfo getUserOutletInfoByPin(String outletPin);

}
