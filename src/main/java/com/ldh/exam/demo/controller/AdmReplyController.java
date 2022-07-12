package com.ldh.exam.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ldh.exam.demo.service.AdmArticleService;
import com.ldh.exam.demo.service.AdmReplyService;
import com.ldh.exam.demo.util.Ut;
import com.ldh.exam.demo.vo.Article;
import com.ldh.exam.demo.vo.Reply;
import com.ldh.exam.demo.vo.ResultData;
import com.ldh.exam.demo.vo.Rq;

@Controller
public class AdmReplyController {

	@Autowired
	private AdmReplyService admReplyService;
	@Autowired
	private AdmArticleService admArticleService;
	@Autowired
	private Rq rq;

	@RequestMapping("/adm/reply/modify")
	public String modify(Model model, int id, String replaceUri) {

		if (Ut.empty(id)) {
			return rq.jsHistoryBack("id을 입력해주세요.");
		}

		Reply reply = admReplyService.getForPrintReply(rq.getLoginedMember(), id);

		// 댓글 존재여부 및 권한 체크
		if (reply == null) {
			return rq.historyBackJsOnView(Ut.format("%d번 댓글을 찾을 수 없습니다.", id));
		}

		if (rq.isAdmin() == false) {
			return rq.historyBackJsOnView("관리자 권한이 없습니다.");
		}

		// 게시글 제목 가져오기
		String relDataTitle = null;

		switch (reply.getRelTypeCode()) {
		case "article":
			Article article = admArticleService.getArticle(reply.getRelId());
			relDataTitle = article.getTitle();
		}

		model.addAttribute("reply", reply);
		model.addAttribute("relDataTitle", relDataTitle);

		return "adm/reply/modify";
	}

	@RequestMapping("/adm/reply/doModify")
	@ResponseBody
	public String doModify(int id, String body, String replaceUri) {

		if (Ut.empty(id)) {
			return rq.jsHistoryBack("댓글 번호가 잘못 되었습니다.");
		}

		if (Ut.empty(body)) {
			return rq.jsHistoryBack("내용을 입력해주세요.");
		}

		Reply reply = admReplyService.getForPrintReply(rq.getLoginedMember(), id);

		// 댓글 존재여부 및 권한 체크
		if (reply == null) {
			return rq.jsHistoryBack(Ut.format("%d번 댓글을 찾을 수 없습니다.", id));
		}

		if (rq.isAdmin() == false) {
			return rq.historyBackJsOnView("관리자 권한이 없습니다.");
		}

		ResultData modifyReplyRd = admReplyService.modifyReply(id, body);

		if (Ut.empty(replaceUri)) {
			switch (reply.getRelTypeCode()) {
			case "article":
				replaceUri = Ut.format("../article/detail?id=%d", reply.getRelId());
				break;
			}
		}

		replaceUri = Ut.getNewUri(replaceUri, "focusReplyId", id + "");

		return rq.jsReplace(modifyReplyRd.getMsg(), replaceUri);
	}

	@RequestMapping("/adm/reply/doDelete")
	@ResponseBody
	public String doDelete(int id, String replaceUri) {

		if (Ut.empty(id)) {
			return rq.jsHistoryBack("댓글 번호가 잘못 되었습니다.");
		}

		Reply reply = admReplyService.getForPrintReply(rq.getLoginedMember(), id);

		// 댓글 존재여부 및 권한 체크
		if (reply == null) {
			return rq.jsHistoryBack(Ut.format("%d번 댓글을 찾을 수 없습니다.", id));
		}

		if (rq.isAdmin() == false) {
			return rq.historyBackJsOnView("관리자 권한이 없습니다.");
		}

		ResultData deleteReplyRd = admReplyService.deleteReply(id);

		if (Ut.empty(replaceUri)) {
			switch (reply.getRelTypeCode()) {
			case "article":
				replaceUri = Ut.format("../article/detail?id=%d", reply.getRelId());
				break;
			}
		}

		return rq.jsReplace(deleteReplyRd.getMsg(), replaceUri);
	}

}