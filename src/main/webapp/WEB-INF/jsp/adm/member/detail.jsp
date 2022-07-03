<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="com.ldh.exam.demo.util.Ut"%>

<c:set var="pageTitle" value="마이" />
<%@ include file="../common/head.jspf"%>

<section class="container mx-auto px-3"">
  <div class="container mx-auto">
    <div class="card bordered">
      <!-- 회원 아이템, first -->
      <div class="px-4 py-8">
        <div class="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-3">
          <div class="row-span-3 order-1">
            <img class="rounded-full" src="https://i.pravatar.cc/100?img=10" alt="">
          </div>

          <div class="order-2">
            <span class="badge badge-primary">번호</span>
            <span>${member.id}</span>
          </div>

          <div class="order-3">
            <span class="badge badge-accent">회원타입</span>
            <span>${member.authLevelName}</span>
          </div>

          <div class="order-4">
            <span class="badge">로그인아이디</span>
            <span class="text-gray-600">${member.loginId}</span>
          </div>

          <div class="order-5">
            <span class="badge">등록일</span>
            <span class="text-gray-600 text-light">${member.regDate}</span>
          </div>

          <div class="order-6">
            <span class="badge">수정일</span>
            <span class="text-gray-600 text-light">${member.updateDate}</span>
          </div>

          <div class="order-7">
            <span class="badge">이름</span>
            <span class="text-gray-600">${member.name}</span>
          </div>

          <div class="order-7">
            <span class="badge">연락처</span>
            <span class="text-gray-600">${member.cellPhoneNo}</span>
          </div>

          <div class="order-7">
            <span class="badge">이메일</span>
            <span class="text-gray-600">${member.email}</span>
          </div>

          <div class="order-7 text-red-400 font-bold">
            <span class="badge font-normal">등록상태</span>
            <c:if test="${ member.delStatus == false }">
              등록완료
            </c:if>
            <c:if test="${ member.delStatus == true }">
              탈퇴: [ ${member.forPrintDelDate} ] 처리완료
            </c:if>
          </div>

          <div class="order-8 sm:order-4 md:order-8">
            <span class="badge">별명</span>
            <span class="text-gray-600">${member.nickname}</span>
          </div>
        </div>

        <div class="mt-5">
          <div class="btn btn-primary btn-sm">
            <a href="#" onclick="if ( !confirm('삭제하시겠습니까?') ) return false;">
              <span>
                <i class="fas fa-trash"></i>
                <span>탈퇴</span>
              </span>
            </a>
          </div>

          <br />
          <button class="btn btn-secondary btn-xs btn-outline mt-3" type="button" onclick="history.back();">뒤로가기</button>
        </div>
      </div>
    </div>
  </div>
</section>

<%@ include file="../common/foot.jspf"%>