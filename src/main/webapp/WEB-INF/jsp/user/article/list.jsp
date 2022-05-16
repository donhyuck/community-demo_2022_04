<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="pageTitle" value="${ board.name } " />
<%@ include file="../common/head.jspf"%>

<section>
  <div class="container mx-auto px-3">
    <div>
      등록된 게시물 : ${ articlesCount } 개
    </div>
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
  </div>
</section>

<%@ include file="../common/foot.jspf"%>