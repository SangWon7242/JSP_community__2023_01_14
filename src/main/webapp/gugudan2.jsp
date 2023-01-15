<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!doctype html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <meta name="viewport"
        content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
  <meta http-equiv="X-UA-Compatible" content="ie=edge">
  <title>구구단 테스트</title>
</head>
<body>
  <%
  int dan = (int) request.getAttribute("dan");
  int limit = (int) request.getAttribute("limit");
  %>

  <h1><%=dan%>단</h1>
  <% for(int i = 1; i <= limit; i++) { %>
  <div><%=dan%> * <%=i%> = <%=dan * i%></div>
  <% } %>
</body>
</html>

