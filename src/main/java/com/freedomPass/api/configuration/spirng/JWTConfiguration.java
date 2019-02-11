package com.freedomPass.api.configuration.spirng;

import com.freedomPass.api.commons.ContextHolder;
import javax.annotation.PostConstruct;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JWTConfiguration {

    @Autowired
    private ContextHolder context;

    private long expirytime;
    private String secretkey;
    private String tokenprefix;
    private String tokenHeader;
    private String refreshTokenHeader;

    @PostConstruct
    public void init() {
        if (!FileUtils.getFile((context.getCatalina().getCatalinaWorkInstanceDir()) + "/config/application.properties").exists()) {
            expirytime = Long.parseLong("43200000");
            secretkey = "ThisIsASecret";
            tokenprefix = "Bearer";
            tokenHeader = "Token";
            refreshTokenHeader = "RefreshToken";
        } else {
            expirytime = Long.parseLong(context.getEnvironment().getProperty("jwt.expirytime"));
            secretkey = context.getEnvironment().getProperty("jwt.secretkey");
            tokenprefix = context.getEnvironment().getProperty("jwt.tokenprefix");
            tokenHeader = context.getEnvironment().getProperty("jwt.tokenheader");
            refreshTokenHeader = context.getEnvironment().getProperty("jwt.refreshtokenheader");
        }
    }

    public long getExpirytime() {
        return expirytime;
    }

    public void setExpirytime(long expirytime) {
        this.expirytime = expirytime;
    }

    public String getSecretkey() {
        return secretkey;
    }

    public void setSecretkey(String secretkey) {
        this.secretkey = secretkey;
    }

    public String getTokenprefix() {
        return tokenprefix;
    }

    public void setTokenprefix(String tokenprefix) {
        this.tokenprefix = tokenprefix;
    }

    public String getTokenHeader() {
        return tokenHeader;
    }

    public void setTokenHeader(String tokenHeader) {
        this.tokenHeader = tokenHeader;
    }

    public String getRefreshTokenHeader() {
        return refreshTokenHeader;
    }

    public void setRefreshTokenHeader(String refreshTokenHeader) {
        this.refreshTokenHeader = refreshTokenHeader;
    }
}
