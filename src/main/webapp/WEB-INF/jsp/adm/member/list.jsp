<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="pageTitle" value="관리자 페이지 - 회원목록 " />
<%@ include file="../common/head.jspf"%>

<section>
  <div class="container mx-auto px-3">
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
              <td>${ member.regDate }</td>
              <td>${ member.updateDate }</td>
              <td>${ member.loginId }</td>
              <td>${ member.name }</td>
              <td>${ member.nickname }</td>
            </tr>
          </c:forEach>
        </tbody>
      </table>
    </div>
    <!-- 회원 목록 영역 끝 -->
  </div>
</section>

<%@ include file="../common/foot.jspf"%>