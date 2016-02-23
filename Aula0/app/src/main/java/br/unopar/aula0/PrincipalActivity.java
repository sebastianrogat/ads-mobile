package br.unopar.aula0;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Date;

public class PrincipalActivity extends AppCompatActivity {
    private Button btnAtualiza;
    private EditText edxAt1, edxNt1, edxAt2, edxNt2;
    private TextView txvMedia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        btnAtualiza = (Button)findViewById(R.id.btnAtualiza);
        edxAt1 = (EditText)findViewById(R.id.edxAt1);
        edxNt1 = (EditText)findViewById(R.id.edxNt1);
        edxAt2 = (EditText)findViewById(R.id.edxAt2);
        edxNt2 = (EditText)findViewById(R.id.edxNt2);

        txvMedia = (TextView)findViewById(R.id.txvMedia);

        btnAtualiza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double at1 = Double.parseDouble(edxAt1.getText().toString());
                double nt1 = Double.parseDouble(edxNt1.getText().toString());
                double at2 = Double.parseDouble(edxAt2.getText().toString());
                double nt2 = Double.parseDouble(edxNt2.getText().toString());

                double media =
                        ((at1 * 0.3 + nt1 * 0.7) * 0.4) +
                        ((at2 * 0.3 + nt2 * 0.7) * 0.6);

                String msg;
                if(media < 6 && media >= 2) {
                    msg = "Você está de exame!";
                } else if( media < 2) {
                    msg = "Você está reprovado!";
                } else {
                    msg = "Aprovado!!!!";
                }

                txvMedia.setText(msg + " Nt: " + String.valueOf(media));
            }
        });
    }
}
