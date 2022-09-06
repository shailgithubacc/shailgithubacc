package com.cerf.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class NotificationRequestDTO {

    private String channel;
    private String message;
    private String from;
    private String to;
    private String subject;
    private boolean isUnicode;
    private String messageId;
    private String vendorId;
    //private String templateId;
    private int retryWith; //1-Primary URL 2-Secondary URL

}
