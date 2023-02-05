package com.sbs.exam.servlet;

import com.sbs.exam.Config;
import com.sbs.exam.Rq;
import com.sbs.exam.exception.SQLErrorException;
import com.sbs.exam.util.DBUtil;
import com.sbs.exam.util.SecSql;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@WebServlet("/article/doWrite")
public class ArticleDoWriteServlet extends HttpServlet {
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    Rq rq = new Rq(req, resp);

    // DB 연결시작
    Connection conn = null;

    String diverName = Config.getDriverClassName();

    HttpSession session = req.getSession();

    if(session.getAttribute("loginedMemberId") == null) {
      rq.appendBody(String.format("<script> alert('로그인 후 이용해주세요.'); location.replace('../member/login'); </script>"));
    }

    try {
      Class.forName(diverName);
    } catch (ClassNotFoundException e) {
      System.out.printf("[ClassNotFoundException 예외, %s]", e.getMessage());
      System.out.println("DB 드라이버 클래스 로딩 실패");
      return;
    }

    try {
      conn = DriverManager.getConnection(Config.getDBUrl(), Config.getDBId(), Config.getDBPw());

      String title = rq.getParam("title", "");
      String body = rq.getParam("body", "");

      int loginedMemberId = (int) session.getAttribute("loginedMemberId");

      SecSql sql = SecSql.from("INSERT INTO article");
      sql.append("SET regDate = NOW()");
      sql.append(", updateDate = NOW()");
      sql.append(", title = ?" , title);
      sql.append(", body = ?" , body);
      sql.append(", memberId = ?" , loginedMemberId);

      int id = DBUtil.insert(conn, sql);
      rq.appendBody(String.format("<script> alert('%d번 글이 생성되었습니다.'); location.replace('list'); </script>", id));


    } catch (SQLException e) {
     e.printStackTrace();
    } finally {
      try {
        if (conn != null && !conn.isClosed()) {
          conn.close();
        }
      } catch (SQLException e) {
        e.printStackTrace();
      } catch (SQLErrorException e) {
        e.getOrigin().printStackTrace();
      }
    }
    // DB 연결 끝
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    doGet(req, resp);
  }
}
