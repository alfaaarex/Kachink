package com.tasktracker.data.local;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.tasktracker.data.model.Task;
import com.tasktracker.data.model.TaskLabel;
import java.lang.Class;
import java.lang.Exception;
import java.lang.IllegalStateException;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class TaskDao_Impl implements TaskDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Task> __insertionAdapterOfTask;

  private final Converters __converters = new Converters();

  private final EntityDeletionOrUpdateAdapter<Task> __deletionAdapterOfTask;

  private final EntityDeletionOrUpdateAdapter<Task> __updateAdapterOfTask;

  private final SharedSQLiteStatement __preparedStmtOfDeleteTaskById;

  private final SharedSQLiteStatement __preparedStmtOfUpdateTaskCompletion;

  private final SharedSQLiteStatement __preparedStmtOfDeleteCompletedTasksBefore;

  public TaskDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfTask = new EntityInsertionAdapter<Task>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `tasks` (`id`,`title`,`description`,`startTime`,`endTime`,`label`,`isCompleted`,`createdAt`,`updatedAt`,`isRecurring`,`recurringPattern`,`notificationEnabled`,`notificationMinutesBefore`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Task entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getTitle());
        statement.bindString(3, entity.getDescription());
        final String _tmp = __converters.fromLocalDateTime(entity.getStartTime());
        if (_tmp == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, _tmp);
        }
        final String _tmp_1 = __converters.fromLocalDateTime(entity.getEndTime());
        if (_tmp_1 == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, _tmp_1);
        }
        final String _tmp_2 = __converters.fromTaskLabel(entity.getLabel());
        statement.bindString(6, _tmp_2);
        final int _tmp_3 = entity.isCompleted() ? 1 : 0;
        statement.bindLong(7, _tmp_3);
        final String _tmp_4 = __converters.fromLocalDateTime(entity.getCreatedAt());
        if (_tmp_4 == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, _tmp_4);
        }
        final String _tmp_5 = __converters.fromLocalDateTime(entity.getUpdatedAt());
        if (_tmp_5 == null) {
          statement.bindNull(9);
        } else {
          statement.bindString(9, _tmp_5);
        }
        final int _tmp_6 = entity.isRecurring() ? 1 : 0;
        statement.bindLong(10, _tmp_6);
        if (entity.getRecurringPattern() == null) {
          statement.bindNull(11);
        } else {
          statement.bindString(11, entity.getRecurringPattern());
        }
        final int _tmp_7 = entity.getNotificationEnabled() ? 1 : 0;
        statement.bindLong(12, _tmp_7);
        statement.bindLong(13, entity.getNotificationMinutesBefore());
      }
    };
    this.__deletionAdapterOfTask = new EntityDeletionOrUpdateAdapter<Task>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `tasks` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Task entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfTask = new EntityDeletionOrUpdateAdapter<Task>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `tasks` SET `id` = ?,`title` = ?,`description` = ?,`startTime` = ?,`endTime` = ?,`label` = ?,`isCompleted` = ?,`createdAt` = ?,`updatedAt` = ?,`isRecurring` = ?,`recurringPattern` = ?,`notificationEnabled` = ?,`notificationMinutesBefore` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final Task entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getTitle());
        statement.bindString(3, entity.getDescription());
        final String _tmp = __converters.fromLocalDateTime(entity.getStartTime());
        if (_tmp == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, _tmp);
        }
        final String _tmp_1 = __converters.fromLocalDateTime(entity.getEndTime());
        if (_tmp_1 == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, _tmp_1);
        }
        final String _tmp_2 = __converters.fromTaskLabel(entity.getLabel());
        statement.bindString(6, _tmp_2);
        final int _tmp_3 = entity.isCompleted() ? 1 : 0;
        statement.bindLong(7, _tmp_3);
        final String _tmp_4 = __converters.fromLocalDateTime(entity.getCreatedAt());
        if (_tmp_4 == null) {
          statement.bindNull(8);
        } else {
          statement.bindString(8, _tmp_4);
        }
        final String _tmp_5 = __converters.fromLocalDateTime(entity.getUpdatedAt());
        if (_tmp_5 == null) {
          statement.bindNull(9);
        } else {
          statement.bindString(9, _tmp_5);
        }
        final int _tmp_6 = entity.isRecurring() ? 1 : 0;
        statement.bindLong(10, _tmp_6);
        if (entity.getRecurringPattern() == null) {
          statement.bindNull(11);
        } else {
          statement.bindString(11, entity.getRecurringPattern());
        }
        final int _tmp_7 = entity.getNotificationEnabled() ? 1 : 0;
        statement.bindLong(12, _tmp_7);
        statement.bindLong(13, entity.getNotificationMinutesBefore());
        statement.bindLong(14, entity.getId());
      }
    };
    this.__preparedStmtOfDeleteTaskById = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM tasks WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfUpdateTaskCompletion = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "UPDATE tasks SET isCompleted = ?, updatedAt = ? WHERE id = ?";
        return _query;
      }
    };
    this.__preparedStmtOfDeleteCompletedTasksBefore = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM tasks WHERE isCompleted = 1 AND updatedAt < ?";
        return _query;
      }
    };
  }

  @Override
  public Object insertTask(final Task task, final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfTask.insertAndReturnId(task);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object insertTasks(final List<Task> tasks, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfTask.insert(tasks);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteTask(final Task task, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfTask.handle(task);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateTask(final Task task, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfTask.handle(task);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteTaskById(final long taskId, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteTaskById.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, taskId);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfDeleteTaskById.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object updateTaskCompletion(final long taskId, final boolean isCompleted,
      final LocalDateTime updatedAt, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateTaskCompletion.acquire();
        int _argIndex = 1;
        final int _tmp = isCompleted ? 1 : 0;
        _stmt.bindLong(_argIndex, _tmp);
        _argIndex = 2;
        final String _tmp_1 = __converters.fromLocalDateTime(updatedAt);
        if (_tmp_1 == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindString(_argIndex, _tmp_1);
        }
        _argIndex = 3;
        _stmt.bindLong(_argIndex, taskId);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfUpdateTaskCompletion.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteCompletedTasksBefore(final LocalDateTime before,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteCompletedTasksBefore.acquire();
        int _argIndex = 1;
        final String _tmp = __converters.fromLocalDateTime(before);
        if (_tmp == null) {
          _stmt.bindNull(_argIndex);
        } else {
          _stmt.bindString(_argIndex, _tmp);
        }
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfDeleteCompletedTasksBefore.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<Task>> getTasksByDate(final LocalDateTime date) {
    final String _sql = "SELECT * FROM tasks WHERE DATE(startTime) = DATE(?) ORDER BY startTime ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    final String _tmp = __converters.fromLocalDateTime(date);
    if (_tmp == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, _tmp);
    }
    return CoroutinesRoom.createFlow(__db, false, new String[] {"tasks"}, new Callable<List<Task>>() {
      @Override
      @NonNull
      public List<Task> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfStartTime = CursorUtil.getColumnIndexOrThrow(_cursor, "startTime");
          final int _cursorIndexOfEndTime = CursorUtil.getColumnIndexOrThrow(_cursor, "endTime");
          final int _cursorIndexOfLabel = CursorUtil.getColumnIndexOrThrow(_cursor, "label");
          final int _cursorIndexOfIsCompleted = CursorUtil.getColumnIndexOrThrow(_cursor, "isCompleted");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final int _cursorIndexOfIsRecurring = CursorUtil.getColumnIndexOrThrow(_cursor, "isRecurring");
          final int _cursorIndexOfRecurringPattern = CursorUtil.getColumnIndexOrThrow(_cursor, "recurringPattern");
          final int _cursorIndexOfNotificationEnabled = CursorUtil.getColumnIndexOrThrow(_cursor, "notificationEnabled");
          final int _cursorIndexOfNotificationMinutesBefore = CursorUtil.getColumnIndexOrThrow(_cursor, "notificationMinutesBefore");
          final List<Task> _result = new ArrayList<Task>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Task _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            final LocalDateTime _tmpStartTime;
            final String _tmp_1;
            if (_cursor.isNull(_cursorIndexOfStartTime)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getString(_cursorIndexOfStartTime);
            }
            _tmpStartTime = __converters.toLocalDateTime(_tmp_1);
            final LocalDateTime _tmpEndTime;
            final String _tmp_2;
            if (_cursor.isNull(_cursorIndexOfEndTime)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getString(_cursorIndexOfEndTime);
            }
            _tmpEndTime = __converters.toLocalDateTime(_tmp_2);
            final TaskLabel _tmpLabel;
            final String _tmp_3;
            _tmp_3 = _cursor.getString(_cursorIndexOfLabel);
            _tmpLabel = __converters.toTaskLabel(_tmp_3);
            final boolean _tmpIsCompleted;
            final int _tmp_4;
            _tmp_4 = _cursor.getInt(_cursorIndexOfIsCompleted);
            _tmpIsCompleted = _tmp_4 != 0;
            final LocalDateTime _tmpCreatedAt;
            final String _tmp_5;
            if (_cursor.isNull(_cursorIndexOfCreatedAt)) {
              _tmp_5 = null;
            } else {
              _tmp_5 = _cursor.getString(_cursorIndexOfCreatedAt);
            }
            final LocalDateTime _tmp_6 = __converters.toLocalDateTime(_tmp_5);
            if (_tmp_6 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.time.LocalDateTime', but it was NULL.");
            } else {
              _tmpCreatedAt = _tmp_6;
            }
            final LocalDateTime _tmpUpdatedAt;
            final String _tmp_7;
            if (_cursor.isNull(_cursorIndexOfUpdatedAt)) {
              _tmp_7 = null;
            } else {
              _tmp_7 = _cursor.getString(_cursorIndexOfUpdatedAt);
            }
            final LocalDateTime _tmp_8 = __converters.toLocalDateTime(_tmp_7);
            if (_tmp_8 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.time.LocalDateTime', but it was NULL.");
            } else {
              _tmpUpdatedAt = _tmp_8;
            }
            final boolean _tmpIsRecurring;
            final int _tmp_9;
            _tmp_9 = _cursor.getInt(_cursorIndexOfIsRecurring);
            _tmpIsRecurring = _tmp_9 != 0;
            final String _tmpRecurringPattern;
            if (_cursor.isNull(_cursorIndexOfRecurringPattern)) {
              _tmpRecurringPattern = null;
            } else {
              _tmpRecurringPattern = _cursor.getString(_cursorIndexOfRecurringPattern);
            }
            final boolean _tmpNotificationEnabled;
            final int _tmp_10;
            _tmp_10 = _cursor.getInt(_cursorIndexOfNotificationEnabled);
            _tmpNotificationEnabled = _tmp_10 != 0;
            final int _tmpNotificationMinutesBefore;
            _tmpNotificationMinutesBefore = _cursor.getInt(_cursorIndexOfNotificationMinutesBefore);
            _item = new Task(_tmpId,_tmpTitle,_tmpDescription,_tmpStartTime,_tmpEndTime,_tmpLabel,_tmpIsCompleted,_tmpCreatedAt,_tmpUpdatedAt,_tmpIsRecurring,_tmpRecurringPattern,_tmpNotificationEnabled,_tmpNotificationMinutesBefore);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<Task>> getUnscheduledTasks() {
    final String _sql = "SELECT * FROM tasks WHERE startTime IS NULL ORDER BY createdAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"tasks"}, new Callable<List<Task>>() {
      @Override
      @NonNull
      public List<Task> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfStartTime = CursorUtil.getColumnIndexOrThrow(_cursor, "startTime");
          final int _cursorIndexOfEndTime = CursorUtil.getColumnIndexOrThrow(_cursor, "endTime");
          final int _cursorIndexOfLabel = CursorUtil.getColumnIndexOrThrow(_cursor, "label");
          final int _cursorIndexOfIsCompleted = CursorUtil.getColumnIndexOrThrow(_cursor, "isCompleted");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final int _cursorIndexOfIsRecurring = CursorUtil.getColumnIndexOrThrow(_cursor, "isRecurring");
          final int _cursorIndexOfRecurringPattern = CursorUtil.getColumnIndexOrThrow(_cursor, "recurringPattern");
          final int _cursorIndexOfNotificationEnabled = CursorUtil.getColumnIndexOrThrow(_cursor, "notificationEnabled");
          final int _cursorIndexOfNotificationMinutesBefore = CursorUtil.getColumnIndexOrThrow(_cursor, "notificationMinutesBefore");
          final List<Task> _result = new ArrayList<Task>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Task _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            final LocalDateTime _tmpStartTime;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfStartTime)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfStartTime);
            }
            _tmpStartTime = __converters.toLocalDateTime(_tmp);
            final LocalDateTime _tmpEndTime;
            final String _tmp_1;
            if (_cursor.isNull(_cursorIndexOfEndTime)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getString(_cursorIndexOfEndTime);
            }
            _tmpEndTime = __converters.toLocalDateTime(_tmp_1);
            final TaskLabel _tmpLabel;
            final String _tmp_2;
            _tmp_2 = _cursor.getString(_cursorIndexOfLabel);
            _tmpLabel = __converters.toTaskLabel(_tmp_2);
            final boolean _tmpIsCompleted;
            final int _tmp_3;
            _tmp_3 = _cursor.getInt(_cursorIndexOfIsCompleted);
            _tmpIsCompleted = _tmp_3 != 0;
            final LocalDateTime _tmpCreatedAt;
            final String _tmp_4;
            if (_cursor.isNull(_cursorIndexOfCreatedAt)) {
              _tmp_4 = null;
            } else {
              _tmp_4 = _cursor.getString(_cursorIndexOfCreatedAt);
            }
            final LocalDateTime _tmp_5 = __converters.toLocalDateTime(_tmp_4);
            if (_tmp_5 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.time.LocalDateTime', but it was NULL.");
            } else {
              _tmpCreatedAt = _tmp_5;
            }
            final LocalDateTime _tmpUpdatedAt;
            final String _tmp_6;
            if (_cursor.isNull(_cursorIndexOfUpdatedAt)) {
              _tmp_6 = null;
            } else {
              _tmp_6 = _cursor.getString(_cursorIndexOfUpdatedAt);
            }
            final LocalDateTime _tmp_7 = __converters.toLocalDateTime(_tmp_6);
            if (_tmp_7 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.time.LocalDateTime', but it was NULL.");
            } else {
              _tmpUpdatedAt = _tmp_7;
            }
            final boolean _tmpIsRecurring;
            final int _tmp_8;
            _tmp_8 = _cursor.getInt(_cursorIndexOfIsRecurring);
            _tmpIsRecurring = _tmp_8 != 0;
            final String _tmpRecurringPattern;
            if (_cursor.isNull(_cursorIndexOfRecurringPattern)) {
              _tmpRecurringPattern = null;
            } else {
              _tmpRecurringPattern = _cursor.getString(_cursorIndexOfRecurringPattern);
            }
            final boolean _tmpNotificationEnabled;
            final int _tmp_9;
            _tmp_9 = _cursor.getInt(_cursorIndexOfNotificationEnabled);
            _tmpNotificationEnabled = _tmp_9 != 0;
            final int _tmpNotificationMinutesBefore;
            _tmpNotificationMinutesBefore = _cursor.getInt(_cursorIndexOfNotificationMinutesBefore);
            _item = new Task(_tmpId,_tmpTitle,_tmpDescription,_tmpStartTime,_tmpEndTime,_tmpLabel,_tmpIsCompleted,_tmpCreatedAt,_tmpUpdatedAt,_tmpIsRecurring,_tmpRecurringPattern,_tmpNotificationEnabled,_tmpNotificationMinutesBefore);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<Task>> getTasksByLabel(final String label) {
    final String _sql = "SELECT * FROM tasks WHERE label = ? ORDER BY startTime ASC, createdAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, label);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"tasks"}, new Callable<List<Task>>() {
      @Override
      @NonNull
      public List<Task> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfStartTime = CursorUtil.getColumnIndexOrThrow(_cursor, "startTime");
          final int _cursorIndexOfEndTime = CursorUtil.getColumnIndexOrThrow(_cursor, "endTime");
          final int _cursorIndexOfLabel = CursorUtil.getColumnIndexOrThrow(_cursor, "label");
          final int _cursorIndexOfIsCompleted = CursorUtil.getColumnIndexOrThrow(_cursor, "isCompleted");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final int _cursorIndexOfIsRecurring = CursorUtil.getColumnIndexOrThrow(_cursor, "isRecurring");
          final int _cursorIndexOfRecurringPattern = CursorUtil.getColumnIndexOrThrow(_cursor, "recurringPattern");
          final int _cursorIndexOfNotificationEnabled = CursorUtil.getColumnIndexOrThrow(_cursor, "notificationEnabled");
          final int _cursorIndexOfNotificationMinutesBefore = CursorUtil.getColumnIndexOrThrow(_cursor, "notificationMinutesBefore");
          final List<Task> _result = new ArrayList<Task>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Task _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            final LocalDateTime _tmpStartTime;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfStartTime)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfStartTime);
            }
            _tmpStartTime = __converters.toLocalDateTime(_tmp);
            final LocalDateTime _tmpEndTime;
            final String _tmp_1;
            if (_cursor.isNull(_cursorIndexOfEndTime)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getString(_cursorIndexOfEndTime);
            }
            _tmpEndTime = __converters.toLocalDateTime(_tmp_1);
            final TaskLabel _tmpLabel;
            final String _tmp_2;
            _tmp_2 = _cursor.getString(_cursorIndexOfLabel);
            _tmpLabel = __converters.toTaskLabel(_tmp_2);
            final boolean _tmpIsCompleted;
            final int _tmp_3;
            _tmp_3 = _cursor.getInt(_cursorIndexOfIsCompleted);
            _tmpIsCompleted = _tmp_3 != 0;
            final LocalDateTime _tmpCreatedAt;
            final String _tmp_4;
            if (_cursor.isNull(_cursorIndexOfCreatedAt)) {
              _tmp_4 = null;
            } else {
              _tmp_4 = _cursor.getString(_cursorIndexOfCreatedAt);
            }
            final LocalDateTime _tmp_5 = __converters.toLocalDateTime(_tmp_4);
            if (_tmp_5 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.time.LocalDateTime', but it was NULL.");
            } else {
              _tmpCreatedAt = _tmp_5;
            }
            final LocalDateTime _tmpUpdatedAt;
            final String _tmp_6;
            if (_cursor.isNull(_cursorIndexOfUpdatedAt)) {
              _tmp_6 = null;
            } else {
              _tmp_6 = _cursor.getString(_cursorIndexOfUpdatedAt);
            }
            final LocalDateTime _tmp_7 = __converters.toLocalDateTime(_tmp_6);
            if (_tmp_7 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.time.LocalDateTime', but it was NULL.");
            } else {
              _tmpUpdatedAt = _tmp_7;
            }
            final boolean _tmpIsRecurring;
            final int _tmp_8;
            _tmp_8 = _cursor.getInt(_cursorIndexOfIsRecurring);
            _tmpIsRecurring = _tmp_8 != 0;
            final String _tmpRecurringPattern;
            if (_cursor.isNull(_cursorIndexOfRecurringPattern)) {
              _tmpRecurringPattern = null;
            } else {
              _tmpRecurringPattern = _cursor.getString(_cursorIndexOfRecurringPattern);
            }
            final boolean _tmpNotificationEnabled;
            final int _tmp_9;
            _tmp_9 = _cursor.getInt(_cursorIndexOfNotificationEnabled);
            _tmpNotificationEnabled = _tmp_9 != 0;
            final int _tmpNotificationMinutesBefore;
            _tmpNotificationMinutesBefore = _cursor.getInt(_cursorIndexOfNotificationMinutesBefore);
            _item = new Task(_tmpId,_tmpTitle,_tmpDescription,_tmpStartTime,_tmpEndTime,_tmpLabel,_tmpIsCompleted,_tmpCreatedAt,_tmpUpdatedAt,_tmpIsRecurring,_tmpRecurringPattern,_tmpNotificationEnabled,_tmpNotificationMinutesBefore);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object getTaskById(final long taskId, final Continuation<? super Task> $completion) {
    final String _sql = "SELECT * FROM tasks WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, taskId);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Task>() {
      @Override
      @Nullable
      public Task call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfStartTime = CursorUtil.getColumnIndexOrThrow(_cursor, "startTime");
          final int _cursorIndexOfEndTime = CursorUtil.getColumnIndexOrThrow(_cursor, "endTime");
          final int _cursorIndexOfLabel = CursorUtil.getColumnIndexOrThrow(_cursor, "label");
          final int _cursorIndexOfIsCompleted = CursorUtil.getColumnIndexOrThrow(_cursor, "isCompleted");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final int _cursorIndexOfIsRecurring = CursorUtil.getColumnIndexOrThrow(_cursor, "isRecurring");
          final int _cursorIndexOfRecurringPattern = CursorUtil.getColumnIndexOrThrow(_cursor, "recurringPattern");
          final int _cursorIndexOfNotificationEnabled = CursorUtil.getColumnIndexOrThrow(_cursor, "notificationEnabled");
          final int _cursorIndexOfNotificationMinutesBefore = CursorUtil.getColumnIndexOrThrow(_cursor, "notificationMinutesBefore");
          final Task _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            final LocalDateTime _tmpStartTime;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfStartTime)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfStartTime);
            }
            _tmpStartTime = __converters.toLocalDateTime(_tmp);
            final LocalDateTime _tmpEndTime;
            final String _tmp_1;
            if (_cursor.isNull(_cursorIndexOfEndTime)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getString(_cursorIndexOfEndTime);
            }
            _tmpEndTime = __converters.toLocalDateTime(_tmp_1);
            final TaskLabel _tmpLabel;
            final String _tmp_2;
            _tmp_2 = _cursor.getString(_cursorIndexOfLabel);
            _tmpLabel = __converters.toTaskLabel(_tmp_2);
            final boolean _tmpIsCompleted;
            final int _tmp_3;
            _tmp_3 = _cursor.getInt(_cursorIndexOfIsCompleted);
            _tmpIsCompleted = _tmp_3 != 0;
            final LocalDateTime _tmpCreatedAt;
            final String _tmp_4;
            if (_cursor.isNull(_cursorIndexOfCreatedAt)) {
              _tmp_4 = null;
            } else {
              _tmp_4 = _cursor.getString(_cursorIndexOfCreatedAt);
            }
            final LocalDateTime _tmp_5 = __converters.toLocalDateTime(_tmp_4);
            if (_tmp_5 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.time.LocalDateTime', but it was NULL.");
            } else {
              _tmpCreatedAt = _tmp_5;
            }
            final LocalDateTime _tmpUpdatedAt;
            final String _tmp_6;
            if (_cursor.isNull(_cursorIndexOfUpdatedAt)) {
              _tmp_6 = null;
            } else {
              _tmp_6 = _cursor.getString(_cursorIndexOfUpdatedAt);
            }
            final LocalDateTime _tmp_7 = __converters.toLocalDateTime(_tmp_6);
            if (_tmp_7 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.time.LocalDateTime', but it was NULL.");
            } else {
              _tmpUpdatedAt = _tmp_7;
            }
            final boolean _tmpIsRecurring;
            final int _tmp_8;
            _tmp_8 = _cursor.getInt(_cursorIndexOfIsRecurring);
            _tmpIsRecurring = _tmp_8 != 0;
            final String _tmpRecurringPattern;
            if (_cursor.isNull(_cursorIndexOfRecurringPattern)) {
              _tmpRecurringPattern = null;
            } else {
              _tmpRecurringPattern = _cursor.getString(_cursorIndexOfRecurringPattern);
            }
            final boolean _tmpNotificationEnabled;
            final int _tmp_9;
            _tmp_9 = _cursor.getInt(_cursorIndexOfNotificationEnabled);
            _tmpNotificationEnabled = _tmp_9 != 0;
            final int _tmpNotificationMinutesBefore;
            _tmpNotificationMinutesBefore = _cursor.getInt(_cursorIndexOfNotificationMinutesBefore);
            _result = new Task(_tmpId,_tmpTitle,_tmpDescription,_tmpStartTime,_tmpEndTime,_tmpLabel,_tmpIsCompleted,_tmpCreatedAt,_tmpUpdatedAt,_tmpIsRecurring,_tmpRecurringPattern,_tmpNotificationEnabled,_tmpNotificationMinutesBefore);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<Task> getTaskByIdFlow(final long taskId) {
    final String _sql = "SELECT * FROM tasks WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, taskId);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"tasks"}, new Callable<Task>() {
      @Override
      @Nullable
      public Task call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfStartTime = CursorUtil.getColumnIndexOrThrow(_cursor, "startTime");
          final int _cursorIndexOfEndTime = CursorUtil.getColumnIndexOrThrow(_cursor, "endTime");
          final int _cursorIndexOfLabel = CursorUtil.getColumnIndexOrThrow(_cursor, "label");
          final int _cursorIndexOfIsCompleted = CursorUtil.getColumnIndexOrThrow(_cursor, "isCompleted");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final int _cursorIndexOfIsRecurring = CursorUtil.getColumnIndexOrThrow(_cursor, "isRecurring");
          final int _cursorIndexOfRecurringPattern = CursorUtil.getColumnIndexOrThrow(_cursor, "recurringPattern");
          final int _cursorIndexOfNotificationEnabled = CursorUtil.getColumnIndexOrThrow(_cursor, "notificationEnabled");
          final int _cursorIndexOfNotificationMinutesBefore = CursorUtil.getColumnIndexOrThrow(_cursor, "notificationMinutesBefore");
          final Task _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            final LocalDateTime _tmpStartTime;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfStartTime)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfStartTime);
            }
            _tmpStartTime = __converters.toLocalDateTime(_tmp);
            final LocalDateTime _tmpEndTime;
            final String _tmp_1;
            if (_cursor.isNull(_cursorIndexOfEndTime)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getString(_cursorIndexOfEndTime);
            }
            _tmpEndTime = __converters.toLocalDateTime(_tmp_1);
            final TaskLabel _tmpLabel;
            final String _tmp_2;
            _tmp_2 = _cursor.getString(_cursorIndexOfLabel);
            _tmpLabel = __converters.toTaskLabel(_tmp_2);
            final boolean _tmpIsCompleted;
            final int _tmp_3;
            _tmp_3 = _cursor.getInt(_cursorIndexOfIsCompleted);
            _tmpIsCompleted = _tmp_3 != 0;
            final LocalDateTime _tmpCreatedAt;
            final String _tmp_4;
            if (_cursor.isNull(_cursorIndexOfCreatedAt)) {
              _tmp_4 = null;
            } else {
              _tmp_4 = _cursor.getString(_cursorIndexOfCreatedAt);
            }
            final LocalDateTime _tmp_5 = __converters.toLocalDateTime(_tmp_4);
            if (_tmp_5 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.time.LocalDateTime', but it was NULL.");
            } else {
              _tmpCreatedAt = _tmp_5;
            }
            final LocalDateTime _tmpUpdatedAt;
            final String _tmp_6;
            if (_cursor.isNull(_cursorIndexOfUpdatedAt)) {
              _tmp_6 = null;
            } else {
              _tmp_6 = _cursor.getString(_cursorIndexOfUpdatedAt);
            }
            final LocalDateTime _tmp_7 = __converters.toLocalDateTime(_tmp_6);
            if (_tmp_7 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.time.LocalDateTime', but it was NULL.");
            } else {
              _tmpUpdatedAt = _tmp_7;
            }
            final boolean _tmpIsRecurring;
            final int _tmp_8;
            _tmp_8 = _cursor.getInt(_cursorIndexOfIsRecurring);
            _tmpIsRecurring = _tmp_8 != 0;
            final String _tmpRecurringPattern;
            if (_cursor.isNull(_cursorIndexOfRecurringPattern)) {
              _tmpRecurringPattern = null;
            } else {
              _tmpRecurringPattern = _cursor.getString(_cursorIndexOfRecurringPattern);
            }
            final boolean _tmpNotificationEnabled;
            final int _tmp_9;
            _tmp_9 = _cursor.getInt(_cursorIndexOfNotificationEnabled);
            _tmpNotificationEnabled = _tmp_9 != 0;
            final int _tmpNotificationMinutesBefore;
            _tmpNotificationMinutesBefore = _cursor.getInt(_cursorIndexOfNotificationMinutesBefore);
            _result = new Task(_tmpId,_tmpTitle,_tmpDescription,_tmpStartTime,_tmpEndTime,_tmpLabel,_tmpIsCompleted,_tmpCreatedAt,_tmpUpdatedAt,_tmpIsRecurring,_tmpRecurringPattern,_tmpNotificationEnabled,_tmpNotificationMinutesBefore);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<Task>> getTasksInRange(final LocalDateTime startTime,
      final LocalDateTime endTime) {
    final String _sql = "SELECT * FROM tasks WHERE isCompleted = 0 AND startTime >= ? AND startTime <= ? ORDER BY startTime ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    final String _tmp = __converters.fromLocalDateTime(startTime);
    if (_tmp == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, _tmp);
    }
    _argIndex = 2;
    final String _tmp_1 = __converters.fromLocalDateTime(endTime);
    if (_tmp_1 == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, _tmp_1);
    }
    return CoroutinesRoom.createFlow(__db, false, new String[] {"tasks"}, new Callable<List<Task>>() {
      @Override
      @NonNull
      public List<Task> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfStartTime = CursorUtil.getColumnIndexOrThrow(_cursor, "startTime");
          final int _cursorIndexOfEndTime = CursorUtil.getColumnIndexOrThrow(_cursor, "endTime");
          final int _cursorIndexOfLabel = CursorUtil.getColumnIndexOrThrow(_cursor, "label");
          final int _cursorIndexOfIsCompleted = CursorUtil.getColumnIndexOrThrow(_cursor, "isCompleted");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final int _cursorIndexOfIsRecurring = CursorUtil.getColumnIndexOrThrow(_cursor, "isRecurring");
          final int _cursorIndexOfRecurringPattern = CursorUtil.getColumnIndexOrThrow(_cursor, "recurringPattern");
          final int _cursorIndexOfNotificationEnabled = CursorUtil.getColumnIndexOrThrow(_cursor, "notificationEnabled");
          final int _cursorIndexOfNotificationMinutesBefore = CursorUtil.getColumnIndexOrThrow(_cursor, "notificationMinutesBefore");
          final List<Task> _result = new ArrayList<Task>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Task _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            final LocalDateTime _tmpStartTime;
            final String _tmp_2;
            if (_cursor.isNull(_cursorIndexOfStartTime)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getString(_cursorIndexOfStartTime);
            }
            _tmpStartTime = __converters.toLocalDateTime(_tmp_2);
            final LocalDateTime _tmpEndTime;
            final String _tmp_3;
            if (_cursor.isNull(_cursorIndexOfEndTime)) {
              _tmp_3 = null;
            } else {
              _tmp_3 = _cursor.getString(_cursorIndexOfEndTime);
            }
            _tmpEndTime = __converters.toLocalDateTime(_tmp_3);
            final TaskLabel _tmpLabel;
            final String _tmp_4;
            _tmp_4 = _cursor.getString(_cursorIndexOfLabel);
            _tmpLabel = __converters.toTaskLabel(_tmp_4);
            final boolean _tmpIsCompleted;
            final int _tmp_5;
            _tmp_5 = _cursor.getInt(_cursorIndexOfIsCompleted);
            _tmpIsCompleted = _tmp_5 != 0;
            final LocalDateTime _tmpCreatedAt;
            final String _tmp_6;
            if (_cursor.isNull(_cursorIndexOfCreatedAt)) {
              _tmp_6 = null;
            } else {
              _tmp_6 = _cursor.getString(_cursorIndexOfCreatedAt);
            }
            final LocalDateTime _tmp_7 = __converters.toLocalDateTime(_tmp_6);
            if (_tmp_7 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.time.LocalDateTime', but it was NULL.");
            } else {
              _tmpCreatedAt = _tmp_7;
            }
            final LocalDateTime _tmpUpdatedAt;
            final String _tmp_8;
            if (_cursor.isNull(_cursorIndexOfUpdatedAt)) {
              _tmp_8 = null;
            } else {
              _tmp_8 = _cursor.getString(_cursorIndexOfUpdatedAt);
            }
            final LocalDateTime _tmp_9 = __converters.toLocalDateTime(_tmp_8);
            if (_tmp_9 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.time.LocalDateTime', but it was NULL.");
            } else {
              _tmpUpdatedAt = _tmp_9;
            }
            final boolean _tmpIsRecurring;
            final int _tmp_10;
            _tmp_10 = _cursor.getInt(_cursorIndexOfIsRecurring);
            _tmpIsRecurring = _tmp_10 != 0;
            final String _tmpRecurringPattern;
            if (_cursor.isNull(_cursorIndexOfRecurringPattern)) {
              _tmpRecurringPattern = null;
            } else {
              _tmpRecurringPattern = _cursor.getString(_cursorIndexOfRecurringPattern);
            }
            final boolean _tmpNotificationEnabled;
            final int _tmp_11;
            _tmp_11 = _cursor.getInt(_cursorIndexOfNotificationEnabled);
            _tmpNotificationEnabled = _tmp_11 != 0;
            final int _tmpNotificationMinutesBefore;
            _tmpNotificationMinutesBefore = _cursor.getInt(_cursorIndexOfNotificationMinutesBefore);
            _item = new Task(_tmpId,_tmpTitle,_tmpDescription,_tmpStartTime,_tmpEndTime,_tmpLabel,_tmpIsCompleted,_tmpCreatedAt,_tmpUpdatedAt,_tmpIsRecurring,_tmpRecurringPattern,_tmpNotificationEnabled,_tmpNotificationMinutesBefore);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object getUpcomingTasksWithNotifications(final LocalDateTime now,
      final Continuation<? super List<Task>> $completion) {
    final String _sql = "SELECT * FROM tasks WHERE isCompleted = 0 AND notificationEnabled = 1 AND startTime > ? ORDER BY startTime ASC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    final String _tmp = __converters.fromLocalDateTime(now);
    if (_tmp == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, _tmp);
    }
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<List<Task>>() {
      @Override
      @NonNull
      public List<Task> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfStartTime = CursorUtil.getColumnIndexOrThrow(_cursor, "startTime");
          final int _cursorIndexOfEndTime = CursorUtil.getColumnIndexOrThrow(_cursor, "endTime");
          final int _cursorIndexOfLabel = CursorUtil.getColumnIndexOrThrow(_cursor, "label");
          final int _cursorIndexOfIsCompleted = CursorUtil.getColumnIndexOrThrow(_cursor, "isCompleted");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final int _cursorIndexOfIsRecurring = CursorUtil.getColumnIndexOrThrow(_cursor, "isRecurring");
          final int _cursorIndexOfRecurringPattern = CursorUtil.getColumnIndexOrThrow(_cursor, "recurringPattern");
          final int _cursorIndexOfNotificationEnabled = CursorUtil.getColumnIndexOrThrow(_cursor, "notificationEnabled");
          final int _cursorIndexOfNotificationMinutesBefore = CursorUtil.getColumnIndexOrThrow(_cursor, "notificationMinutesBefore");
          final List<Task> _result = new ArrayList<Task>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Task _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            final LocalDateTime _tmpStartTime;
            final String _tmp_1;
            if (_cursor.isNull(_cursorIndexOfStartTime)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getString(_cursorIndexOfStartTime);
            }
            _tmpStartTime = __converters.toLocalDateTime(_tmp_1);
            final LocalDateTime _tmpEndTime;
            final String _tmp_2;
            if (_cursor.isNull(_cursorIndexOfEndTime)) {
              _tmp_2 = null;
            } else {
              _tmp_2 = _cursor.getString(_cursorIndexOfEndTime);
            }
            _tmpEndTime = __converters.toLocalDateTime(_tmp_2);
            final TaskLabel _tmpLabel;
            final String _tmp_3;
            _tmp_3 = _cursor.getString(_cursorIndexOfLabel);
            _tmpLabel = __converters.toTaskLabel(_tmp_3);
            final boolean _tmpIsCompleted;
            final int _tmp_4;
            _tmp_4 = _cursor.getInt(_cursorIndexOfIsCompleted);
            _tmpIsCompleted = _tmp_4 != 0;
            final LocalDateTime _tmpCreatedAt;
            final String _tmp_5;
            if (_cursor.isNull(_cursorIndexOfCreatedAt)) {
              _tmp_5 = null;
            } else {
              _tmp_5 = _cursor.getString(_cursorIndexOfCreatedAt);
            }
            final LocalDateTime _tmp_6 = __converters.toLocalDateTime(_tmp_5);
            if (_tmp_6 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.time.LocalDateTime', but it was NULL.");
            } else {
              _tmpCreatedAt = _tmp_6;
            }
            final LocalDateTime _tmpUpdatedAt;
            final String _tmp_7;
            if (_cursor.isNull(_cursorIndexOfUpdatedAt)) {
              _tmp_7 = null;
            } else {
              _tmp_7 = _cursor.getString(_cursorIndexOfUpdatedAt);
            }
            final LocalDateTime _tmp_8 = __converters.toLocalDateTime(_tmp_7);
            if (_tmp_8 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.time.LocalDateTime', but it was NULL.");
            } else {
              _tmpUpdatedAt = _tmp_8;
            }
            final boolean _tmpIsRecurring;
            final int _tmp_9;
            _tmp_9 = _cursor.getInt(_cursorIndexOfIsRecurring);
            _tmpIsRecurring = _tmp_9 != 0;
            final String _tmpRecurringPattern;
            if (_cursor.isNull(_cursorIndexOfRecurringPattern)) {
              _tmpRecurringPattern = null;
            } else {
              _tmpRecurringPattern = _cursor.getString(_cursorIndexOfRecurringPattern);
            }
            final boolean _tmpNotificationEnabled;
            final int _tmp_10;
            _tmp_10 = _cursor.getInt(_cursorIndexOfNotificationEnabled);
            _tmpNotificationEnabled = _tmp_10 != 0;
            final int _tmpNotificationMinutesBefore;
            _tmpNotificationMinutesBefore = _cursor.getInt(_cursorIndexOfNotificationMinutesBefore);
            _item = new Task(_tmpId,_tmpTitle,_tmpDescription,_tmpStartTime,_tmpEndTime,_tmpLabel,_tmpIsCompleted,_tmpCreatedAt,_tmpUpdatedAt,_tmpIsRecurring,_tmpRecurringPattern,_tmpNotificationEnabled,_tmpNotificationMinutesBefore);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<Task>> getAllTasks() {
    final String _sql = "SELECT * FROM tasks ORDER BY createdAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"tasks"}, new Callable<List<Task>>() {
      @Override
      @NonNull
      public List<Task> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "description");
          final int _cursorIndexOfStartTime = CursorUtil.getColumnIndexOrThrow(_cursor, "startTime");
          final int _cursorIndexOfEndTime = CursorUtil.getColumnIndexOrThrow(_cursor, "endTime");
          final int _cursorIndexOfLabel = CursorUtil.getColumnIndexOrThrow(_cursor, "label");
          final int _cursorIndexOfIsCompleted = CursorUtil.getColumnIndexOrThrow(_cursor, "isCompleted");
          final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
          final int _cursorIndexOfUpdatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "updatedAt");
          final int _cursorIndexOfIsRecurring = CursorUtil.getColumnIndexOrThrow(_cursor, "isRecurring");
          final int _cursorIndexOfRecurringPattern = CursorUtil.getColumnIndexOrThrow(_cursor, "recurringPattern");
          final int _cursorIndexOfNotificationEnabled = CursorUtil.getColumnIndexOrThrow(_cursor, "notificationEnabled");
          final int _cursorIndexOfNotificationMinutesBefore = CursorUtil.getColumnIndexOrThrow(_cursor, "notificationMinutesBefore");
          final List<Task> _result = new ArrayList<Task>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final Task _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            final String _tmpDescription;
            _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
            final LocalDateTime _tmpStartTime;
            final String _tmp;
            if (_cursor.isNull(_cursorIndexOfStartTime)) {
              _tmp = null;
            } else {
              _tmp = _cursor.getString(_cursorIndexOfStartTime);
            }
            _tmpStartTime = __converters.toLocalDateTime(_tmp);
            final LocalDateTime _tmpEndTime;
            final String _tmp_1;
            if (_cursor.isNull(_cursorIndexOfEndTime)) {
              _tmp_1 = null;
            } else {
              _tmp_1 = _cursor.getString(_cursorIndexOfEndTime);
            }
            _tmpEndTime = __converters.toLocalDateTime(_tmp_1);
            final TaskLabel _tmpLabel;
            final String _tmp_2;
            _tmp_2 = _cursor.getString(_cursorIndexOfLabel);
            _tmpLabel = __converters.toTaskLabel(_tmp_2);
            final boolean _tmpIsCompleted;
            final int _tmp_3;
            _tmp_3 = _cursor.getInt(_cursorIndexOfIsCompleted);
            _tmpIsCompleted = _tmp_3 != 0;
            final LocalDateTime _tmpCreatedAt;
            final String _tmp_4;
            if (_cursor.isNull(_cursorIndexOfCreatedAt)) {
              _tmp_4 = null;
            } else {
              _tmp_4 = _cursor.getString(_cursorIndexOfCreatedAt);
            }
            final LocalDateTime _tmp_5 = __converters.toLocalDateTime(_tmp_4);
            if (_tmp_5 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.time.LocalDateTime', but it was NULL.");
            } else {
              _tmpCreatedAt = _tmp_5;
            }
            final LocalDateTime _tmpUpdatedAt;
            final String _tmp_6;
            if (_cursor.isNull(_cursorIndexOfUpdatedAt)) {
              _tmp_6 = null;
            } else {
              _tmp_6 = _cursor.getString(_cursorIndexOfUpdatedAt);
            }
            final LocalDateTime _tmp_7 = __converters.toLocalDateTime(_tmp_6);
            if (_tmp_7 == null) {
              throw new IllegalStateException("Expected NON-NULL 'java.time.LocalDateTime', but it was NULL.");
            } else {
              _tmpUpdatedAt = _tmp_7;
            }
            final boolean _tmpIsRecurring;
            final int _tmp_8;
            _tmp_8 = _cursor.getInt(_cursorIndexOfIsRecurring);
            _tmpIsRecurring = _tmp_8 != 0;
            final String _tmpRecurringPattern;
            if (_cursor.isNull(_cursorIndexOfRecurringPattern)) {
              _tmpRecurringPattern = null;
            } else {
              _tmpRecurringPattern = _cursor.getString(_cursorIndexOfRecurringPattern);
            }
            final boolean _tmpNotificationEnabled;
            final int _tmp_9;
            _tmp_9 = _cursor.getInt(_cursorIndexOfNotificationEnabled);
            _tmpNotificationEnabled = _tmp_9 != 0;
            final int _tmpNotificationMinutesBefore;
            _tmpNotificationMinutesBefore = _cursor.getInt(_cursorIndexOfNotificationMinutesBefore);
            _item = new Task(_tmpId,_tmpTitle,_tmpDescription,_tmpStartTime,_tmpEndTime,_tmpLabel,_tmpIsCompleted,_tmpCreatedAt,_tmpUpdatedAt,_tmpIsRecurring,_tmpRecurringPattern,_tmpNotificationEnabled,_tmpNotificationMinutesBefore);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
