package com.sbs.exam.dto;

import java.util.Map;

public class Article {
  public int id;
  public String regDate;
  public String updateDate;
  public String title;
  public String body;
  public int memberId;

  public Article(int id, String regDate, String updateDate, String title, String body, int memberId) {
    this.id = id;
    this.regDate = regDate;
    this.updateDate = updateDate;
    this.title = title;
    this.body = body;
    this.memberId = memberId;
  }

  public Article(Map<String, Object> row) {
    this.id = (int) row.get("id");
    this.regDate = (String) row.get("regDate");
    this.updateDate = (String) row.get("updateDate");
    this.title = (String) row.get("title");
    this.body = (String) row.get("body");
    this.memberId = (int) row.get("memberId");;
  }
}
