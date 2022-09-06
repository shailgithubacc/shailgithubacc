package com.cerf.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class VendorDTO {
    private String vendorId;
    private String vendorName;
    private boolean haveSecondaryURL;
}
