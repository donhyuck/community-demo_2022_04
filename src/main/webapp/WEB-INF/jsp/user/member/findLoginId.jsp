<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="pageTitle" value="아이디 찾기 " />
<%@ include file="../common/head.jspf"%>

<section>
  <div class="container mx-auto px-3">
    <form class="table-box-type-1" method="post" action="../member/doFindLoginId">
      <table>
        <colgroup>
          <col width="200" />
        </colgroup>

        <tbody>
          <tr>
            <th>이름</th>
            <td>
              <input type="text" class="w-96 input input-bordered" required="required" name="name" placeholder="이름"
                maxlength="30" />
            </td>
          </tr>

          <tr>
            <th>이메일</th>
            <td>
              <input type="email" class="w-96 input input-bordered" required="required" name="email" placeholder="이메일"
                maxlength="100" />
            </td>
          </tr>

        </tbody>
      </table>

      <div class="btns mt-5">
        <button class="btn btn-secondary btn-outline mr-3" type="button" onclick="history.back();">뒤로가기</button>
        <button class="btn btn-primary" type="submit">아이디 찾기</button>
      </div>
    </form>
  </div>
</section>
<%@ include file="../common/foot.jspf"%>