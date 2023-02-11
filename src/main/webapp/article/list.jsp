<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="com.sbs.exam.dto.Article"%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
  List<Article> articles = (List<Article>) request.getAttribute("articles");
  int cPage = (int) request.getAttribute("page");
  int totalPage = (int) request.getAttribute("totalPage");
%>

<!doctype html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <title>게시물 리스트</title>
</head>
<body>
  <h1>게시물 리스트</h1>

  <%@ include file="../part/topBar.jspf"%>

  <div>
    <a href="write">게시물 작성</a>
  </div>

  <table border="1" style="text-align:center;">
    <colgroup>
      <col width="100">
      <col>
      <col width="100">
    </colgroup>
    <thead>
    <tr>
      <th>번호</th>
      <th>현재 날짜</th>
      <th>제목</th>
    </tr>
    </thead>
    <tbody>
    <%
    for (Article article : articles) {
    %>
      <tr>
        <td><%= article.id%></td>
        <td><%= article.regDate%></td>
        <td>
          <a href="detail?id=<%= article.id%>">
            <%= article.title%>
          </a>
        </td>
      </tr>
    <%
    }
    %>
    </tbody>
  </table>

  <style type="text/css">
    .page > a.red {
      color : red;
    }
  </style>

  <div class="page">
    <% if ( cPage > 1 ) { %>
      <a href="list?page=1">◀</a>
    <% } %>

    <%
    int pageMenuSize = 5;
    int from = cPage - pageMenuSize;

    if(from < 1) {
      from = 1;
    }

    int end = cPage + 10;

    if(end > totalPage) {
      end = totalPage;
    }

    for (int i = from; i <= end; i++ ) {
    %>
      <a class="<%= cPage == i ? "red" : "" %>" href="list?page=<%=i%>"><%=i%></a>
    <% } %>

    <% if ( cPage < totalPage ) { %>
      <a href="list?page=<%= totalPage %>">▶</a>
    <% } %>

  </div>

</body>
</html>