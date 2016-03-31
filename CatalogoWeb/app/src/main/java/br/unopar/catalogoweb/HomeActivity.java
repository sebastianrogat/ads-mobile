package br.unopar.catalogoweb;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import br.unopar.catalogoweb.modelo.Mercadoria;

public class HomeActivity extends AppCompatActivity {
    private Button btnReload;
    private ListView lsvItens;
    private ArrayAdapter<Mercadoria> adaptador;
    private List<Mercadoria> dados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(
                        new Intent(HomeActivity.this, DetalheActivity.class), 1);
            }
        });

        lsvItens = (ListView)findViewById(R.id.lsvItens);
        btnReload = (Button)findViewById(R.id.btnReload);

        dados = new ArrayList<>();
        adaptador = new ArrayAdapter<>(
                this, android.R.layout.simple_list_item_1, dados);

        lsvItens.setAdapter(adaptador);

        carregaDados();
        registraEventos();
    }

    private void registraEventos() {
        lsvItens.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Mercadoria m = adaptador.getItem(position);

                Intent abrirDetalhe = new Intent(HomeActivity.this, DetalheActivity.class);
                abrirDetalhe.putExtra("mercadoria", m);

                startActivityForResult(abrirDetalhe, 1);
            }
        });
    }

    private void carregaDados() {
        lsvItens.setVisibility(View.VISIBLE);
        btnReload.setVisibility(View.GONE);

        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setTitle("Carregando...");
        dialog.setCancelable(false);

        dialog.show();

        JsonArrayRequest requisicao =
                new JsonArrayRequest(
                        "http://2learning-svc.azurewebsites.net/api/mercadoria/",
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                processaResultado(response);

                                dialog.hide();

                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                lsvItens.setVisibility(View.GONE);
                                btnReload.setVisibility(View.VISIBLE);

                                dialog.hide();

                                showMsg(error.toString());
                            }
                        });

        RequestQueue fila = Volley.newRequestQueue(this);
        fila.add(requisicao);
    }

    private void processaResultado(JSONArray array) {
        for(int i = 0; i < array.length(); i++) {
            try {
                JSONObject obj = array.getJSONObject(i);
                Mercadoria m = new Mercadoria(
                        obj.getInt("ID"),
                        obj.getString("Nome"),
                        obj.getDouble("Preco"));

                dados.add(m);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        adaptador.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK) {
            String op = data.getStringExtra("op");
            Mercadoria merc = (Mercadoria) data.getSerializableExtra("merc");

            switch (op) {
                case "adicionar":
                    dados.add(merc);
                    break;
                case "atualizar":
                    sincronizar(merc);
                    break;
                case "excluir":
                    dados.remove(encontraIndex(merc.getID()));
                    break;
            }
        }

        adaptador.notifyDataSetChanged();
    }

    private void sincronizar(Mercadoria atual) {
        Mercadoria anterior = dados.get(encontraIndex(atual.getID()));
        anterior.setNome(atual.getNome());
        anterior.setPreco(atual.getPreco());
    }

    private int encontraIndex(int id) {
        for (int i = 0; i < dados.size(); i++) {
            if(dados.get(i).getID() == id) {
                return i;
            }
        }

        return -1;
    }

    private void showMsg(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    public void btnReloadClick(View view) {
        carregaDados();
    }
}
