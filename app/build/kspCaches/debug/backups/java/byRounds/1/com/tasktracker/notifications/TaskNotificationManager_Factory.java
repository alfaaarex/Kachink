package com.tasktracker.notifications;

import android.content.Context;
import androidx.work.WorkManager;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
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
public final class TaskNotificationManager_Factory implements Factory<TaskNotificationManager> {
  private final Provider<Context> contextProvider;

  private final Provider<WorkManager> workManagerProvider;

  public TaskNotificationManager_Factory(Provider<Context> contextProvider,
      Provider<WorkManager> workManagerProvider) {
    this.contextProvider = contextProvider;
    this.workManagerProvider = workManagerProvider;
  }

  @Override
  public TaskNotificationManager get() {
    return newInstance(contextProvider.get(), workManagerProvider.get());
  }

  public static TaskNotificationManager_Factory create(Provider<Context> contextProvider,
      Provider<WorkManager> workManagerProvider) {
    return new TaskNotificationManager_Factory(contextProvider, workManagerProvider);
  }

  public static TaskNotificationManager newInstance(Context context, WorkManager workManager) {
    return new TaskNotificationManager(context, workManager);
  }
}
