<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="pageTitle" value="댓글 수정" />
<%@ include file="../common/head.jspf"%>

<!-- 입력데이터 유효성 확인 -->
<script>
	let ReplyModify__submitDone = false;
	function ReplyModify__submit(form) {

		if (ReplyModify__submitDone) {
			return;
		}

		form.body.value = form.body.value.trim();

		if (form.body.value.length == 0) {
			alert('수정내용을 입력해주세요.')
			form.body.focus();
			return;
		}

		ReplyModify__submitDone = true;
		form.submit();
	}
</script>

<section>
  <div class="container mx-auto px-3">
    <form class="table-box-type-1" method="post" action="../reply/doModify"
      onsubmit="ReplyModify__submit(this); return false;">
      <input type="hidden" name="id" value="${ reply.id }" />
      <input type="hidden" name="replaceUri" value="${ param.replaceUri }" />
      <table>
        <colgroup>
          <col width="200" />
        </colgroup>

        <tbody>
          <tr>
            <th>게시물 번호</th>
            <td>
              <div>${ reply.relId }</div>
            </td>
          </tr>
          <tr>
            <th>게시물 제목</th>
            <td>
              <div>${ relDataTitle }</div>
            </td>
          </tr>
          <tr>
            <th>댓글번호</th>
            <td>
              <div class="badge badge-primary">${ reply.id }</div>
            </td>
          </tr>
          <tr>
            <th>작성날짜</th>
            <td>${ reply.forPrintType1RegDate }</td>
          </tr>
          <tr>
            <th>수정날짜</th>
            <td>${ reply.forPrintType2UpateDate }</td>
          </tr>
          <tr>
            <th>작성자</th>
            <td>${ reply.extra__writerName }</td>
          </tr>
          <tr>
            <th>추천수</th>
            <td>
              <span class="badge badge-primary">${ reply.goodReactionPoint }</span>
            </td>
          </tr>
          <tr>
            <th>댓글내용</th>
            <td>
              <textarea class="w-full textarea textarea-bordered" name="body" rows="5" placeholder="내용을 입력해주세요.">${ reply.body }</textarea>
            </td>
          </tr>
        </tbody>
      </table>

      <div class="btns mt-5">
        <a class="btn btn-secondary btn-outline mr-3" href="${ param.replaceUri }">뒤로가기</a>
        <button class="btn btn-primary mr-3" type="submit">댓글수정</button>
      </div>
    </form>
  </div>
</section>
<%@ include file="../common/foot.jspf"%>