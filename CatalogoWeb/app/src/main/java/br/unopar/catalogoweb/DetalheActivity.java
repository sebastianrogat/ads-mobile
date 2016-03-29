package br.unopar.catalogoweb;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import br.unopar.catalogoweb.custom.XJsonObjectRequest;
import br.unopar.catalogoweb.modelo.Mercadoria;

public class DetalheActivity extends AppCompatActivity {
    private EditText edxNome, edxPreco;
    private Button btnExcluir;
    private Mercadoria mercadoria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe);

        edxNome = (EditText) findViewById(R.id.edxNome);
        edxPreco = (EditText) findViewById(R.id.edxPreco);
        btnExcluir = (Button) findViewById(R.id.btnExcluir);

        mercadoria = (Mercadoria) getIntent().getSerializableExtra("mercadoria");

        if (mercadoria == null) {
            mercadoria = new Mercadoria();
            btnExcluir.setVisibility(View.INVISIBLE);
        } else {
            edxNome.setText(mercadoria.getNome());
            edxPreco.setText(String.valueOf(mercadoria.getPreco()));
        }
    }

    public void btnSalvarClick(View view) {
        String nome = edxNome.getText().toString();
        double preco = Double.valueOf(edxPreco.getText().toString());

        mercadoria.setNome(nome);
        mercadoria.setPreco(preco);

        if (mercadoria.getID() == 0)
            inserir();
        else
            atualizar();
    }

    public void btnExcluirClick(View view) {
        excluir();
    }

    private void inserir() {
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setTitle("Processando...");
        dialog.setCancelable(false);

        dialog.show();

        JSONObject obj = new JSONObject();
        try {
            obj.put("Nome", mercadoria.getNome());
            obj.put("Preco", mercadoria.getPreco());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest requisicao =
                new JsonObjectRequest(
                        Request.Method.POST,
                        "http://2learning-svc.azurewebsites.net/api/mercadoria/",
                        obj,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    mercadoria.setID(response.getInt("Identity"));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                dialog.hide();

                                showMsg("Dados salvo com sucesso");
                                retornaMercadoria("adicionar");
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                dialog.hide();

                                showMsg(error.toString());
                            }
                        });

        RequestQueue fila = Volley.newRequestQueue(this);
        fila.add(requisicao);
    }

    private void atualizar() {
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setTitle("Processando...");
        dialog.setCancelable(false);

        dialog.show();

        JSONObject obj = new JSONObject();
        try {
            obj.put("Nome", mercadoria.getNome());
            obj.put("Preco", mercadoria.getPreco());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String id = String.valueOf(mercadoria.getID());

        JsonObjectRequest requisicao =
                new XJsonObjectRequest(
                        Request.Method.PUT,
                        "http://2learning-svc.azurewebsites.net/api/mercadoria/" + id,
                        obj,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                dialog.hide();

                                showMsg("Dados alterados com sucesso");
                                retornaMercadoria("atualizar");
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                dialog.hide();

                                showMsg(error.toString());
                            }
                        });

        RequestQueue fila = Volley.newRequestQueue(this);
        fila.add(requisicao);
    }

    private void excluir() {
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setTitle("Processando...");
        dialog.setCancelable(false);

        dialog.show();

        String id = String.valueOf(mercadoria.getID());

        JsonObjectRequest requisicao =
                new XJsonObjectRequest(
                        Request.Method.DELETE,
                        "http://2learning-svc.azurewebsites.net/api/mercadoria/" + id,
                        "",
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                dialog.hide();

                                showMsg("Mercadoria excluída");
                                retornaMercadoria("excluir");
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                dialog.hide();

                                showMsg(error.toString());
                            }
                        });

        RequestQueue fila = Volley.newRequestQueue(this);
        fila.add(requisicao);
    }

    private void retornaMercadoria(String operacao) {
        Intent retorno = new Intent();
        retorno.putExtra("op", operacao);
        retorno.putExtra("merc", mercadoria);

        setResult(RESULT_OK, retorno);

        finish();

    }

    private void showMsg(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

}
