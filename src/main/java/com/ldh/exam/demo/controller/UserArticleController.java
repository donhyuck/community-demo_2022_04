package com.ldh.exam.demo.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ldh.exam.demo.service.ArticleService;
import com.ldh.exam.demo.util.Ut;
import com.ldh.exam.demo.vo.Article;
import com.ldh.exam.demo.vo.ResultData;
import com.ldh.exam.demo.vo.Rq;

@Controller
public class UserArticleController {

	@Autowired
	private ArticleService articleService;

	// 액션 메서드 시작
	@RequestMapping("/user/article/detail")
	public String showDetail(HttpServletRequest req, Model model, int id) {

		Rq rq = (Rq) req.getAttribute("rq");

		Article article = articleService.getForPrintArticle(rq.getLoginedMemberId(), id);

		model.addAttribute("article", article);

		return "user/article/detail";
	}

	@RequestMapping("/user/article/list")
	public String showArticleList(HttpServletRequest req, Model model) {

		Rq rq = (Rq) req.getAttribute("rq");

		List<Article> articles = articleService.getForPrintArticles(rq.getLoginedMemberId());

		model.addAttribute("articles", articles);

		return "user/article/list";
	}

	@RequestMapping("/user/article/doWrite")
	@ResponseBody
	public ResultData<Article> doWrite(HttpServletRequest req, String title, String body) {

		Rq rq = (Rq) req.getAttribute("rq");

		// 게시글정보 입력 확인
		if (Ut.empty(title)) {
			return ResultData.from("F-1", "제목을 입력해주세요.");
		}
		if (Ut.empty(body)) {
			return ResultData.from("F-2", "내용을 입력해주세요.");
		}

		ResultData<Integer> writeArticleRd = articleService.writeArticle(rq.getLoginedMemberId(), title, body);
		int id = writeArticleRd.getData1();

		Article article = articleService.getForPrintArticle(rq.getLoginedMemberId(), id);

		return ResultData.newData(writeArticleRd, "article", article);
	}

	@RequestMapping("/user/article/modify")
	public String modify(HttpServletRequest req, int id) {

		Rq rq = (Rq) req.getAttribute("rq");

		Article article = articleService.getForPrintArticle(rq.getLoginedMemberId(), id);

		if (article == null) {
			return rq.historyBackJsOnView(Ut.format("%d번 게시글을 찾을 수 없습니다.", id));
		}

		ResultData actorCanModifyRd = articleService.actorCanModify(rq.getLoginedMemberId(), article);

		if (actorCanModifyRd.isFail()) {
			return rq.historyBackJsOnView(actorCanModifyRd.getMsg());
		}

		return "user/article/modify";
	}

	@RequestMapping("/user/article/doModify")
	@ResponseBody
	public ResultData<Article> doModify(HttpServletRequest req, int id, String title, String body) {

		Rq rq = (Rq) req.getAttribute("rq");

		Article article = articleService.getForPrintArticle(rq.getLoginedMemberId(), id);

		// 게시글 존재여부 및 권한 체크
		ResultData actorCanModifyRd = articleService.actorCanModify(rq.getLoginedMemberId(), article);

		if (actorCanModifyRd.isFail()) {
			return actorCanModifyRd;
		}

		return articleService.modifyArticle(id, title, body);
	}

	@RequestMapping("/user/article/doDelete")
	@ResponseBody
	public String doDelete(HttpServletRequest req, int id) {

		Rq rq = (Rq) req.getAttribute("rq");

		Article article = articleService.getForPrintArticle(rq.getLoginedMemberId(), id);

		// 게시글 존재여부 및 권한 체크
		if (article == null) {
			return Ut.jsHistoryBack("해당 게시물을 찾을 수 없습니다.");
		}

		if (article.getMemberId() != rq.getLoginedMemberId()) {
			return Ut.jsHistoryBack("해당 게시물에 대한 권한이 없습니다.");
		}

		articleService.deleteArticle(id);

		return Ut.jsReplace(Ut.format("%d번 게시물을 삭제했습니다.", id), "../article/list");
	}
	// 액션 메서드 끝

}