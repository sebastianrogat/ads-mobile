package br.unopar.catmer.Models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by adria on 03/01/2016.
 */
public class Repositorio {
    private SQLiteDatabase db;

    public Repositorio(Context ctx) {
        AbrirBanco abrirBanco = new AbrirBanco(ctx);

        db = abrirBanco.getWritableDatabase();
    }

    public void inserir(Mercadoria merc) {
        ContentValues valores = new ContentValues();
        valores.put("Nome", merc.getNome());
        valores.put("Preco", merc.getPreco());
        valores.put("Fabricante", merc.getFabricante());
        valores.put("Descricao", merc.getDescricao());
        valores.put("Foto", merc.getFotoAsByte());

        long pk = db.insert("Mercadoria", null, valores);
        merc.setId(pk);
    }

    public void atualizar(Mercadoria merc) {
        ContentValues valores = new ContentValues();
        valores.put("Nome", merc.getNome());
        valores.put("Preco", merc.getPreco());
        valores.put("Fabricante", merc.getFabricante());
        valores.put("Descricao", merc.getDescricao());
        valores.put("Foto", merc.getFotoAsByte());

        String[] whereArgs = new String[1];
        whereArgs[0] = String.valueOf(merc.getId());

        db.update("Mercadoria", valores, "ID = ?", whereArgs);
    }

    public void excluir(Mercadoria merc) {
        String[] whereArgs = new String[1];
        whereArgs[0] = String.valueOf(merc.getId());

        db.delete("Mercadoria", "ID = ?", whereArgs);
    }

    public ArrayList<Mercadoria> recuperaPorNome(String nomeLike) {
        ArrayList<Mercadoria> retorno = new ArrayList<>();

        String sql = "SELECT * FROM Mercadoria WHERE Nome LIKE ? ORDER BY Nome";
        String[] whereArgs = new String[1];
        whereArgs[0] = nomeLike;

        Cursor c = db.rawQuery(sql, whereArgs);
        while(c.moveToNext()) {
            Mercadoria m = new Mercadoria(
                    c.getLong(0),
                    c.getString(1),
                    c.getBlob(2),
                    c.getDouble(3),
                    c.getString(4),
                    c.getString(5));

            retorno.add(m);
        }

        return retorno;

    }

    public Mercadoria recuperaPorID(long id) {
        String sql = "SELECT * FROM Mercadoria WHERE ID = ?";

        String[] whereArgs = new String[1];
        whereArgs[0] = String.valueOf(id);

        Cursor c = db.rawQuery(sql, whereArgs);

        if(!c.moveToNext()) {
            return null;
        }

        return new Mercadoria(
                c.getLong(0),
                c.getString(1),
                c.getBlob(2),
                c.getDouble(3),
                c.getString(4),
                c.getString(5));
    }

    private class AbrirBanco extends SQLiteOpenHelper {

        public AbrirBanco(Context context) {
            super(context, "banco.db", null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            String script =
                    "CREATE TABLE Mercadoria (" +
                            " ID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
                            " Nome TEXT NOT NULL," +
                            " Foto BLOB," +
                            " Preco REAL NOT NULL," +
                            " Fabricante TEXT NOT NULL," +
                            " Descricao TEXT);";
            db.execSQL(script);

            db.execSQL("INSERT INTO Mercadoria (Nome, Preco, Fabricante) VALUES('Mercadoria 1', 1.99, 'Fabricante 1');");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
}
