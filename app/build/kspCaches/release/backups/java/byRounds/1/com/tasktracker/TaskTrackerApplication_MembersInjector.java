package com.tasktracker;

import androidx.hilt.work.HiltWorkerFactory;
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
public final class TaskTrackerApplication_MembersInjector implements MembersInjector<TaskTrackerApplication> {
  private final Provider<HiltWorkerFactory> workerFactoryProvider;

  public TaskTrackerApplication_MembersInjector(Provider<HiltWorkerFactory> workerFactoryProvider) {
    this.workerFactoryProvider = workerFactoryProvider;
  }

  public static MembersInjector<TaskTrackerApplication> create(
      Provider<HiltWorkerFactory> workerFactoryProvider) {
    return new TaskTrackerApplication_MembersInjector(workerFactoryProvider);
  }

  @Override
  public void injectMembers(TaskTrackerApplication instance) {
    injectWorkerFactory(instance, workerFactoryProvider.get());
  }

  @InjectedFieldSignature("com.tasktracker.TaskTrackerApplication.workerFactory")
  public static void injectWorkerFactory(TaskTrackerApplication instance,
      HiltWorkerFactory workerFactory) {
    instance.workerFactory = workerFactory;
  }
}
