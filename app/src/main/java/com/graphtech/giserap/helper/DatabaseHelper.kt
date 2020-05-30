package com.graphtech.giserap.helper

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.graphtech.giserap.helper.DatabaseContract.FavoriteColumns.Companion.TABLE_NAME
import com.graphtech.giserap.helper.DatabaseContract.*

internal class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {

        private const val DATABASE_NAME = "dbgiserap"

        private const val DATABASE_VERSION = 1

        private val SQL_CREATE_TABLE_GISERAP = "CREATE TABLE $TABLE_NAME" +
                " (${FavoriteColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                " ${FavoriteColumns.USERNAME} TEXT NOT NULL," +
                " ${FavoriteColumns.AVATAR} TEXT NOT NULL)"
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_TABLE_GISERAP)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }
}