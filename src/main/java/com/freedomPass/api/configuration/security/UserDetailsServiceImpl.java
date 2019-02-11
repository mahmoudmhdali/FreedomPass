package com.freedomPass.api.configuration.security;

import com.freedomPass.project.model.Role;
import com.freedomPass.project.model.UserProfile;
import com.freedomPass.project.service.UserService;
import java.util.ArrayList;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("customUserDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Transactional(readOnly = true)
    @Override
    public UserProfile loadUserByUsername(String email) throws UsernameNotFoundException {
        UserProfile user = userService.toUser(email);
        if (user == null) {
            throw new BadCredentialsException("Username not found");
        }

        if (user.getDeletedDate() != null) {
            throw new UsernameNotFoundException("Your account is no more available");
        }

        Collection<Role> roleCollection = new ArrayList<>();
        user.getGroupCollection().stream().forEach((group) -> {
            group.getRoleCollection().stream().forEach((role) -> {
                roleCollection.add(role);
            });
        });
        user.setUserAuthorities(roleCollection);
        return user;
    }

}
