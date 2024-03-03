package cn.linmt.quiet.config;

import org.hibernate.bytecode.enhance.spi.LazyPropertyInitializer;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.event.internal.DefaultMergeEventListener;
import org.hibernate.event.spi.MergeContext;
import org.hibernate.persister.entity.EntityPersister;
import org.hibernate.property.access.internal.PropertyAccessStrategyBackRefImpl;
import org.hibernate.type.Type;

public class IgnoreNullEventListener extends DefaultMergeEventListener {

  public static final IgnoreNullEventListener INSTANCE = new IgnoreNullEventListener();

  @Override
  protected void copyValues(
      final EntityPersister entityPersister,
      final Object entity,
      final Object target,
      final SessionImplementor source,
      final MergeContext copyCache) {
    // 源目标
    Object[] original = entityPersister.getValues(entity);
    // 存储目标
    Object[] targets = entityPersister.getValues(target);

    Type[] types = entityPersister.getPropertyTypes();

    Object[] copied = new Object[original.length];
    int len = types.length;
    for (int i = 0; i < len; i++) {
      if (original[i] == null
          || original[i] == LazyPropertyInitializer.UNFETCHED_PROPERTY
          || original[i] == PropertyAccessStrategyBackRefImpl.UNKNOWN) {
        copied[i] = targets[i];
      } else {
        copied[i] = types[i].replace(original[i], targets[i], source, target, copyCache);
      }
    }
    entityPersister.setValues(target, copied);
  }
}
