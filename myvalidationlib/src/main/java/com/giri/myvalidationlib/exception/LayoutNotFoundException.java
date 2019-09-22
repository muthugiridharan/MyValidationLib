package com.giri.myvalidationlib.exception;

/**
 * Created by Giri
 * Throws when invalid layout
 */
public class LayoutNotFoundException extends RuntimeException {

    /**
     * @param error message
     */
    public LayoutNotFoundException(String error){
        super(error);
    }
}
