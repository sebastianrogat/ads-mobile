package br.unopar.sqlitelab;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by adria on 02/18/2016.
 */
public class AbrirBanco extends SQLiteOpenHelper{

    public AbrirBanco(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String script = "CREATE TABLE AcessoUsuario (" +
                " ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                " Login TEXT NOT NULL," +
                " DataAcesso INTEGER NOT NULL);";

        db.execSQL(script);

        script = "CREATE TABLE Usuario (" +
                " ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                " Login TEXT NOT NULL);";

        db.execSQL(script);

        script = "CREATE TABLE Fornecedor (" +
                " ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                " RazaoSocial TEXT NOT NULL);";

        db.execSQL(script);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        for(int i = oldVersion; i < newVersion; i++) {
            String script;
            if(i == 1) {
                script = "CREATE TABLE Usuario (" +
                        " ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                        " Login TEXT NOT NULL);";

                db.execSQL(script);
            } else if(i == 2) {
                script = "CREATE TABLE Fornecedor (" +
                        " ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                        " RazaoSocial TEXT NOT NULL);";

                db.execSQL(script);
            }
        }
    }
}
