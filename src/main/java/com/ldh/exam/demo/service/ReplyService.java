package com.ldh.exam.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ldh.exam.demo.repository.ReplyRepository;
import com.ldh.exam.demo.util.Ut;
import com.ldh.exam.demo.vo.Member;
import com.ldh.exam.demo.vo.Reply;
import com.ldh.exam.demo.vo.ResultData;

@Service
public class ReplyService {

	@Autowired
	private ReplyRepository replyRepository;

	public ResultData<Integer> writeReply(int memberId, String relTypeCode, int relId, String body) {

		replyRepository.writeReply(memberId, relTypeCode, relId, body);

		int id = replyRepository.getLastInsertId();

		return ResultData.from("S-1", Ut.format("%d번 댓글이 등록되었습니다.", id), "id", id);
	}

	public List<Reply> getForPrintReplies(Member member, String relTypeCode, int relId) {

		List<Reply> replies = replyRepository.getForPrintReplies(relTypeCode, relId);

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

		if (reply.getMemberId() != member.getId()) {
			return ResultData.from("F-2", "해당 댓글에 대한 권한이 없습니다.");
		}

		return ResultData.from("S-1", "댓글 수정이 가능합니다.");
	}

	private ResultData actorCanDelete(Member member, Reply reply) {

		if (reply == null) {
			return ResultData.from("F-1", "해당 댓글을 찾을 수 없습니다.");
		}

		if (reply.getMemberId() != member.getId()) {
			return ResultData.from("F-2", "해당 댓글에 대한 권한이 없습니다.");
		}

		return ResultData.from("S-1", "댓글 삭제가 가능합니다.");
	}
}
