package com.ldh.exam.demo.repository;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.ldh.exam.demo.vo.Member;

@Mapper
public interface AdmMemberRepository {

	@Select("""
			SELECT *
			FROM `member` as m
			WHERE m.id=#{id}
						""")
	Member getMemberById(@Param("id") int id);

	@Select("""
			<script>
			SELECT COUNT(*) AS cnt
			FROM `member` AS m
			WHERE 1
			<if test="authLevel != 0">
				AND m.authLevel = #{authLevel}
			</if>
			<if test="searchKeyword != ''">
				<choose>
					<when test="searchKeywordTypeCode == 'loginId'">
						AND m.loginId LIKE CONCAT('%',#{searchKeyword},'%')
					</when>
					<when test="searchKeywordTypeCode == 'name'">
						AND m.name LIKE CONCAT('%',#{searchKeyword},'%')
					</when>
					<when test="searchKeywordTypeCode == 'nickname'">
						AND m.nickname LIKE CONCAT('%',#{searchKeyword},'%')
					</when>
					<otherwise>
						AND (
							m.loginId  LIKE CONCAT('%',#{searchKeyword},'%')
							OR
							m.name LIKE CONCAT('%',#{searchKeyword},'%')
							OR
							m.nickname LIKE CONCAT('%',#{searchKeyword},'%')
						)
					</otherwise>
				</choose>
			</if>
			</script>
			""")
	int getMembersCount(int authLevel, String searchKeywordTypeCode, String searchKeyword);

	@Select("""
			<script>
			SELECT m.*
			FROM `member` AS m
			WHERE 1
			<if test="authLevel != 0">
			    AND m.authLevel = #{authLevel}
			</if>
			<if test="searchKeyword != ''">
				<choose>
					<when test="searchKeywordTypeCode == 'loginId'">
						AND m.loginId LIKE CONCAT('%', #{searchKeyword}, '%')
					</when>
					<when test="searchKeywordTypeCode == 'name'">
						AND m.name LIKE CONCAT('%', #{searchKeyword}, '%')
					</when>
					<when test="searchKeywordTypeCode == 'nickname'">
						AND m.nickname LIKE CONCAT('%', #{searchKeyword}, '%')
					</when>
					<otherwise>
						AND (
							m.loginId LIKE CONCAT('%', #{searchKeyword}, '%')
							OR
							m.name LIKE CONCAT('%', #{searchKeyword}, '%')
							OR
							m.nickname LIKE CONCAT('%', #{searchKeyword}, '%')
						)
					</otherwise>
				</choose>
			</if>
			ORDER BY m.id DESC
			<if test="limitTake != -1">
				LIMIT #{limitStart}, #{limitTake}
			</if>
			</script>
			""")
	List<Member> getForPrintMembers(int authLevel, String searchKeywordTypeCode, String searchKeyword, int limitStart,
			int limitTake);

	@Update("""
			<script>
			UPDATE `member`
			<set>
				updateDate = NOW(),
				delStatus = 1,
				delDate = NOW(),
			</set>
			WHERE id = #{id}
			</script>
			""")
	void deleteMember(int id);

}
