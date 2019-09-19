package com.giri.myvalidationlib.validator;

class Constants {

    //Valid only when one upper case and one lowercase and length of 8 characters
    static final String B_PASSWORD = "(?=.*?[A-Z])(?=.*?[a-z]).{8,}$";

    //Valid only when one upper case,lower case and one number of length of 8 character
    static final String M_PASSWORD = "(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9]).{8,}$";

    //Valid only when one upper,lower case,one number and spl character of length of 8 character
    static final String H_PASSWORD = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$";

    //String contains a number
    static final String CONTAIN_NUMBER = "(?=.*?[0-9]).*";

    //String contains a symbol
    static final String CONTAIN_SYMBOL = "(?=.*?[#?!@$%^&*-]).*";
}
