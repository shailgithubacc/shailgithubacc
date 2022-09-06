package com.cerf.service.filter.vendor.impl;

import com.cerf.dto.RequestDTO;
import com.cerf.dto.VendorDTO;
import com.cerf.service.filter.vendor.VendorFilterService;

import java.util.List;

public class SMSVendorServiceImpl implements VendorFilterService {

    @Override
    public List<VendorDTO> getVendors(RequestDTO requestDTO) {
        // TODO: 24-08-2022 check msg is otp or non-otp and international or national then
        //  get the vendors as accordingly and return the vendor list
        return null;
    }
}
