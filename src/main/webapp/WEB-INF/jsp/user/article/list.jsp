<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="pageTitle" value="${ board.name } " />
<%@ include file="../common/head.jspf"%>

<section>
  <div class="container mx-auto px-3">

    <div>등록된 게시물 : ${ articlesCount } 개</div>

    <!-- 게시글 목록 영역 시작 -->
    <div class="table-box-type-1">
      <table>
        <colgroup>
          <col width="60" />
          <col width="150" />
          <col width="150" />
          <col />
          <col width="150" />
        </colgroup>
        <thead>
          <tr>
            <th>번호</th>
            <th>작성날짜</th>
            <th>수정날짜</th>
            <th>제목</th>
            <th>작성자</th>
          </tr>
        </thead>

        <tbody>
          <c:forEach var="article" items="${ articles }">
            <tr>
              <td>${ article.id }</td>
              <td>${ article.regDate.substring(2,16) }</td>
              <td>${ article.updateDate.substring(2,16) }</td>
              <td>
                <a class="btn-text-link" href="../article/detail?id=${ article.id }">${ article.title }</a>
              </td>
              <td>${ article.extra__writerName }</td>
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

        <c:if test="${ startPage > 1 }">
          <a href="?page=1&boardId=${ boardId }" class="btn btn-sm">1</a>
          <c:if test="${ startPage > 2 }">
            <a class="btn btn-sm btn-disabled">...</a>
          </c:if>
        </c:if>

        <c:forEach begin="${ startPage }" end="${ endPage }" var="i">
          <a href="?page=${i}&boardId=${ boardId }" class="btn btn-sm ${ page == i ? 'btn-active' : ''}">${ i }</a>
        </c:forEach>

        <c:if test="${ endPage < pagesCount }">
          <c:if test="${ endPage < pagesCount-1 }">
            <a class="btn btn-sm btn-disabled">...</a>
          </c:if>
          <a href="?page=${ pagesCount }&boardId=${ boardId }" class="btn btn-sm">${ pagesCount }</a>
        </c:if>
      </div>
    </div>
    <!-- 페이지 영역 끝 -->
  </div>
</section>

<%@ include file="../common/foot.jspf"%>