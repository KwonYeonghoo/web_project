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
public class PostDto {
    private Long postId;
    private String postTitle;
    private String postWriter;
    private Date postDate;
    private String postContent;
    private int postViewNum;

    // 이미지 
    private String postFileName;

    private String postFilePath;

}
