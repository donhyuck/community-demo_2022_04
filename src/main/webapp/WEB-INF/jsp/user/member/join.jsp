<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="pageTitle" value="회원가입" />
<%@ include file="../common/head.jspf"%>

<section>
  <div class="container mx-auto px-3">
    <form class="table-box-type-1" method="post" action="../member/doJoin">
      <input type="hidden" name="afterLoginUri" value="${ param.afterLoginUri }">
      <table>
        <colgroup>
          <col width="200" />
        </colgroup>

        <tbody>
          <tr>
            <th>아이디</th>
            <td>
              <input type="text" class="w-96 input input-bordered" name="loginId" placeholder="로그인 아이디" />
            </td>
          </tr>

          <tr>
            <th>비밀번호</th>
            <td>
              <input type="password" class="w-96 input input-bordered" name="loginPw" placeholder="로그인 비밀번호" />
            </td>
          </tr>

          <tr>
            <th>비밀번호 확인</th>
            <td>
              <input type="password" class="w-96 input input-bordered" name="loginPwConfirm" placeholder="로그인 비밀번호 확인" />
            </td>
          </tr>

          <tr>
            <th>이름</th>
            <td>
              <input type="text" class="w-96 input input-bordered" name="name" placeholder="이름" />
            </td>
          </tr>

          <tr>
            <th>닉네임</th>
            <td>
              <input type="text" class="w-96 input input-bordered" name="nickname" placeholder="닉네임" />
            </td>
          </tr>

          <tr>
            <th>연락처</th>
            <td>
              <input type="text" class="w-96 input input-bordered" name="cellPhoneNo" placeholder="하이픈(-)제외 예) 010********" />
            </td>
          </tr>

          <tr>
            <th>이메일</th>
            <td>
              <input type="email" class="w-96 input input-bordered" name="email" placeholder="예) abc@test.com" />
            </td>
          </tr>

        </tbody>
      </table>

      <div class="btns mt-5">
        <button class="btn btn-secondary btn-outline mr-3" type="button" onclick="history.back();">뒤로가기</button>
        <button class="btn btn-primary" type="submit">회원가입</button>
      </div>

    </form>
  </div>
</section>
<%@ include file="../common/foot.jspf"%>