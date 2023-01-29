<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>

<%@ page isELIgnored="false" language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
  Map<String, Object> articleRow = (Map<String, Object>) request.getAttribute("articleRow");
%>


<!doctype html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <title>게시물 수정</title>
</head>
<body>
  <h1>게시물 수정</h1>

  <form action="doModify" method="POST">
    <input type="hidden" name="id" value="${param.id}" />

    <div>번호 : <%= articleRow.get("id")%></div>
    <div>날짜 : <%= articleRow.get("regDate")%></div>
    <div>제목 : <input autocomplete="off" placeholder="제목을 입력해주세요." name="title" type="text" value="<%=articleRow.get("title")%>"></div>
    <div>내용 : <textarea autocomplete="off" placeholder="내용을 입력해주세요." name="body"><%=articleRow.get("body")%></textarea></div>
    <div>
      <button type="submit">수정</button>
      <a href="list">리스트</a>
    </div>
  </form>

</body>
</html>