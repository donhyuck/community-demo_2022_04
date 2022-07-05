package com.ldh.exam.demo.vo;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import com.ldh.exam.demo.service.MemberService;
import com.ldh.exam.demo.util.Ut;

import lombok.Getter;

@Component
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class Rq {

	@Getter
	private boolean isLogined;
	@Getter
	private boolean isExpiredPassword;
	@Getter
	private Member loginedMember;
	@Getter
	private int loginedMemberId;
	@Getter
	private boolean isAjax;

	private HttpServletRequest req;
	private HttpServletResponse resp;
	private HttpSession session;
	private Map<String, String> paramMap;

	public Rq(HttpServletRequest req, HttpServletResponse resp, MemberService memberService) {

		this.req = req;
		this.resp = resp;
		this.session = req.getSession();
		paramMap = Ut.getParamMap(req);

		boolean isLogined = false;
		boolean isExpiredPassword = false;
		Member loginedMember = null;
		int loginedMemberId = 0;

		if (session.getAttribute("loginedMemberId") != null) {
			isLogined = true;
			loginedMemberId = (int) session.getAttribute("loginedMemberId");
		}

		if (loginedMemberId != 0) {
			String loginedMemberJsonStr = (String) session.getAttribute("loginedMemberJsonStr");
			loginedMember = Ut.fromJsonStr(loginedMemberJsonStr, Member.class);
		}

		// 로그인한 회원중 비밀번호 만료기간이 넘은 사람이 있는가
		if (loginedMember != null) {

			if (session.getAttribute("isExpiredPassword") == null) {

				isExpiredPassword = memberService.isExpiredPassword(loginedMemberId);
				session.setAttribute("isExpiredPassword", isExpiredPassword);
			}

			isExpiredPassword = (boolean) session.getAttribute("isExpiredPassword");
		}

		this.isLogined = isLogined;
		this.loginedMemberId = loginedMemberId;
		this.loginedMember = loginedMember;
		this.isExpiredPassword = isExpiredPassword;

		// 해당 요청이 ajax 요청인지 아닌지 체크
		String requestUri = req.getRequestURI();
		boolean isAjax = requestUri.endsWith("Ajax");

		if (isAjax == false) {
			if (paramMap.containsKey("ajax") && paramMap.get("ajax").equals("Y")) {
				isAjax = true;
			} else if (paramMap.containsKey("isAjax") && paramMap.get("isAjax").equals("Y")) {
				isAjax = true;
			}
		}

		if (isAjax == false) {
			if (requestUri.contains("/get")) {
				isAjax = true;
			}
		}

		this.isAjax = isAjax;
	}

	public void printHistoryBackJs(String msg) throws IOException {

		resp.setContentType("text/html; charset=UTF-8");

		print(Ut.jsHistoryBack(msg));
	}

	public void printReplaceJs(String msg, String url) throws IOException {

		resp.setContentType("text/html; charset=UTF-8");

		print(Ut.jsReplace(msg, url));
	}

	public void print(String str) throws IOException {
		resp.getWriter().append(str);
	}

	public void println(String str) throws IOException {
		print(str + "\n");
	}

	public void login(Member member) {
		session.setAttribute("loginedMemberId", member.getId());
		session.setAttribute("loginedMemberJsonStr", member.toJsonStr());
	}

	public boolean isNotLogined() {
		return !isLogined;
	}

	public void logout() {
		session.removeAttribute("loginedMemberId");
	}

	public String historyBackJsOnView(String msg) {
		req.setAttribute("msg", msg);
		req.setAttribute("historyBack", true);
		return "common/js";
	}

	public String historyBackJsOnView(String resultCode, String msg) {
		req.setAttribute("msg", String.format("[%s] %s", resultCode, msg));
		req.setAttribute("historyBack", true);
		return "common/js";
	}

	public String jsHistoryBack(String msg) {
		return Ut.jsHistoryBack(msg);
	}

	public String jsHistoryBack(String resultCode, String msg) {
		msg = String.format("[%s] %s", resultCode, msg);
		return Ut.jsHistoryBack(msg);
	}

	public String jsReplace(String msg, String uri) {
		return Ut.jsReplace(msg, uri);
	}

	public String getCurrentUri() {

		String currentUri = req.getRequestURI();
		String queryString = req.getQueryString();

		if (queryString != null && queryString.length() > 0) {
			currentUri += "?" + queryString;
		}

		return currentUri;
	}

	public String getEncodedCurrentUri() {
		return Ut.getUriEncoded(getCurrentUri());
	}

	public String getLoginUri() {
		return "/user/member/login?afterLoginUri=" + getAfterLoginUri();
	}

	public String getJoinUri() {
		return "/user/member/join?afterLoginUri=" + getAfterLoginUri();
	}

	public String getLogoutUri() {

		String requestUri = req.getRequestURI();

		switch (requestUri) {
		case "/user/member/logout":
			return "";
		}

		return "/user/member/doLogout?afterLogoutUri=" + getAfterLogoutUri();
	}

	public String getAfterLoginUri() {

		String requestUri = req.getRequestURI();

		switch (requestUri) {
		case "/user/member/login":
		case "/user/member/join":
		case "/user/member/findLoginId":
		case "/user/member/findLoginPw":
			return Ut.getUriEncoded(Ut.getStrAttr(paramMap, "afterLoginUri", ""));
		}

		return getEncodedCurrentUri();
	}

	private String getAfterLogoutUri() {

		return getEncodedCurrentUri();
	}

	public String getArticleDetailUriFromArticleList(Article article) {
		return "/user/article/detail?id=" + article.getId() + "&listUri=" + getEncodedCurrentUri();
	}

	public boolean isAdmin() {

		if (isLogined == false) {
			return false;
		}

		if (loginedMember.getAuthLevel() == 7) {
			return true;
		}

		return false;
	}

	public String getParamJsonStr() {
		return Ut.toJsonStr(paramMap);
	}
}