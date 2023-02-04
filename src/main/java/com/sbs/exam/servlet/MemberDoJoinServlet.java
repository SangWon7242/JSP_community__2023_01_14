package com.sbs.exam.servlet;

import com.sbs.exam.Config;
import com.sbs.exam.Rq;
import com.sbs.exam.util.DBUtil;
import com.sbs.exam.util.SecSql;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@WebServlet("/member/doJoin")
public class MemberDoJoinServlet extends HttpServlet {
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
      String name = req.getParameter("name");

      SecSql sql = SecSql.from("SELECT COUNT(*) AS cnt");
      sql.append("FROM member");
      sql.append("WHERE loginId = ?", loginId);

      boolean isJoinAvailableLogindId = DBUtil.selectRowIntValue(conn, sql) == 0;

      if(isJoinAvailableLogindId == false) {
        rq.appendBody(String.format("<script> alert('%s(은) 이미 사용중인 로그인 아이디입니다.'); history.back(); </script>", loginId));
        return;
      }


      sql = SecSql.from("INSERT INTO member");
      sql.append("SET regDate = NOW()");
      sql.append(", updateDate = NOW()");
      sql.append(", loginId = ?" , loginId);
      sql.append(", loginPw = ?" , loginPw);
      sql.append(", name = ?" , name);

      int id = DBUtil.insert(conn, sql);
      rq.appendBody(String.format("<script> alert('%d번 회원이 생성되었습니다.'); location.replace('../home/main'); </script>", id));

    } catch (SQLException e) {
     e.printStackTrace();
    } finally {
      try {
        if (conn != null && !conn.isClosed()) {
          conn.close();
        }
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
    // DB 연결 끝
  }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    doGet(req, resp);
  }
}
