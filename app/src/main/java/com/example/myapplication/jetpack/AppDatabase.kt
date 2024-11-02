package com.example.myapplication.jetpack

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.myapplication.lei.Book
import com.example.myapplication.lei.User

@Database(version = 2, entities = [User::class, Book::class])
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    abstract fun bookDao(): BookDao

    companion object {

        val MIGRATION_1_2 = object : Migration(1, 2) { // 论升级
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("create table Book (id integer primary " +
                        "key autoincrement not null, name text not null, " +
                        "pages integer not null)")
            }
        }

        private var instance: AppDatabase? = null


        fun getDatabase(context: Context): AppDatabase {
            instance?.let {
                return it
            } // 不为空则直接返回，否则就现场构造一个
            return Room.databaseBuilder(context.applicationContext,
                AppDatabase::class.java, "app_database")
                .addMigrations(MIGRATION_1_2)
                .build().apply {
                    instance = this
                }
        }

    }


}