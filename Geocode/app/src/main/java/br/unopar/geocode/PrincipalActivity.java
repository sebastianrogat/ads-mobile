package br.unopar.geocode;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class PrincipalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
    }

    public void btnPesquisarClick(View view) {
        String endereco = ((EditText)findViewById(R.id.edxEndereco)).getText().toString();

        try {
            endereco = URLEncoder.encode(endereco, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String url = "https://maps.googleapis.com/maps/api/geocode/json?address=" + endereco;

        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setTitle("Pesquisando...");
        dialog.setCancelable(false);
        dialog.show();

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        dialog.hide();
                        processaResultado(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        dialog.hide();
                        processaErro(error);
                    }
                }
        );
        RequestQueue fila = Volley.newRequestQueue(this);
        fila.add(request);
    }

    private void processaResultado(JSONObject dado) {
        String endereco = null;

        try {
            endereco = dado
                .getJSONArray("results")
                .getJSONObject(0)
                .getString("formatted_address");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        TextView txvData = (TextView)findViewById(R.id.txvData);
        txvData.setText(endereco);
    }

    private void processaErro(VolleyError erro) {
        Toast.makeText(this, erro.getMessage(), Toast.LENGTH_LONG).show();
    }
}
