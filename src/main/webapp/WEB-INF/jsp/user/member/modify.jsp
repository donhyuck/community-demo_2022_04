<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="pageTitle" value="회원정보 수정" />
<%@ include file="../common/head.jspf"%>

<script>
	let MemberModify__submitDone = false;
	function MemberModify__submit(form) {

		if (MemberModify__submitDone) {
			return;
		}

		form.body.value = form.body.value.trim();

		if (form.body.value.length == 0) {
			alert('수정내용을 입력해주세요.')
			form.body.focus();
			return;
		}

		MemberModify__submitDone = true;
		form.submit();
	}
</script>

<section>
  <div class="container mx-auto px-3">
    <form class="table-box-type-1" method="post" action="../member/doModify"
      onsubmit="MemberModify__submitDone(this); return false;">
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
              <input type="password" class="input input-bordered w-80" name="loginPw" placeholder="새 비밀번호를 입력해주세요." />
            </td>
          </tr>
          <tr>
            <th>새 비밀번호 확인</th>
            <td>
              <input type="password" class="input input-bordered w-80" name="loginPwConfirm" placeholder="다시 비밀번호를 입력해주세요." />
            </td>
          </tr>
          <tr>
            <th>이름</th>
            <td>
              <input type="text" class="input input-bordered w-80" name="name" value="${ rq.loginedMember.name }"
                placeholder="이름을 입력해주세요." />
            </td>
          </tr>
          <tr>
            <th>별명</th>
            <td>
              <input type="text" class="input input-bordered w-80" name="nickname" value="${ rq.loginedMember.nickname }"
                placeholder="별명을 입력해주세요." />
            </td>
          </tr>
          <tr>
            <th>연락처</th>
            <td>
              <input type="tel" class="input input-bordered w-80" name="cellPhoneNo"
                value="${ rq.loginedMember.cellphoneNo }" placeholder="연락처를 입력해주세요." />
            </td>
          </tr>
          <tr>
            <th>이메일</th>
            <td>
              <input type="text" class="input input-bordered w-80" name="email" value="${ rq.loginedMember.email }"
                placeholder="이메일을 입력해주세요." />
            </td>
          </tr>
        </tbody>
      </table>

      <div class="btns mt-5">
        <button class="btn btn-secondary btn-outline mr-3" type="button" onclick="history.back();">뒤로가기</button>
        <button class="btn btn-primary" type="submit">회원정보수정</button>
      </div>

    </form>
  </div>
</section>
<%@ include file="../common/foot.jspf"%>