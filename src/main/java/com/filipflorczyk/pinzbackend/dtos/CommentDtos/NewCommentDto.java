package com.filipflorczyk.pinzbackend.dtos.CommentDtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.filipflorczyk.pinzbackend.dtos.BaseDto;
import com.filipflorczyk.pinzbackend.dtos.IdentificationDto;
import com.filipflorczyk.pinzbackend.dtos.PlayerDtos.PlayerDto;
import com.filipflorczyk.pinzbackend.dtos.PostDtos.PostDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class NewCommentDto{

    @NotBlank(message = "Comment content cannot be blank")
    @Size(max = 10000, message = "Maximum size of comment is 10000 letters")
    private String content;

    private IdentificationDto clubId;

    private IdentificationDto postId;
}
