package com.ldh.exam.demo.repository;

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

}
