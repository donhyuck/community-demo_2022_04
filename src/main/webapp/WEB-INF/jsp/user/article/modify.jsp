<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="pageTitle" value="게시글 수정" />
<%@ include file="../common/head.jspf"%>

<section>
  <div class="container mx-auto px-3">
    <form class="table-box-type-1" method="post" action="../article/doModify">
      <input type="hidden" name="id" value="${ article.id }" />
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
            <td>
              <input class="w-96" type="text" name="title" value="${ article.title }" placeholder="제목을 입력해주세요." />
            </td>
          </tr>
          <tr>
            <th>내용</th>
            <td>
              <textarea class="w-full" name="body" rows="10">${ article.body }</textarea>
            </td>
          </tr>
        </tbody>
      </table>

      <div class="btns mt-5">
        <button type="button" onclick="history.back();" class="btn-text-link">뒤로가기</button>
        <button type="submit" onclick="history.back();" class="btn-text-link">수정하기</button>
      </div>
    </form>
  </div>
</section>
<%@ include file="../common/foot.jspf"%>