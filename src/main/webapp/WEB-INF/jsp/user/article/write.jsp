<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="pageTitle" value="게시글 작성" />
<%@ include file="../common/head.jspf"%>
<%@ include file="../../common/toastUiEditorLib.jspf"%>

<section>
  <div class="container mx-auto px-3">
    <form class="table-box-type-1" method="post" action="../article/doWrite">
      <table>
        <colgroup>
          <col width="200" />
        </colgroup>

        <tbody>
          <tr>
            <th>작성자</th>
            <td>${ rq.loginedMember.nickname }</td>
          </tr>
          <tr>
            <th>게시판 선택</th>
            <td>
              <select class="select select-bordered" name="boardId">
                <option selected disabled>게시판을 선택해주세요.</option>
                <option value="1">공지</option>
                <option value="2">자유</option>
              </select>
            </td>
          </tr>
          <tr>
            <th>제목</th>
            <td>
              <input class="w-96 input input-bordered" type="text" required="required" name="title" placeholder="제목을 입력해주세요." />
            </td>
          </tr>
          <tr>
            <th>내용</th>
            <td>
              <textarea class="w-full textarea textarea-bordered" required="required" name="body" rows="10"
                placeholder="내용을 입력해주세요."></textarea>
            </td>
          </tr>
        </tbody>
      </table>

      <div class="btns mt-5">
        <button class="btn btn-secondary btn-outline mr-3" type="button" onclick="history.back();">뒤로가기</button>
        <button class="btn btn-primary mr-3" type="submit">작성하기</button>
      </div>
    </form>
  </div>
</section>
<%@ include file="../common/foot.jspf"%>