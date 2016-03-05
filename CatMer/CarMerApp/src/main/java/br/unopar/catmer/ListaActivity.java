package br.unopar.catmer;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import br.unopar.catmer.Models.Mercadoria;
import br.unopar.catmer.Models.Repositorio;

public class ListaActivity extends AppCompatActivity {
    private EditText edxPesquisa;
    private ListView lsvItens;
    private ArrayAdapter<Mercadoria>  adaptador;
    private ArrayList<Mercadoria> dados;
    private Repositorio repositorio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        edxPesquisa = (EditText)findViewById(R.id.edxPesquisa);
        lsvItens = (ListView)findViewById(R.id.lsvItens);

        repositorio = new Repositorio(this);
        dados = new ArrayList<>();

        adaptador = new ArrayAdapter<Mercadoria>(
                this, android.R.layout.simple_list_item_1, dados);
        lsvItens.setAdapter(adaptador);
        
        eventos();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_lista, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent abrirDetalhe = new Intent(this, DetalheActivity.class);
        startActivity(abrirDetalhe);

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();

        dados.clear();
        dados.addAll(repositorio.recuperaPorNome("%"));

        adaptador.notifyDataSetChanged();
        edxPesquisa.setText("");
    }

    private void eventos() {
        edxPesquisa.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                dados.clear();
                dados.addAll(repositorio.recuperaPorNome(
                        "%"+ s.toString() + "%"));

                adaptador.notifyDataSetChanged();

                if(dados.size() == 0) {
                    showMsg("Nenhuma mercadoria encontrada.");
                }
            }
        });

        lsvItens.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Mercadoria m = adaptador.getItem(position);

                Intent abrirDetalhe = new Intent(ListaActivity.this, DetalheActivity.class);
                abrirDetalhe.putExtra("pk", m.getId());

                startActivity(abrirDetalhe);
            }
        });
    }

    private void showMsg(String texto) {
        Toast.makeText(ListaActivity.this, texto, Toast.LENGTH_SHORT).show();
    }
}
