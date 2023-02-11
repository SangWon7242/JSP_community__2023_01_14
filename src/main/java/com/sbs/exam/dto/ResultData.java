package com.sbs.exam.dto;

import com.sbs.exam.util.Util;

import java.util.Map;

public class ResultData {
  private String msg;
  private String resultCode;
  private Map<String, Object> body;

  private ResultData() {

  }

  public String getMsg() {
    return msg;
  }

  public String getResultCode() {
    return resultCode;
  }

  public Map<String, Object> getBody() {
    return body;
  }

  public static ResultData from(String resultCode, String msg, Object... bodyArgs) {
    ResultData rd = new ResultData();

    rd.resultCode = resultCode;
    rd.msg = msg;
    rd.body = Util.mapOf(bodyArgs);

    return rd;
  }

  public boolean isSuccess() {
    return resultCode.startsWith("S-1");
  }

  public boolean isFail() {
    return !isSuccess();
  }
}
