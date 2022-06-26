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

		if (member == null) {
			return ResultData.from("F-3", "로그인 상태가 아닙니다.");
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

		if (member == null) {
			return ResultData.from("F-3", "로그인 상태가 아닙니다.");
		}

		if (reply.getMemberId() != member.getId()) {
			return ResultData.from("F-2", "해당 댓글에 대한 권한이 없습니다.");
		}

		return ResultData.from("S-1", "댓글 삭제가 가능합니다.");
	}

	public Reply getForPrintReply(Member member, int id) {

		Reply reply = replyRepository.getForPrintReply(id);

		updateForPrintData(member, reply);

		return reply;
	}

	public ResultData deleteReply(int id) {

		replyRepository.deleteReply(id);

		return ResultData.from("S-1", Ut.format("%d번 댓글을 삭제했습니다.", id));
	}

	public ResultData modifyReply(int id, String body) {

		replyRepository.modifyReply(id, body);

		return ResultData.from("S-1", Ut.format("%d번 댓글을 수정했습니다.", id));
	}

	public ResultData increaseGoodReactionPoint(int relId) {

		int affectedRowsCount = replyRepository.increaseGoodReactionPoint(relId);

		if (affectedRowsCount == 0) {
			return ResultData.from("F-1", "해당 게시물이 존재하지 않습니다.");
		}

		return ResultData.from("S-1", "좋아요가 1만큼 증가합니다.", "affectedRowsCount", affectedRowsCount);
	}

	public ResultData increaseBadReactionPoint(int relId) {

		int affectedRowsCount = replyRepository.increaseBadReactionPoint(relId);

		if (affectedRowsCount == 0) {
			return ResultData.from("F-1", "해당 게시물이 존재하지 않습니다.");
		}

		return ResultData.from("S-1", "싫어요가 1만큼 증가합니다.", "affectedRowsCount", affectedRowsCount);
	}

	public ResultData decreaseGoodReactionPoint(int relId) {

		int affectedRowsCount = replyRepository.decreaseGoodReactionPoint(relId);

		if (affectedRowsCount == 0) {
			return ResultData.from("F-1", "해당 게시물이 존재하지 않습니다.");
		}

		return ResultData.from("S-1", "좋아요가 1만큼 감소합니다.", "affectedRowsCount", affectedRowsCount);

	}

	public ResultData decreaseBadReactionPoint(int relId) {

		int affectedRowsCount = replyRepository.decreaseBadReactionPoint(relId);

		if (affectedRowsCount == 0) {
			return ResultData.from("F-1", "해당 게시물이 존재하지 않습니다.");
		}

		return ResultData.from("S-1", "싫어요가 1만큼 감소합니다.", "affectedRowsCount", affectedRowsCount);

	}

}
