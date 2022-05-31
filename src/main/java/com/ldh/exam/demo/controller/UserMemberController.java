package com.ldh.exam.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ldh.exam.demo.service.MemberService;
import com.ldh.exam.demo.util.Ut;
import com.ldh.exam.demo.vo.Member;
import com.ldh.exam.demo.vo.ResultData;
import com.ldh.exam.demo.vo.Rq;

@Controller
public class UserMemberController {

	@Autowired
	private MemberService memberService;
	@Autowired
	private Rq rq;

	// http://localhost:8011/user/member/doJoin?loginId=bbb&loginPw=aaa&name=a&nickname=aa&cellPhoneNo=111&email=a@test.com
	@RequestMapping("/user/member/doJoin")
	@ResponseBody
	public ResultData<Member> doJoin(String loginId, String loginPw, String name, String nickname, String cellPhoneNo,
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
		if (Ut.empty(cellPhoneNo)) {
			return ResultData.from("F-5", "연락처(을)를 입력해주세요.");
		}
		if (Ut.empty(email)) {
			return ResultData.from("F-6", "이메일(을)를 입력해주세요.");
		}

		ResultData<Integer> joinRd = memberService.join(loginId, loginPw, name, nickname, cellPhoneNo, email);

		if (joinRd.isFail()) {
			return (ResultData) joinRd;
		}

		Member member = memberService.getMemberById(joinRd.getData1());

		return ResultData.newData(joinRd, "member", member);
	}

	@RequestMapping("/user/member/login")
	public String showLogin() {
		return "user/member/login";
	}

	@RequestMapping("/user/member/doLogin")
	@ResponseBody
	public String doLogin(String loginId, String loginPw, @RequestParam(defaultValue = "/") String afterLoginUri) {

		if (Ut.empty(loginId)) {
			return rq.jsHistoryBack("아이디를 입력해주세요.");
		}
		if (Ut.empty(loginPw)) {
			return rq.jsHistoryBack("비밀번호를 입력해주세요.");
		}

		Member member = memberService.getMemberByLoginId(loginId);

		if (member == null) {
			return rq.jsHistoryBack("등록되지 않은 회원입니다.");
		}

		if (member.getLoginPw().equals(loginPw) == false) {
			return rq.jsHistoryBack("잘못된 비밀번호입니다.");
		}

		rq.login(member);

		return rq.jsReplace(Ut.format("%s님 환영합니다.", member.getNickname()), afterLoginUri);
	}

	@RequestMapping("/user/member/doLogout")
	@ResponseBody
	public String doLogout() {

		if (!rq.isLogined()) {
			return rq.jsHistoryBack("이미 로그아웃 상태입니다.");
		}

		rq.logout();

		return rq.jsReplace("로그아웃되었습니다.", "/");
	}

	@RequestMapping("/user/member/myPage")
	public String showMyPage() {
		return "user/member/myPage";
	}

	@RequestMapping("/user/member/checkPassword")
	public String showCheckPassword() {
		return "user/member/checkPassword";
	}

	@RequestMapping("/user/member/doCheckPassword")
	@ResponseBody
	public String doCheckPassword(String loginPw, String replaceUri) {

		if (Ut.empty(loginPw)) {
			return rq.jsHistoryBack("비밀번호를 입력해주세요.");
		}

		if (rq.getLoginedMember().getLoginPw().equals(loginPw) == false) {
			return rq.jsHistoryBack("잘못된 비밀번호입니다.");
		}

		// 인증코드 생성
		if (replaceUri.equals("../member/modify")) {
			String authKeyForMemberModify = memberService.genAuthKeyForMemberModify(rq.getLoginedMemberId());

			replaceUri += "?authKeyForMemberModify=" + authKeyForMemberModify;
		}

		return rq.jsReplace("", replaceUri);
	}

	@RequestMapping("/user/member/modify")
	public String showModify(String authKeyForMemberModify) {

		// 인증코드 체크
		if (Ut.empty(authKeyForMemberModify)) {
			return rq.historyBackJsOnView("인증코드가 필요합니다.");
		}

		ResultData checkAuthKeyForMemberModifyRd = memberService.checkAuthKeyForMemberModify(rq.getLoginedMemberId(),
				authKeyForMemberModify);

		if (checkAuthKeyForMemberModifyRd.isFail()) {
			return rq.historyBackJsOnView(checkAuthKeyForMemberModifyRd.getMsg());
		}

		return "user/member/modify";
	}

	@RequestMapping("/user/member/doModify")
	@ResponseBody
	public String doModify(String authKeyForMemberModify, String loginPw, String name, String nickname,
			String cellPhoneNo, String email) {

		// 인증코드 체크
		if (Ut.empty(authKeyForMemberModify)) {
			return rq.historyBackJsOnView("인증코드가 필요합니다.");
		}

		ResultData checkAuthKeyForMemberModifyRd = memberService.checkAuthKeyForMemberModify(rq.getLoginedMemberId(),
				authKeyForMemberModify);

		if (checkAuthKeyForMemberModifyRd.isFail()) {
			return rq.historyBackJsOnView(checkAuthKeyForMemberModifyRd.getMsg());
		}

		if (Ut.empty(loginPw)) {
			loginPw = null;
		}

		if (Ut.empty(name)) {
			return rq.jsHistoryBack("이름을 입력해주세요.");
		}

		if (Ut.empty(nickname)) {
			return rq.jsHistoryBack("별명을 입력해주세요.");
		}

		if (Ut.empty(cellPhoneNo)) {
			return rq.jsHistoryBack("연락처를 입력해주세요.");
		}

		if (Ut.empty(email)) {
			return rq.jsHistoryBack("이메일을 입력해주세요.");
		}

		ResultData modifyRd = memberService.modify(rq.getLoginedMemberId(), loginPw, name, nickname, cellPhoneNo,
				email);

		return rq.jsReplace(modifyRd.getMsg(), "/");
	}
}