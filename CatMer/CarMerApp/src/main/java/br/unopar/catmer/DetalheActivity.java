package br.unopar.catmer;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import br.unopar.catmer.Models.Mercadoria;
import br.unopar.catmer.Models.Repositorio;

public class DetalheActivity extends AppCompatActivity {
    private EditText edxNome, edxPreco, edxFabricante, edxDescricao;
    private ImageView imvFoto;
    private Button btnExcluir;

    private Repositorio repositorio;
    private Mercadoria mercadoria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        edxNome = (EditText)findViewById(R.id.edxNome);
        imvFoto = (ImageView)findViewById(R.id.imvFoto);
        edxPreco = (EditText)findViewById(R.id.edxPreco);
        edxFabricante = (EditText)findViewById(R.id.edxFabricante);
        edxDescricao = (EditText)findViewById(R.id.edxDescricao);

        btnExcluir = (Button)findViewById(R.id.btnExcluir);

        Bitmap image = null;

        repositorio = new Repositorio(this);

        long pk = getIntent().getLongExtra("pk", 0);
        if(pk != 0) {
            mercadoria = repositorio.recuperaPorID(pk);

            edxNome.setText(mercadoria.getNome());
            edxPreco.setText(String.valueOf(mercadoria.getPreco()));
            edxFabricante.setText(mercadoria.getFabricante());
            edxDescricao.setText(mercadoria.getDescricao());

            image = mercadoria.getFoto();
        } else {
            btnExcluir.setVisibility(View.INVISIBLE);
        }

        if(image == null) {
            imvFoto.setImageResource(R.drawable.no_user);
        } else {
            imvFoto.setImageBitmap(image);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK) {
            Bitmap foto = (Bitmap)data.getExtras().get("data");
            imvFoto.setImageBitmap(foto);
        }
    }

    public void btnSalvarClick(View view) {
        if(mercadoria == null) {
            mercadoria = new Mercadoria();
        }

        mercadoria.setNome(edxNome.getText().toString());
        mercadoria.setPreco(Double.valueOf(edxPreco.getText().toString()));
        mercadoria.setFabricante(edxFabricante.getText().toString());
        mercadoria.setDescricao(edxDescricao.getText().toString());
        mercadoria.setFoto(((BitmapDrawable)imvFoto.getDrawable()).getBitmap());

        if(mercadoria.getId() == 0)
            repositorio.inserir(mercadoria);
        else
            repositorio.atualizar(mercadoria);

        Toast.makeText(DetalheActivity.this, "Dados armazenados com sucesso.", Toast.LENGTH_SHORT).show();
        finish();
    }
    public void btnExcluirClick(View view) {
        repositorio.excluir(mercadoria);

        Toast.makeText(DetalheActivity.this, "Dados excluídos com sucesso.", Toast.LENGTH_SHORT).show();
        finish();
    }
    public void imvFotoClick(View view) {
        Intent selecionarFoto = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(selecionarFoto, 0);
    }
}
