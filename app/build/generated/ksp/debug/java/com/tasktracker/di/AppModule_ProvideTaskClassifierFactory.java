package com.tasktracker.di;

import android.content.Context;
import com.tasktracker.ml.TaskClassifier;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata("dagger.hilt.android.qualifiers.ApplicationContext")
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava"
})
public final class AppModule_ProvideTaskClassifierFactory implements Factory<TaskClassifier> {
  private final Provider<Context> contextProvider;

  public AppModule_ProvideTaskClassifierFactory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public TaskClassifier get() {
    return provideTaskClassifier(contextProvider.get());
  }

  public static AppModule_ProvideTaskClassifierFactory create(Provider<Context> contextProvider) {
    return new AppModule_ProvideTaskClassifierFactory(contextProvider);
  }

  public static TaskClassifier provideTaskClassifier(Context context) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideTaskClassifier(context));
  }
}
