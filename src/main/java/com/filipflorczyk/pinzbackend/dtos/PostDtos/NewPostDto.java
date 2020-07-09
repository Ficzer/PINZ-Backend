package com.filipflorczyk.pinzbackend.dtos.PostDtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.filipflorczyk.pinzbackend.dtos.BaseDto;
import com.filipflorczyk.pinzbackend.dtos.ClubDtos.ClubDto;
import com.filipflorczyk.pinzbackend.dtos.CommentDto;
import com.filipflorczyk.pinzbackend.dtos.IdentificationDto;
import com.filipflorczyk.pinzbackend.dtos.PlayerDtos.PlayerDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class NewPostDto{

    @NotBlank(message = "Post content cannot be blank")
    @Size(max = 10000, message = "Maximum size of post is 10000 letters")
    private String content;

    private IdentificationDto clubId;
}
