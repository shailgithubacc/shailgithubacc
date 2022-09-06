package com.cerf.service.notification;

import com.cerf.dto.RequestDTO;
import com.cerf.dto.VendorDTO;

import java.util.List;

public interface MsgNotificationService {

    void sendNotification(RequestDTO requestDTO, List<VendorDTO> vendorDTOList);
}
