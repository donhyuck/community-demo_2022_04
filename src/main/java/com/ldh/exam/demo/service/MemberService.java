package com.ldh.exam.demo.service;

import org.springframework.stereotype.Service;

import com.ldh.exam.demo.repository.MemberRepository;
import com.ldh.exam.demo.vo.Member;

@Service
public class MemberService {

	private MemberRepository memberRepository;

	public MemberService(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}

	public int join(String loginId, String loginPw, String name, String nickname, String cellphoneNo, String email) {

		// 로그인 아이디 확인
		Member oldMember = getMemberByLoginId(loginId);
		if (oldMember != null) {
			return -1;
		}

		// 이름, 이메일 확인
		oldMember = getMemberByNameAndEmail(name, email);
		if (oldMember != null) {
			return -2;
		}

		memberRepository.join(loginId, loginPw, name, nickname, cellphoneNo, email);
		return memberRepository.getLastInsertId();
	}

	private Member getMemberByLoginId(String loginId) {
		return memberRepository.getMemberByLoginId(loginId);
	}

	private Member getMemberByNameAndEmail(String name, String email) {
		return memberRepository.getMemberByNameAndEmail(name, email);
	}

	public Member getMemberById(int id) {
		return memberRepository.getMemberById(id);
	}

}