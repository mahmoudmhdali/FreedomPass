package com.freedomPass.project.dao;

import com.freedomPass.project.model.UserPassPurchased;
import java.util.List;

public interface UserPassPurchasedDao {

    List<UserPassPurchased> getUserPassPurchaseds();

    UserPassPurchased getUserPassPurchased(Long id);
}
