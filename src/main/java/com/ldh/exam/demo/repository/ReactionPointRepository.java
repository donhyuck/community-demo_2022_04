package com.ldh.exam.demo.repository;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ReactionPointRepository {

	@Select("""
			<script>
			SELECT IFNULL(SUM(rp.point), 0) AS sum
			FROM reactionPoint AS rp
			WHERE rp.relTypeCode = #{relTypeCode}
			AND rp.relId = #{relId}
			AND rp.memberId = #{memberId}
			</script>
			""")
	public int getSumReactionPointByMemberId(int memberId, String relTypeCode, int relId);

	@Insert("""
			<script>
			INSERT INTO reactionPoint
			SET regDate = NOW(),
			updateDate = NOW(),
			memberId = #{memberId},
			relTypeCode = #{relTypeCode},
			relId = #{relId},
			`point` = 1
			</script>
			""")
	public void addGoodReactionPoint(int memberId, String relTypeCode, int relId);

	@Insert("""
			<script>
			INSERT INTO reactionPoint
			SET regDate = NOW(),
			updateDate = NOW(),
			memberId = #{memberId},
			relTypeCode = #{relTypeCode},
			relId = #{relId},
			`point` = -1
			</script>
			""")
	public void addBadReactionPoint(int memberId, String relTypeCode, int relId);

	@Delete("""
			<script>
			DELETE FROM reactionPoint
			WHERE memberId = #{memberId}
			AND relTypeCode = #{relTypeCode}
			AND relId = #{relId}
			</script>
			""")
	public void deleteReactionPoint(int memberId, String relTypeCode, int relId);

}
