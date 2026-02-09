package com.tasktracker.di;

import android.content.Context;
import androidx.work.WorkManager;
import com.tasktracker.notifications.TaskNotificationManager;
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
public final class AppModule_ProvideTaskNotificationManagerFactory implements Factory<TaskNotificationManager> {
  private final Provider<Context> contextProvider;

  private final Provider<WorkManager> workManagerProvider;

  public AppModule_ProvideTaskNotificationManagerFactory(Provider<Context> contextProvider,
      Provider<WorkManager> workManagerProvider) {
    this.contextProvider = contextProvider;
    this.workManagerProvider = workManagerProvider;
  }

  @Override
  public TaskNotificationManager get() {
    return provideTaskNotificationManager(contextProvider.get(), workManagerProvider.get());
  }

  public static AppModule_ProvideTaskNotificationManagerFactory create(
      Provider<Context> contextProvider, Provider<WorkManager> workManagerProvider) {
    return new AppModule_ProvideTaskNotificationManagerFactory(contextProvider, workManagerProvider);
  }

  public static TaskNotificationManager provideTaskNotificationManager(Context context,
      WorkManager workManager) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideTaskNotificationManager(context, workManager));
  }
}
