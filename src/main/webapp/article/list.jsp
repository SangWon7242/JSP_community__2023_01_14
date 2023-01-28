<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
  List<Map<String, Object>> articleRows = (List<Map<String, Object>>) request.getAttribute("articleRows");
%>

<!doctype html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <title>게시물 리스트</title>
</head>
<body>
  <h1>게시물 리스트</h1>

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
    for (Map<String, Object> articleRow : articleRows) {
    %>
      <tr>
        <td><%= articleRow.get("id")%></td>
        <td><%= articleRow.get("regDate")%></td>
        <td>
          <a href="detail?id=<%=(int) articleRow.get("id")%>">
            <%= articleRow.get("title")%>
          </a>
        </td>
      </tr>
    <%
    }
    %>
    </tbody>
  </table>
</body>
</html>