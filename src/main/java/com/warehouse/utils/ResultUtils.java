package com.warehouse.utils;

import com.warehouse.commons.DataResults;
import com.warehouse.commons.ResultCode;

public class ResultUtils {
    public static DataResults simpleResult(boolean ifSuccess){
        if (ifSuccess){
            return DataResults.success(ResultCode.SUCCESS);
        }else {
            return DataResults.fail(ResultCode.FAIL);
        }
    }


}
