package com.ldh.exam.demo.repository;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.ldh.exam.demo.vo.Article;
import com.ldh.exam.demo.vo.Member;

@Mapper
public interface MemberRepository {

	@Insert("""
			INSERT INTO `member`
			SET regDate = NOW(),
			updateDate = NOW(),
			loginId = #{loginId},
			loginPw = #{loginPw},
			`name` = #{name},
			nickname = #{nickname},
			cellPhoneNo = #{cellPhoneNo},
			email = #{email}
						""")
	void join(@Param("loginId") String loginId, @Param("loginPw") String loginPw, @Param("name") String name,
			@Param("nickname") String nickname, @Param("cellPhoneNo") String cellPhoneNo, @Param("email") String email);

	@Select("SELECT LAST_INSERT_ID()")
	int getLastInsertId();

	@Select("""
			SELECT *
			FROM `member` as m
			WHERE m.id=#{id}
						""")
	Member getMemberById(@Param("id") int id);

	@Select("""
			SELECT *
			FROM `member` as m
			WHERE m.loginId=#{loginId}
						""")
	Member getMemberByLoginId(@Param("loginId") String loginId);

	@Select("""
			SELECT *
			FROM `member` as m
			WHERE m.name=#{name}
			AND m.email=#{email}
						""")
	Member getMemberByNameAndEmail(@Param("name") String name, @Param("email") String email);

	@Update("""
			<script>
			UPDATE `member`
			<set>
				updateDate = NOW(),
				<if test="loginPw != null">
					loginPw = #{loginPw},
				</if>
				<if test="name != null">
					`name` = #{name},
				</if>
				<if test="nickname != null">
					nickname = #{nickname},
				</if>
				<if test="cellPhoneNo != null">
					cellPhoneNo = #{cellPhoneNo},
				</if>
				<if test="email != null">
					email = #{email},
				</if>
			</set>
			WHERE id = #{id}
			</script>
						""")
	void modify(int id, String loginPw, String name, String nickname, String cellPhoneNo, String email);

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
						AND m.title LIKE CONCAT('%',#{searchKeyword},'%')
					</when>
					<when test="searchKeywordTypeCode == 'name'">
						AND m.name LIKE CONCAT('%',#{searchKeyword},'%')
					</when>
					<when test="searchKeywordTypeCode == 'nickname'">
						AND m.nickname LIKE CONCAT('%',#{searchKeyword},'%')
					</when>
					<otherwise>
						AND (
							m.loginId LIKE CONCAT('%',#{searchKeyword},'%')
							OR
							m.name LIKE CONCAT('%',#{searchKeyword},'%')
							OR
							m.nickname LIKE CONCAT('%',#{searchKeyword},'%')
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
	List<Article> getForPrintMembers(int authLevel, String searchKeywordTypeCode, String searchKeyword, int limitStart,
			int limitTake);

}
