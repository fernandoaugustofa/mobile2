package br.edu.computacaoifg.Carga

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.*


/**
 * Created by Alessandro on 04/10/2017.
 */

class MyDatabaseOpenHelper(ctx: Context) : ManagedSQLiteOpenHelper(ctx, "MyDatabase") {
    companion object {
        private var instance: MyDatabaseOpenHelper? = null

        @Synchronized
        fun getInstance(ctx: Context): MyDatabaseOpenHelper {
            instance = instance ?: MyDatabaseOpenHelper(ctx.applicationContext)
            return instance!!
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
        // Here you create tables
        db.createTable("tarefa", true,
                "_id" to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                "nome" to TEXT ,
                "date" to TEXT,
                "done" to INTEGER)
        db.createTable("drive", true,
                "_id" to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                "id_driver" to INTEGER,
                "placa" to TEXT,
                "tipo" to TEXT,
                "carr" to TEXT)
        db.createTable("driver",true,
                "_id" to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                "nome" to TEXT ,
                "cpf" to TEXT,
                "rg" to TEXT,
                "cnh" to TEXT,
                "antt" to TEXT,
                "fone" to TEXT,
                "docs" to TEXT,
                "pos" to TEXT,
                "endereco" to TEXT)




    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.dropTable("tarefa")

        // Here you can upgrade tables, as usual
    }
}