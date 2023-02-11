package com.sbs.exam.servlet;

import com.sbs.exam.Config;
import com.sbs.exam.Rq;
import com.sbs.exam.controller.ArticleController;
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
import java.util.List;
import java.util.Map;

@WebServlet("/usr/*")
public class DispatcherServlet extends HttpServlet {
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    Rq rq = new Rq(req, resp);

    String requestUri = req.getRequestURI();
    String[] requestUriBits = requestUri.split("/");

    if (requestUriBits.length < 4) {
      rq.appendBody("올바른 요청이 아닙니다.");
      return;
    }

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

      HttpSession session = req.getSession();

      boolean isLogined = false;
      int loginedMemberId = -1;
      Map<String, Object> loginedMemberRow = null;

      if (session.getAttribute("loginedMemberId") != null) {
        loginedMemberId = (int) session.getAttribute("loginedMemberId");
        isLogined = true;

        SecSql sql = SecSql.from("SELECT * FROM member");
        sql.append("WHERE id = ?", loginedMemberId);
        loginedMemberRow = DBUtil.selectRow(conn, sql);
      }

      req.setAttribute("isLogined", isLogined);
      req.setAttribute("loginedMemberId", loginedMemberId);
      req.setAttribute("loginedMemberRow", loginedMemberRow);

      String controllerName = requestUriBits[2];
      String actionMethodName = requestUriBits[3];

      if (controllerName.equals("article")) {
        ArticleController controller = new ArticleController(rq, conn);

        if( actionMethodName.equals("list")) {
          controller.actionList();
        }
      }

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
