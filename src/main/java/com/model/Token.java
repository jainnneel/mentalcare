package com.model;


import java.util.Date;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Token {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String token;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date doc;
	
	@OneToOne
    private	Users u; 
	
	public Token() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Token(Users u) {
		super();
		this.u = u;
		this.token = UUID.randomUUID().toString();
		this.doc = new Date();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Date getDoc() {
		return doc;
	}

	public void setDoc(Date doc) {
		this.doc = doc;
	}

	public Users getU() {
		return u;
	}

	public void setU(Users u) {
		this.u = u;
	}
	
	
}
