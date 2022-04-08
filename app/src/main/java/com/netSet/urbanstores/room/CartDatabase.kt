package com.netSet.urbanstores.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase


@Database(entities = [CartEntity::class],version = 3,exportSchema = false)


abstract class CartDatabase:RoomDatabase() {
    abstract fun CartDao():CartDao


    val MIGRATION_2_3: Migration = object : Migration(2, 3) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL(
                "ALTER TABLE products_table "
                        + " ADD COLUMN image_url"
            )
        }
    }

    companion object{
        @Volatile
        private var INSTANCE:CartDatabase?=null

        fun getDatabase(context: Context):CartDatabase{
            val tempInstance= INSTANCE
            if (tempInstance !=null){
                return tempInstance
            }
            synchronized(this){
                val instance= Room.databaseBuilder(
                    context.applicationContext,
                    CartDatabase::class.java,
                    "location_database"
                )  .fallbackToDestructiveMigration().build()
                INSTANCE=instance
                return instance
            }
        }
    }

}