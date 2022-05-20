package com.ldh.exam.demo.service;

import org.springframework.stereotype.Service;

import com.ldh.exam.demo.repository.ReactionPointRepository;

@Service
public class ReactionPointService {

	private ReactionPointRepository reactionPointRepository;

	public ReactionPointService(ReactionPointRepository reactionPointRepository) {
		this.reactionPointRepository = reactionPointRepository;
	}

	public boolean actorCanMakeReactionPoint(int memberId, String relTypeCode, int relId) {

		if (memberId == 0) {
			return false;
		}

		return reactionPointRepository.getSumReactionPointByMemberId(memberId, relTypeCode, relId) == 0;
	}
}
