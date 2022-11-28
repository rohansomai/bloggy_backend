package com.project.bloggy.dto;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class LabelDTO {

    private Long labelId;

    @Length(max = 100)
    @NotBlank
    private String title;

    private String description;

}
