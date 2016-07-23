package io.translation.yandex.yatranslation;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import butterknife.BindString;
import butterknife.ButterKnife;
import io.translation.yandex.yatranslation.api.TranslateApi;
import io.translation.yandex.yatranslation.model.SlovoModel;
import io.translation.yandex.yatranslation.model.TranslationResponse;
import io.translation.yandex.yatranslation.model.Word;
import io.translation.yandex.yatranslation.model.json.InitJsonWordList;
import io.translation.yandex.yatranslation.model.json.JsonWTF;
import io.translation.yandex.yatranslation.screens.MainFragment;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity {
    public static int TASK_WORDS_COUNT = 10;

    // TODO: можно сделать через dagger, если будет желание
    Retrofit mRetrofit;
    TranslateApi mTranslateApi;

    SlovoModel mDatabase;

    @BindString(R.string.ya_translation_api_key)
    String apiKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initializeLibraries();

        checkDatabase();

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_fragment_container, new MainFragment())
                .commit();
    }

    // Нет, мне не стдыно
    private void checkDatabase() {
        final SlovoModel slovoModel = new SlovoModel();

        Set<Word> words = slovoModel.getWords();
        if (words == null || words.size() == 0) {
            new AsyncTask<Void, Void, Void>() {
                AlertDialog mAlertDialog;
                int mProgress;

                @Override
                protected void onPreExecute() {
                    mAlertDialog = new AlertDialog.Builder(MainActivity.this)
                            .setTitle("Работа")
                            .setMessage("Подождите: " + mProgress + "/100").create();
                    mAlertDialog.show();
                }

                @Override
                protected void onProgressUpdate(Void... values) {
                    mAlertDialog.setMessage("Подождите: " + ++mProgress + "/100"); // HARDCORE
                }

                @Override
                protected Void doInBackground(Void[] params) {
                    OkHttpClient loggingClient = provideOkHttpClient();

                    mRetrofit = new Retrofit.Builder()
                            .baseUrl("https://dictionary.yandex.net/api/v1/dicservice.json/")
                            .client(loggingClient)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    mTranslateApi = mRetrofit.create(TranslateApi.class);

                    JsonWTF jsonWTF = new JsonWTF();
                    InitJsonWordList initJsonWordList = jsonWTF.getLocalJson(getApplicationContext());
                    List<String> hehe = initJsonWordList.getRuList();

                    for (String word : hehe) {
                        try {
                            publishProgress();
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
                    return null;
                }

                @Override
                protected void onPostExecute(Void aVoid) {
                    super.onPostExecute(aVoid);
                    mAlertDialog.hide();
                }
            }.execute();
        }
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
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("font/Yandex Sans Display-Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }

    public SlovoModel provideDatabase() {
        return mDatabase;
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}