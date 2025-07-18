package com.huseyin.basicapp.exception;

import com.huseyin.basicapp.weather.model.entity.Include;

import java.lang.reflect.Array;
import java.util.Arrays;

public class InvalidIncludeException extends RuntimeException{

    public InvalidIncludeException(String value, Include[] values){
        super("Invalid include value :%s \n Possible opstions are:%s".formatted(value, Arrays.toString(values)));
    }

}
