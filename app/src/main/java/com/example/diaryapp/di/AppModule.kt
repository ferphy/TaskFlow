package com.example.diaryapp.di


import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.diaryapp.data.TaskDatabase
import com.example.diaryapp.model.TaskDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    val MIGRATION_1_2 = object : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL("ALTER TABLE task_tbl ADD COLUMN state TEXT NOT NULL DEFAULT 'pending'")
        }
    }

    @Singleton
    @Provides
    fun provideTaskDao(taskDatabase: TaskDatabase): TaskDao
    = taskDatabase.taskDao()

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): TaskDatabase
    = Room.databaseBuilder(
        context,
        TaskDatabase::class.java,
        "tasks_db"
    ).fallbackToDestructiveMigration()
        .addMigrations(MIGRATION_1_2)
        .build()

}