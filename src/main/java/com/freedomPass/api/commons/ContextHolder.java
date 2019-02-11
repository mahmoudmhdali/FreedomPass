/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.freedomPass.api.commons;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class ContextHolder {
    
    @Autowired
    Environment environment;
    
    @Autowired
    CatalinaService catalinaService;
        
    @Autowired
    Logger logger;
    
    
    public String getInstanceName ()
    {
        return System.getProperty("application.name");
    }
    
    public Environment getEnvironment ()
    {
        return environment;
    }
    
    public CatalinaService getCatalina ()
    {
        return catalinaService;
    }
        
    public Logger getLogger ()
    {
        return logger;
    }
}
