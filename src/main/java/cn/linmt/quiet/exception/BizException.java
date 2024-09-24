package cn.linmt.quiet.exception;

import lombok.Getter;

@Getter
public class BizException extends RuntimeException {

  private final Integer code;

  private final Object[] args;

  public BizException(Integer code, Object... args) {
    this.code = code;
    this.args = args;
  }
}
