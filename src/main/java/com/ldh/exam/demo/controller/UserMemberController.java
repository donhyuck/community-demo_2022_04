package com.ldh.exam.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ldh.exam.demo.service.MemberService;
import com.ldh.exam.demo.vo.Member;

@Controller
public class UserMemberController {

	@Autowired
	private MemberService memberService;

	// http://localhost:8011/user/member/doJoin?loginId=bbb&loginPw=aaa&name=a&nickname=aa&cellphoneNo=111&email=a@test.com
	@RequestMapping("/user/member/doJoin")
	@ResponseBody
	public Object doJoin(String loginId, String loginPw, String name, String nickname, String cellphoneNo,
			String email) {

		if (loginId == null || loginId.trim().length() == 0) {
			return "loginId(을)를 입력해주세요.";
		}
		if (loginPw == null || loginId.trim().length() == 0) {
			return "loginPw(을)를 입력해주세요.";
		}
		if (name == null || loginId.trim().length() == 0) {
			return "name(을)를 입력해주세요.";
		}
		if (nickname == null || loginId.trim().length() == 0) {
			return "nickname(을)를 입력해주세요.";
		}
		if (cellphoneNo == null || loginId.trim().length() == 0) {
			return "cellphoneNo(을)를 입력해주세요.";
		}
		if (email == null || loginId.trim().length() == 0) {
			return "email(을)를 입력해주세요.";
		}

		int id = memberService.join(loginId, loginPw, name, nickname, cellphoneNo, email);

		if (id == -1) {
			return "해당 로그인아이디는 이미 사용중입니다.";
		}

		Member member = memberService.getMemberById(id);

		return member;
	}
}