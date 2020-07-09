package com.filipflorczyk.pinzbackend.dtos.PostDtos;

import com.filipflorczyk.pinzbackend.dtos.IdentificationDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class NewPostDto{

    @NotBlank(message = "Post content cannot be blank")
    @Size(max = 10000, message = "Maximum size of post is 10000 letters")
    private String content;

    private IdentificationDto clubId;
}
