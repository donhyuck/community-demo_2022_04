package com.ldh.exam.demo.controller;

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
	public ResultData doJoin(String loginId, String loginPw, String name, String nickname, String cellphoneNo,
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

		ResultData joinRd = memberService.join(loginId, loginPw, name, nickname, cellphoneNo, email);

		if (joinRd.isFail()) {
			return joinRd;
		}

		Member member = memberService.getMemberById((int) joinRd.getData1());

		return ResultData.newData(joinRd, member);
	}
}