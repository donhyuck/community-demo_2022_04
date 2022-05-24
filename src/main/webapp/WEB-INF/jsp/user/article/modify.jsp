<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="pageTitle" value="게시글 수정" />
<%@ include file="../common/head.jspf"%>

<script>
	let ArticleModify__submitDone = false;
	function ArticleModify__submit(form) {

		if (ArticleModify__submitDone) {
			return;
		}

		form.body.value = form.body.value.trim();

		if (form.body.value.length == 0) {
			alert('수정내용을 입력해주세요.')
			form.body.focus();
			return;
		}

		ArticleModify__submitDone = true;
		form.submit();
	}
</script>

<section>
  <div class="container mx-auto px-3">
    <form class="table-box-type-1" method="post" action="../article/doModify"
      onsubmit="ArticleModify__submitDone(this); return false;">
      <input type="hidden" name="id" value="${ article.id }" />
      <table>
        <colgroup>
          <col width="200" />
        </colgroup>

        <tbody>
          <tr>
            <th>번호</th>
            <td>
              <div class="badge badge-primary">${ article.id }</div>
            </td>
          </tr>
          <tr>
            <th>작성날짜</th>
            <td>${ article.forPrintType1RegDate }</td>
          </tr>
          <tr>
            <th>수정날짜</th>
            <td>${ article.forPrintType2UpateDate }</td>
          </tr>
          <tr>
            <th>작성자</th>
            <td>${ article.extra__writerName }</td>
          </tr>
          <tr>
            <th>조회수</th>
            <td>
              <span class="badge badge-primary article-detail__hit-count">${ article.hitCount }</span>
            </td>
          </tr>
          <tr>
            <th>추천수</th>
            <td>
              <span class="badge badge-primary">${ article.goodReactionPoint }</span>
            </td>
          </tr>
          <tr>
            <th>제목</th>
            <td>
              <input class="w-96 input input-bordered" type="text" name="title" value="${ article.title }"
                placeholder="제목을 입력해주세요." />
            </td>
          </tr>
          <tr>
            <th>내용</th>
            <td>
              <textarea class="w-full textarea textarea-bordered" name="body" rows="10" placeholder="내용을 입력해주세요.">${ article.body }</textarea>
            </td>
          </tr>
        </tbody>
      </table>

      <div class="btns mt-5">
        <button class="btn btn-secondary btn-outline mr-3" type="button" onclick="history.back();">뒤로가기</button>
        <button class="btn btn-primary mr-3" type="submit">수정하기</button>
        <button class="btn btn-primary ">
          <a href="../article/detail?id=${ article.id }">상세보기</a>
        </button>
      </div>
    </form>
  </div>
</section>
<%@ include file="../common/foot.jspf"%>