package com.andri.loteriadoifsul;


import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.core.content.ContextCompat;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private TextView texto;
    private Button botao;
    private Spinner menu;
    private TextView concurso;
    private TextView dados;

    String[] opcoes = {"MEGA-SENA", "QUINA", "LOTOFACIL", "LOTOMANIA", "TIMEMANIA", "DIA DE SORTE"};

    private final String BASE_URL = "http://loteria.cronogramatds.online/";

    private ApiService apiService;

    private Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        texto = findViewById(R.id.textLoteria);
        menu = findViewById(R.id.spinner);
        concurso = findViewById(R.id.textconcurso);
        dados = findViewById(R.id.textdados);

        // Configuração do Retrofit
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(new OkHttpClient())
                .addConverterFactory(GsonConverterFactory.create()) // Conversor para usar Gson
                .build();

        apiService = retrofit.create(ApiService.class);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity.this,
                android.R.layout.simple_spinner_dropdown_item, opcoes);
        menu.setAdapter(adapter);

        menu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Pega a opção selecionada no Spinner
                String selectedOption = opcoes[position];
                texto.setText(selectedOption);

                // Recuperar dados da API
                loteriaApi(selectedOption);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        Button btnLogout = findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(v -> realizarLogout());
    }
    private void loteriaApi(String selectedOption) {
        String apiType;
        int backgroundColor;

        switch (selectedOption) {
            case "MEGA-SENA":
                apiType = "megasena";
                backgroundColor = R.color.verdeClaro;
                break;
            case "QUINA":
                apiType = "quina";
                backgroundColor = R.color.roxo;
                break;
            case "LOTOFACIL":
                apiType = "lotofacil";
                backgroundColor = R.color.rosa;
                break;
            case "LOTOMANIA":
                apiType = "lotomania";
                backgroundColor = R.color.laranja;
                break;
            case "TIMEMANIA":
                apiType = "timemania";
                backgroundColor = R.color.verdeEscuro;
                break;
            case "DIA DE SORTE":
                apiType = "diadasorte";
                backgroundColor = R.color.marrom;
                break;
            default:
                return;
        }

        // Alterar a cor de fundo da tela
        getWindow().getDecorView().setBackgroundColor(ContextCompat.getColor(getApplicationContext(), backgroundColor));

        Call<Sorteio> call = apiService.getSorteio(apiType);
        call.enqueue(new Callback<Sorteio>() {
            @Override
            public void onResponse(Call<Sorteio> call, retrofit2.Response<Sorteio> response) {
                if (response.isSuccessful()) {
                    Sorteio sorteio = response.body();
                    if (sorteio != null) {
                        concurso.setText("Concurso N°: " + sorteio.getNumero() + "\n" +
                                "Data: " + sorteio.getData() + "\n");

                        String numerosSorteados = formatarNumerosSorteados(sorteio.getNumerosSorteados());
                        dados.setText("Números Sorteados: " + "\n" + "\n" + numerosSorteados);
                    }
                }
            }
            @Override
            public void onFailure(Call<Sorteio> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Erro: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
            private String formatarNumerosSorteados(List<Integer> numerosSorteados) {
                StringBuilder formattedNumbers = new StringBuilder();
                for (int i = 0; i < numerosSorteados.size(); i++) {
                    formattedNumbers.append(numerosSorteados.get(i));
                    if (i < numerosSorteados.size() - 1) {
                        formattedNumbers.append(" - ");
                    }
                }
                return formattedNumbers.toString();
            }

        });
    }
    private void realizarLogout() {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

}

