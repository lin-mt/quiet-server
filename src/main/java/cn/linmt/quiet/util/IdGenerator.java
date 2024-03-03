package cn.linmt.quiet.util;

import java.io.Serializable;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Id 生成器.
 *
 * @author <a href="mailto:lin-mt@outlook.com">lin-mt</a>
 */
@SuppressWarnings("unused")
public class IdGenerator implements IdentifierGenerator {

  private static final Logger logger = LoggerFactory.getLogger(IdGenerator.class);
  private static final IdWorker idWorker = new IdWorker(0L);

  @Override
  public Serializable generate(SharedSessionContractImplementor session, Object object)
      throws HibernateException {
    return idWorker.nextId();
  }
}
