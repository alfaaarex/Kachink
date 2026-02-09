package com.tasktracker.di;

import com.tasktracker.data.local.TaskDao;
import com.tasktracker.data.local.TaskDatabase;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata
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
public final class AppModule_ProvideTaskDaoFactory implements Factory<TaskDao> {
  private final Provider<TaskDatabase> databaseProvider;

  public AppModule_ProvideTaskDaoFactory(Provider<TaskDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public TaskDao get() {
    return provideTaskDao(databaseProvider.get());
  }

  public static AppModule_ProvideTaskDaoFactory create(Provider<TaskDatabase> databaseProvider) {
    return new AppModule_ProvideTaskDaoFactory(databaseProvider);
  }

  public static TaskDao provideTaskDao(TaskDatabase database) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideTaskDao(database));
  }
}
