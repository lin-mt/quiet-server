package cn.linmt.quiet.modal.document;

import java.util.List;
import lombok.Data;

@Data
public class Request {

  private List<HttpHeader> headers;

  private List<FormDataParam> formDataParams;

  private List<QueryParam> queryParams;

  private List<UrlEncodeParam> urlEncodeParams;

  private String jsonSchema;

  private String body;

  private String description;
}
