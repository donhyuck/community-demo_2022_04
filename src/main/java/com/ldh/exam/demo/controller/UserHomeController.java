package com.ldh.exam.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserHomeController {
	@RequestMapping("/user/home/main")
	@ResponseBody
	public String showMain() {
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

}
