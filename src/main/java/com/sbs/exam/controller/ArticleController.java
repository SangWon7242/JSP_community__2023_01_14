package com.sbs.exam.controller;

import com.sbs.exam.Rq;
import com.sbs.exam.dto.Article;
import com.sbs.exam.dto.ResultData;
import com.sbs.exam.service.ArticleService;
import jakarta.servlet.http.HttpSession;

import java.sql.Connection;
import java.util.List;

public class ArticleController extends Controller {
  private ArticleService articleService;

  public ArticleController(Rq rq, Connection conn) {
    articleService = new ArticleService(rq, conn);
  }

  @Override
  public void performAction(Rq rq) {
    switch (rq.getActionMethodName()) {
      case "list":
        actionList(rq);
        break;
      case "write":
        actionShowWrite(rq);
        break;
      case "doWrite":
        actionDoWrite(rq);
        break;
    }
  }

  private void actionDoWrite(Rq rq) {
    HttpSession session = rq.getReq().getSession();

    if(session.getAttribute("loginedMemberId") == null) {
      rq.print(String.format("<script> alert('로그인 후 이용해주세요.'); location.replace('../member/login'); </script>"));
    }

    String title = rq.getParam("title", "");
    String body = rq.getParam("body", "");
    int loginedMemberId = (int) session.getAttribute("loginedMemberId");

    if(title.length() == 0) {
      rq.historyBack("title을 입력해주세요.");
      return;
    }

    if(body.length() == 0) {
      rq.historyBack("body를 입력해주세요.");
      return;
    }

    ResultData writeRd = articleService.write(title, body, loginedMemberId);

  }

  private void actionShowWrite(Rq rq) {
    rq.jsp("../article/write");
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
