/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.freedomPass.api.commons;

import org.springframework.stereotype.Component;

@Component
public class CatalinaService {

    private String applicationName;

    public String getCatalinaHome() {
        return System.getProperty("catalina.home");
    }

    public String getCatalinaWorkDir() {
        return getCatalinaHome() + "/work/";
    }

    public String getCatalinaLogDir() {
        return getCatalinaHome() + "/logs/";
    }

    public String getCatalinaWorkInstanceDir() {
        return getCatalinaWorkDir() + applicationName;
    }

    public String getCatalinaLogInstanceDir() {
        return getCatalinaLogDir() + applicationName;
    }

    public String getApplicationNameDeployed() {
        return this.applicationName;
    }

    public void setApplicationNameDeployed(String appName) {
        this.applicationName = appName;
    }
}
