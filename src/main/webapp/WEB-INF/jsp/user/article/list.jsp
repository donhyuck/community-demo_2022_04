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
          <col width="120" />
          <col width="120" />
          <col width="120" />
          <col width="120" />
        </colgroup>
        <thead>
          <tr>
            <th>
              <span class="badge badge-primary">번호</span>
            </th>
            <th>
              <span class="badge">등록날짜</span>
            </th>
            <th>
              <span class="badge">수정날짜</span>
            </th>
            <th>
              <span class="badge badge-outline">제목</span>
            </th>
            <th>
              <span class="badge badge-outline ml-3">작성자</span>
            </th>
            <th>
              <span class="badge badge-outline ml-3">추선수</span>
            </th>
            <th>
              <span class="badge badge-outline ml-3">조회수</span>
            </th>
            <th></th>
          </tr>
        </thead>

        <tbody>
          <c:forEach var="article" items="${ articles }">
            <tr class="px-4 py-8">
              <td>
                <a href="${rq.getArticleDetailUriFromArticleList(article)}" class="hover:underline ml-4">
                  <span>${article.id}</span>
                </a>
              </td>
              <td>${ article.forPrintType1RegDate }</td>
              <td>${ article.forPrintType1UpdateDate }</td>
              <td>
                <a class="btn-text-link block w-full truncate" href="${rq.getArticleDetailUriFromArticleList(article)}">${ article.title }</a>
              </td>
              <td class="text-center">${ article.extra__writerName }</td>
              <td class="text-center">${ article.goodReactionPoint }</td>
              <td class="text-center">${ article.hitCount }</td>
              <td>
                <div class="mb-2">
                  <a href="../article/modify?id=${ article.id }">
                    <span>
                      <i class="fas fa-edit"></i>
                    </span>
                    <span>수정</span>
                  </a>
                </div>
                <div>
                  <a href="../article/doDelete?id=${ article.id }" onclick="if ( !confirm('삭제하시겠습니까?') ) return false;">
                    <span>
                      <i class="fas fa-trash"></i>
                    </span>
                    <span>삭제</span>
                  </a>
                </div>
              </td>
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