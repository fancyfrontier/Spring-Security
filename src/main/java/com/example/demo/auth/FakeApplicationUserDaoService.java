package com.example.demo.auth;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import com.example.demo.member.Member;
import com.example.demo.member.MemberRepo;
import com.google.common.collect.Lists;
import static com.example.demo.security.ApplicationUserRole.*;

@Repository("fake")
public class FakeApplicationUserDaoService implements ApplicationUserDao{

	private final PasswordEncoder passwordEncoder;
	
	@Autowired
	Member member;

	@Autowired
	MemberRepo memberRepo;
	
	@Autowired
	public FakeApplicationUserDaoService(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public Optional<ApplicationUser> selectApplicationUserByUsername(String username) {
		return getApplicationUsers(username)
				.stream()
				.filter(applicationUser -> username.equals(applicationUser.getUsername()))
				.findFirst();
	}
	
	
	private List<ApplicationUser> getApplicationUsers(String username){
		
		Member member = memberRepo.findByUsername(username);
		
		if(member.getRole().equals("ADMIN")) {
			List<ApplicationUser> applicationUsers = Lists.newArrayList(
					new ApplicationUser(
							ADMIN.getGrantedAuthorities(),
							passwordEncoder.encode(member.getPassword()),
							member.getUsername(),
							true,
							true,
							true,
							true
					)
			);
			return applicationUsers;
			
		}else if(member.getRole().equals("ADMINTRAINEE")) {
			List<ApplicationUser> applicationUsers = Lists.newArrayList(
					new ApplicationUser(
							ADMINTRAINEE.getGrantedAuthorities(),
							passwordEncoder.encode(member.getPassword()),
							member.getUsername(),
							true,
							true,
							true,
							true
					)
			);
			return applicationUsers;
		}else{
			List<ApplicationUser> applicationUsers = Lists.newArrayList(
					new ApplicationUser(
							STUDENT.getGrantedAuthorities(),
							passwordEncoder.encode(member.getPassword()),
							member.getUsername(),
							true,
							true,
							true,
							true
					)
			);
			return applicationUsers;
		}
		
		
		
	}
	
//	private List<ApplicationUser> getApplicationUsers(){
//		List<ApplicationUser> applicationUsers = Lists.newArrayList(
//				new ApplicationUser(
//						STUDENT.getGrantedAuthorities(),
//						passwordEncoder.encode("password"),
//						"annasmith",
//						true,
//						true,
//						true,
//						true
//				),
//				new ApplicationUser(
//						ADMIN.getGrantedAuthorities(),
//						passwordEncoder.encode("password"),
//						"linda",
//						true,
//						true,
//						true,
//						true
//				),
//				new ApplicationUser(
//						ADMINTRAINEE.getGrantedAuthorities(),
//						passwordEncoder.encode("password"),
//						"tom",
//						true,
//						true,
//						true,
//						true
//				)
//		);
//		return applicationUsers;
//	}
	
}
