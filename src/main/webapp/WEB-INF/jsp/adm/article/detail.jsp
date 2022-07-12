<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="pageTitle" value="관리자페이지 - 게시글 상세" />
<%@ include file="../common/head.jspf"%>
<%@ include file="../../common/toastUiEditorLib.jspf"%>

<!-- 게시글 상세보기 영역 시작 -->
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
            <td>
              <div class="badge badge-primary">${ article.id }</div>
            </td>
          </tr>
          <tr>
            <th>작성날짜</th>
            <td>${ article.forPrintType2RegDate }</td>
          </tr>
          <tr>
            <th>수정날짜</th>
            <td>${ article.forPrintType2UpateDate }</td>
          </tr>
          <tr>
            <th>작성자</th>
            <td>${ article.extra__writerName }</td>
          </tr>
          <tr>
            <th>조회수</th>
            <td>
              <span class="badge badge-primary article-detail__hit-count">${ article.hitCount }</span>
            </td>
          </tr>
          <!-- 추천수 영역 시작 -->
          <tr>
            <th>추천수</th>
            <td>
              <div class="flex items-center">
                <div class="mr-2">
                  <span class="badge">${ article.goodReactionPoint }</span>
                  <div class="badge badge-secondary">
                    <span>좋아요&nbsp;</span>
                    <span>
                      <i class="fas fa-thumbs-up"></i>
                    </span>
                  </div>
                </div>
                <div>
                  <span class="badge">${ article.badReactionPoint }</span>
                  <div class="badge badge-accent">
                    <span>싫어요</span>
                    <span>
                      <i class="fas fa-thumbs-up"></i>
                    </span>
                  </div>
                </div>
              </div>
            </td>
          </tr>
          <!-- 추천수 영역 끝 -->
          <tr>
            <th>제목</th>
            <td>${ article.title }</td>
          </tr>
          <tr>
            <th>내용</th>
            <td>
              <div class="toast-ui-viewer">
                <script type="text/x-template">${article.body}</script>
              </div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>

    <!-- 게시글 수정, 삭제 영역 시작 -->
    <div class="btns mt-5">
      <c:if test="${ empty param.listUri }">
        <button class="btn btn-secondary btn-outline mr-3" type="button" onclick="history.back();">뒤로가기</button>
      </c:if>
      <c:if test="${ not empty param.listUri }">
        <a class="btn btn-secondary btn-outline mr-3" type="button" href="${ param.listUri }">뒤로가기</a>
      </c:if>

      <button class="btn btn-primary mr-3">
        <a href="../article/modify?id=${ article.id }">수정하기</a>
      </button>

      <button class="btn btn-primary">
        <a href="../article/doDelete?ids=${ article.id }" onclick="if( confirm('정말 삭제하시겠습니까?') == false ) return false;">삭제하기</a>
      </button>
    </div>
    <!-- 게시글 수정, 삭제 영역 끝 -->
  </div>
</section>
<!-- 게시글 상세보기 영역 끝 -->

<!-- 댓글 영역 시작 -->
<!-- 댓글 수정 모달 시작 -->
<style>
.section-reply-modify {
	position: fixed;
	top: 0;
	left: 0;
	width: 100%;
	height: 100%;
	background-color: rgba(0, 0, 0, 0.5);
	z-index: 10;
	display: none;
	align-items: center;
	justify-content: center;
}

.section-reply-modify>div {
	background-color: white;
	padding: 20px 30px;
	border-radius: 30px;
}
</style>

<script>
	function ReplyModify__showModal(el) {
		const $div = $(el).closest('[data-id]');
		const replyId = $div.attr('data-id');
		const replyBody = $div.find('.reply-body').html();

		$('.section-reply-modify [name="id"]').val(replyId);
		$('.section-reply-modify [name="body"]').val(replyBody);

		$('.section-reply-modify').css('display', 'flex');
	}
	function ReplyModify__hideModal() {
		$('.section-reply-modify').hide();
	}

	let ReplyModify__submitDone = false;
	function ReplyModify__submit(form) {

		if (ReplyModify__submitDone) {
			return;
		}

		form.body.value = form.body.value.trim();

		if (form.body.value.length == 0) {
			alert('수정내용을 입력해주세요.')
			form.body.focus();
			return;
		}

		ReplyModify__submitDone = true;
		form.submit();
	}
</script>

