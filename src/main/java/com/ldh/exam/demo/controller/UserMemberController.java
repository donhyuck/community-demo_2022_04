package com.ldh.exam.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ldh.exam.demo.service.MemberService;
import com.ldh.exam.demo.util.Ut;
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

		if (Ut.empty(loginId)) {
			return "아이디(을)를 입력해주세요.";
		}
		if (Ut.empty(loginPw)) {
			return "비밀번호(을)를 입력해주세요.";
		}
		if (Ut.empty(name)) {
			return "이름(을)를 입력해주세요.";
		}
		if (Ut.empty(nickname)) {
			return "닉네임(을)를 입력해주세요.";
		}
		if (Ut.empty(cellphoneNo)) {
			return "연락처(을)를 입력해주세요.";
		}
		if (Ut.empty(email)) {
			return "이메일(을)를 입력해주세요.";
		}

		int id = memberService.join(loginId, loginPw, name, nickname, cellphoneNo, email);

		if (id == -1) {
			return Ut.format("해당 로그인아이디(%s)는 이미 사용중입니다.", loginId);
		}

		if (id == -2) {
			return Ut.format("해당 이름(%s)과 이메일(%s)은 이미 사용중입니다.", name, email);
		}

		Member member = memberService.getMemberById(id);

		return member;
	}
}