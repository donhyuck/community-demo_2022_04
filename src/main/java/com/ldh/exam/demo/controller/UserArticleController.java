package com.ldh.exam.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ldh.exam.demo.vo.Article;

@Controller
public class UserArticleController {

	private int articlesLastId;
	private List<Article> articles;

	public UserArticleController() {

		articlesLastId = 0;
		articles = new ArrayList<>();

		makeTestData();

	}

	// 서비스 메서드 시작
	private void makeTestData() {

		for (int i = 1; i <= 10; i++) {

			String title = i + "번 제목";
			String body = "내용" + i;

			writeArticle(title, body);
		}

	}

	private Article getArticle(int id) {

		for (Article article : articles) {
			if (article.getId() == id) {
				return article;
			}
		}

		return null;
	}

	private Article writeArticle(String title, String body) {

		int id = articlesLastId + 1;

		Article article = new Article(id, title, body);

		articles.add(article);
		articlesLastId = id;

		return article;
	}

	private void deleteArticle(int id) {

		Article article = getArticle(id);

		articles.remove(article);
	}

	// 서비스 메서드 끝

	// 액션 메서드 시작
	@RequestMapping("/user/article/doAdd")
	@ResponseBody
	public Article doAdd(String title, String body) {

		Article article = writeArticle(title, body);

		return article;
	}

	@RequestMapping("/user/article/doDelete")
	@ResponseBody
	public String doDelete(int id) {

		Article article = getArticle(id);

		if (article == null) {
			return id + "번 게시물을 찾을 수 없습니다.";
		}

		deleteArticle(id);

		return null;
	}

	@RequestMapping("/user/article/getArticles")
	@ResponseBody
	public List<Article> getArticles() {

		return articles;
	}

	// 액션 메서드 끝

}