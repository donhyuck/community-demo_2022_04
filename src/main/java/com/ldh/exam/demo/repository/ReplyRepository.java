package com.ldh.exam.demo.repository;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

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
			IFNULL(SUM(IF(rp.point > 0, rp.point, 0)), 0) AS extra_goodReactionPoint,
			IFNULL(SUM(IF(rp.point < 0, rp.point, 0)), 0) AS extra_badReactionPoint
			FROM (
				SELECT r.*, m.name AS extra__writerName
				FROM reply AS r
				LEFT JOIN `member` AS m
				ON r.memberId = m.id
			) AS r
			LEFT JOIN `reactionPoint` AS rp
			ON rp.relTypeCode = 'reply'
			AND r.id = rp.relId
			GROUP BY r.id
			""")
	List<Reply> getForPrintReplies(String relTypeCode, int relId);

	@Select("""
			SELECT r.*,
			IFNULL(SUM(IF(rp.point > 0, rp.point, 0)), 0) AS extra_goodReactionPoint,
			IFNULL(SUM(IF(rp.point < 0, rp.point, 0)), 0) AS extra_badReactionPoint
			FROM (
			SELECT r.*, m.name AS extra__writerName
			FROM reply AS r
			LEFT JOIN `member` AS m
			ON r.memberId = m.id
			) AS r
			LEFT JOIN `reactionPoint` AS rp
			ON rp.relTypeCode = 'reply'
			AND r.id = rp.relId
			GROUP BY r.id
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
