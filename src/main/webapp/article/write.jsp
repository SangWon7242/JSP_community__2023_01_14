<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!doctype html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <title>게시물 작성</title>
</head>
<body>
  <script>
    let ArticleWrite__submitDone = false;

    function ArticleWrite__submit(form) {
      if(ArticleWrite__submitDone) {
        return;
      }

      if(form.title.value.length == 0) {
        alert('제목을 입력해주세요.');
        form.title.focus();

        return;
      }

      if(form.body.value.length == 0) {
        alert('내용을 입력해주세요.');
        form.body.focus();

        return;
      }

      form.submit();
      ArticleWrite__submitDone = true;
    }
  </script>


  <h1>게시물 작성</h1>

  <form action="doWrite" method="POST" onsubmit="ArticleWrite__submit(this); return false;">
    <input type="hidden" name="redirectUri" value="../article/detail?id=[NEW_ID]">

    <div>제목 : <input autocomplete="off" placeholder="제목을 입력해주세요." name="title" type="text"></div>
    <div>내용 : <textarea autocomplete="off" placeholder="내용을 입력해주세요." name="body" type="text"></textarea></div>
    <div>
      <button type="submit">작성</button>
      <a href="list">리스트</a>
    </div>
  </form>

</body>
</html>