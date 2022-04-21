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

	private void makeTestData() {

		for (int i = 0; i < 10; i++) {

			int id = articlesLastId + 1;
			String title = i + "번 제목";
			String body = "내용" + i;

			Article article = new Article(i, title, body);

			articles.add(article);
			articlesLastId = id;
		}

	}

	@RequestMapping("/user/article/doAdd")
	@ResponseBody
	public Article doAdd(String title, String body) {

		int id = articlesLastId + 1;

		Article article = new Article(id, title, body);

		articles.add(article);
		articlesLastId = id;

		return article;
	}

	@RequestMapping("/user/article/getArticles")
	@ResponseBody
	public List<Article> getArticles() {

		return articles;
	}

}