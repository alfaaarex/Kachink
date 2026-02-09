package com.tasktracker.ml;

import android.content.Context;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
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
public final class TaskClassifier_Factory implements Factory<TaskClassifier> {
  private final Provider<Context> contextProvider;

  public TaskClassifier_Factory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public TaskClassifier get() {
    return newInstance(contextProvider.get());
  }

  public static TaskClassifier_Factory create(Provider<Context> contextProvider) {
    return new TaskClassifier_Factory(contextProvider);
  }

  public static TaskClassifier newInstance(Context context) {
    return new TaskClassifier(context);
  }
}
