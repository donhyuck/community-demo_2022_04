<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="pageTitle" value="회원가입" />
<%@ include file="../common/head.jspf"%>

<script src="https://cdnjs.cloudflare.com/ajax/libs/lodash.js/4.17.21/lodash.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/js-sha256/0.9.0/sha256.min.js"></script>

<script>
	let MemberJoin__submitDone = false;
	let validLoginId = "";

	function MemberJoin__submit(form) {

		if (MemberJoin__submitDone) {
			alert('처리중입니다.');
			return;
		}

		form.loginId.value = form.loginId.value.trim();
		if (form.loginId.value.length == 0) {
			alert('아이디을 입력해주세요.');
			form.loginId.focus();
			return;
		}

		if (form.loginId.value != validLoginId) {
			alert('해당 로그인아이디는 이미 사용중이거나 유효하지 않습니다.');
			form.loginId.focus();
			return;
		}

		form.loginPw.value = form.loginPw.value.trim();
		if (form.loginPw.value.length == 0) {
			alert('비밀번호를 입력해주세요.');
			form.loginPw.focus();
			return;
		}

		form.loginPwConfirm.value = form.loginPwConfirm.value.trim();
		if (form.loginPwConfirm.value.length == 0) {
			alert('비밀번호 확인을 입력해주세요.');
			form.loginPwConfirm.focus();
			return;
		}

		if (form.loginPw.value != form.loginPwConfirm.value) {
			alert('비밀번호가 일치하지 않습니다.');
			form.loginPw.focus();
			return;
		}

		form.name.value = form.name.value.trim();
		if (form.name.value.length == 0) {
			alert('이름을 입력해주세요.');
			form.name.focus();
			return;
		}

		form.nickname.value = form.nickname.value.trim();
		if (form.nickname.value.length == 0) {
			alert('닉네임을 입력해주세요.');
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

		MemberJoin__submitDone = true;
		form.submit();
	}

	function checkLoginIdDup(el) {
		const form = $(el).closest('form').get(0);

		if (form.loginId.value.length == 0) {
			validLoginId = '';
			return;
		}

		if (validLoginId == form.loginId.value) {
			return;
		}
		$('.loginId-msg').html('<div class="mt-2">확인중..</div>');

		$.get('../member/getLoginIdDup', {
			isAjax : 'Y',
			loginId : form.loginId.value
		}, function(data) {
			$('.loginId-msg').html('<div class="mt-2">' + data.msg + '</div>');

			if (data.success) {
				validLoginId = data.data1;
			} else {
				validLoginId = '';
			}
		}, 'json');
	}

	const checkLoginIdDupDebounced = _.debounce(checkLoginIdDup, 300);
</script>

<section>
  <div class="container mx-auto px-3">
    <form class="table-box-type-1" method="post" action="../member/doJoin"
      onsubmit="MemberJoin__submit(this); return false;">
      <input type="hidden" name="afterLoginUri" value="${ param.afterLoginUri }">
      <table>
        <colgroup>
          <col width="200" />
        </colgroup>

        <tbody>
          <tr>
            <th>아이디</th>
            <td>
              <input type="text" class="w-96 input input-bordered" name="loginId" placeholder="로그인 아이디"
                onkeyup="checkLoginIdDupDebounced(this);" autocomplete="off" />
              <div class="loginId-msg"></div>
            </td>
          </tr>

          <tr>
            <th>비밀번호</th>
            <td>
              <input type="password" class="w-96 input input-bordered" name="loginPw" placeholder="로그인 비밀번호" />
            </td>
          </tr>

          <tr>
            <th>비밀번호 확인</th>
            <td>
              <input type="password" class="w-96 input input-bordered" name="loginPwConfirm" placeholder="로그인 비밀번호 확인" />
            </td>
          </tr>

          <tr>
            <th>이름</th>
            <td>
              <input type="text" class="w-96 input input-bordered" name="name" placeholder="이름" />
            </td>
          </tr>

          <tr>
            <th>닉네임</th>
            <td>
              <input type="text" class="w-96 input input-bordered" name="nickname" placeholder="닉네임" />
            </td>
          </tr>

          <tr>
            <th>연락처</th>
            <td>
              <input type="text" class="w-96 input input-bordered" name="cellPhoneNo"
                placeholder="하이픈(-)제외 예) 010********" />
            </td>
          </tr>

          <tr>
            <th>이메일</th>
            <td>
              <input type="email" class="w-96 input input-bordered" name="email" placeholder="예) abc@test.com" />
            </td>
          </tr>

        </tbody>
      </table>

      <div class="btns mt-5">
        <button class="btn btn-secondary btn-outline mr-3" type="button" onclick="history.back();">뒤로가기</button>
        <button class="btn btn-primary" type="submit">회원가입</button>
      </div>

    </form>
  </div>
</section>
<%@ include file="../common/foot.jspf"%>