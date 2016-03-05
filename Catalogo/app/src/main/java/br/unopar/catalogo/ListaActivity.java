package br.unopar.catalogo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class ListaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);
    }

    public void btnAddClick(View view) {
        Intent abrirDetalhe = new Intent(this, DetalheActivity.class);
        startActivity(abrirDetalhe);
    }
}
