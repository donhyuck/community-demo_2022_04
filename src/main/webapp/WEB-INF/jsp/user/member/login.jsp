<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="pageTitle" value="로그인" />
<%@ include file="../common/head.jspf"%>

<script src="https://cdnjs.cloudflare.com/ajax/libs/js-sha256/0.9.0/sha256.min.js"></script>

<script>
	let MemberLogin__submitDone = false;

	function MemberLogin__submit(form) {

		if (MemberLogin__submitDone) {
			alert('처리중입니다.');
			return;
		}

		form.loginId.value = form.loginId.value.trim();
		if (form.loginId.value.length == 0) {
			alert('아이디을 입력해주세요.');
			form.loginId.focus();
			return;
		}

		form.loginPwInput.value = form.loginPwInput.value.trim();
		if (form.loginPwInput.value.length == 0) {
			alert('비밀번호를 입력해주세요.');
			form.loginPwInput.focus();
			return;
		}

		form.loginPw.value = sha256(form.loginPwInput.value);
		form.loginPwInput.value = '';

		MemberLogin__submitDone = true;
		form.submit();
	}
</script>

<section>
  <div class="container mx-auto px-3">
    <form class="table-box-type-1" method="post" action="../member/doLogin"
      onsubmit="MemberLogin__submit(this); return false;">
      <input type="hidden" name="afterLoginUri" value="${ param.afterLoginUri }">
      <input type="hidden" name="loginPw">
      <table>
        <colgroup>
          <col width="200" />
        </colgroup>

        <tbody>
          <tr>
            <th>아이디</th>
            <td>
              <input type="text" class="w-96 input input-bordered" name="loginId" placeholder="로그인 아이디" />
            </td>
          </tr>

          <tr>
            <th>비밀번호</th>
            <td>
              <input type="password" class="w-96 input input-bordered" name="loginPwInput" placeholder="로그인 비밀번호" />
            </td>
          </tr>

        </tbody>
      </table>

      <div class="btns mt-5">
        <button class="btn btn-secondary btn-outline mr-3" type="button" onclick="history.back();">뒤로가기</button>
        <button class="btn btn-primary" type="submit">로그인</button>

        <div class="mt-3">
          <a href="../member/findLoginId" class="btn btn-link btn-sm btn-outline" type="submit">
            <span>
              <i class="fas fa-sign-in-alt"></i>
            </span>
            &nbsp;
            <span>아이디 찾기</span>
          </a>
          <a href="../member/findLoginPw" class="btn btn-link btn-sm btn-outline" type="submit">
            <span>
              <i class="fas fa-sign-in-alt"></i>
            </span>
            &nbsp;
            <span>비밀번호 찾기</span>
          </a>
        </div>

      </div>
    </form>
  </div>
</section>
<%@ include file="../common/foot.jspf"%>