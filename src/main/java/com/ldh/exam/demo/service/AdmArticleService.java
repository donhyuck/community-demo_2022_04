package com.ldh.exam.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ldh.exam.demo.repository.AdmArticleRepository;
import com.ldh.exam.demo.util.Ut;
import com.ldh.exam.demo.vo.Article;
import com.ldh.exam.demo.vo.Member;
import com.ldh.exam.demo.vo.ResultData;

@Service
public class AdmArticleService {

	private AdmArticleRepository admArticleRepository;

	public AdmArticleService(AdmArticleRepository admArticleRepository) {
		this.admArticleRepository = admArticleRepository;
	}

	public Article getForPrintArticle(int id) {

		Article article = admArticleRepository.getForPrintArticle(id);
		return article;
	}

	public List<Article> getForPrintArticles(int actorId, int boardId, String searchKeywordTypeCode,
			String searchKeyword, int itemsCountInAPage, int page) {

		int limitStart = (page - 1) * itemsCountInAPage;
		int limitTake = itemsCountInAPage;

		List<Article> articles = admArticleRepository.getForPrintArticles(boardId, searchKeywordTypeCode, searchKeyword,
				limitStart, limitTake);

		return articles;
	}

	public ResultData<Article> modifyArticle(int id, String title, String body) {

		admArticleRepository.modifyArticle(id, title, body);

		Article article = getForPrintArticle(id);

		return ResultData.from("S-1", Ut.format("%d번 게시물을 수정했습니다.", id), "article", article);
	}

	public void deleteArticle(int id) {
		admArticleRepository.deleteArticle(id);
	}

	public int getArticlesCount(int boardId, String searchKeywordTypeCode, String searchKeyword) {
		return admArticleRepository.getArticlesCount(boardId, searchKeywordTypeCode, searchKeyword);
	}

	public Article getArticle(int id) {
		return admArticleRepository.getArticle(id);
	}

	public void deleteArticles(List<Integer> articleIds) {

		for (int articleId : articleIds) {
			Article article = getArticle(articleId);

			if (article != null) {
				deleteArticle(articleId);
			}
		}
	}
}