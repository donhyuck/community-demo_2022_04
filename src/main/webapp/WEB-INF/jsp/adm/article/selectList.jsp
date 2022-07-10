<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="pageTitle" value="관리자 페이지 - 게시판목록 " />
<%@ include file="../common/head.jspf"%>
<%@ include file="../../common/toastUiEditorLib.jspf"%>

<section>
  <div class="container mx-auto px-3">

    <!-- 게시판 박스 영역 시작 -->
    <div class="search-box flex justify-between grid md:grid-cols-2">
      <div class="flex items-center ml-1 h-16">
        <span>관리중인 게시판 : </span>
        <span class="badge badge-primary">${ boardsCount }</span>
        개
      </div>
    </div>
    <!-- 게시판 박스 영역 끝 -->

    <div class="container mx-auto">
      <!-- 게시글 목록 영역 시작 -->
      <div class="mt-3">
        <table class="table table-fixed w-full">
          <colgroup>
            <col width="80" />
            <col />
            <col width="180" />
            <col width="180" />
          </colgroup>
          <thead>
            <tr>
              <th>
                <span class="badge badge-primary">번호</span>
              </th>
              <th>
                <span class="badge">게시판 이름</span>
              </th>
              <th class="text-center">
                <span class="text-base">게시글 수</span>
              </th>
              <th class="text-center">
                <span class="text-base">정보열람</span>
              </th>
            </tr>
          </thead>

          <tbody>
            <c:forEach var="board" items="${ boards }">
              <tr class="px-4 py-8">
                <th>
                  <div class="ml-4">${board.id}</div>
                </th>
                <td>
                  <div class="w-full truncate">${ board.name }</div>
                </td>
                <td class="text-center">
                  <div>${ board.postCount }</div>
                </td>
                <td>
                  <div class="btn btn-outline">
                    <a href="../article/list?boardId=1">전체보기</a>
                  </div>
                </td>
              </tr>
            </c:forEach>
          </tbody>
        </table>
      </div>
      <!-- 게시글 목록 영역 끝 -->
    </div>

  </div>
</section>
<%@ include file="../common/foot.jspf"%>