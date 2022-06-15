<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="pageTitle" value="회원정보 수정" />
<%@ include file="../common/head.jspf"%>
<script src="https://cdnjs.cloudflare.com/ajax/libs/js-sha256/0.9.0/sha256.min.js"></script>

<script>
	let MemberModify__submitDone = false;
	function MemberModify__submit(form) {

		if (MemberModify__submitDone) {
			return;
		}

		form.loginPwInput.value = form.loginPwInput.value.trim();
		if (form.loginPwInput.value.length > 0) {
			form.loginPwConfirm.value = form.loginPwConfirm.value.trim();

			if (form.loginPwConfirm.value.length == 0) {
				alert('새 비밀번호 확인을 입력해주세요.');
				form.loginPwConfirm.focus();
				return;
			}

			if (form.loginPwConfirm.value != form.loginPwInput.value) {
				alert('비밀번호가 일치하지 않습니다.');
				form.loginPwConfirm.focus();
				return;
			}
		}

		form.name.value = form.name.value.trim();
		if (form.name.value.length == 0) {
			alert('이름을 입력해주세요.');
			form.name.focus();
			return;
		}

		form.nickname.value = form.nickname.value.trim();
		if (form.nickname.value.length == 0) {
			alert('별명을 입력해주세요.');
			form.nickname.focus();
			return;
		}

		form.cellPhoneNo.value = form.cellPhoneNo.value.trim();
		if (form.cellPhoneNo.value.length == 0) {
			alert('연락처를 입력해주세요.');
			form.cellPhoneNo.focus();
			return;
		}

		form.email.value = form.email.value.trim();
		if (form.email.value.length == 0) {
			alert('이메일을 입력해주세요.');
			form.email.focus();
			return;
		}

		if (form.loginPwInput.value.length > 0) {
			form.loginPw.value = sha256(form.loginPwInput.value);
			form.loginPwInput.value = '';
			form.loginPwConfirm.value = '';
		}

		MemberModify__submitDone = true;
		form.submit();
	}
</script>

<section>
  <div class="container mx-auto px-3">
    <form class="table-box-type-1" method="post" action="../member/doModify"
      onsubmit="MemberModify__submit(this); return false;">
      <input type="hidden" name="authKeyForMemberModify" value="${ param.authKeyForMemberModify }" />
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
            <th>새 비밀번호</th>
            <td>
              <input type="password" class="input input-bordered w-80" name="loginPwInput" placeholder="새 비밀번호를 입력해주세요."
                maxlength="30" />
            </td>
          </tr>
          <tr>
            <th>새 비밀번호 확인</th>
            <td>
              <input type="password" class="input input-bordered w-80 " name="loginPwConfirm"
                placeholder="다시 비밀번호를 입력해주세요." maxlength="30" />
            </td>
          </tr>
          <tr>
            <th>이름</th>
            <td>
              <input type="text" class="input input-bordered w-80" name="name" value="${ rq.loginedMember.name }"
                placeholder="이름을 입력해주세요." maxlength="30" />
            </td>
          </tr>
          <tr>
            <th>별명</th>
            <td>
              <input type="text" class="input input-bordered w-80" name="nickname"
                value="${ rq.loginedMember.nickname }" placeholder="별명을 입력해주세요." maxlength="30" />
            </td>
          </tr>
          <tr>
            <th>연락처</th>
            <td>
              <input type="tel" class="input input-bordered w-80" name="cellPhoneNo"
                value="${ rq.loginedMember.cellPhoneNo }" placeholder="연락처를 입력해주세요." maxlength="30" />
            </td>
          </tr>
          <tr>
            <th>이메일</th>
            <td>
              <input type="text" class="input input-bordered w-80" name="email" value="${ rq.loginedMember.email }"
                placeholder="이메일을 입력해주세요." maxlength="50" />
            </td>
          </tr>
        </tbody>
      </table>

      <div class="btns mt-5">
        <button class="btn btn-secondary btn-outline mr-3" type="button" onclick="history.back();">뒤로가기</button>
        <button class="btn btn-primary" type="submit">
          <span>
            <i class="fas fa-user-plus"></i>
          </span>
          회원정보수정
        </button>
      </div>

    </form>
  </div>
</section>
<%@ include file="../common/foot.jspf"%>