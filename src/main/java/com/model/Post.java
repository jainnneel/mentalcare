package com.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
public class Post {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@NotEmpty(message = "must fill *")
	private String title;

	
	@Size(min=15,message = "Minimum 15 char requied")
	@Column(length=5000)
	private String content;
	
	private Date d;
	
	@ManyToOne
	private Users user;

	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="post")
	private List<Comment> comment = new ArrayList<>();
	
	public Post() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Post(int id, @NotEmpty String title,
			@NotEmpty String content, Date d, Users user, List<Comment> comment) {
		super();
		this.id = id;
		this.title = title;
		this.content = content;
		this.d = d;
		this.user = user;
		this.comment = comment;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public Date getD() {
		return d;
	}

	public void setD(Date d) {
		this.d = d;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	public List<Comment> getComment() {
		return comment;
	}

	public void setComment(List<Comment> comment) {
		this.comment = comment;
	}

	@Override
	public String toString() {
		return "Post [id=" + id + ", title=" + title + ", content=" + content + ", d=" + d + ", user=" + user
				+ ", comment=" + comment + "]";
	}

	
	

}
