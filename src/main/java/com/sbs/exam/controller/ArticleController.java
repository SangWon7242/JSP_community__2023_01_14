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
      case "detail":
        actionDetailList(rq);
      case "write":
        actionShowWrite(rq);
        break;
      case "doWrite":
        actionDoWrite(rq);
       break;
      default:
        rq.println("존재하지 않는 페이지입니다.");
        break;
    }
  }

  private void actionDetailList(Rq rq) {
    int id = rq.getIntParam("id", 0);

    if( id == 0) {
      rq.historyBack("id를 입력해주세요.");
      return;
    }

    Article article = articleService.getForPrintArticleById(id);

    rq.setAttr("article", article);
    rq.jsp("../article/detail");
  }

  private void actionDoWrite(Rq rq) {
    HttpSession session = rq.getReq().getSession();
    String redirectUri = rq.getParam("redirectUri", "../article/list");

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

    int id = (int) writeRd.getBody().get("id");
    redirectUri = redirectUri.replace("[NEW_ID]", id + "");

    // rq.printf(writeRd.getMsg());
    rq.replace(writeRd.getMsg(), redirectUri);
  }

  private void actionShowWrite(Rq rq) {
    rq.jsp("../article/write");
  }

  public void actionList(Rq rq) {

    int page = rq.getIntParam("page", 1);
    int totalPage = articleService.getForPrintListTotalPage();
    List<Article> articles = articleService.getForPrintArticles(page);

    rq.setAttr("articles", articles);
    rq.setAttr("page", page);
    rq.setAttr("totalPage", totalPage);

    rq.jsp("../article/list");
  }
}
