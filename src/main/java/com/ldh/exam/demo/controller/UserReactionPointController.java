package com.ldh.exam.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ldh.exam.demo.service.ReactionPointService;
import com.ldh.exam.demo.vo.Rq;

@Controller
public class UserReactionPointController {

	@Autowired
	private ReactionPointService reactionPointService;
	@Autowired
	private Rq rq;

	@RequestMapping("/user/reactionPoint/doGoodReaction")
	public String doGoodReaction(String relTypeCode, int relId) {

		return null;
	}

	@RequestMapping("/user/reactionPoint/doBadReaction")
	public String doBadReaction(String relTypeCode, int relId) {

		return null;
	}
}