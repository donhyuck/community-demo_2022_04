<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="pageTitle" value="게시글 상세" />
<%@ include file="../common/head.jspf"%>

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
            <td>${ article.id }</td>
          </tr>
          <tr>
            <th>작성날짜</th>
            <td>${ article.regDate.substring(2,16) }</td>
          </tr>
          <tr>
            <th>수정날짜</th>
            <td>${ article.updateDate.substring(2,16) }</td>
          </tr>
          <tr>
            <th>작성자</th>
            <td>${ article.extra__writerName }</td>
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
      <button type="button" onclick="history.back();" class="btn-text-link">뒤로가기</button>
      <a href="../article/modify?id=${ article.id }" class="btn-text-link">게시물 수정</a>
      <c:if test="${ article.extra__actorCanDelete }">
        <a href="../article/doDelete?id=${ article.id }" onclick="if( confirm('정말 삭제하시겠습니까?') == false ) return false;" class="btn-text-link">게시물 삭제</a>
      </c:if>
    </div>
  </div>
</section>

<%@ include file="../common/foot.jspf"%>