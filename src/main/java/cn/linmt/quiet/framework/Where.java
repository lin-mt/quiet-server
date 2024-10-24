package cn.linmt.quiet.framework;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.*;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.function.Consumer;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

@SuppressWarnings("ALL")
public class Where {

  private final BooleanBuilder builder;

  private Where() {
    this.builder = new BooleanBuilder();
  }

  public static Where builder() {
    return new Where();
  }

  public BooleanBuilder getPredicate() {
    return builder;
  }

  public Where and(Predicate right) {
    builder.and(right);
    return this;
  }

  public Where andAnyOf(Predicate... args) {
    builder.andAnyOf(args);
    return this;
  }

  public Where andNot(Predicate right) {
    return and(right.not());
  }

  public Where or(Predicate right) {
    builder.or(right);
    return this;
  }

  public Where orAllOf(Predicate... args) {
    builder.orAllOf(args);
    return this;
  }

  public Where orNot(Predicate right) {
    return or(right.not());
  }

  public Where notNullEq(Boolean param, BooleanPath path) {
    if (param != null) {
      builder.and(path.eq(param));
    }
    return this;
  }

  public <T extends Number & Comparable<?>> Where notNullEq(T param, NumberPath<T> path) {
    if (param != null) {
      builder.and(path.eq(param));
    }
    return this;
  }

  public Where isIdEq(Long param, NumberPath<Long> path) {
    if (param != null && param > 0L) {
      builder.and(path.eq(param));
    }
    return this;
  }

  public <T extends Number & Comparable<?>> Where leZeroIsNull(T param, NumberPath<T> path) {
    if (param != null && param.longValue() <= 0) {
      builder.and(path.isNull());
    }
    return this;
  }

  public Where notBlankEq(String param, StringPath path) {
    if (StringUtils.isNoneBlank(param)) {
      builder.and(path.eq(param));
    }
    return this;
  }

  public Where with(@NotNull Consumer<Where> consumer) {
    if (consumer != null) {
      consumer.accept(this);
    }
    return this;
  }

  public <T extends Enum<T>> Where notNullEq(T param, EnumPath<T> path) {
    if (param != null) {
      builder.and(path.eq(param));
    }
    return this;
  }

  public Where notBlankContains(String param, StringPath path) {
    if (StringUtils.isNoneBlank(param)) {
      builder.and(path.contains(param));
    }
    return this;
  }

  public Where notNullBefore(LocalDateTime param, DateTimePath<LocalDateTime> path) {
    if (param != null) {
      builder.and(path.before(param));
    }
    return this;
  }

  public Where notNullAfter(LocalDateTime param, DateTimePath<LocalDateTime> path) {
    if (param != null) {
      builder.and(path.after(param));
    }
    return this;
  }

  public Where notEmptyIn(Collection<? extends Long> param, NumberPath<Long> path) {
    if (CollectionUtils.isNotEmpty(param)) {
      builder.and(path.in(param));
    }
    return this;
  }

  public Where findInSet(Long param, SetPath<Long, NumberPath<Long>> path) {
    if (param != null) {
      builder.and(Expressions.booleanTemplate("FIND_IN_SET({0}, {1}) > 0", param, path));
    }
    return this;
  }

  public Where jsonContains(Long param, SetPath<Long, NumberPath<Long>> path) {
    if (param != null) {
      builder.and(Expressions.booleanTemplate("JSON_CONTAINS({0}, {1}) > 0", param, path));
    }
    return this;
  }
}
