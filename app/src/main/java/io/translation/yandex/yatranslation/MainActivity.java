package io.translation.yandex.yatranslation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import butterknife.BindString;
import butterknife.ButterKnife;
import io.translation.yandex.yatranslation.api.TranslateApi;
import io.translation.yandex.yatranslation.model.SlovoModel;
import io.translation.yandex.yatranslation.screens.MainFragment;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    // TODO: можно сделать через dagger, если будет желание
    Retrofit mRetrofit;
    TranslateApi mTranslateApi;

    @BindString(R.string.ya_translation_api_key)
    String apiKey;

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

        SlovoModel.init(getApplicationContext());
        SlovoModel slovoModel = new SlovoModel();
        //slovoModel.deleteItAll();
        //slovoModel.addWord(new Word("Кот", "Cat", 1));
        //slovoModel.addWord(new Word("Собака", "Dog", 3));
/*
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

        final SlovoModel slovoModel = new SlovoModel();
        slovoModel.deleteItAll();
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void[] params) {
                JsonWTF jsonWTF = new JsonWTF();
                InitJsonWordList initJsonWordList = jsonWTF.getLocalJson(getApplicationContext());
                List<String> hehe = initJsonWordList.getRuList();

                for (String word : hehe) {
                    try {
                        Response<TranslationResponse> response = mTranslateApi.lookup(getResources().getString(R.string.ya_translation_api_key), "ru-en", word).execute();
                        if (response.isSuccessful()) {
                            if (response.body().definition.size() != 0 && response.body().definition.get(0).translations.size() != 0) {
                                String wordString = response.body().definition.get(0).word;
                                String translate = response.body().definition.get(0).translations.get(0).word;
                                Word wordWord = new Word(wordString, translate, 0);
                                slovoModel.addWord(wordWord);
                            }
                        } else {
                            Log.e("Response", "FUU");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                Set<Word> words = new SlovoModel().getWords();
                words.size();
                return null;
            }
        }.execute();
        */
    }
}