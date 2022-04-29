package com.ldh.exam.demo.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ldh.exam.demo.service.MemberService;
import com.ldh.exam.demo.util.Ut;
import com.ldh.exam.demo.vo.Member;
import com.ldh.exam.demo.vo.ResultData;

@Controller
public class UserMemberController {

	@Autowired
	private MemberService memberService;

	// http://localhost:8011/user/member/doJoin?loginId=bbb&loginPw=aaa&name=a&nickname=aa&cellphoneNo=111&email=a@test.com
	@RequestMapping("/user/member/doJoin")
	@ResponseBody
	public ResultData<Member> doJoin(String loginId, String loginPw, String name, String nickname, String cellphoneNo,
			String email) {

		if (Ut.empty(loginId)) {
			return ResultData.from("F-1", "아이디(을)를 입력해주세요.");
		}
		if (Ut.empty(loginPw)) {
			return ResultData.from("F-2", "비밀번호(을)를 입력해주세요.");
		}
		if (Ut.empty(name)) {
			return ResultData.from("F-3", "이름(을)를 입력해주세요.");
		}
		if (Ut.empty(nickname)) {
			return ResultData.from("F-4", "닉네임(을)를 입력해주세요.");
		}
		if (Ut.empty(cellphoneNo)) {
			return ResultData.from("F-5", "연락처(을)를 입력해주세요.");
		}
		if (Ut.empty(email)) {
			return ResultData.from("F-6", "이메일(을)를 입력해주세요.");
		}

		ResultData<Integer> joinRd = memberService.join(loginId, loginPw, name, nickname, cellphoneNo, email);

		if (joinRd.isFail()) {
			return (ResultData) joinRd;
		}

		Member member = memberService.getMemberById(joinRd.getData1());

		return ResultData.newData(joinRd, member);
	}

	@RequestMapping("/user/member/doLogin")
	@ResponseBody
	public ResultData<Member> doLogin(HttpSession httpSession, String loginId, String loginPw) {

		boolean isLogined = false;

		if (httpSession.getAttribute("loginedMemberId") != null) {
			isLogined = true;
		}
		
		if (isLogined) {
			return ResultData.from("F-5", "이미 로그인되었습니다.");
		}

		if (Ut.empty(loginId)) {
			return ResultData.from("F-1", "아이디(을)를 입력해주세요.");
		}
		if (Ut.empty(loginPw)) {
			return ResultData.from("F-2", "비밀번호(을)를 입력해주세요.");
		}

		Member member = memberService.getMemberByLoginId(loginId);

		if (member == null) {
			return ResultData.from("F-3", "등록되지 않은 회원입니다.");
		}

		if (member.getLoginPw().equals(loginPw) == false) {
			return ResultData.from("F-4", "잘못된 비밀번호입니다.");
		}

		httpSession.setAttribute("loginedMemberId", member.getId());

		return ResultData.from("S-1", Ut.format("%s님 환영합니다.", member.getNickname()));
	}
}