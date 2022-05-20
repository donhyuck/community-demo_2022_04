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

	public boolean actorCanMakeReactionPoint(int memberId, String relTypeCode, int relId) {

		if (memberId == 0) {
			return false;
		}

		return reactionPointRepository.getSumReactionPointByMemberId(memberId, relTypeCode, relId) == 0;
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
		reactionPointRepository.addGoodReactionPoint(memberId, relTypeCode, relId);

		switch (relTypeCode) {
		case "article":
			articleService.increaseBadReactionPoint(relId);
			break;
		}

		return ResultData.from("S-1", "싫어요 처리되었습니다.");
	}
}
