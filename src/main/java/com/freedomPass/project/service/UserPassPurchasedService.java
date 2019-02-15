package com.freedomPass.project.service;

import com.freedomPass.project.model.UserPassPurchased;
import java.util.List;

public interface UserPassPurchasedService {

    List<UserPassPurchased> getUserPassPurchaseds();

    UserPassPurchased getUserPassPurchased(Long id);

}
