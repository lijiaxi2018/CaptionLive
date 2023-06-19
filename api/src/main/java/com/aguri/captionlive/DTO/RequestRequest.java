package com.aguri.captionlive.DTO;

import lombok.Data;

@Data
public class RequestRequest {

    private Long type;

    private int status;

    private Long sender;

    private Long recipient;

    private boolean senderRead;

    private boolean recipientRead;

    private String body;

}
