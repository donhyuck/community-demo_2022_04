package com.ldh.exam.demo.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

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
	public ResultData<Article> getArticle(int id) {

		Article article = articleService.getArticle(id);

		if (article == null) {
			return ResultData.from("F-1", Ut.format("%d번 게시물을 찾을 수 없습니다.", id));
		}

		return ResultData.from("S-1", Ut.format("%d번 게시물입니다.", id), article);
	}

	@RequestMapping("/user/article/getArticles")
	@ResponseBody
	public ResultData<List<Article>> getArticles() {

		List<Article> articles = articleService.getArticles();

		return ResultData.from("S-1", "게시물 목록입니다.", articles);
	}

	@RequestMapping("/user/article/doAdd")
	@ResponseBody
	public ResultData<Article> doAdd(HttpSession httpSession, String title, String body) {

		// 로그인 확인
		boolean isLogined = false;
		int loginedMemberId = 0;

		if (httpSession.getAttribute("loginedMemberId") != null) {
			isLogined = true;
			loginedMemberId = (int) httpSession.getAttribute("loginedMemberId");
		}

		if (isLogined == false) {
			return ResultData.from("F-A", "로그인 후 이용해주세요.");
		}

		// 게시글정보 입력 확인
		if (Ut.empty(title)) {
			return ResultData.from("F-1", "제목을 입력해주세요.");
		}
		if (Ut.empty(body)) {
			return ResultData.from("F-2", "내용을 입력해주세요.");
		}

		ResultData<Integer> writeArticleRd = articleService.writeArticle(loginedMemberId, title, body);
		int id = writeArticleRd.getData1();

		Article article = articleService.getArticle(id);

		return ResultData.newData(writeArticleRd, article);
	}

	@RequestMapping("/user/article/doModify")
	@ResponseBody
	public ResultData<Integer> doModify(HttpSession httpSession, int id, String title, String body) {

		// 로그인 확인
		boolean isLogined = false;
		int loginedMemberId = 0;

		if (httpSession.getAttribute("loginedMemberId") != null) {
			isLogined = true;
			loginedMemberId = (int) httpSession.getAttribute("loginedMemberId");
		}

		if (isLogined == false) {
			return ResultData.from("F-A", "로그인 후 이용해주세요.");
		}

		Article article = articleService.getArticle(id);

		if (article == null) {
			ResultData.from("F-1", Ut.format("%d번 게시물을 찾을 수 없습니다.", id));
		}

		// 권한 체크
		if (article.getMemberId() != loginedMemberId) {
			return ResultData.from("F-2", "해당 게시글에 대한 권한이 없습니다.");
		}

		articleService.modifyArticle(id, title, body);

		return ResultData.from("S-1", Ut.format("%d번 게시물을 수정했습니다.", id), id);
	}

	@RequestMapping("/user/article/doDelete")
	@ResponseBody
	public ResultData<Integer> doDelete(HttpSession httpSession, int id) {

		// 로그인 확인
		boolean isLogined = false;
		int loginedMemberId = 0;

		if (httpSession.getAttribute("loginedMemberId") != null) {
			isLogined = true;
			loginedMemberId = (int) httpSession.getAttribute("loginedMemberId");
		}

		if (isLogined == false) {
			return ResultData.from("F-A", "로그인 후 이용해주세요.");
		}

		Article article = articleService.getArticle(id);

		if (article == null) {
			ResultData.from("F-1", Ut.format("%d번 게시물을 찾을 수 없습니다.", id));
		}

		// 권한 체크
		if (article.getMemberId() != loginedMemberId) {
			return ResultData.from("F-2", "해당 게시글에 대한 권한이 없습니다.");
		}

		articleService.deleteArticle(id);

		return ResultData.from("S-1", Ut.format("%d번 게시물을 삭제했습니다.", id), id);
	}
	// 액션 메서드 끝

}