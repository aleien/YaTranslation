package io.translation.yandex.yatranslation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import io.translation.yandex.yatranslation.screens.MainFragment;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    // TODO: можно сделать через dagger, если будет желание
    Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initializeLibraries();

        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_fragment_container, new MainFragment())
                .commit();

    }

    private void initializeLibraries() {
        retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
