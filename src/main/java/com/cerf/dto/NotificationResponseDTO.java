package com.cerf.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class NotificationResponseDTO {

    private boolean success;
    private String responseId;
    private String errorCode;
    private String errorMessage;
}
