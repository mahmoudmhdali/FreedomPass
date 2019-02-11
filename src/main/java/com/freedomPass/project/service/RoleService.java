package com.freedomPass.project.service;

import com.freedomPass.project.model.Role;
import java.util.List;

public interface RoleService {

    List<Role> getRoles();

    Role getRole(Long id);

    Role getRole(String name);
}
