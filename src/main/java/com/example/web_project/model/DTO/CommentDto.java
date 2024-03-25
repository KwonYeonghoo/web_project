package com.example.web_project.model.DTO;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CommentDto {
    private Long commentId;
    private String commentComment;
    private Date commentDate;
    private Long commentPostid;
    private String commentUserid; 

}
