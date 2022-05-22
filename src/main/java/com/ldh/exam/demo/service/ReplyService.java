package com.ldh.exam.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ldh.exam.demo.repository.ReplyRepository;
import com.ldh.exam.demo.util.Ut;
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
}
