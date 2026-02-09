package com.tasktracker.ui.viewmodel;

import com.tasktracker.data.repository.TaskRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
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
public final class StatsViewModel_Factory implements Factory<StatsViewModel> {
  private final Provider<TaskRepository> repositoryProvider;

  public StatsViewModel_Factory(Provider<TaskRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public StatsViewModel get() {
    return newInstance(repositoryProvider.get());
  }

  public static StatsViewModel_Factory create(Provider<TaskRepository> repositoryProvider) {
    return new StatsViewModel_Factory(repositoryProvider);
  }

  public static StatsViewModel newInstance(TaskRepository repository) {
    return new StatsViewModel(repository);
  }
}
