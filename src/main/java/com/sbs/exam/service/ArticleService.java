package com.sbs.exam.service;

import com.sbs.exam.Rq;
import com.sbs.exam.util.DBUtil;
import com.sbs.exam.util.SecSql;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

public class ArticleService {
  private Rq rq;
  private Connection conn;
  public ArticleService(Rq rq, Connection conn) {
    this.rq = rq;
    this.conn = conn;
  }

  public int getItemsInAPage() {
    return 5;
  }

  public int getForPrintListTotalPage() {
    int itemInAPage = getItemsInAPage();

    SecSql sql = SecSql.from("SELECT COUNT(*) AS cnt");
    sql.append("FROM article");

    int totalCount = DBUtil.selectRowIntValue(conn, sql);

    int totalPage = (int) Math.ceil((double) totalCount / itemInAPage);

    return totalPage;
  }

  public List<Map<String, Object>> getForPrintArticleRows(int page) {

    int itemInAPage = getItemsInAPage();
    int limitFrom = (page - 1) * itemInAPage;

    if (rq.getReq().getParameter("page") != null && rq.getReq().getParameter("page").length() != 0) {
      page = rq.getIntParam("page", 0);
    }

    SecSql sql = SecSql.from("SELECT *");
    sql.append("FROM article");
    sql.append("ORDER BY id DESC");
    sql.append("LIMIT ?, ?", limitFrom, itemInAPage);

    List<Map<String, Object>> articleRows = DBUtil.selectRows(conn, sql);

    return articleRows;
  }
}
