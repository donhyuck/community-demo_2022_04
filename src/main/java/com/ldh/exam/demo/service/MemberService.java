package com.ldh.exam.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ldh.exam.demo.repository.MemberRepository;
import com.ldh.exam.demo.util.Ut;
import com.ldh.exam.demo.vo.Member;
import com.ldh.exam.demo.vo.ResultData;

@Service
public class MemberService {

	@Autowired
	private MemberRepository memberRepository;
	@Autowired
	private AttrService attrService;
	@Autowired
	private MailService mailService;

	@Value("${custom.siteMainUri}")
	private String siteMainUri;
	@Value("${custom.siteName}")
	private String siteName;

	public ResultData<Integer> join(String loginId, String loginPw, String name, String nickname, String cellPhoneNo,
			String email) {

		// 로그인 아이디 확인
		Member oldMember = getMemberByLoginId(loginId);
		if (oldMember != null) {
			return ResultData.from("F-7", Ut.format("해당 아이디(%s)는 이미 사용중입니다.", loginId));
		}

		// 이름, 이메일 확인
		oldMember = getMemberByNameAndEmail(name, email);
		if (oldMember != null) {
			return ResultData.from("F-8", Ut.format("해당 이름(%s)과 이메일(%s)은 이미 사용중입니다.", name, email));
		}

		memberRepository.join(loginId, loginPw, name, nickname, cellPhoneNo, email);
		int id = memberRepository.getLastInsertId();

		return ResultData.from("S-1", "회원가입이 완료되었습니다.", "id", id);
	}

	public Member getMemberByLoginId(String loginId) {
		return memberRepository.getMemberByLoginId(loginId);
	}

	public Member getMemberByNameAndEmail(String name, String email) {
		return memberRepository.getMemberByNameAndEmail(name, email);
	}

	public Member getMemberById(int id) {
		return memberRepository.getMemberById(id);
	}

	public ResultData modify(int memberId, String loginPw, String name, String nickname, String cellPhoneNo,
			String email) {

		memberRepository.modify(memberId, loginPw, name, nickname, cellPhoneNo, email);

		return ResultData.from("S-1", "회원정보가 수정되었습니다.");

	}

	public String genAuthKeyForMemberModify(int memberId) {

		String authKeyForMemberModify = Ut.getTempPassword(10);

		attrService.setValue("member", memberId, "extra", "authKeyForMemberModify", authKeyForMemberModify,
				Ut.getDateStrLater(60 * 5));

		return authKeyForMemberModify;
	}

	public ResultData checkAuthKeyForMemberModify(int memberId, String authKeyForMemberModify) {

		String savedAuthKey = attrService.getValue("member", memberId, "extra", "authKeyForMemberModify");

		if (!savedAuthKey.equals(authKeyForMemberModify)) {
			return ResultData.from("F-1", "일치하지 않거나 만료되었습니다.");
		}

		return ResultData.from("S-1", "정상적인 코드입니다.");
	}

	public int getMembersCount(int authLevel, String searchKeywordTypeCode, String searchKeyword) {

		return memberRepository.getMembersCount(authLevel, searchKeywordTypeCode, searchKeyword);
	}

	public List<Member> getForPrintMembers(int authLevel, String searchKeywordTypeCode, String searchKeyword,
			int itemsCountInAPage, int page) {

		int limitStart = (page - 1) * itemsCountInAPage;
		int limitTake = itemsCountInAPage;

		List<Member> members = memberRepository.getForPrintMembers(authLevel, searchKeywordTypeCode, searchKeyword,
				limitStart, limitTake);

		return members;
	}

	public void doDeleteMembers(List<Integer> memberIds) {

		for (int memberId : memberIds) {
			Member member = getMemberById(memberId);

			if (member != null) {
				doDeleteMember(member);
			}
		}
	}

	private void doDeleteMember(Member member) {
		memberRepository.deleteMember(member.getId());
	}

	public ResultData findLoginId(Member member) {

		if (member == null) {
			return ResultData.from("F-1", "이름 또는 이메일을 정확히 입력해주세요.");
		}

		return ResultData.from("S-1", Ut.format("%s 님의 등록된 아이디 : %s", member.getName(), member.getLoginId()));
	}

	public ResultData findLoginPw(Member member, String name, String email) {

		if (member == null) {
			return ResultData.from("F-1", "아이디가 잘못 입력되었거나 등록되지 않은 회원입니다.");
		}

		if (member.getName().equals(name) == false) {
			return ResultData.from("F-2", "이름이 잘못 입력되었거나 등록되지 않은 회원입니다.");
		}

		if (member.getEmail().equals(email) == false) {
			return ResultData.from("F-3", "이메일이 잘못 입력되었거나 등록되지 않은 회원입니다.");
		}

		return ResultData.from("S-1", "임시 비밀번호가 발송됩니다.");
	}

	public ResultData notifyTempLoginPwByEmail(Member actor) {

		String title = "[" + siteName + "] 임시 패스워드 발송";
		String tempPassword = Ut.getTempPassword(6);
		String body = "<h1>임시 패스워드 : " + tempPassword + "</h1>";
		body += "<a href=\"" + siteMainUri + "/user/member/login\" target=\"_blank\">로그인 하러가기</a>";

		ResultData sendResultData = mailService.send(actor.getEmail(), title, body);

		if (sendResultData.isFail()) {
			return sendResultData;
		}

		setTempPassword(actor, tempPassword);

		return ResultData.from("S-1", "계정의 이메일주소로 임시 패스워드가 발송되었습니다.");
	}

	private void setTempPassword(Member actor, String tempPassword) {
		memberRepository.modify(actor.getId(), tempPassword, null, null, null, null);
	}

}
