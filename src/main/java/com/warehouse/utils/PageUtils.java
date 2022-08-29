package com.warehouse.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class PageUtils {
    public static <T> List<T> getPageList(List<T> list,Integer page,Integer limit){
        int start = (page-1)*limit;
        int end = page*limit;
        if (end>list.size()){
            end=list.size();
        }
        List<T> newList = new ArrayList<>();
        for (int i = start; i < end; i++) {
            newList.add(list.get(i));
        }
        return newList;
    }
}
