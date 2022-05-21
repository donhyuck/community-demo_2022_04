package com.ldh.exam.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ldh.exam.demo.repository.ReactionPointRepository;
import com.ldh.exam.demo.vo.ResultData;

@Service
public class ReactionPointService {

	@Autowired
	private ReactionPointRepository reactionPointRepository;
	@Autowired
	private ArticleService articleService;

	public ResultData actorCanMakeReactionPoint(int memberId, String relTypeCode, int relId) {

		if (memberId == 0) {
			return ResultData.from("F-1", "로그인 후 이용해주세요.");
		}

		int sumReactionPointByMemberId = reactionPointRepository.getSumReactionPointByMemberId(memberId, relTypeCode,
				relId);

		if (sumReactionPointByMemberId != 0) {
			return ResultData.from("F-2", "리액션을 할 수 없습니다.", "sumReactionPointByMemberId", sumReactionPointByMemberId);
		}

		return ResultData.from("S-1", "리액션이 가능합니다.", "sumReactionPointByMemberId", sumReactionPointByMemberId);
	}

	public ResultData addGoodReactionPoint(int memberId, String relTypeCode, int relId) {
		reactionPointRepository.addGoodReactionPoint(memberId, relTypeCode, relId);

		switch (relTypeCode) {
		case "article":
			articleService.increaseGoodReactionPoint(relId);
			break;
		}

		return ResultData.from("S-1", "좋아요 처리되었습니다.");
	}

	public ResultData addBadReactionPoint(int memberId, String relTypeCode, int relId) {
		reactionPointRepository.addBadReactionPoint(memberId, relTypeCode, relId);

		switch (relTypeCode) {
		case "article":
			articleService.increaseBadReactionPoint(relId);
			break;
		}

		return ResultData.from("S-1", "싫어요 처리되었습니다.");
	}
}
