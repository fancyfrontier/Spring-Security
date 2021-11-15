package com.example.demo.member;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

@Entity
@Component
@Table(name="Member")
public class Member {
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "MemberID")
	private Integer memberid;
	
	@Column(name = "Username", nullable = false)
	private String username;
	
	@Column(name = "Password", nullable = false)
	private String password;

	@Column(name = "Role", nullable = false)
	private String role;
	
	public Integer getMemberid() {
		return memberid;
	}

	public void setMemberid(Integer memberid) {
		this.memberid = memberid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
	
	
}
