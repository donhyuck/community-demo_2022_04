package com.ldh.exam.demo.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

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
			ORDER BY a.id DESC
			</script>
			""")
	public List<Article> getArticles(@Param("boardId") int boardId);

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
			</script>
			""")
	public int getArticlesCount(@Param("boardId") int boardId);

}