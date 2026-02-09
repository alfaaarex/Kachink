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
public final class LockScreenNotificationService_MembersInjector implements MembersInjector<LockScreenNotificationService> {
  private final Provider<TaskRepository> repositoryProvider;

  public LockScreenNotificationService_MembersInjector(
      Provider<TaskRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  public static MembersInjector<LockScreenNotificationService> create(
      Provider<TaskRepository> repositoryProvider) {
    return new LockScreenNotificationService_MembersInjector(repositoryProvider);
  }

  @Override
  public void injectMembers(LockScreenNotificationService instance) {
    injectRepository(instance, repositoryProvider.get());
  }

  @InjectedFieldSignature("com.tasktracker.notifications.LockScreenNotificationService.repository")
  public static void injectRepository(LockScreenNotificationService instance,
      TaskRepository repository) {
    instance.repository = repository;
  }
}
