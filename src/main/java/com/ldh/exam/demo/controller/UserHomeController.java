package com.ldh.exam.demo.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Controller
public class UserHomeController {
	private int count;

	@RequestMapping("/user/home/test")
	@ResponseBody
	public String showMain1() {
		return "UserHomeController 출력 테스트.";
	}

	// 리턴타입 실험
	@RequestMapping("/user/home/getString")
	@ResponseBody
	public String getString() {
		return "HI";
	}

	@RequestMapping("/user/home/getCharacter")
	@ResponseBody
	public char getCharacter() {
		return 'H';
	}

	@RequestMapping("/user/home/getInt")
	@ResponseBody
	public int getInt() {
		return 10;
	}

	@RequestMapping("/user/home/getFloat")
	@ResponseBody
	public float getFloat() {
		return 10.7f;
	}

	@RequestMapping("/user/home/getDouble")
	@ResponseBody
	public double getDouble() {
		return 10.7;
	}

	@RequestMapping("/user/home/getBoolean")
	@ResponseBody
	public boolean getBoolean() {
		return true;
	}

	@RequestMapping("/user/home/getMap")
	@ResponseBody
	public Map<String, Object> getMap() {

		Map<String, Object> map = new HashMap<>();

		map.put("철수나이", 23);
		map.put("영희나이", 21);

		return map;
	}

	@RequestMapping("/user/home/getList")
	@ResponseBody
	public List<String> getList() {

		List<String> list = new ArrayList<>();

		list.add("철수");
		list.add("영희");

		return list;
	}

	@RequestMapping("/user/home/getArticle")
	@ResponseBody
	public Article getArticle() {

		Article article = new Article(1, "제목1");

		return article;
	}

	@RequestMapping("/user/home/getArticles")
	@ResponseBody
	public List<Article> getArticles() {

		Article article1 = new Article(1, "제목1");
		Article article2 = new Article(2, "제목2");

		List<Article> articleList = new ArrayList<>();
		articleList.add(article1);
		articleList.add(article2);
		
		return articleList;
	}

}

@Data
@NoArgsConstructor
@AllArgsConstructor
class Article {

	private int id;
	private String title;
}
