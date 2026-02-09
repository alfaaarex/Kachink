package com.tasktracker.notifications;

import com.tasktracker.data.repository.TaskRepository;
import dagger.MembersInjector;
import dagger.internal.DaggerGenerated;
import dagger.internal.InjectedFieldSignature;
import dagger.internal.QualifierMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

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
public final class TaskActionReceiver_MembersInjector implements MembersInjector<TaskActionReceiver> {
  private final Provider<TaskRepository> repositoryProvider;

  private final Provider<TaskNotificationManager> notificationManagerProvider;

  public TaskActionReceiver_MembersInjector(Provider<TaskRepository> repositoryProvider,
      Provider<TaskNotificationManager> notificationManagerProvider) {
    this.repositoryProvider = repositoryProvider;
    this.notificationManagerProvider = notificationManagerProvider;
  }

  public static MembersInjector<TaskActionReceiver> create(
      Provider<TaskRepository> repositoryProvider,
      Provider<TaskNotificationManager> notificationManagerProvider) {
    return new TaskActionReceiver_MembersInjector(repositoryProvider, notificationManagerProvider);
  }

  @Override
  public void injectMembers(TaskActionReceiver instance) {
    injectRepository(instance, repositoryProvider.get());
    injectNotificationManager(instance, notificationManagerProvider.get());
  }

  @InjectedFieldSignature("com.tasktracker.notifications.TaskActionReceiver.repository")
  public static void injectRepository(TaskActionReceiver instance, TaskRepository repository) {
    instance.repository = repository;
  }

  @InjectedFieldSignature("com.tasktracker.notifications.TaskActionReceiver.notificationManager")
  public static void injectNotificationManager(TaskActionReceiver instance,
      TaskNotificationManager notificationManager) {
    instance.notificationManager = notificationManager;
  }
}
