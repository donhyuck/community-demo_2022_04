package com.ldh.exam.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserHomeController {
	private int count;

	@RequestMapping("/user/home/test")
	@ResponseBody
	public String showMain1() {
		return "UserHomeController 출력 테스트.";
	}

	@RequestMapping("/user/home/getCount")
	@ResponseBody
	public int getCount() {
		return count;
	}

	// http://localhost:8011/user/home/doSetCount?count=
	@RequestMapping("/user/home/doSetCount")
	@ResponseBody
	public String showMain5(int count) {
		this.count = count;
		return "count값을 " + count + "으로 설정";
	}

}
