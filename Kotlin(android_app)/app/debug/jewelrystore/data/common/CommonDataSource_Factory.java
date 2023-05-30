// Generated by Dagger (https://dagger.dev).
package com.sysoliatina.jewelrystore.data.common;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import javax.inject.Provider;

@DaggerGenerated
@SuppressWarnings({
    "unchecked",
    "rawtypes"
})
public final class CommonDataSource_Factory implements Factory<CommonDataSource> {
  private final Provider<CommonService> commonServiceProvider;

  public CommonDataSource_Factory(Provider<CommonService> commonServiceProvider) {
    this.commonServiceProvider = commonServiceProvider;
  }

  @Override
  public CommonDataSource get() {
    return newInstance(commonServiceProvider.get());
  }

  public static CommonDataSource_Factory create(Provider<CommonService> commonServiceProvider) {
    return new CommonDataSource_Factory(commonServiceProvider);
  }

  public static CommonDataSource newInstance(CommonService commonService) {
    return new CommonDataSource(commonService);
  }
}