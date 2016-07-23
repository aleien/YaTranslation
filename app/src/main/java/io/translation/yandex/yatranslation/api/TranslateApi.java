package io.translation.yandex.yatranslation.api;

import java.util.List;

import io.translation.yandex.yatranslation.model.TranslationResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by aleien on 23.07.16.
 */

public interface TranslateApi {

    @GET("getLangs")
    Call<List<String>> getLangs(@Query("key") String key);

    @GET("lookup")
    Call<TranslationResponse> lookup(@Query("key") String key,
                                     @Query("lang") String lang,
                                     @Query("text") String text);

}
