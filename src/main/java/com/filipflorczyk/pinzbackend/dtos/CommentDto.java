package com.filipflorczyk.pinzbackend.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.ResourceSupport;

import javax.validation.constraints.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class CommentDto extends ResourceSupport {

    @NotBlank(message = "Comment content cannot be blank")
    @Size(max = 10000, message = "Maximum size of comment is 10000 letters")
    private String content;

    @PastOrPresent(message = "Comment creation time must be past or present")
    private LocalDateTime creationDateTime;

    @PositiveOrZero(message = "Stars number in comment must be positive or zero")
    private Integer stars;

    @NotNull(message = "Post in comment cannot be null")
    private PostDto post;

    private PlayerDto author;
}
