package br.unopar.catalogoretrofit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private RemoteSvc servico;

    private ArrayList<Mercadoria> dados;
    private ArrayAdapter<Mercadoria> adaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://2learning-svc.azurewebsites.net/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        servico = retrofit.create(RemoteSvc.class);

        dados = new ArrayList<>();

        adaptador = new ArrayAdapter<Mercadoria>(
                this, android.R.layout.simple_list_item_1, dados);

        ListView lsvItens = (ListView)findViewById(R.id.lsvItens);
        lsvItens.setAdapter(adaptador);

        carregarDados();
    }

    private void carregarDados() {
        Call<List<Mercadoria>> recMercadorias = servico.recuperaMercadorias();

        recMercadorias.enqueue(
                new Callback<List<Mercadoria>>() {
                    @Override
                    public void onResponse(Call<List<Mercadoria>> call, Response<List<Mercadoria>> response) {
                        if(response.isSuccess()) {
                            dados.addAll(response.body());
                            adaptador.notifyDataSetChanged();
                        } else {
                            showMsg("Erro na resposta");
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Mercadoria>> call, Throwable t) {
                        showMsg(t.getMessage());
                    }
                }
        );
    }

    private void showMsg(String message) {
        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
    }
}
