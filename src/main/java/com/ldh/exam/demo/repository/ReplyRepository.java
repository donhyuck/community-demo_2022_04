package com.ldh.exam.demo.repository;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.ldh.exam.demo.vo.Reply;

@Mapper
public interface ReplyRepository {

	@Insert("""
			INSERT INTO reply
			SET regDate = NOW(),
			updateDate = NOW(),
			memberId = #{memberId},
			relTypeCode = #{relTypeCode},
			relId = #{relId},
			`body` = #{body}
			""")
	void writeReply(int memberId, String relTypeCode, int relId, String body);

	@Select("""
			SELECT LAST_INSERT_ID()
			""")
	int getLastInsertId();

	@Select("""
			SELECT r.*,
			m.nickname AS extra__writerName
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

}
