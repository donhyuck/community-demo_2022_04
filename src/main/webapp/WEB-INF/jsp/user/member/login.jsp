<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="pageTitle" value="로그인" />
<%@ include file="../common/head.jspf"%>


<section>
  <div class="container mx-auto px-3">
    <form class="table-box-type-1" method="post" action="../member/doLogin">
      <table>
        <colgroup>
          <col width="200" />
        </colgroup>

        <tbody>
          <tr>
            <th>아이디</th>
            <td>
              <input type="text" class="w-96" name="loginId" placeholder="로그인 아이디" />
            </td>
          </tr>

          <tr>
            <th>비밀번호</th>
            <td>
              <input type="password" class="w-96" name="loginPw" placeholder="로그인 비밀번호" />
            </td>
          </tr>

        </tbody>
      </table>

      <div class="btns mt-3">
        <button class="btn-text-link" type="button" onclick="history.back();">뒤로가기</button>
        <button class="btn-text-link" type="submit">로그인</button>
      </div>

    </form>
  </div>
</section>
<%@ include file="../common/foot.jspf"%>