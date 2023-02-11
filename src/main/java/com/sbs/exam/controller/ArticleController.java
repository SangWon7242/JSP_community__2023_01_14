package com.sbs.exam.controller;

import com.sbs.exam.Rq;
import com.sbs.exam.service.ArticleService;
import com.sbs.exam.util.DBUtil;
import com.sbs.exam.util.SecSql;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

public class ArticleController {
  private ArticleService articleService;

  public ArticleController(Rq rq, Connection conn) {
    articleService = new ArticleService(rq, conn);
  }

  public void actionList(Rq rq) {

    int page = 1;
    int totalPage = articleService.getForPrintListTotalPage();
    List<Map<String, Object>> articleRows = articleService.getForPrintArticleRows(page);

    rq.getReq().setAttribute("articleRows", articleRows);
    rq.getReq().setAttribute("page", page);
    rq.getReq().setAttribute("totalPage", totalPage);

    rq.jsp("../article/list");
  }
}
