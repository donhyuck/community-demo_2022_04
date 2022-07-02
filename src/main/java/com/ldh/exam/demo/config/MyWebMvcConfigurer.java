package com.ldh.exam.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.ldh.exam.demo.interceptor.BeforeActionInterceptor;
import com.ldh.exam.demo.interceptor.NeedAdminInterceptor;
import com.ldh.exam.demo.interceptor.NeedLoginInterceptor;
import com.ldh.exam.demo.interceptor.NeedLogoutInterceptor;

@Configuration
public class MyWebMvcConfigurer implements WebMvcConfigurer {

	// beforeActionInterceptor 인터셉터 불러오기
	@Autowired
	BeforeActionInterceptor beforeActionInterceptor;

	// needLoginInterceptor 로그인 인터셉터 불러오기
	@Autowired
	NeedLoginInterceptor needLoginInterceptor;

	// needLogoutInterceptor 로그아웃 인터셉터 불러오기
	@Autowired
	NeedLogoutInterceptor needLogoutInterceptor;

	// needAdminInterceptor 관리자 인터셉터 불러오기
	@Autowired
	NeedAdminInterceptor needAdminInterceptor;

	// 이 함수는 인터셉터를 적용하는 역할을 합니다.
	@Override
	public void addInterceptors(InterceptorRegistry registry) {

		InterceptorRegistration ir;

		// 작업 전 인터셉터
		ir = registry.addInterceptor(beforeActionInterceptor);
		ir.addPathPatterns("/**");
		ir.excludePathPatterns("/favicon.ico");
		ir.excludePathPatterns("/resource/**");
		ir.excludePathPatterns("/error");

		// 로그인 요구 인터셉터
		ir = registry.addInterceptor(needLoginInterceptor);
		ir.addPathPatterns("/user/article/write");
		ir.addPathPatterns("/user/article/doWrite");
		ir.addPathPatterns("/user/article/modify");
		ir.addPathPatterns("/user/article/doModify");
		ir.addPathPatterns("/user/article/doDelete");
		ir.addPathPatterns("/user/reactionPoint/doGoodReaction");
		ir.addPathPatterns("/user/reactionPoint/doBadReaction");
		ir.addPathPatterns("/user/reactionPoint/doCancelGoodReaction");
		ir.addPathPatterns("/user/reactionPoint/doCancelBadReaction");
		ir.addPathPatterns("/user/reply/**");
		ir.addPathPatterns("/user/member/myPage");
		ir.addPathPatterns("/user/member/checkPassword");
		ir.addPathPatterns("/user/member/doCheckPassword");
		ir.addPathPatterns("/user/member/modify");
		ir.addPathPatterns("/user/member/doModify");
		ir.addPathPatterns("/adm/**");
		ir.excludePathPatterns("/adm/member/login");
		ir.excludePathPatterns("/adm/member/doLogin");
		ir.excludePathPatterns("/adm/member/findLoginId");
		ir.excludePathPatterns("/adm/member/doFindLoginId");
		ir.excludePathPatterns("/adm/member/findLoginPw");
		ir.excludePathPatterns("/adm/member/doFindLoginPw");

		// 로그아웃 요구 인터셉터
		ir = registry.addInterceptor(needLogoutInterceptor);
		ir.addPathPatterns("/user/member/join");
		ir.addPathPatterns("/user/member/doJoin");
		ir.addPathPatterns("/user/member/getLoginIdDup");
		ir.addPathPatterns("/user/member/login");
		ir.addPathPatterns("/user/member/doLogin");
		ir.addPathPatterns("/user/member/findLoginId");
		ir.addPathPatterns("/user/member/doFindLoginId");
		ir.addPathPatterns("/user/member/findLoginPw");
		ir.addPathPatterns("/user/member/doFindLoginPw");

		// 관리자 확인 인터셉터
		ir = registry.addInterceptor(needAdminInterceptor);
		ir.addPathPatterns("/adm/**");
		ir.excludePathPatterns("/adm/member/login");
		ir.excludePathPatterns("/adm/member/doLogin");
		ir.excludePathPatterns("/adm/member/join");
		ir.excludePathPatterns("/adm/member/doJoin");
		ir.excludePathPatterns("/adm/member/findLoginId");
		ir.excludePathPatterns("/adm/member/doFindLoginId");
		ir.excludePathPatterns("/adm/member/findLoginPw");
		ir.excludePathPatterns("/adm/member/doFindLoginPw");
	}
}