package cn.linmt.quiet.config;

import cn.linmt.quiet.config.jackson.LongToStringSerializer;
import cn.linmt.quiet.config.jackson.StringToLongDeserializer;
import cn.linmt.quiet.config.properties.ServerProperties;
import com.blazebit.persistence.Criteria;
import com.blazebit.persistence.CriteriaBuilderFactory;
import com.blazebit.persistence.spi.CriteriaBuilderConfiguration;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceUnit;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.EventType;
import org.hibernate.internal.SessionFactoryImpl;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(ServerProperties.class)
public class ServerConfig {

  @PersistenceContext private final EntityManager entityManager;
  @PersistenceUnit private EntityManagerFactory entityManagerFactory;

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public JPAQueryFactory jpaQueryFactory() {
    return new JPAQueryFactory(entityManager);
  }

  @Bean
  public GrantedAuthorityDefaults grantedAuthorityDefaults() {
    return new GrantedAuthorityDefaults("ROLE_");
  }

  @Bean
  @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
  public CriteriaBuilderFactory createCriteriaBuilderFactory() {
    CriteriaBuilderConfiguration config = Criteria.getDefault();
    return config.createCriteriaBuilderFactory(entityManagerFactory);
  }

  @Bean
  public ObjectMapper jsonObjectMapper(Jackson2ObjectMapperBuilder builder) {
    ObjectMapper objectMapper = builder.createXmlMapper(false).build();
    SimpleModule module = new SimpleModule();
    module.addSerializer(Long.class, new LongToStringSerializer());
    module.addDeserializer(Long.class, new StringToLongDeserializer());
    // 日期序列化与反序列化
    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    module.addSerializer(LocalDate.class, new LocalDateSerializer(dateFormatter));
    module.addDeserializer(LocalDate.class, new LocalDateDeserializer(dateFormatter));
    // 时间序列化与反序列化
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    module.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(dateTimeFormatter));
    MultiFormatLocalDateTimeDeserializer localDateTimeDeserializer =
        new MultiFormatLocalDateTimeDeserializer();
    localDateTimeDeserializer.addFormat("yyyy-MM-dd HH:mm:ss");
    localDateTimeDeserializer.addFormat("yyyy-MM-dd HH:mm");
    module.addDeserializer(LocalDateTime.class, localDateTimeDeserializer);
    objectMapper.registerModule(module);
    return objectMapper;
  }

  @PostConstruct
  protected void init() {
    SessionFactoryImpl sessionFactory = entityManagerFactory.unwrap(SessionFactoryImpl.class);
    EventListenerRegistry registry =
        sessionFactory.getServiceRegistry().getService(EventListenerRegistry.class);
    assert registry != null;
    registry.getEventListenerGroup(EventType.MERGE).clearListeners();
    registry
        .getEventListenerGroup(EventType.MERGE)
        .prependListener(IgnoreNullEventListener.INSTANCE);
  }

  // 自定义的 LocalDateTime 反序列化器，支持多个格式
  public static class MultiFormatLocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {
    private final Map<Integer, DateTimeFormatter> patternLength2formatter = new HashMap<>(6);

    public void addFormat(String pattern) {
      if (StringUtils.isEmpty(pattern)) {
        throw new IllegalArgumentException("pattern can not be empty");
      }
      patternLength2formatter.put(pattern.length(), DateTimeFormatter.ofPattern(pattern));
    }

    @Override
    public LocalDateTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
      String dateString = p.getText().trim();
      DateTimeFormatter formatter = patternLength2formatter.get(dateString.length());
      if (formatter != null) {
        return LocalDateTime.parse(dateString, formatter);
      }
      throw new IllegalArgumentException("Invalid date format: " + dateString);
    }
  }
}
