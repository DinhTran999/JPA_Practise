package com.axonactive.jpa.helper;

import java.util.ResourceBundle;

public class Helper {
    private Helper(){

    }

    public static ResourceBundle getResourceBundle(String fileName) throws IllegalArgumentException {
        try {
            return ResourceBundle.getBundle(fileName);
        } catch (Exception e) {
            throw new IllegalArgumentException("Could not find: " + fileName);
        }
    }
}
