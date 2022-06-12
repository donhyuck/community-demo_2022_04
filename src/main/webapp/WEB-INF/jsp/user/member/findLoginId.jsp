<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="pageTitle" value="아이디 찾기 " />
<%@ include file="../common/head.jspf"%>

<script>
	let MemberFindLoginId__submitDone = false;
	function MemberFindLoginId__submit(form) {

		if (MemberFindLoginId__submitDone) {
			return;
		}

		form.name.value = form.name.value.trim();
		if (form.name.value.length == 0) {
			alert('이름을 입력해주세요.')
			form.name.focus();
			return;
		}

		form.email.value = form.email.value.trim();
		if (form.email.value.length == 0) {
			alert('이메일을 입력해주세요.')
			form.email.focus();
			return;
		}

		MemberFindLoginId__submitDone = true;
		form.submit();
	}
</script>

<section>
  <div class="container mx-auto px-3">
    <form class="table-box-type-1" method="post" action="../member/doFindLoginId"
      onsubmit="MemberFindLoginId__submit(this); return false;">
      <table>
        <colgroup>
          <col width="200" />
        </colgroup>

        <tbody>
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
        <button class="btn btn-primary" type="submit">아이디 찾기</button>
      </div>
    </form>
  </div>
</section>
<%@ include file="../common/foot.jspf"%>