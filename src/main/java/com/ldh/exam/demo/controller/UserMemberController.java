package com.ldh.exam.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ldh.exam.demo.service.MemberService;

@Controller
public class UserMemberController {

	private MemberService memberService;

	public UserMemberController(MemberService memberService) {
		this.memberService = memberService;
	}

	// http://localhost:8011/user/member/doJoin?loginId=a&loginPw=a&name=a&nickname=a&=a&cellphoneNo=11&email1@test.com=a&
	@RequestMapping("/user/member/doJoin")
	@ResponseBody
	public String doJoin(String loginId, String loginPw, String name, String nickname, int cellphoneNo, String email) {

		memberService.join(loginId, loginPw, name, nickname, cellphoneNo, email);
		return "회원가입 완료";
	}
}