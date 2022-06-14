<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="pageTitle" value="비밀번호 확인" />
<%@ include file="../common/head.jspf"%>
<script src="https://cdnjs.cloudflare.com/ajax/libs/js-sha256/0.9.0/sha256.min.js"></script>

<script>
	let MemberCheckLoginPw__submitDone = false;

	function MemberCheckLoginPw__submit(form) {

		if (MemberCheckLoginPw__submitDone) {
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

		MemberCheckLoginPw__submitDone = true;
		form.submit();
	}
</script>

<section>
  <div class="container mx-auto px-3">
    <form class="table-box-type-1" method="post" action="../member/doCheckPassword"
      onsubmit="MemberCheckLoginPw__submit(this); return false;">
      <input type="hidden" name="replaceUri" value="${ param.replaceUri }" />
      <input type="hidden" name="loginPw">
      <table>
        <colgroup>
          <col width="200" />
        </colgroup>

        <tbody>
          <tr>
            <th>아이디</th>
            <td>${ rq.loginedMember.loginId }</td>
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
        <button class="btn btn-primary" type="submit">비밀번호 확인</button>
      </div>

    </form>
  </div>
</section>
<%@ include file="../common/foot.jspf"%>