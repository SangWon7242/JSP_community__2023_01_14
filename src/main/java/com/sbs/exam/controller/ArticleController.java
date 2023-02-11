package com.sbs.exam.controller;

import com.sbs.exam.Rq;
import com.sbs.exam.dto.Article;
import com.sbs.exam.service.ArticleService;

import java.sql.Connection;
import java.util.List;

public class ArticleController {
  private ArticleService articleService;

  public ArticleController(Rq rq, Connection conn) {
    articleService = new ArticleService(rq, conn);
  }

  public void actionList(Rq rq) {

    int page = 1;
    int totalPage = articleService.getForPrintListTotalPage();
    List<Article> articles = articleService.getForPrintArticles(page);

    rq.getReq().setAttribute("articles", articles);
    rq.getReq().setAttribute("page", page);
    rq.getReq().setAttribute("totalPage", totalPage);

    rq.jsp("../article/list");
  }
}
