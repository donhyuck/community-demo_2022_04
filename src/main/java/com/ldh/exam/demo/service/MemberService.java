package com.ldh.exam.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

}
