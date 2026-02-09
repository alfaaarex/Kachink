package com.tasktracker.notifications;

import android.content.Context;
import androidx.work.WorkerParameters;
import dagger.internal.DaggerGenerated;
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
public final class TaskReminderWorker_Factory {
  private final Provider<TaskNotificationManager> notificationManagerProvider;

  public TaskReminderWorker_Factory(Provider<TaskNotificationManager> notificationManagerProvider) {
    this.notificationManagerProvider = notificationManagerProvider;
  }

  public TaskReminderWorker get(Context context, WorkerParameters params) {
    return newInstance(context, params, notificationManagerProvider.get());
  }

  public static TaskReminderWorker_Factory create(
      Provider<TaskNotificationManager> notificationManagerProvider) {
    return new TaskReminderWorker_Factory(notificationManagerProvider);
  }

  public static TaskReminderWorker newInstance(Context context, WorkerParameters params,
      TaskNotificationManager notificationManager) {
    return new TaskReminderWorker(context, params, notificationManager);
  }
}
