package com.ldh.exam.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ldh.exam.demo.service.ReactionPointService;
import com.ldh.exam.demo.util.Ut;
import com.ldh.exam.demo.vo.ResultData;
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

		ResultData actorCanMakeReactionPointRd = reactionPointService.actorCanMakeReactionPoint(rq.getLoginedMemberId(),
				relTypeCode, relId);

		if (actorCanMakeReactionPointRd.isFail()) {
			return rq.jsHistoryBack(actorCanMakeReactionPointRd.getMsg());
		}

		ResultData addGoodReactionPointRd = reactionPointService.addGoodReactionPoint(rq.getLoginedMemberId(),
				relTypeCode, relId);

		return rq.jsReplace(addGoodReactionPointRd.getMsg(), replaceUri);
	}

	@RequestMapping("/user/reactionPoint/doBadReaction")
	@ResponseBody
	public String doBadReaction(String relTypeCode, int relId, String replaceUri) {

		ResultData actorCanMakeReactionPointRd = reactionPointService.actorCanMakeReactionPoint(rq.getLoginedMemberId(),
				relTypeCode, relId);

		if (actorCanMakeReactionPointRd.isFail()) {
			return rq.jsHistoryBack(actorCanMakeReactionPointRd.getMsg());
		}

		ResultData addBadReactionPointRd = reactionPointService.addBadReactionPoint(rq.getLoginedMemberId(),
				relTypeCode, relId);

		return rq.jsReplace(addBadReactionPointRd.getMsg(), replaceUri);
	}

	@RequestMapping("/user/reactionPoint/doCancelGoodReaction")
	@ResponseBody
	public String doCancelGoodReaction(String relTypeCode, int relId, String replaceUri) {

		ResultData actorCanMakeReactionPointRd = reactionPointService.actorCanMakeReactionPoint(rq.getLoginedMemberId(),
				relTypeCode, relId);

		if (actorCanMakeReactionPointRd.isSuccess()) {
			return rq.jsHistoryBack("이미 취소되었습니다.");
		}

		ResultData deleteGoodReactionPointRd = reactionPointService.deleteGoodReactionPoint(rq.getLoginedMemberId(),
				relTypeCode, relId);

		return rq.jsReplace(deleteGoodReactionPointRd.getMsg(), replaceUri);
	}

	@RequestMapping("/user/reactionPoint/doCancelBadReaction")
	@ResponseBody
	public String doCancelBadReaction(String relTypeCode, int relId, String replaceUri) {

		ResultData actorCanMakeReactionPointRd = reactionPointService.actorCanMakeReactionPoint(rq.getLoginedMemberId(),
				relTypeCode, relId);

		if (actorCanMakeReactionPointRd.isSuccess()) {
			return rq.jsHistoryBack("이미 취소되었습니다.");
		}

		ResultData deleteBadReactionPointRd = reactionPointService.deleteBadReactionPoint(rq.getLoginedMemberId(),
				relTypeCode, relId);

		return rq.jsReplace(deleteBadReactionPointRd.getMsg(), replaceUri);
	}
}