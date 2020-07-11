package com.filipflorczyk.pinzbackend.dtos.MessageDto;

import com.filipflorczyk.pinzbackend.dtos.IdentificationDto;
import com.filipflorczyk.pinzbackend.dtos.PlayerDtos.PlayerDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class NewMessageDto {

    @NotBlank(message = "Message content cannot be blank")
    @Size(max = 10000, message = "Maximum size of message is 10000 letters")
    private String content;

    @NotNull(message = "Receiver player cannot be null")
    private IdentificationDto receiverPlayer;
}
