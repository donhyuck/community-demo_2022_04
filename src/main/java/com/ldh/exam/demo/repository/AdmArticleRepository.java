package com.ldh.exam.demo.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.ldh.exam.demo.vo.Article;

@Mapper
public interface AdmArticleRepository {

	public Article getForPrintArticle(@Param("id") int id);

	public List<Article> getForPrintArticles(@Param("boardId") int boardId,
			@Param("searchKeywordTypeCode") String searchKeywordTypeCode, @Param("searchKeyword") String searchKeyword,
			@Param("limitStart") int limitStart, @Param("limitTake") int limitTake);

	public void modifyArticle(@Param("id") int id, @Param("title") String title, @Param("body") String body);

	public void deleteArticle(@Param("id") int id);

	public int getArticlesCount(@Param("boardId") int boardId,
			@Param("searchKeywordTypeCode") String searchKeywordTypeCode, @Param("searchKeyword") String searchKeyword);

	@Select("""
			<script>
			SELECT *
			FROM article
			WHERE id = #{id}
			</script>
			""")
	public Article getArticle(int id);
}