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