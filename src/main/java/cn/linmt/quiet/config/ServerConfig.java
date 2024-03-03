package cn.linmt.quiet.config;

import cn.linmt.quiet.config.jackson.LongToStringSerializer;
import cn.linmt.quiet.config.jackson.StringToLongDeserializer;
import cn.linmt.quiet.config.properties.ServerProperties;
import com.blazebit.persistence.Criteria;
import com.blazebit.persistence.CriteriaBuilderFactory;
import com.blazebit.persistence.spi.CriteriaBuilderConfiguration;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceUnit;
import lombok.RequiredArgsConstructor;
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
}
