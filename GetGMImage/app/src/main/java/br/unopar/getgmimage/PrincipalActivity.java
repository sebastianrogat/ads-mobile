package br.unopar.getgmimage;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class PrincipalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
    }

    public void btnCarregarClick(View view) {
        String endereco = ((EditText)findViewById(R.id.edxEndereco)).getText().toString();
        String zoom = ((EditText)findViewById(R.id.edxZoom)).getText().toString();
        String largura = ((EditText)findViewById(R.id.edxLargura)).getText().toString();
        String altura = ((EditText)findViewById(R.id.edxAltura)).getText().toString();

        requisitaMapa(endereco, zoom, largura, altura);
    }

    private void requisitaMapa(
            String endereco, String zoom, String largura, String altura) {

        final ProgressDialog progresso = new ProgressDialog(this);
        progresso.setTitle("Por favor espere!");
        progresso.setCancelable(false);

        progresso.show();

        try {
            endereco = URLEncoder.encode(endereco, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String url = "https://maps.googleapis.com/maps/api/staticmap?center={endereco}&zoom={zoom}&size={x}x{y}";
        url = url
                .replace("{endereco}", endereco)
                .replace("{zoom}", zoom)
                .replace("{x}", largura)
                .replace("{y}", altura);

        ImageRequest request = new ImageRequest(
                url,    
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        progresso.hide();
                        atualizaMapa(response);
                    }
                },
                0, 0,
                null,
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progresso.hide();
                        mostraErro(error);
                    }
                }
        );

        RequestQueue fila = Volley.newRequestQueue(this);
        fila.add(request);
    }

    private void atualizaMapa(Bitmap mapa) {
        ImageView imvMapa = (ImageView)findViewById(R.id.imvMapa);
        imvMapa.setImageBitmap(mapa);
    }

    private void mostraErro(VolleyError volleyError) {
        Toast.makeText(this, volleyError.getMessage(), Toast.LENGTH_SHORT).show();
    }
}
