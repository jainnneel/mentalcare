package com.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.sun.istack.Nullable;

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
	
	@OneToMany(mappedBy = "comment")
	@Nullable
	private List<Comment> comments = new ArrayList<>();
	
	@ManyToOne
	@JoinColumn(name="comment_id", nullable=true)
	@JsonIgnore
	@Nullable
	private Comment comment;
	
	
//	private Date d;
	
	public Comment() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Comment(int id, String text, Users user, Post post, List<Comment> comments, Comment comment, Date date) {
		super();
		this.id = id;
		this.review = text;
		this.user = user;
		this.post = post;
		this.comments = comments;
		this.comment = comment;
//		this.d = date;
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
		return comment;
	}

	public void setComment(Comment comment) {
		this.comment = comment;
	}

	/*
	 * @Override public String toString() { return "Comment [id=" + id + ", review="
	 * + review + ", user=" + user + ", post=" + post + ", comments=" + comments +
	 * ", comment=" + comment + "]"; }
	 */

//	public Date getD() {
//		return d;
//	}
//
//	public void setD(Date date) {
//		this.d = date;
//	}

	
	
}
