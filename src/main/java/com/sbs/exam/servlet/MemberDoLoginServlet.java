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
import java.util.Map;

@WebServlet("/member/doLogin")
public class MemberDoLoginServlet extends HttpServlet {
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    Rq rq = new Rq(req, resp);

    // DB 연결시작
    Connection conn = null;

    String diverName = Config.getDriverClassName();

    try {
      Class.forName(diverName);
    } catch (ClassNotFoundException e) {
      System.out.printf("[ClassNotFoundException 예외, %s]", e.getMessage());
      System.out.println("DB 드라이버 클래스 로딩 실패");
      return;
    }

    try {
      conn = DriverManager.getConnection(Config.getDBUrl(), Config.getDBId(), Config.getDBPw());

      String loginId = req.getParameter("loginId");
      String loginPw = req.getParameter("loginPw");

      SecSql sql = SecSql.from("SELECT *");
      sql.append("FROM member");
      sql.append("WHERE loginId = ?", loginId);

      Map<String, Object> memberRow = DBUtil.selectRow(conn, sql);

      if(memberRow.isEmpty()) {
        rq.appendBody(String.format("<script> alert('%s 회원은 없는 아이디입니다.'); history.back(); </script>", loginId));
        return;
      }

      if(((String) memberRow.get("loginPw")).equals(loginPw) == false) {
        rq.appendBody(String.format("<script> alert('로그인 비번이 일치하지 않습니다.'); location.replace('../home/main'); </script>"));
        return;
      }

      HttpSession session = req.getSession();
      session.setAttribute("loginedMemberId", memberRow.get("id"));

      rq.appendBody(String.format("<script> alert('로그인 성공'); location.replace('../home/main') </script>"));

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
