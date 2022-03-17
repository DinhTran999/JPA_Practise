package com.axonactive.jpa.helper;

import java.util.ResourceBundle;

public class AppConfigService {
    private AppConfigService(){

    }
    static ResourceBundle resourceBundle = Helper.getResourceBundle("appConfig");

    public static String getSecretKey(){
        return resourceBundle.getString("secretKey");
    }

    public static String getIssuer(){
        return resourceBundle.getString("issuer");
    }

    public static int getTimeToLive(){
        return Integer.parseInt(resourceBundle.getString("timeToLive"));
    }
}
