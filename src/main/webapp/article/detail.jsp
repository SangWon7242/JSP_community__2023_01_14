<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="com.sbs.exam.dto.Article"%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
  Article article = (Article) request.getAttribute("article");
%>

<!doctype html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <title>게시물 상세페이지</title>
</head>
<body>
  <h1>게시물 상세페이지</h1>

  <%@ include file="../part/topBar.jspf"%>

  <table border="1" style="text-align:center;">
    <colgroup>
      <col width="100">
      <col>
      <col>
      <col width="100">
      <col width="100">
    </colgroup>
    <thead>
      <tr>
        <th>번호</th>
        <th>현재 날짜</th>
        <th>수정 날짜</th>
        <th>제목</th>
        <th>내용</th>
        <th>비고</th>
      </tr>
    </thead>
    <tbody>
      <tr>
        <td><%= article.id%></td>
        <td><%= article.regDate%></td>
        <td><%= article.updateDate%></td>
        <td><%= article.title%></td>
        <td><%= article.body%></td>
        <td>
          <a href="doDelete?id=<%= article.id%>">
            삭제
          </a>
          &nbsp;
          <a href="modify?id=<%= article.id%>">
            수정
          </a>
        </td>
      </tr>
    </tbody>
  </table>

  <div>
    <a href="list">리스트</a>
  </div>
</body>
</html>