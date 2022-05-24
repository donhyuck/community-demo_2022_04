<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="com.ldh.exam.demo.util.Ut"%>

<c:set var="pageTitle" value="마이" />
<%@ include file="../common/head.jspf"%>

<section>
  <div class="container mx-auto px-3">
    <div class="table-box-type-1" method="post" action="../member/doLogin">
      <table>
        <colgroup>
          <col width="200" />
        </colgroup>

        <tbody>
          <tr>
            <th>아이디</th>
            <td>${ rq.loginedMember.loginId }</td>
          </tr>
          <tr>
            <th>이름</th>
            <td>${ rq.loginedMember.name }</td>
          </tr>
          <tr>
            <th>별명</th>
            <td>${ rq.loginedMember.nickname }</td>
          </tr>
          <tr>
            <th>연락처</th>
            <td>${ rq.loginedMember.cellphoneNo }</td>
          </tr>
          <tr>
            <th>이메일</th>
            <td>${ rq.loginedMember.email }</td>
          </tr>
        </tbody>
      </table>

      <div class="btns mt-5">
        <button class="btn btn-secondary btn-outline mr-3" type="button" onclick="history.back();">뒤로가기</button>
        <a href="../member/checkPassword?replaceUri=${ Ut.getUriEncoded('../member/modify') }" class="btn btn-primary">회원정보수정</a>
      </div>

    </div>
  </div>
</section>
<%@ include file="../common/foot.jspf"%>