// Generated by Dagger (https://dagger.dev).
package com.sysoliatina.jewelrystore.presentation.viewmodels;

import com.sysoliatina.jewelrystore.data.client.ClientRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import javax.inject.Provider;

@DaggerGenerated
@SuppressWarnings({
    "unchecked",
    "rawtypes"
})
public final class CartViewModel_Factory implements Factory<CartViewModel> {
  private final Provider<ClientRepository> clientRepositoryProvider;

  public CartViewModel_Factory(Provider<ClientRepository> clientRepositoryProvider) {
    this.clientRepositoryProvider = clientRepositoryProvider;
  }

  @Override
  public CartViewModel get() {
    return newInstance(clientRepositoryProvider.get());
  }

  public static CartViewModel_Factory create(Provider<ClientRepository> clientRepositoryProvider) {
    return new CartViewModel_Factory(clientRepositoryProvider);
  }

  public static CartViewModel newInstance(ClientRepository clientRepository) {
    return new CartViewModel(clientRepository);
  }
}