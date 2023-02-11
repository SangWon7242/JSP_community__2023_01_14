package com.sbs.exam.controller;

import com.sbs.exam.Rq;
import com.sbs.exam.util.DBUtil;
import com.sbs.exam.util.SecSql;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

public class ArticleController {
  private Rq rq;
  private Connection conn;
  public ArticleController(Rq rq, Connection conn) {
    this.rq = rq;
    this.conn = conn;
  }

  public void actionList() {
    int page = 1;

    if (rq.getReq().getParameter("page") != null && rq.getReq().getParameter("page").length() != 0) {
      page = rq.getIntParam("page", 0);
    }

    int itemInAPage = 10;
    int limitFrom = (page - 1) * itemInAPage;

    SecSql sql = SecSql.from("SELECT COUNT(*) AS cnt");
    sql.append("FROM article");

    int totalCount = DBUtil.selectRowIntValue(conn, sql);
    int totalPage = (int) Math.ceil((double) totalCount / itemInAPage);

    sql = SecSql.from("SELECT *");
    sql.append("FROM article");
    sql.append("ORDER BY id DESC");
    sql.append("LIMIT ?, ?", limitFrom, itemInAPage);

    List<Map<String, Object>> articleRows = DBUtil.selectRows(conn, sql);

    rq.getReq().setAttribute("articleRows", articleRows);
    rq.getReq().setAttribute("page", page);
    rq.getReq().setAttribute("totalPage", totalPage);

    rq.jsp("../article/list");
  }
}
