package com.project.bloggy.dto;

import com.project.bloggy.entity.Label;
import com.project.bloggy.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostDTO {

    private Long postId;

    @NotBlank
    private String title;

    @NotBlank
    private String content;

    private String location;

    private Date addedDate;

    private LabelDTO label;

    private UserDTO user;
}
