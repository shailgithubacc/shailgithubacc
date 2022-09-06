package com.cerf.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RequestDTO {
    private String channel;
    private String message;
    private String from;
    private String to;
    private String subject;
    private boolean isUnicode;
    private String messageId;
    private boolean intFlag; //false-domestic true-International
    private boolean isOtp;
    private long requestReceivedTime;//Epoch time
}
