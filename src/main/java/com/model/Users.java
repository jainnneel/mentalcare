package com.model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name = "users")
public class Users {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Email
	@Size(min = 3, message = "Enter valid email")
	private String email;

	@NotEmpty
	@Size(min = 3, max = 20, message = "Password must be between 3 to 12")
	private String pass;

	private Date date;

	private String role = "USER";

	private boolean isEnable;

	public Users() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public boolean isEnable() {
		return isEnable;
	}

	public void setEnable(boolean isEnable) {
		this.isEnable = isEnable;
	}

	public Users(int id, @NotEmpty @Size(min = 3, max = 20, message = "Email must be between 3 to 12") String email,
			@NotEmpty @Size(min = 3, max = 20, message = "Password must be between 3 to 12") String pass, Date date,
			String role, boolean isEnable) {
		super();
		this.id = id;
		this.email = email;
		this.pass = pass;
		this.date = date;
		this.role = role;
		this.isEnable = isEnable;
	}

	@Override
	public String toString() {
		return "Users [id=" + id + ", email=" + email + ", pass=" + pass + ", date=" + date + ", role=" + role
				+ ", isEnable=" + isEnable + "]";
	}

	public List<GrantedAuthority> getAuth() {
		return new ArrayList<GrantedAuthority>();
	}

}
