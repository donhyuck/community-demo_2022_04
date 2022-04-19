package com.ldh.exam.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserHomeController {
	private int count;

	@RequestMapping("/user/home/main1")
	@ResponseBody
	public String showMain1() {
		return "안녕하세요.";
	}

	@RequestMapping("/user/home/main2")
	@ResponseBody
	public String showMain2() {
		return "반갑습니다.";
	}

	@RequestMapping("/user/home/main3")
	@ResponseBody
	public String showMain3() {
		return "또 만나요.";
	}

	@RequestMapping("/user/home/main4")
	@ResponseBody
	public int showMain4() {
		count++;
		return count;
	}

	@RequestMapping("/user/home/main5")
	@ResponseBody
	public String showMain5() {
		count = 0;
		return "count값을 0으로 초기화";
	}

}
