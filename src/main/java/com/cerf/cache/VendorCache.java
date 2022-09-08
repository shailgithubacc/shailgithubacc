package com.cerf.cache;

import com.cerf.constant.ApplicationConstant;
import com.cerf.dto.VendorDTO;
import com.cerf.util.ApplicationUtility;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class VendorCache {
    private static final Logger logger = LoggerFactory.getLogger(VendorCache.class);
    private ConcurrentHashMap<String, VendorDTO> vendorRepo= new ConcurrentHashMap<>();
    private final List<VendorDTO> otpDomesticVendorCache = new ArrayList<>();
    private final List<VendorDTO> otpInternationalVendorCache = new ArrayList<>();
    private final List<VendorDTO> nonOtpDomesticVendorCache = new ArrayList<>();
    private final List<VendorDTO> nonOtpInternationalVendorCache = new ArrayList<>();
    private final List<VendorDTO> emailVendorCache = new ArrayList<>();
    private final List<VendorDTO> flashMsgVendorCache = new ArrayList<>();
    private final List<VendorDTO> ivrVendorCache = new ArrayList<>();

    private VendorCache(){

    }

    public void createVendorCache() {
        Map<Integer, VendorDTO> tempVendorCache = new HashMap<>();
        JSONArray vendorArrObj = ApplicationUtility.parseVendorData();
        clearCache(vendorArrObj);
        if (vendorArrObj != null) {
            vendorArrObj.forEach(vendor -> {
                JSONObject vendorRow = (JSONObject) vendor;
                String vendorId = Optional.ofNullable(vendorRow.getString("vendorId")).orElse(null);
                String vendorName = Optional.ofNullable(vendorRow.getString("vendorName")).orElse(null);
                String channel = Optional.ofNullable(vendorRow.getString("channel")).orElse(null);
                int intFlag = Optional.ofNullable(vendorRow.getInt("intFlag")).orElse(1);//1 for domestic 2 for international and 3 for both
                int otpFlag = Optional.ofNullable(vendorRow.getInt("otpFlag")).orElse(2); //1 for otp , 2 for non otp
                boolean haveSecondaryURL = Optional.ofNullable(vendorRow.getBoolean("haveSecondaryURL")).orElse(false);
                VendorDTO vendorDTO =new VendorDTO(vendorId,vendorName,haveSecondaryURL,intFlag,otpFlag,channel);
                if(!StringUtils.isBlank(vendorId)) {
                    vendorRepo.put(vendorId, vendorDTO);
                    createVendorRepo(vendorDTO);
                }
            });
        }
    }

    public VendorDTO getVendorById(String vendorId){
        return vendorRepo.get(vendorId);
    }

    public List<VendorDTO> getOTPDomesticVendorCache(){
        return otpDomesticVendorCache;
    }

    public List<VendorDTO> getOtpInternationalVendorCache(){
        return otpInternationalVendorCache;
    }

    public List<VendorDTO> getNonOtpDomesticVendorCache(){
        return nonOtpDomesticVendorCache;
    }

    public List<VendorDTO> getNonOtpInternationalVendorCache(){
        return nonOtpInternationalVendorCache;
    }

    public List<VendorDTO> getEmailVendorCache(){
        return emailVendorCache;
    }

    public List<VendorDTO> getFlashMsgVendorCache(){
        return flashMsgVendorCache;
    }

    public List<VendorDTO> getIvrVendorCache(){
        return ivrVendorCache;
    }
    private void createVendorRepo(VendorDTO vendorDTO){
        switch (vendorDTO.getChannel().toUpperCase()) {
            case ApplicationConstant.SMS_CHANNEL :

                break;
            case ApplicationConstant.EMAIL_CHANNEL :
                createEmailRepo(vendorDTO);
                break;
            case ApplicationConstant.IVR_CHANNEL :
                createIVRRepo(vendorDTO);
                break;
            case ApplicationConstant.FLASH_CHANNEL :
                createFlashMsgRepo(vendorDTO);
                break;
            default:
                logger.info("createVendorRepo Invalid channel type received {} ",vendorDTO.getChannel());
                break;
        }
    }

    private void createSMSRepo(VendorDTO vendorDTO){
        if(vendorDTO.getOtpFlag() == 1 && (vendorDTO.getIntFlag() == 1 || vendorDTO.getIntFlag() == 3)){
            otpDomesticVendorCache.add(vendorDTO);
        } else if (vendorDTO.getOtpFlag() == 1 && (vendorDTO.getIntFlag() == 2 || vendorDTO.getIntFlag() == 3)) {
            otpInternationalVendorCache.add(vendorDTO);
        } else if (vendorDTO.getOtpFlag() == 2 && (vendorDTO.getIntFlag() == 1 || vendorDTO.getIntFlag() == 3)) {
            nonOtpDomesticVendorCache.add(vendorDTO);
        } else if (vendorDTO.getOtpFlag() == 2 && (vendorDTO.getIntFlag() == 2 || vendorDTO.getIntFlag() == 3)) {
            nonOtpInternationalVendorCache.add(vendorDTO);
        }
    }
    private void createEmailRepo(VendorDTO vendorDTO){
        if(!StringUtils.isBlank(vendorDTO.getVendorId())) {
            emailVendorCache.add(vendorDTO);
        }
    }

    private void createIVRRepo(VendorDTO vendorDTO){
        if(!StringUtils.isBlank(vendorDTO.getVendorId())) {
            ivrVendorCache.add(vendorDTO);
        }
    }

    private void createFlashMsgRepo(VendorDTO vendorDTO){
        if(!StringUtils.isBlank(vendorDTO.getVendorId())) {
            flashMsgVendorCache.add(vendorDTO);
        }
    }
    private void clearCache(JSONArray vendorArr){
        if (vendorArr.length() > 0){
            vendorRepo.clear();
            otpDomesticVendorCache.clear();
            otpInternationalVendorCache.clear();
            nonOtpDomesticVendorCache.clear();
            nonOtpInternationalVendorCache.clear();
            emailVendorCache.clear();
            flashMsgVendorCache.clear();
            ivrVendorCache.clear();
        }
    }
}
