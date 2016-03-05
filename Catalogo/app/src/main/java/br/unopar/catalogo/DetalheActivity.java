package br.unopar.catalogo;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class DetalheActivity extends AppCompatActivity {
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe);
    }

    public void btnSalvarClick(View view) {
        EditText edxNome = (EditText)findViewById(R.id.edxNome);

        String nome = edxNome.getText().toString();

        ContentValues valores = new ContentValues();
        valores.put("Nome", nome);

        // db.insert("Mercadoria", null, valores);

        Toast.makeText(
                DetalheActivity.this, "Salvo com sucesso", Toast.LENGTH_SHORT).show();
        finish();
    }
}
