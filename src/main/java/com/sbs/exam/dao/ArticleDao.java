package com.sbs.exam.dao;

import com.sbs.exam.dto.Article;
import com.sbs.exam.util.DBUtil;
import com.sbs.exam.util.SecSql;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

public class ArticleDao {
  private Connection conn;
  public ArticleDao(Connection conn) {
    this.conn = conn;
  }

  public int getTotalCount() {
    SecSql sql = SecSql.from("SELECT COUNT(*) AS cnt");
    sql.append("FROM article");

    int totalCount = DBUtil.selectRowIntValue(conn, sql);

    return totalCount;
  }

  public List<Map<String, Object>> getArticleRows(int itemInAPage, int limitFrom) {
    SecSql sql = SecSql.from("SELECT *");
    sql.append("FROM article");
    sql.append("ORDER BY id DESC");
    sql.append("LIMIT ?, ?", limitFrom, itemInAPage);

    List<Map<String, Object>> articleRows = DBUtil.selectRows(conn, sql);

    return articleRows;
  }

  public int write(String title, String body, int loginedMemberId) {
    SecSql sql = SecSql.from("INSERT INTO article");
    sql.append("SET regDate = NOW()");
    sql.append(", updateDate = NOW()");
    sql.append(", title = ?" , title);
    sql.append(", body = ?" , body);
    sql.append(", memberId = ?" , loginedMemberId);

    int id = DBUtil.insert(conn, sql);

    return id;
  }

  public Article getForPrintArticleById(int id) {
    SecSql sql = SecSql.from("SELECT *");
    sql.append("FROM article");
    sql.append("WHERE id = ?", id);

    return new Article(DBUtil.selectRow(conn, sql));
  }
}
