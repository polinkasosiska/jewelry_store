// Generated by Dagger (https://dagger.dev).
package com.sysoliatina.jewelrystore.di;

import com.sysoliatina.jewelrystore.data.client.ClientDataSource;
import com.sysoliatina.jewelrystore.data.client.ClientService;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

@DaggerGenerated
@SuppressWarnings({
    "unchecked",
    "rawtypes"
})
public final class AppModule_ProvideClientDataSourceFactory implements Factory<ClientDataSource> {
  private final Provider<ClientService> clientServiceProvider;

  public AppModule_ProvideClientDataSourceFactory(Provider<ClientService> clientServiceProvider) {
    this.clientServiceProvider = clientServiceProvider;
  }

  @Override
  public ClientDataSource get() {
    return provideClientDataSource(clientServiceProvider.get());
  }

  public static AppModule_ProvideClientDataSourceFactory create(
      Provider<ClientService> clientServiceProvider) {
    return new AppModule_ProvideClientDataSourceFactory(clientServiceProvider);
  }

  public static ClientDataSource provideClientDataSource(ClientService clientService) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideClientDataSource(clientService));
  }
}
