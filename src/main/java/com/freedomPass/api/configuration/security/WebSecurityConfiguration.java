package com.freedomPass.api.configuration.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    @Qualifier("customUserDetailsService")
    UserDetailsService userDetailsService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private FailedAuthenticationEntryPoint unauthorizedHandler;

    @Autowired
    private AccessDeniedHandler accessDeniedHandler;

    @Autowired
    LogoutHandler logoutHandler;

    @Autowired
    public void configureGlobalSecurity(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder);
    }

    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public LoginFilter loginFilterBean() throws Exception {
        return new LoginFilter("/login");
    }

    @Bean
    public RequestAuthenticationFilter requestAuthenticationFilterBean() {
        return new RequestAuthenticationFilter();
    }

    @Bean
    public AllowCustomHeadersFilter allowCustomHeadersBean() {
        return new AllowCustomHeadersFilter();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // Disable CSRF protection since tokens are immune to it
                .csrf().disable()
                // If the user is not authenticated, returns 401
                .exceptionHandling()
                .authenticationEntryPoint(unauthorizedHandler)
                .accessDeniedHandler(accessDeniedHandler)
                .and()
                .logout().addLogoutHandler(logoutHandler).logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .and()
                // This is a stateless application, disable sessions
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                // Security policy
                .authorizeRequests()
                .antMatchers("/dashboard/**").hasRole("VIEW_DASHBOARD")
                .antMatchers("/reports").hasAnyRole("VIEW_REPORTS", "INSTALLER", "VIEW_GROUPS", "ADD_GROUPS", "EDIT_GROUPS", "DELETE_GROUPS", "VIEW_USERS", "ADD_USERS", "EDIT_USERS", "DELETE_USERS")
                .antMatchers("/reports/add").hasRole("INSTALLER")
                .antMatchers("/reports/edit").hasRole("INSTALLER")
                .antMatchers("/reports/delete/{id}").hasRole("INSTALLER")
                .antMatchers("/Settings/getAllSettings").hasAnyRole("VIEW_SETTINGS", "ADD_SETTINGS", "EDIT_SETTINGS", "DELETE_SETTINGS")
                .antMatchers("/Settings/getSettings/**").hasAnyRole("VIEW_SETTINGS", "ADD_SETTINGS", "EDIT_SETTINGS", "DELETE_SETTINGS")
                .antMatchers("/SettingsMapping/**").hasAnyRole("VIEW_SETTINGS", "ADD_SETTINGS", "EDIT_SETTINGS", "DELETE_SETTINGS")
                .antMatchers("/Settings/addSetting").hasRole("ADD_SETTINGS")
                .antMatchers("/Settings/addSubSetting").hasRole("ADD_SETTINGS")
                .antMatchers("/Settings/editSetting").hasRole("EDIT_SETTINGS")
                .antMatchers("/Settings/editSubSetting").hasRole("EDIT_SETTINGS")
                .antMatchers("/Settings/deleteSubSetting").hasRole("DELETE_SETTINGS")
                .antMatchers("/groups", "/groups/{id}", "/groups/view").hasAnyRole("VIEW_GROUPS", "ADD_GROUPS", "EDIT_GROUPS", "DELETE_GROUPS", "VIEW_USERS", "ADD_USERS", "EDIT_USERS", "DELETE_USERS")
                .antMatchers("/groups/add").hasRole("ADD_GROUPS")
                .antMatchers("/groups/update").hasRole("EDIT_GROUPS")
                .antMatchers("/groups/delete", "/groups/deleteSelection").hasRole("DELETE_GROUPS")
                .antMatchers("/roles/**").hasAnyRole("VIEW_GROUPS", "ADD_GROUPS", "EDIT_GROUPS", "DELETE_GROUPS")
                .antMatchers("/blacklists").hasAnyRole("VIEW_BLACKLISTS", "ADD_BLACKLISTS", "DELETE_BLACKLISTS")
                .antMatchers("/blacklists/add").hasRole("ADD_BLACKLISTS")
                .antMatchers("/blacklists/upload").hasRole("ADD_BLACKLISTS")
                .antMatchers("/blacklists/delete/**").hasRole("DELETE_BLACKLISTS")
                // NEW ROLES BASED ON FREEDOM PASS APP
                .antMatchers("/users", "/users/{pageNumber}/{maxResult}", "/users/{id}", "/users/view").hasAnyRole("SYSTEM", "COMPANY", "OUR_SYSTEM_USER", "VIEW_USERS", "ADD_USERS", "EDIT_USERS", "DELETE_USERS", "VIEW_GROUPS")
                .antMatchers("/users/add").hasAnyRole("SYSTEM", "COMPANY", "OUR_SYSTEM_USER", "ADD_USERS")
                .antMatchers("/users/update").hasAnyRole("SYSTEM", "OUR_SYSTEM_USER", "EDIT_USERS")
                .antMatchers("/users/delete").hasAnyRole("SYSTEM", "OUR_SYSTEM_USER", "DELETE_USERS")
                .antMatchers("/userOutletOffer/add").hasAnyRole("SYSTEM")
                // Allow anonymous access to "/" path
                .antMatchers("/**").permitAll()
                .and()
                .addFilterBefore(allowCustomHeadersBean(), UsernamePasswordAuthenticationFilter.class)
                // Custom filter for logging in users at "/login"
                .addFilterBefore(loginFilterBean(), UsernamePasswordAuthenticationFilter.class)
                // Custom filter for authenticating users using tokens
                .addFilterBefore(requestAuthenticationFilterBean(), UsernamePasswordAuthenticationFilter.class)
                // Disable resource caching
                .headers().cacheControl();
    }
}
