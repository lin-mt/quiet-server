package cn.linmt.quiet.exception;

import cn.linmt.quiet.modal.http.Result;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class BizException extends RuntimeException {

  private final Result result;
}
