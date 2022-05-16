package com.ldh.exam.demo.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ldh.exam.demo.service.ArticleService;
import com.ldh.exam.demo.service.BoardService;
import com.ldh.exam.demo.util.Ut;
import com.ldh.exam.demo.vo.Article;
import com.ldh.exam.demo.vo.Board;
import com.ldh.exam.demo.vo.ResultData;
import com.ldh.exam.demo.vo.Rq;

@Controller
public class UserArticleController {

	@Autowired
	private ArticleService articleService;
	@Autowired
	private BoardService boardService;

	// 액션 메서드 시작
	@RequestMapping("/user/article/detail")
	public String showDetail(HttpServletRequest req, Model model, int id) {

		Rq rq = (Rq) req.getAttribute("rq");

		Article article = articleService.getForPrintArticle(rq.getLoginedMemberId(), id);

		model.addAttribute("article", article);

		return "user/article/detail";
	}

	@RequestMapping("/user/article/list")
	public String showArticleList(HttpServletRequest req, Model model, int boardId) {

		Board board = boardService.getBoardById(boardId);

		Rq rq = (Rq) req.getAttribute("rq");

		if (board == null) {
			return rq.historyBackJsOnView(Ut.format("%d번 게시판을 찾을 수 없습니다.", boardId));
		}

		List<Article> articles = articleService.getForPrintArticles(rq.getLoginedMemberId(), boardId);

		model.addAttribute("articles", articles);
		model.addAttribute("board", board);

		return "user/article/list";
	}

	@RequestMapping("/user/article/write")
	public String showWrite(HttpServletRequest req, Model model) {

		return "user/article/write";
	}

	@RequestMapping("/user/article/doWrite")
	@ResponseBody
	public String doWrite(HttpServletRequest req, String title, String body, String replaceUri) {

		Rq rq = (Rq) req.getAttribute("rq");

		// 게시글정보 입력 확인
		if (Ut.empty(title)) {
			return rq.jsHistoryBack("제목을 입력해주세요.");
		}
		if (Ut.empty(body)) {
			return rq.jsHistoryBack("내용을 입력해주세요.");
		}

		ResultData<Integer> writeArticleRd = articleService.writeArticle(rq.getLoginedMemberId(), title, body);
		int id = writeArticleRd.getData1();

		if (Ut.empty(replaceUri)) {
			replaceUri = Ut.format("../article/detail?id=%d", id);
		}

		return rq.jsReplace(Ut.format("%d번 게시글이 등록되었습니다.", id), replaceUri);
	}

	@RequestMapping("/user/article/modify")
	public String showModify(HttpServletRequest req, int id, Model model) {

		Rq rq = (Rq) req.getAttribute("rq");

		Article article = articleService.getForPrintArticle(rq.getLoginedMemberId(), id);

		if (article == null) {
			return rq.historyBackJsOnView(Ut.format("%d번 게시글을 찾을 수 없습니다.", id));
		}

		ResultData actorCanModifyRd = articleService.actorCanModify(rq.getLoginedMemberId(), article);

		if (actorCanModifyRd.isFail()) {
			return rq.historyBackJsOnView(actorCanModifyRd.getMsg());
		}

		model.addAttribute("article", article);

		return "user/article/modify";
	}

	@RequestMapping("/user/article/doModify")
	@ResponseBody
	public String doModify(HttpServletRequest req, int id, String title, String body) {

		Rq rq = (Rq) req.getAttribute("rq");

		Article article = articleService.getForPrintArticle(rq.getLoginedMemberId(), id);

		// 게시글 존재여부 및 권한 체크
		if (article == null) {
			return rq.jsHistoryBack(Ut.format("%d번 게시글을 찾을 수 없습니다.", id));
		}

		ResultData actorCanModifyRd = articleService.actorCanModify(rq.getLoginedMemberId(), article);
		if (actorCanModifyRd.isFail()) {
			return rq.jsHistoryBack(actorCanModifyRd.getMsg());
		}

		articleService.modifyArticle(id, title, body);

		return rq.jsReplace(Ut.format("%d번 게시글을 수정했습니다.", id), Ut.format("../article/detail?id=%d", id));
	}

	@RequestMapping("/user/article/doDelete")
	@ResponseBody
	public String doDelete(HttpServletRequest req, int id) {

		Rq rq = (Rq) req.getAttribute("rq");

		Article article = articleService.getForPrintArticle(rq.getLoginedMemberId(), id);

		// 게시글 존재여부 및 권한 체크
		if (article == null) {
			return rq.jsHistoryBack(Ut.format("%d번 게시글을 찾을 수 없습니다.", id));
		}

		if (article.getMemberId() != rq.getLoginedMemberId()) {
			return rq.jsHistoryBack("해당 게시물에 대한 권한이 없습니다.");
		}

		articleService.deleteArticle(id);

		return rq.jsReplace(Ut.format("%d번 게시물을 삭제했습니다.", id), "../article/list");
	}
	// 액션 메서드 끝

}