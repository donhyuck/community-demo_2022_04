package com.ldh.exam.demo.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.ldh.exam.demo.vo.Article;

@Mapper
public interface ArticleRepository {

	@Select("""
			SELECT a.*, m.nickname AS extra__writerName
			FROM article AS a
			LEFT JOIN `member` AS m
			ON a.memberId = m.id
			WHERE a.id = #{id}
			""")
	public Article getForPrintArticle(@Param("id") int id);

	@Select("""
			<script>
			SELECT a.*, m.nickname AS extra__writerName
			FROM article AS a
			LEFT JOIN `member` AS m
			ON a.memberId = m.id
			WHERE 1
			<if test="boardId != 0">
				AND a.boardId = #{boardId}
			</if>
			<if test="searchKeyword != ''">
				<choose>
					<when test="searchKeywordTypeCode == 'title'">
						AND a.title LIKE CONCAT('%',#{searchKeyword},'%')
					</when>
					<when test="searchKeywordTypeCode == 'body'">
						AND a.body LIKE CONCAT('%',#{searchKeyword},'%')
					</when>
					<otherwise>
						AND (
							a.title LIKE CONCAT('%',#{searchKeyword},'%')
							OR
							a.body LIKE CONCAT('%',#{searchKeyword},'%')
						)


					</otherwise>
				</choose>
			</if>
			ORDER BY a.id DESC
			<if test="limitTake != -1">
				LIMIT #{limitStart}, #{limitTake}
			</if>
			</script>
			""")
	public List<Article> getArticles(int boardId, String searchKeywordTypeCode, String searchKeyword, int limitStart,
			int limitTake);

	public void writeArticle(@Param("memberId") int memberId, @Param("boardId") int boardId,
			@Param("title") String title, @Param("body") String body);

	public void modifyArticle(@Param("id") int id, @Param("title") String title, @Param("body") String body);

	public void deleteArticle(@Param("id") int id);

	public int getLastInsertId();

	@Select("""
			<script>
			SELECT COUNT(*) AS cnt
			FROM article AS a
			WHERE 1
			<if test="boardId != 0">
				AND a.boardId = #{boardId}
			</if>
			<if test="searchKeyword != ''">
				<choose>
					<when test="searchKeywordTypeCode == 'title'">
						AND a.title LIKE CONCAT('%',#{searchKeyword},'%')
					</when>
					<when test="searchKeywordTypeCode == 'body'">
						AND a.body LIKE CONCAT('%',#{searchKeyword},'%')
					</when>
					<otherwise>
						AND (
							a.title LIKE CONCAT('%',#{searchKeyword},'%')
							OR
							a.body LIKE CONCAT('%',#{searchKeyword},'%')
						)


					</otherwise>
				</choose>
			</if>
			</script>
			""")
	public int getArticlesCount(int boardId, String searchKeywordTypeCode, String searchKeyword);

	@Update("""
			<script>
			UPDATE article
			SET hitCount = hitCount + 1
			WHERE id = #{id}
			</script>
			""")
	public int increaseHitCount(int id);

	@Select("""
			<script>
			SELECT hitCount
			FROM article
			WHERE id = #{id}
			</script>
			""")
	public int getArticleHitCount(int id);

}