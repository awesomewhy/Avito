package com.example.avito.dto.otherdto;

import com.example.avito.enums.MessageType;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatMessageDto {

    private MessageType type;
    private String content;
    private String sender;

}
