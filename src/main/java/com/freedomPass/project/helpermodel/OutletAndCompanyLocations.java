package com.freedomPass.project.helpermodel;

import com.freedomPass.project.model.UserProfile;
import org.springframework.security.core.context.SecurityContextHolder;

public class OutletAndCompanyLocations {

    private String longitude1;

    private String longitude2;

    private String longitude3;

    private String longitude4;

    private String latitude1;

    private String latitude2;

    private String latitude3;

    private String latitude4;

    public String getLongitude1() {
        return longitude1;
    }

    public void setLongitude1(String longitude1) {
        this.longitude1 = longitude1;
    }

    public String getLongitude2() {
        return longitude2;
    }

    public void setLongitude2(String longitude2) {
        this.longitude2 = longitude2;
    }

    public String getLongitude3() {
        return longitude3;
    }

    public void setLongitude3(String longitude3) {
        this.longitude3 = longitude3;
    }

    public String getLongitude4() {
        return longitude4;
    }

    public void setLongitude4(String longitude4) {
        this.longitude4 = longitude4;
    }

    public String getLatitude1() {
        return latitude1;
    }

    public void setLatitude1(String latitude1) {
        this.latitude1 = latitude1;
    }

    public String getLatitude2() {
        return latitude2;
    }

    public void setLatitude2(String latitude2) {
        this.latitude2 = latitude2;
    }

    public String getLatitude3() {
        return latitude3;
    }

    public void setLatitude3(String latitude3) {
        this.latitude3 = latitude3;
    }

    public String getLatitude4() {
        return latitude4;
    }

    public void setLatitude4(String latitude4) {
        this.latitude4 = latitude4;
    }

}
