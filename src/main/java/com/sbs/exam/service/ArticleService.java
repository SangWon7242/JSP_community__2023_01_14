package com.sbs.exam.service;

import com.sbs.exam.Rq;
import com.sbs.exam.dao.ArticleDao;
import com.sbs.exam.util.DBUtil;
import com.sbs.exam.util.SecSql;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

public class ArticleService {
  private Rq rq;
  private Connection conn;
  private ArticleDao articleDao;
  public ArticleService(Rq rq, Connection conn) {
    this.rq = rq;
    articleDao = new ArticleDao(conn);
  }

  public int getItemsInAPage() {
    return 5;
  }

  public int getForPrintListTotalPage() {
    int itemInAPage = getItemsInAPage();
    int totalCount = articleDao.getTotalCount();
    int totalPage = (int) Math.ceil((double) totalCount / itemInAPage);

    return totalPage;
  }

  public List<Map<String, Object>> getForPrintArticleRows(int page) {

    int itemInAPage = getItemsInAPage();
    int limitFrom = (page - 1) * itemInAPage;

    if (rq.getReq().getParameter("page") != null && rq.getReq().getParameter("page").length() != 0) {
      page = rq.getIntParam("page", 0);
    }

    List<Map<String, Object>> articleRows = articleDao.getArticleRows(itemInAPage, limitFrom);

    return articleRows;
  }
}
