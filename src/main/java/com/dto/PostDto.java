package com.dto;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class PostDto {

    @NotEmpty(message = "must fill *")
    private String title;
    
    @Size(min=15,message = "Minimum 15 char requied")
    @Column(length=5000)
    private String content;

    public PostDto() {
        super();
    }

    public PostDto(String title, String content) {
        super();
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    
    
}
