package com.mall.choisinsa.util.regex;

public class MemberRegEx {

    public static boolean isAvailableLoginId(String loginId) {
        return loginId.matches("^([a-zA-Z0-9]*)$");
    }

    public static boolean isAvailablePassword(String password) {
        return password.matches("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[~!@#$%^&*()+|=])[A-Za-z\\d~!@#$%^&*()+|=]{6,20}$");
    }

}
