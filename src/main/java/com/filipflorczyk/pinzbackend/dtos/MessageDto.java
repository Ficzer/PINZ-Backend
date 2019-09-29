package com.filipflorczyk.pinzbackend.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.ResourceSupport;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class MessageDto extends ResourceSupport {

    @NotBlank(message = "Message content cannot be blank")
    @Size(max = 10000, message = "Maximum size of message is 10000 letters")
    private String content;

    @PastOrPresent(message = "Message creation time must be past or present")
    private LocalDateTime creationDateTime;

    @NotNull(message = "Receiver player cannot be null")
    private PlayerDto receiverPlayer;

    private PlayerDto senderPlayer;
}
