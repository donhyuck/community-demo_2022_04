package com.ldh.exam.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ldh.exam.demo.service.ReactionPointService;
import com.ldh.exam.demo.util.Ut;
import com.ldh.exam.demo.vo.Rq;

@Controller
public class UserReactionPointController {

	@Autowired
	private ReactionPointService reactionPointService;
	@Autowired
	private Rq rq;

	@RequestMapping("/user/reactionPoint/doGoodReaction")
	@ResponseBody
	public String doGoodReaction(String relTypeCode, int relId, String replaceUri) {

		boolean actorCanMakeReactionPoint = reactionPointService
				.actorCanMakeReactionPoint(rq.getLoginedMemberId(), relTypeCode, relId).isSuccess();

		if (actorCanMakeReactionPoint == false) {
			return rq.jsHistoryBack("이미 처리되었습니다.");
		}

		reactionPointService.addGoodReactionPoint(rq.getLoginedMemberId(), relTypeCode, relId);

		return rq.jsReplace(Ut.format("%d번 글이 추천되었습니다.", relId), replaceUri);
	}

	@RequestMapping("/user/reactionPoint/doBadReaction")
	@ResponseBody
	public String doBadReaction(String relTypeCode, int relId, String replaceUri) {

		boolean actorCanMakeReactionPoint = reactionPointService
				.actorCanMakeReactionPoint(rq.getLoginedMemberId(), relTypeCode, relId).isSuccess();

		if (actorCanMakeReactionPoint == false) {
			return rq.jsHistoryBack("이미 처리되었습니다.");
		}

		reactionPointService.addBadReactionPoint(rq.getLoginedMemberId(), relTypeCode, relId);

		return rq.jsReplace(Ut.format("%d번 글이 비추천되었습니다.", relId), replaceUri);
	}
}