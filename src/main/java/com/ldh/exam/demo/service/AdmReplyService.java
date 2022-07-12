package com.ldh.exam.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ldh.exam.demo.repository.AdmReplyRepository;
import com.ldh.exam.demo.util.Ut;
import com.ldh.exam.demo.vo.Member;
import com.ldh.exam.demo.vo.Reply;
import com.ldh.exam.demo.vo.ResultData;

@Service
public class AdmReplyService {

	@Autowired
	private AdmReplyRepository admReplyRepository;

	public List<Reply> getForPrintReplies(Member member, String relTypeCode, int relId) {

		List<Reply> replies = admReplyRepository.getForPrintReplies(relTypeCode, relId);

		for (Reply reply : replies) {
			updateForPrintData(member, reply);
		}

		return replies;
	}

	private void updateForPrintData(Member member, Reply reply) {

		if (reply == null) {
			return;
		}

		ResultData actorCanModifyRd = actorCanModify(member, reply);
		reply.setExtra__actorCanModify(actorCanModifyRd.isSuccess());

		ResultData actorCanDeleteRd = actorCanDelete(member, reply);
		reply.setExtra__actorCanDelete(actorCanDeleteRd.isSuccess());

	}

	private ResultData actorCanModify(Member member, Reply reply) {

		if (reply == null) {
			return ResultData.from("F-1", "해당 댓글을 찾을 수 없습니다.");
		}

		if (member == null) {
			return ResultData.from("F-3", "로그인 상태가 아닙니다.");
		}

		if (member.getAuthLevel() != 7) {
			return ResultData.from("F-2", "관리자 권한이 없습니다.");
		}

		return ResultData.from("S-1", "댓글 수정이 가능합니다.");
	}

	private ResultData actorCanDelete(Member member, Reply reply) {

		if (reply == null) {
			return ResultData.from("F-1", "해당 댓글을 찾을 수 없습니다.");
		}

		if (member == null) {
			return ResultData.from("F-3", "로그인 상태가 아닙니다.");
		}

		if (member.getAuthLevel() != 7) {
			return ResultData.from("F-2", "관리자 권한이 없습니다.");
		}

		return ResultData.from("S-1", "댓글 삭제가 가능합니다.");
	}

	public Reply getForPrintReply(Member member, int id) {

		Reply reply = admReplyRepository.getForPrintReply(id);

		updateForPrintData(member, reply);

		return reply;
	}

	public ResultData deleteReply(int id) {

		admReplyRepository.deleteReply(id);

		return ResultData.from("S-1", Ut.format("%d번 댓글을 삭제했습니다.", id));
	}

	public ResultData modifyReply(int id, String body) {

		admReplyRepository.modifyReply(id, body);

		return ResultData.from("S-1", Ut.format("%d번 댓글을 수정했습니다.", id));
	}
}
