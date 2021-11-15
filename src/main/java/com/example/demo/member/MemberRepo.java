package com.example.demo.member;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepo extends JpaRepository<Member, Integer> {
	
	public Member findByUsername(String username);
	
	public Member findByPassword(Member member);
}
