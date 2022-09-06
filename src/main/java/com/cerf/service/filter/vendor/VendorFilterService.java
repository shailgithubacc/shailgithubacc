package com.cerf.service.filter.vendor;

import com.cerf.dto.RequestDTO;
import com.cerf.dto.VendorDTO;

import java.util.List;

public interface VendorFilterService {

    List<VendorDTO> getVendors(RequestDTO requestDTO);
}
