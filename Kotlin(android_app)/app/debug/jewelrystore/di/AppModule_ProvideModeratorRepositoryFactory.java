// Generated by Dagger (https://dagger.dev).
package com.sysoliatina.jewelrystore.di;

import com.sysoliatina.jewelrystore.data.moderator.ModeratorDataSource;
import com.sysoliatina.jewelrystore.data.moderator.ModeratorRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

@DaggerGenerated
@SuppressWarnings({
    "unchecked",
    "rawtypes"
})
public final class AppModule_ProvideModeratorRepositoryFactory implements Factory<ModeratorRepository> {
  private final Provider<ModeratorDataSource> moderatorDataSourceProvider;

  public AppModule_ProvideModeratorRepositoryFactory(
      Provider<ModeratorDataSource> moderatorDataSourceProvider) {
    this.moderatorDataSourceProvider = moderatorDataSourceProvider;
  }

  @Override
  public ModeratorRepository get() {
    return provideModeratorRepository(moderatorDataSourceProvider.get());
  }

  public static AppModule_ProvideModeratorRepositoryFactory create(
      Provider<ModeratorDataSource> moderatorDataSourceProvider) {
    return new AppModule_ProvideModeratorRepositoryFactory(moderatorDataSourceProvider);
  }

  public static ModeratorRepository provideModeratorRepository(
      ModeratorDataSource moderatorDataSource) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideModeratorRepository(moderatorDataSource));
  }
}