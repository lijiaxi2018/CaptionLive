package com.aguri.captionlive.DTO;

import lombok.Data;

@Data
public class MessageRequest {

        private Long requestId;

        private Boolean isReply;

        private String Content;

}
