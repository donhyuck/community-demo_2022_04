package com.ldh.exam.demo.repository;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import com.ldh.exam.demo.vo.Article;

@Mapper
public interface ArticleRepository {

	@Select("select * from article where id = #{id}")
	public Article getArticle(@Param("id") int id);

	// SELECT * FROM article ORDER BY id DESC;
	public List<Article> getArticles();

	// INSERT INTO article SET regDate = NOW(), updateDate = NOW(), title = ?, `body` = ?;
	public Article writeArticle(String title, String body);

	// UPDATE article SET title = ?, `body` = ?, updateDate = NOW() WHERE id = ?;
	public void modifyArticle(int id, String title, String body);

	// DELETE FROM article WHERE id = ?;
	public void deleteArticle(int id);

}
