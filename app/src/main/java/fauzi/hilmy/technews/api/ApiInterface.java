package fauzi.hilmy.technews.api;

import fauzi.hilmy.technews.response.ResponseNews;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {
    @GET("everything?q=technology&apiKey=795f5a13a92a46b1bfdd25f8d1307c23")
    Call<ResponseNews> getTech();
}
