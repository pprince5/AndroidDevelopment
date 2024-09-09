package com.example.taxfillingapp;

import android.content.Context;
import androidx.room.Room;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.List;
import java.util.function.Consumer;

public class UserService {


    private static UserService instance;
    private final UserDao userdao;

    private UserService(Context context) {
        AppDatabase db = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "userdb")
                .addMigrations(MIGRATION_1_2)
                .build();
        userdao = db.userDao();
    }

    public static synchronized UserService getInstance(Context context) {
        if (instance == null) {
            instance = new UserService(context);
        }
        return instance;
    }

    // ...

    private static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE users ADD COLUMN status TEXT DEFAULT 'awaited'");
        }
    };

    public interface OperationCallback {
        void onOperationCompleted();
        void onError(Exception e);
    }

    public void insertUser(User user, OperationCallback callback) {
        new Thread(() -> {
            try {
                userdao.insert(user);
                User insertedUser = userdao.findByEmail(user.email);
                if (callback != null) {
                    callback.onOperationCompleted();
                }
            } catch (Exception e) {
                if (callback != null) {
                    callback.onError(e);
                }
            }
        }).start();
    }

    public void updateUser(User user, OperationCallback callback) {
        new Thread(() -> {
            try {
                userdao.update(user);
                if (callback != null) {
                    callback.onOperationCompleted();
                }
            } catch (Exception e) {
                if (callback != null) {
                    callback.onError(e);
                }
            }
        }).start();
    }

    public void deleteUser(User user, OperationCallback callback) {
        new Thread(() -> {
            try {
                userdao.delete(user);
                if (callback != null) {
                    callback.onOperationCompleted();
                }
            } catch (Exception e) {
                if (callback != null) {
                    callback.onError(e);
                }
            }
        }).start();
    }

    public void fetchAllUsers(Consumer<List<User>> onResult) {
        new Thread(() -> {
            try {
                List<User> users = userdao.getAll();
                if (onResult != null) {
                    onResult.accept(users);
                }
            } catch (Exception e) {
                // Handle error here
            }
        }).start();
    }

    public void fetchUsernames(Consumer<List<String>> onResult) {
        new Thread(() -> {
            try {
                List<String> usernames = userdao.getUsername();
                if (onResult != null) {
                    onResult.accept(usernames);
                }
            } catch (Exception e) {
                // Handle error here
            }
        }).start();
    }
    public void findUserByEmail(String email, Consumer<User> onResult) {
        new Thread(() -> {
            try {
                User user = userdao.findByEmail(email);
                if (onResult != null) {
                    onResult.accept(user);
                }
            } catch (Exception e) {
                // Handle error here, perhaps log the error
                e.printStackTrace();
            }
        }).start();
    }

}
