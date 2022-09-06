package com.cerf.util;

import com.cerf.service.RestCallService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

public class ApplicationUtility {
    private static final Logger logger = LoggerFactory.getLogger(ApplicationUtility.class);
    @Autowired
    static
    RestCallService restCallService;
    public static  JSONArray parseVendorData(){
        JSONArray vendorArr =null;
        try {
            String vendorJsonString = restCallService.getVendorsData();
            if (vendorJsonString != null) {
                vendorArr = new JSONArray();
                JSONObject vendorObj = new JSONObject(vendorJsonString);
                vendorArr = vendorObj.getJSONArray("data");
            }
        }catch (Exception e){
            logger.error("Exception occurred to parsing the data ",e);
        }
        return vendorArr;
    }
}
