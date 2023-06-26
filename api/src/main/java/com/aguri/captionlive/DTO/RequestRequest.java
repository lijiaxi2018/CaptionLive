package com.aguri.captionlive.DTO;

import com.aguri.captionlive.model.Request;
import lombok.Data;

@Data
public class RequestRequest {

    private Request.Type type;

    private int status;

    private Long sender;

    private Long recipient;

    private boolean senderRead;

    private boolean recipientRead;

    private String body;

}
