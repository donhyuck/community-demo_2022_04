package com.ldh.exam.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ldh.exam.demo.repository.ReactionPointRepository;
import com.ldh.exam.demo.util.Ut;
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

		return ResultData.from("S-1", Ut.format("[%d번 글] 좋아요", relId));
	}

	public ResultData addBadReactionPoint(int memberId, String relTypeCode, int relId) {

		reactionPointRepository.addBadReactionPoint(memberId, relTypeCode, relId);

		switch (relTypeCode) {
		case "article":
			articleService.increaseBadReactionPoint(relId);
			break;
		}

		return ResultData.from("S-1", Ut.format("[%d번 글] 싫어요", relId));
	}

	public ResultData deleteGoodReactionPoint(int memberId, String relTypeCode, int relId) {

		reactionPointRepository.deleteReactionPoint(memberId, relTypeCode, relId);

		switch (relTypeCode) {
		case "article":
			articleService.decreaseGoodReactionPoint(relId);
			break;
		}

		return ResultData.from("S-1", Ut.format("[%d번 글] 좋아요 취소", relId));
	}

	public ResultData deleteBadReactionPoint(int memberId, String relTypeCode, int relId) {

		reactionPointRepository.deleteReactionPoint(memberId, relTypeCode, relId);

		switch (relTypeCode) {
		case "article":
			articleService.decreaseBadReactionPoint(relId);
			break;
		}

		return ResultData.from("S-1", Ut.format("[%d번 글] 싫어요 취소", relId));
	}
}
