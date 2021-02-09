package com.dto;

public class CommentDto {

    private String review;

    public CommentDto() {
        super();
    }

    public CommentDto(String review) {
        super();
        this.review = review;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }
    
    
}
