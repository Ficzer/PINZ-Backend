package com.filipflorczyk.pinzbackend.dtos;

import com.filipflorczyk.pinzbackend.entities.Club;
import com.filipflorczyk.pinzbackend.entities.Player;
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
public class ClubMessageDto extends ResourceSupport {

    @NotBlank(message = "Message content cannot be blank")
    @Size(max = 10000, message = "Maximum size of message is 10000 letters")
    private String content; //TODO check message behavior when you pass " sign or other strange letters

    @PastOrPresent(message = "Club message creation time must be past or present")
    private LocalDateTime creationDateTime;

    private PlayerDto senderPlayer;

    @NotNull(message = "Club cannot be null")
    private ClubDto club;
}
