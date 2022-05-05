package com.ldh.exam.demo.repository;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.ldh.exam.demo.vo.Article;

@Mapper
public interface ArticleRepository {

	public Article getArticle(@Param("id") int id);

	@Select("""
			SELECT a.*, m.nickname AS extra__writerName
			FROM article AS a
			LEFT JOIN `member` AS m
			ON a.memberId = m.id
			ORDER BY a.id DESC
			""")
	public List<Article> getArticles();

	public void writeArticle(@Param("memberId") int memberId, @Param("title") String title, @Param("body") String body);

	public void modifyArticle(@Param("id") int id, @Param("title") String title, @Param("body") String body);

	public void deleteArticle(@Param("id") int id);

	public int getLastInsertId();

}