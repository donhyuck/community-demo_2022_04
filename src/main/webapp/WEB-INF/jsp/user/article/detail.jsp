<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="pageTitle" value="게시글 상세" />
<%@ include file="../common/head.jspf"%>

<script>
	const params = {};
	params.id = parseInt('${param.id}');
</script>
<script>
	function ArticleDetail__increaseHitCount() {
		$.get('../article/doIncreaseHitCountRd', {
			id : params.id,
			ajaxMode : 'Y'
		}, function(data) {
			$('article-detail__hit-count').empty().html(data.data1);
		}, 'json');
	}
	$(function() {
		ArticleDetail__increaseHitCount();
	})
</script>

<section>
  <div class="container mx-auto px-3">
    <div class="table-box-type-1">
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
            <td>${ article.regDateForPrint }</td>
          </tr>
          <tr>
            <th>수정날짜</th>
            <td>${ article.updateDateForPrint }</td>
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
            <th>제목</th>
            <td>${ article.title }</td>
          </tr>
          <tr>
            <th>내용</th>
            <td>${ article.body }</td>
          </tr>
        </tbody>
      </table>
    </div>

    <div class="btns mt-5">
      <button class="btn btn-secondary btn-outline mr-3" type="button" onclick="history.back();">뒤로가기</button>

      <c:if test="${ article.extra__actorCanModify }">
        <button class="btn btn-primary mr-3">
          <a href="../article/modify?id=${ article.id }">수정하기</a>
        </button>
      </c:if>

      <c:if test="${ article.extra__actorCanDelete }">
        <button class="btn btn-primary">
          <a href="../article/doDelete?id=${ article.id }"
            onclick="if( confirm('정말 삭제하시겠습니까?') == false ) return false;">삭제하기</a>
        </button>
      </c:if>
    </div>
  </div>
</section>

<%@ include file="../common/foot.jspf"%>