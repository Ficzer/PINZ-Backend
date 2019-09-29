package com.filipflorczyk.pinzbackend.dtos;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.ResourceSupport;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PostDto extends ResourceSupport {

    @NotBlank(message = "Post content cannot be blank")
    @Size(max = 10000, message = "Maximum size of post is 10000 letters")
    private String content;

    @PastOrPresent(message = "Post creation time must be past or present")
    private LocalDateTime creationDateTime;

    @PositiveOrZero(message = "Stars number in post must be positive or zero")
    private Integer stars;

    private ClubDto club;

    private PlayerDto author;

    private List<CommentDto> commentList;
}
