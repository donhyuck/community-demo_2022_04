package com.ldh.exam.demo.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
	@RequestMapping("/user/article/detail")
	public String showDetail(HttpSession httpSession, Model model, int id) {

		// 로그인 확인
		boolean isLogined = false;
		int loginedMemberId = 0;

		if (httpSession.getAttribute("loginedMemberId") != null) {
			isLogined = true;
			loginedMemberId = (int) httpSession.getAttribute("loginedMemberId");
		}

		Article article = articleService.getForPrintArticle(loginedMemberId, id);

		model.addAttribute("article", article);

		return "user/article/detail";
	}

	@RequestMapping("/user/article/list")
	public String showArticleList(HttpSession httpSession, Model model) {

		// 로그인 확인
		boolean isLogined = false;
		int loginedMemberId = 0;

		if (httpSession.getAttribute("loginedMemberId") != null) {
			isLogined = true;
			loginedMemberId = (int) httpSession.getAttribute("loginedMemberId");
		}

		List<Article> articles = articleService.getForPrintArticles(loginedMemberId);

		model.addAttribute("articles", articles);

		return "user/article/list";
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

		Article article = articleService.getForPrintArticle(loginedMemberId, id);

		return ResultData.newData(writeArticleRd, "article", article);
	}

	@RequestMapping("/user/article/doModify")
	@ResponseBody
	public ResultData<Article> doModify(HttpSession httpSession, int id, String title, String body) {

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

		Article article = articleService.getForPrintArticle(loginedMemberId, id);

		// 게시글 존재여부 및 권한 체크
		ResultData actorCanModifyRd = articleService.actorCanModify(loginedMemberId, article);

		if (actorCanModifyRd.isFail()) {
			return actorCanModifyRd;
		}

		return articleService.modifyArticle(id, title, body);
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

		Article article = articleService.getForPrintArticle(loginedMemberId, id);

		// 게시글 존재여부 및 권한 체크
		ResultData actorCanModifyRd = articleService.actorCanModify(loginedMemberId, article);

		if (actorCanModifyRd.isFail()) {
			return actorCanModifyRd;
		}

		articleService.deleteArticle(id);

		return ResultData.from("S-1", Ut.format("%d번 게시물을 삭제했습니다.", id), "id", id);
	}
	// 액션 메서드 끝

}