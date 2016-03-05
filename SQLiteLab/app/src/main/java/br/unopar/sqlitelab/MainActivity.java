package br.unopar.sqlitelab;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializar meu banco de dados
        AbrirBanco abrirBanco = new AbrirBanco(
                this, "banco.db", null, 3);

        db = abrirBanco.getWritableDatabase();

        inserirAcessoUsuario();
    }

    private void inserirAcessoUsuario() {
        String login = "unopar";
        Date dataAcesso = new Date();

        // INSERT INTO AcessoUsuario (Login, DataAcesso) VALUES('unopar', 121223)
        ContentValues valores = new ContentValues();
        valores.put("Login", login);
        valores.put("DataAcesso", dataAcesso.getTime());

        long id = db.insert("AcessoUsuario", null, valores);

        if (id != -1)
            showMsg("Registro inserido com sucesso!");
        else
            showMsg("Registro não foi inserido!");
    }

    private void atualizaAcessoUsuario() {
        String login = "unopar";
        Date dataAcesso = new Date();

        // UPDATE AcessoUsuario SET DataAcesso = 1133131231 WHERE Login = 'unopar'
        ContentValues valores = new ContentValues();
        valores.put("DataAcesso", dataAcesso.getTime());

        String[] whereArgs = new String[1];
        whereArgs[0] = login;

        int contaUpdate = db.update("AcessoUsuario", valores, "Login = ?", whereArgs);

        if (contaUpdate > 0)
            showMsg("Registro atualizado com sucesso.");
        else
            showMsg("Registro não foi atualizado.");
    }

    private void excluirAcessoUsuario() {
        String login = "unopar";

        // DELETE FROM AcessoUsuario WHERE Login = 'unopar'
        String[] whereArgs = new String[1];
        whereArgs[0] = login;

        int contaDelete = db.delete("AcessoUsuario", "Login = ?", whereArgs);

        if (contaDelete > 0)
            showMsg("Registro excluído com sucesso.");
        else
            showMsg("Registro não foi excluído.");
    }

    private void contaAcessoUsuario() {
        String login = "unopar";

        // SELECT COUNT(*) FROM AcessoUsuario WHERE Login = 'unopar'
        String sql = "SELECT COUNT(*) FROM AcessoUsuario WHERE Login = ?";

        String[] whereArgs = new String[1];
        whereArgs[0] = login;

        Cursor cursor = db.rawQuery(sql, whereArgs);

        cursor.moveToNext();
        long conta = cursor.getLong(0);

        showMsg("Total de Registros: " + String.valueOf(conta));
    }

    private String contaAcessoPorUsuario() {
        String sql = "SELECT Login, COUNT(*) FROM AcessoUsuario GROUP BY Login";

        StringBuilder retorno = new StringBuilder();

        Cursor cursor = db.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            retorno.append(
                    cursor.getString(0)
                            + ": "
                            + String.valueOf(cursor.getLong(1)));
        }

        if (retorno.length() == 0) {
            return "Nenhum acesso registrado!";
        }

        return retorno.toString();
    }


    private void showMsg(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    public void btnInserirClick(View view) {
        inserirAcessoUsuario();
    }

    public void btnAtualizarClick(View view) {
        atualizaAcessoUsuario();
    }

    public void btnExcluirClick(View view) {
        excluirAcessoUsuario();
    }

    public void btnContaClick(View view) {
        contaAcessoUsuario();
    }

    public void btnContaAcessosClick(View view) {
        showMsg(contaAcessoPorUsuario());
    }
}
