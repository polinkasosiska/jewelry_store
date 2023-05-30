// Generated by Dagger (https://dagger.dev).
package com.sysoliatina.jewelrystore.data.moderator;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import javax.inject.Provider;

@DaggerGenerated
@SuppressWarnings({
    "unchecked",
    "rawtypes"
})
public final class ModeratorRepository_Factory implements Factory<ModeratorRepository> {
  private final Provider<ModeratorDataSource> moderatorDataSourceProvider;

  public ModeratorRepository_Factory(Provider<ModeratorDataSource> moderatorDataSourceProvider) {
    this.moderatorDataSourceProvider = moderatorDataSourceProvider;
  }

  @Override
  public ModeratorRepository get() {
    return newInstance(moderatorDataSourceProvider.get());
  }

  public static ModeratorRepository_Factory create(
      Provider<ModeratorDataSource> moderatorDataSourceProvider) {
    return new ModeratorRepository_Factory(moderatorDataSourceProvider);
  }

  public static ModeratorRepository newInstance(ModeratorDataSource moderatorDataSource) {
    return new ModeratorRepository(moderatorDataSource);
  }
}