<div class="section section-reply-modify hidden">
  <div>
    <div class="container mx-auto">
      <form class="" method="post" action="../reply/doModify" onsubmit="ReplyModify__submit(this); return false;">
        <input type="hidden" name="id" value="" />
        <input type="hidden" name="replaceUri" value="${ rq.currentUri}" />

        <div class="form-control">
          <label class="label">댓글내용</label>
          <textarea class="textarea textarea-bordered w-full h-24" name="body" rows="3" maxlength="2000"
            placeholder="내용을 입력해주세요.">${ reply.body }</textarea>
        </div>

        <div class="mt-4 btn-wrap gap-1">
          <button type="button" onclick="history.back();" class="btn btn-secondary btn-outline btn-sm mb-1" title="닫기">
            <span>닫기</span>
          </button>
          <button type="submit" href="#" class="btn btn-primary btn-outline btn-sm mb-1">
            <span>수정</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</div>
<!-- 댓글 수정 모달 끝 -->

<section class="mt-5">
  <div class="container mx-auto px-3">
    <h1 class="title-bar-type-2 px-4 font-semibold">댓글</h1>
  </div>
</section>

<!-- 댓글 작성후 스크롤 이동, 포커스 스크립트 시작 -->
<style>
.reply-list [data-id] {
	transition: background-color 1s;
}

.reply-list [data-id].focus {
	background-color: #efefef;
	transition: background-color 0s;
}
</style>

<script>
	function ReplyList__goToReply(id) {
		setTimeout(function() {
			const $target = $('.reply-list [data-id="' + id + '"]');
			const targetOffset = $target.offset();
			$(window).scrollTop(targetOffset.top - 50);
			$target.addClass('focus');

			setTimeout(function() {
				$target.removeClass('focus');
			}, 1000);
		}, 1000);
	}
	if (param.focusReplyId) {
		ReplyList__goToReply(param.focusReplyId);
	}
</script>
<!-- 댓글 작성후 스크롤 이동, 포커스 스크립트 끝 -->

<!-- 댓글 리스트 영역 시작 -->
<section class="reply-list mt-5">
  <div class="container mx-auto px-3">
    <h1>전체댓글 (${ replies.size() } 건)</h1>
    <c:forEach items="${replies}" var="reply">
      <div data-id="${ reply.id }" class="mt-5 mb-8 mx-4 flex justify-between grid md:grid-cols-2 shadow p-3 rounded-lg">
        <script type="text/x-template" class="reply-body hidden">${reply.body}</script>
        <!-- 댓글 내용 영역 시작 -->
        <div class="px-1">
          <!-- 작성자, 등록(수정)일 -->
          <div class="text-gray-400 text-light text-sm">
            <spqn class="font-bold text-black">${reply.extra__writerName}</spqn>
            <span class="mx-1">·</span>
            <c:if test="${ reply.regDate == reply.updateDate }">
              <span>${reply.forPrintType2RegDate}</span>
              <span class="mx-1">·</span>
              <span>registered</span>
            </c:if>
            <c:if test="${ reply.regDate != reply.updateDate }">
              <span>${reply.forPrintType2UpateDate}</span>
              <span class="mx-1">·</span>
              <span>modified</span>
            </c:if>
          </div>

          <!-- 댓글 내용 -->
          <div class="break-all">${reply.forPrintBody}</div>

          <!-- 추천, 반대 영역 시작 -->
          <div class="mt-1 text-gray-400">
            <div class="badge badge-xl badge-secondary mr-1">
              <span>
                <i class="fas fa-thumbs-up"></i>
              </span>
              &nbsp;
              <span>${ reply.goodReactionPoint }</span>
            </div>
            <div class="badge badge-xl badge-accent">
              <span>
                <i class="fas fa-thumbs-down"></i>
              </span>
              &nbsp;
              <span>${ reply.badReactionPoint }</span>
            </div>
          </div>
          <!-- 추천, 반대 영역 끝 -->
        </div>
        <!-- 댓글 내용 영역 끝 -->

        <!-- 댓글 수정, 삭제 영역 시작 -->
        <div class="btns flex items-center ml-auto mr-10">
          <div class="btn btn-primary btn-outline btn-xs mr-3">
            <a class="plain-link" onclick="ReplyModify__showModal(this);">
              <span>
                <i class="fas fa-edit"></i>
              </span>
              <span>수정</span>
            </a>
          </div>
          <div class="btn btn-primary btn-outline btn-xs">
            <a href="../reply/doDelete?id=${ reply.id }&replaceUri=${rq.encodedCurrentUri}"
              onclick="if( confirm('정말 삭제하시겠습니까?') == false ) return false;">
              <span>
                <i class="fas fa-trash"></i>
              </span>
              <span>삭제</span>
            </a>
          </div>
        </div>
        <!-- 댓글 수정, 삭제 영역 영역 끝 -->
      </div>
    </c:forEach>
  </div>
</section>
<!-- 댓글 리스트 영역 끝 -->
<!-- 댓글 영역 끝 -->
<%@ include file="../common/foot.jspf"%>