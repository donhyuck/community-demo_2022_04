package com.ldh.exam.demo.repository;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.ldh.exam.demo.vo.Reply;

@Mapper
public interface AdmReplyRepository {

	@Select("""
			SELECT r.*,
			m.name AS extra__writerName
			FROM reply AS r
			LEFT JOIN `member` AS m
			ON r.memberId = m.id
			WHERE relTypeCode = #{relTypeCode}
			AND relId = #{relId}
			ORDER BY r.id DESC
			""")
	List<Reply> getForPrintReplies(String relTypeCode, int relId);

	@Select("""
			SELECT r.*,
			m.nickname AS extra__writerName
			FROM reply AS r
			LEFT JOIN `member` AS m
			ON r.memberId = m.id
			WHERE r.id = #{id}
			""")
	Reply getForPrintReply(int id);

	@Delete("""
			DELETE FROM reply
			WHERE id = #{id}
			""")
	void deleteReply(int id);

	@Update("""
			UPDATE reply
			SET updateDate = NOW(),
			`body` = #{body}
			WHERE id = #{id}
			""")
	void modifyReply(int id, String body);
}
