package io.translation.yandex.yatranslation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

import butterknife.BindString;
import butterknife.ButterKnife;
import io.translation.yandex.yatranslation.api.TranslateApi;
import io.translation.yandex.yatranslation.model.TranslationResponse;
import io.translation.yandex.yatranslation.screens.MainFragment;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    // TODO: можно сделать через dagger, если будет желание
    Retrofit mRetrofit;
    TranslateApi mTranslateApi;

    @BindString(R.string.ya_translation_api_key) String apiKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initializeLibraries();

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_fragment_container, new MainFragment())
                .commit();

    }

    OkHttpClient provideOkHttpClient() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);
        return httpClient.build();
    }

    private void initializeLibraries() {
        OkHttpClient loggingClient = provideOkHttpClient();

        mRetrofit = new Retrofit.Builder()
                .baseUrl("https://dictionary.yandex.net/api/v1/dicservice.json/")
                .client(loggingClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mTranslateApi = mRetrofit.create(TranslateApi.class);

//        mTranslateApi.getLangs(getResources().getString(R.string.ya_translation_api_key))
//        .enqueue(new Callback<List<String>>() {
//            @Override
//            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
//                Toast.makeText(MainActivity.this, "Got response!", Toast.LENGTH_LONG).show();
//            }
//
//            @Override
//            public void onFailure(Call<List<String>> call, Throwable t) {
//                Log.d("Retrofit error", t.getMessage());
//                Toast.makeText(MainActivity.this, "Error!", Toast.LENGTH_LONG).show();
//            }
//        });

        mTranslateApi.lookup(getResources().getString(R.string.ya_translation_api_key), "ru-en", "кошка").enqueue(new Callback<TranslationResponse>() {
            @Override
            public void onResponse(Call<TranslationResponse> call, Response<TranslationResponse> response) {
                Log.d("Response", response.body().definition.get(0).word);
                Toast.makeText(MainActivity.this, "Got response!", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<TranslationResponse> call, Throwable t) {
                Log.d("Retrofit error", t.getMessage());
                Toast.makeText(MainActivity.this, "Error!", Toast.LENGTH_LONG).show();
            }
        });
    }
}
