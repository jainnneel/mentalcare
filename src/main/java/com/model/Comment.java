package com.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.springframework.lang.Nullable;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@id")
public class Comment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String review;
	
	@ManyToOne
	@JsonIgnore
	private Users user;
	
	@ManyToOne
	@JsonIgnore
	private Post post;
	
	@OneToMany(mappedBy = "parentComment")
	@Nullable
	private List<Comment> comments = new ArrayList<>();
	
	@ManyToOne
	@JoinColumn(name="comment_id", nullable=true)
	@JsonIgnore
	@Nullable
	private Comment parentComment;
	
	public Comment() {
		super();
	}

	public Comment(int id, String text, Users user, Post post, List<Comment> comments, Comment comment) {
		super();
		this.id = id;
		this.review = text;
		this.user = user;
		this.post = post;
		this.comments = comments;
		this.parentComment = comment;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getReview() {
		return review;
	}

	public void setReview(String text) {
		this.review = text;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public Comment getComment() {
		return parentComment;
	}

	public void setComment(Comment comment) {
		this.parentComment = comment;
	}

}
