package com.huseyin.basicapp.util;

import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class LocalDateListCreator {


    public static List<LocalDate> getDateList(
            LocalDate startDate,
            LocalDate endDate
    ){
        List<LocalDate> localDateList = new ArrayList<>();
        for(; !startDate.isAfter(endDate); startDate = startDate.plusDays(1)){
            localDateList.add(startDate);
        }
        return localDateList;

    }


}
