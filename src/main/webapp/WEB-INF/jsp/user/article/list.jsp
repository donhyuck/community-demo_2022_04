<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="pageTitle" value="${ board.name } " />
<%@ include file="../common/head.jspf"%>

<section>
  <div class="container mx-auto px-3">

    <!-- 검색 박스 영역 시작 -->
    <div class="flex">
      <div>
        등록된 게시물 :
        <span class="badge badge-primary">${ articlesCount }</span>
        개
      </div>
      <div class="flex-grow"></div>
      <form class="flex">
        <input type="hidden" name="boardId" value="${param.boardId}" />

        <select class="select select-bordered" name="searchKeywordTypeCode" data-value="${param.searchKeywordTypeCode}">
          <option disabled="disabled">검색타입</option>
          <option value="title">제목</option>
          <option value="body">내용</option>
          <option value="title,body">제목과 내용</option>
        </select>
        <input type="text" name="searchKeyword" value="${param.searchKeyword}" maxlength="20"
          class="ml-2 w-72 input input-bordered" placeholder="검색어를 입력하세요." />

        <button type="submit" class="ml-2 btn btn-primary">검색</button>
      </form>
    </div>
    <!-- 검색 박스 영역 끝 -->

    <!-- 게시글 목록 영역 시작 -->
    <div class="mt-3">
      <table class="table table-fixed w-full">
        <colgroup>
          <col width="60" />
          <col width="100" />
          <col width="100" />
          <col />
          <col width="90" />
          <col width="80" />
          <col width="80" />
        </colgroup>
        <thead>
          <tr>
            <th>번호</th>
            <th>작성날짜</th>
            <th>수정날짜</th>
            <th>제목</th>
            <th>작성자</th>
            <th>추천</th>
            <th>조회</th>
          </tr>
        </thead>

        <tbody>
          <c:forEach var="article" items="${ articles }">
            <tr>
              <th>${ article.id }</th>
              <td>${ article.forPrintType1RegDate }</td>
              <td>${ article.forPrintType1UpdateDate }</td>
              <td>
                <a class="btn-text-link block w-full truncate" href="${rq.getArticleDetailUriFromArticleList(article)}">${ article.title }</a>
              </td>
              <td>${ article.extra__writerName }</td>
              <td>${ article.goodReactionPoint }</td>
              <td>${ article.hitCount }</td>
            </tr>
          </c:forEach>
        </tbody>
      </table>
    </div>
    <!-- 게시글 목록 영역 끝 -->

    <!-- 페이지 영역 시작 -->
    <div class="page-menu mt-3">
      <div class="btn-group justify-center">
        <c:set var="pageMenuArmlen" value="5" />
        <c:set var="startPage" value="${ (page - pageMenuArmlen) >= 1 ? (page - pageMenuArmlen) : 1}" />
        <c:set var="endPage" value="${ (page + pageMenuArmlen) <= pagesCount ? (page + pageMenuArmlen) : pagesCount}" />

        <c:set var="pageBaseUri" value="?boardId=${ boardId }" />
        <c:set var="pageBaseUri" value="${pageBaseUri}&searchKeyword=${param.searchKeyword}" />
        <c:set var="pageBaseUri" value="${pageBaseUri}&searchKeywordTypeCode=${param.searchKeywordTypeCode}" />

        <c:if test="${ startPage > 1 }">
          <a href="${pageBaseUri}&page=1" class="btn btn-sm">1</a>
          <c:if test="${ startPage > 2 }">
            <a class="btn btn-sm btn-disabled">...</a>
          </c:if>
        </c:if>

        <c:forEach begin="${ startPage }" end="${ endPage }" var="i">
          <a href="${pageBaseUri}&page=${i}" class="btn btn-sm ${ page == i ? 'btn-active' : ''}">${ i }</a>
        </c:forEach>

        <c:if test="${ endPage < pagesCount }">
          <c:if test="${ endPage < pagesCount-1 }">
            <a class="btn btn-sm btn-disabled">...</a>
          </c:if>
          <a href="${pageBaseUri}&page=${ pagesCount }" class="btn btn-sm">${ pagesCount }</a>
        </c:if>
      </div>
    </div>
    <!-- 페이지 영역 끝 -->
  </div>
</section>

<%@ include file="../common/foot.jspf"%>