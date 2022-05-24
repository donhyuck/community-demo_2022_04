package com.ldh.exam.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ldh.exam.demo.service.ReplyService;
import com.ldh.exam.demo.util.Ut;
import com.ldh.exam.demo.vo.Reply;
import com.ldh.exam.demo.vo.ResultData;
import com.ldh.exam.demo.vo.Rq;

@Controller
public class UserReplyController {

	@Autowired
	private ReplyService replyService;
	@Autowired
	private Rq rq;

	@RequestMapping("/user/reply/doWrite")
	@ResponseBody
	public String doWrite(String relTypeCode, int relId, String body, String replaceUri) {

		// 댓글정보 입력 확인
		if (Ut.empty(relTypeCode)) {
			return rq.jsHistoryBack("relTypeCode을 입력해주세요.");
		}
		if (Ut.empty(relId)) {
			return rq.jsHistoryBack("relId을 입력해주세요.");
		}
		if (Ut.empty(body)) {
			return rq.jsHistoryBack("내용을 입력해주세요.");
		}

		ResultData<Integer> writeReplyRd = replyService.writeReply(rq.getLoginedMemberId(), relTypeCode, relId, body);
		int id = writeReplyRd.getData1();

		if (Ut.empty(replaceUri)) {
			switch (relTypeCode) {
			case "article":
				replaceUri = Ut.format("../article/detail?id=%d", relId);
				break;
			}
		}

		return rq.jsReplace(writeReplyRd.getMsg(), replaceUri);
	}

	@RequestMapping("/user/reply/doDelete")
	@ResponseBody
	public String doDelete(int id, String replaceUri) {

		if (Ut.empty(id)) {
			return rq.jsHistoryBack("댓글 번호가 잘못 되었습니다.");
		}

		Reply reply = replyService.getForPrintReply(rq.getLoginedMember(), id);

		// 댓글 존재여부 및 권한 체크
		if (reply == null) {
			return rq.jsHistoryBack(Ut.format("%d번 댓글을 찾을 수 없습니다.", id));
		}

		if (reply.isExtra__actorCanDelete() == false) {
			return rq.jsHistoryBack(Ut.format("%d번 댓글에 대한 삭제권한이 없습니다.", id));
		}

		ResultData deleteReplyRd = replyService.deleteReply(id);

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