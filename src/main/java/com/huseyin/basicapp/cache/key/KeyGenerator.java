package com.huseyin.basicapp.cache.key;



import java.time.LocalDate;

public class KeyGenerator {

    public static String generateKey(String location, LocalDate localDate, String include){
        return "weather:%s:%s:%s".formatted(include,location,localDate);
    }


}
