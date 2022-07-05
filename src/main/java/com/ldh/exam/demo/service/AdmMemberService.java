package com.ldh.exam.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ldh.exam.demo.repository.AdmMemberRepository;
import com.ldh.exam.demo.vo.Member;

@Service
public class AdmMemberService {

	@Autowired
	private AdmMemberRepository admMemberRepository;
	@Autowired
	private AttrService attrService;
	@Autowired
	private MailService mailService;

	public List<Member> getForPrintMembers(int authLevel, String searchKeywordTypeCode, String searchKeyword,
			int itemsCountInAPage, int page) {

		int limitStart = (page - 1) * itemsCountInAPage;
		int limitTake = itemsCountInAPage;

		List<Member> members = admMemberRepository.getForPrintMembers(authLevel, searchKeywordTypeCode, searchKeyword,
				limitStart, limitTake);

		return members;
	}

	public int getMembersCount(int authLevel, String searchKeywordTypeCode, String searchKeyword) {

		return admMemberRepository.getMembersCount(authLevel, searchKeywordTypeCode, searchKeyword);
	}

	public void doDeleteMembers(List<Integer> memberIds) {

		for (int memberId : memberIds) {
			Member member = getMemberById(memberId);

			if (member != null) {
				doDeleteMember(member);
			}
		}
	}

	public Member getMemberById(int id) {
		return admMemberRepository.getMemberById(id);
	}

	private void doDeleteMember(Member member) {
		admMemberRepository.deleteMember(member.getId());
	}
}