package com.ldh.exam.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ldh.exam.demo.service.ArticleService;
import com.ldh.exam.demo.util.Ut;
import com.ldh.exam.demo.vo.Article;
import com.ldh.exam.demo.vo.ResultData;

@Controller
public class UserArticleController {

	@Autowired
	private ArticleService articleService;

	// 액션 메서드 시작
	@RequestMapping("/user/article/getArticle")
	@ResponseBody
	public ResultData getArticle(int id) {

		Article article = articleService.getArticle(id);

		if (article == null) {
			return ResultData.from("F-1", Ut.format("%d번 게시물을 찾을 수 없습니다.", id));
		}

		return ResultData.from("S-1", Ut.format("%d번 게시물입니다.", id));
	}

	@RequestMapping("/user/article/getArticles")
	@ResponseBody
	public ResultData getArticles() {

		List<Article> articles = articleService.getArticles();

		return ResultData.from("S-1", "게시물 목록입니다.", articles);
	}

	@RequestMapping("/user/article/doAdd")
	@ResponseBody
	public Article doAdd(String title, String body) {

		int id = articleService.writeArticle(title, body);

		Article article = articleService.getArticle(id);

		return article;
	}

	@RequestMapping("/user/article/doModify")
	@ResponseBody
	public String doModify(int id, String title, String body) {

		Article article = articleService.getArticle(id);

		if (article == null) {
			return id + "번 게시물을 찾을 수 없습니다.";
		}

		articleService.modifyArticle(id, title, body);

		return id + "번 게시물을 수정했습니다.";
	}

	@RequestMapping("/user/article/doDelete")
	@ResponseBody
	public String doDelete(int id) {

		Article article = articleService.getArticle(id);

		if (article == null) {
			return id + "번 게시물을 찾을 수 없습니다.";
		}

		articleService.deleteArticle(id);

		return id + "번 게시물을 삭제했습니다.";
	}
	// 액션 메서드 끝

}