<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="pageTitle" value="관리자 페이지 - 회원목록 " />
<%@ include file="../common/head.jspf"%>

<section class="mt-3">
  <div class="container mx-auto px-3">
    <!-- 검색 박스 영역 시작 -->
    <div class="flex">
      <div>
        가입한 회원수 :
        <span class="badge badge-primary">${ membersCount }</span>
        명
      </div>
      <div class="flex-grow"></div>
      <form class="flex">
        <input type="hidden" name="boardId" value="${param.boardId}" />

        <select class="select select-bordered" name="authLevel" data-value="${authLevel}">
          <option disabled="disabled">회원구분</option>
          <option value="3">일반회원</option>
          <option value="7">관리자</option>
          <option value="0">전체</option>
        </select>

        <select class="select select-bordered" name="searchKeywordTypeCode" data-value="${searchKeywordTypeCode}">
          <option disabled="disabled">검색타입</option>
          <option value="loginId">아이디</option>
          <option value="name">이름</option>
          <option value="nickname">별명</option>
          <option value="loginId,name,nickname">전부포함</option>
        </select>

        <input type="text" name="searchKeyword" value="${param.searchKeyword}" maxlength="20"
          class="ml-2 w-72 input input-bordered" placeholder="검색어를 입력하세요." />

        <button type="submit" class="ml-2 btn btn-primary">검색</button>
      </form>
    </div>
    <!-- 검색 박스 영역 끝 -->

    <!-- 회원 목록 영역 시작 -->
    <div class="mt-3">
      <table class="table table-fixed w-full">
        <colgroup>
          <col />
          <col />
          <col />
          <col />
          <col />
          <col />
        </colgroup>
        <thead>
          <tr>
            <th>번호</th>
            <th>가입날짜</th>
            <th>갱신날짜</th>
            <th>아이디</th>
            <th>이름</th>
            <th>별명</th>
          </tr>
        </thead>

        <tbody>
          <c:forEach var="member" items="${ members }">
            <tr>
              <th>${ member.id }</th>
              <td>${ member.forPrintType1RegDate }</td>
              <td>${ member.forPrintType1UpdateDate }</td>
              <td>${ member.loginId }</td>
              <td>${ member.name }</td>
              <td>${ member.nickname }</td>
            </tr>
          </c:forEach>
        </tbody>
      </table>
    </div>
    <!-- 회원 목록 영역 끝 -->
    <!-- 페이지 영역 시작 -->
    <div class="page-menu mt-3">
      <div class="btn-group justify-center">
        <c:set var="pageMenuArmlen" value="6" />
        <c:set var="startPage" value="${ (page - pageMenuArmlen) >= 1 ? (page - pageMenuArmlen) : 1}" />
        <c:set var="endPage" value="${ (page + pageMenuArmlen) <= pagesCount ? (page + pageMenuArmlen) : pagesCount}" />

        <c:set var="pageBaseUri" value="?authLevel=${ authLevel }" />
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