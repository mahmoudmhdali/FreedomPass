/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.freedomPass.api.configuration.converter;

import com.freedomPass.project.model.Role;
import com.freedomPass.project.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class RoleConverter implements Converter<Object, Role> {

    @Autowired
    RoleService roleService;

    /**
     * Gets Roles by Id
     *
     * @param element
     * @return
     * @see
     * org.springframework.core.convert.converter.Converter#convert(java.lang.Object)
     */
    @Override
    public Role convert(Object element) {
        if (element instanceof String) {
            Long id = Long.parseLong((String) element);
            Role role = roleService.getRole(id);
            return role;
        }
        return (Role) element;
    }
}
