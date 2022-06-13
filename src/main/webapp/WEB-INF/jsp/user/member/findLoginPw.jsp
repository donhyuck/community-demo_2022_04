<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="pageTitle" value="비밀번호 찾기 " />
<%@ include file="../common/head.jspf"%>

<script>
	let MemberFindLoginPw__submitDone = false;
	function MemberFindLoginPw__submit(form) {

		if (MemberFindLoginPw__submitDone) {
			return;
		}

		form.loginId.value = form.loginId.value.trim();
		if (form.loginId.value.length == 0) {
			alert('아이디를 입력해주세요.');
			form.loginId.focus();
			return;
		}

		form.name.value = form.name.value.trim();
		if (form.name.value.length == 0) {
			alert('이름을 입력해주세요.');
			form.name.focus();
			return;
		}

		form.email.value = form.email.value.trim();
		if (form.email.value.length == 0) {
			alert('이메일을 입력해주세요.');
			form.email.focus();
			return;
		}

		MemberFindLoginPw__submitDone = true;
		form.submit();
	}
</script>

<section>
  <div class="container mx-auto px-3">
    <form class="table-box-type-1" method="post" action="../member/doFindLoginPw"
      onsubmit="MemberFindLoginPw__submit(this); return false;">
      <table>
        <colgroup>
          <col width="200" />
        </colgroup>

        <tbody>
          <tr>
            <th>아이디</th>
            <td>
              <input type="text" class="w-96 input input-bordered" name="loginId" placeholder="아이디" maxlength="30" />
            </td>
          </tr>

          <tr>
            <th>이름</th>
            <td>
              <input type="text" class="w-96 input input-bordered" name="name" placeholder="이름" maxlength="30" />
            </td>
          </tr>

          <tr>
            <th>이메일</th>
            <td>
              <input type="email" class="w-96 input input-bordered" name="email" placeholder="이메일" maxlength="100" />
            </td>
          </tr>

        </tbody>
      </table>

      <div class="btns mt-5">
        <button class="btn btn-secondary btn-outline mr-3" type="button" onclick="history.back();">뒤로가기</button>
        <button class="btn btn-primary" type="submit">비밀번호 찾기</button>
        <div class="mt-3">
          <a href="../member/findLoginId" class="btn btn-link btn-sm btn-outline" type="submit">
            <span>
              <i class="fas fa-sign-in-alt"></i>
            </span>
            &nbsp;
            <span>아이디 찾기</span>
          </a>
        </div>
      </div>
    </form>
  </div>
</section>
<%@ include file="../common/foot.jspf"%>