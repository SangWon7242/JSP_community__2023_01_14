package com.sbs.exam.service;

import com.sbs.exam.Rq;
import com.sbs.exam.dao.ArticleDao;
import com.sbs.exam.dto.Article;
import com.sbs.exam.dto.ResultData;
import com.sbs.exam.util.DBUtil;
import com.sbs.exam.util.SecSql;
import com.sbs.exam.util.Util;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ArticleService {
  private Rq rq;
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

  public List<Article> getForPrintArticles(int page) {

    int itemInAPage = getItemsInAPage();
    int limitFrom = (page - 1) * itemInAPage;

    if (rq.getReq().getParameter("page") != null && rq.getReq().getParameter("page").length() != 0) {
      page = rq.getIntParam("page", 0);
    }

    List<Map<String, Object>> articleRows = articleDao.getArticleRows(itemInAPage, limitFrom);

    List<Article> articles = new ArrayList<>();

    for( Map<String, Object> articleRow : articleRows) {
      articles.add(new Article(articleRow));
    }

    return articles;
  }

  public ResultData write(String title, String body, int loginedMemberId) {
    int id = articleDao.write(title, body, loginedMemberId);

    return ResultData.from("S-1", Util.f("%d번 게시물이 생성되었습니다.", id), "id", id);
  }
}
