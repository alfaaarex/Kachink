package com.tasktracker.ui.viewmodel;

import com.tasktracker.data.repository.TaskRepository;
import com.tasktracker.ml.TaskClassifier;
import com.tasktracker.notifications.TaskNotificationManager;
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
public final class MainViewModel_Factory implements Factory<MainViewModel> {
  private final Provider<TaskRepository> repositoryProvider;

  private final Provider<TaskClassifier> taskClassifierProvider;

  private final Provider<TaskNotificationManager> notificationManagerProvider;

  public MainViewModel_Factory(Provider<TaskRepository> repositoryProvider,
      Provider<TaskClassifier> taskClassifierProvider,
      Provider<TaskNotificationManager> notificationManagerProvider) {
    this.repositoryProvider = repositoryProvider;
    this.taskClassifierProvider = taskClassifierProvider;
    this.notificationManagerProvider = notificationManagerProvider;
  }

  @Override
  public MainViewModel get() {
    return newInstance(repositoryProvider.get(), taskClassifierProvider.get(), notificationManagerProvider.get());
  }

  public static MainViewModel_Factory create(Provider<TaskRepository> repositoryProvider,
      Provider<TaskClassifier> taskClassifierProvider,
      Provider<TaskNotificationManager> notificationManagerProvider) {
    return new MainViewModel_Factory(repositoryProvider, taskClassifierProvider, notificationManagerProvider);
  }

  public static MainViewModel newInstance(TaskRepository repository, TaskClassifier taskClassifier,
      TaskNotificationManager notificationManager) {
    return new MainViewModel(repository, taskClassifier, notificationManager);
  }
}
