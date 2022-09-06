package com.cerf.cache;

import com.cerf.dto.VendorDTO;
import com.cerf.util.ApplicationUtility;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class VendorCache {
    private final Logger logger = LoggerFactory.getLogger(VendorCache.class);


    private static ConcurrentHashMap<Integer, VendorDTO> vendorRepo= new ConcurrentHashMap<>();

    private static List<VendorDTO> otpDomesticVendorCache = new ArrayList<>();
    private static List<VendorDTO> otpInternationalVendorCache = new ArrayList<>();
    private static List<VendorDTO> nonOtpDomesticVendorCache = new ArrayList<>();
    private static List<VendorDTO> nonOtpInternationalVendorCache = new ArrayList<>();

    private static List<VendorDTO> emailVendorCache = new ArrayList<>();
    private static List<VendorDTO> flashMsgVendorCache = new ArrayList<>();
    private static List<VendorDTO> voiceVendorCache = new ArrayList<>();

    private VendorCache(){

    }

    private static void createVendorCache() {
        Map<Integer, VendorDTO> tempVendorCache = new HashMap<>();
        JSONArray vendorArrObj = ApplicationUtility.parseVendorData();
        clearCache(vendorArrObj);
        if (vendorArrObj != null) {
            vendorArrObj.forEach(vendor -> {
                JSONObject vendorRow = (JSONObject) vendor;
                String vendorId = Optional.ofNullable(vendorRow.getString("vendorId")).orElse(null);
                String vendorName = Optional.ofNullable(vendorRow.getString("vendorName")).orElse(null);
                String intFlag = Optional.ofNullable(vendorRow.getString("intFlag")).orElse(null);
                String otpFlag = Optional.ofNullable(vendorRow.getString("otpFlag")).orElse(null);
                String channel = Optional.ofNullable(vendorRow.getString("channel")).orElse(null);

            });
        }
    }
    private static void clearCache(JSONArray vendorArr){
        if (vendorArr.length() > 0){
            otpDomesticVendorCache.clear();
            otpInternationalVendorCache.clear();
            nonOtpDomesticVendorCache.clear();
            nonOtpInternationalVendorCache.clear();
            emailVendorCache.clear();
            flashMsgVendorCache.clear();
            voiceVendorCache.clear();
        }
    }
}
