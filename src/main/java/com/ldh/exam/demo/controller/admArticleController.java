package com.ldh.exam.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ldh.exam.demo.service.AdmArticleService;
import com.ldh.exam.demo.service.BoardService;
import com.ldh.exam.demo.service.ReactionPointService;
import com.ldh.exam.demo.service.ReplyService;
import com.ldh.exam.demo.util.Ut;
import com.ldh.exam.demo.vo.Article;
import com.ldh.exam.demo.vo.Board;
import com.ldh.exam.demo.vo.Reply;
import com.ldh.exam.demo.vo.Rq;

@Controller
public class admArticleController {

	@Autowired
	private AdmArticleService admArticleService;
	@Autowired
	private ReactionPointService reactionPointService;
	@Autowired
	private BoardService boardService;
	@Autowired
	private ReplyService replyService;
	@Autowired
	private Rq rq;

	// 액션 메서드 시작
	@RequestMapping("/adm/article/detail")
	public String showDetail(Model model, int id) {

		Article article = admArticleService.getForPrintArticle(id);
		model.addAttribute("article", article);

		List<Reply> replies = replyService.getForPrintReplies(rq.getLoginedMember(), "article", id);
		model.addAttribute("replies", replies);

		return "adm/article/detail";
	}

	@RequestMapping("/adm/article/list")
	public String showArticleList(Model model, @RequestParam(defaultValue = "1") int boardId,
			@RequestParam(defaultValue = "title,body") String searchKeywordTypeCode,
			@RequestParam(defaultValue = "") String searchKeyword, @RequestParam(defaultValue = "1") int page) {

		Board board = boardService.getBoardById(boardId);

		if (board == null) {
			return rq.historyBackJsOnView(Ut.format("%d번 게시판을 찾을 수 없습니다.", boardId));
		}

		int articlesCount = admArticleService.getArticlesCount(boardId, searchKeywordTypeCode, searchKeyword);

		int itemsCountInAPage = 10;
		int pagesCount = (int) Math.ceil((double) articlesCount / itemsCountInAPage);

		List<Article> articles = admArticleService.getForPrintArticles(rq.getLoginedMemberId(), boardId,
				searchKeywordTypeCode, searchKeyword, itemsCountInAPage, page);

		model.addAttribute("articles", articles);
		model.addAttribute("articlesCount", articlesCount);
		model.addAttribute("pagesCount", pagesCount);
		model.addAttribute("board", board);
		model.addAttribute("boardId", boardId);
		model.addAttribute("page", page);

		return "adm/article/list";
	}

	@RequestMapping("/adm/article/modify")
	public String showModify(int id, Model model) {

		Article article = admArticleService.getForPrintArticle(id);

		// 게시글 존재여부 체크
		if (article == null) {
			return rq.jsHistoryBack("F-1", Ut.format("%d번 게시글을 찾을 수 없습니다.", id));
		}

		model.addAttribute("article", article);

		return "adm/article/modify";
	}

	@RequestMapping("/adm/article/doModify")
	@ResponseBody
	public String doModify(int id, String title, String body) {

		Article article = admArticleService.getForPrintArticle(id);

		// 게시글 존재여부 체크
		if (article == null) {
			return rq.jsHistoryBack("F-1", Ut.format("%d번 게시글을 찾을 수 없습니다.", id));
		}

		admArticleService.modifyArticle(id, title, body);

		return rq.jsReplace(Ut.format("%d번 게시글을 수정했습니다.", id), Ut.format("../article/detail?id=%d", id));
	}

	@RequestMapping("/adm/article/doDelete")
	@ResponseBody
	public String doDelete(int id) {

		Article article = admArticleService.getForPrintArticle(id);

		// 게시글 존재여부 체크
		if (article == null) {
			return rq.jsHistoryBack(Ut.format("%d번 게시글을 찾을 수 없습니다.", id));
		}

		admArticleService.deleteArticle(id);

		return rq.jsReplace(Ut.format("%d번 게시물을 삭제했습니다.", id), "../article/list");
	}
	// 액션 메서드 끝
}