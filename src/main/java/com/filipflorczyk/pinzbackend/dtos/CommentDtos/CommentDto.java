package com.filipflorczyk.pinzbackend.dtos.CommentDtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.filipflorczyk.pinzbackend.dtos.BaseDto;
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
public class CommentDto extends BaseDto {

    @NotBlank(message = "Comment content cannot be blank")
    @Size(max = 10000, message = "Maximum size of comment is 10000 letters")
    private String content;

    @PastOrPresent(message = "Comment creation time must be past or present")
    private LocalDateTime creationDateTime;

    @PositiveOrZero(message = "Stars number in comment must be positive or zero")
    private Integer stars;

    @JsonIgnore
    @NotNull(message = "Post in comment cannot be null")
    private PostDto post;

    private PlayerDto author;
}
