package fauzi.hilmy.technews;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import fauzi.hilmy.technews.api.ApiClient;
import fauzi.hilmy.technews.api.ApiInterface;
import fauzi.hilmy.technews.response.ArticlesItem;
import fauzi.hilmy.technews.response.ResponseNews;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.recyclerTech)
    RecyclerView recyclerTech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        loadData();

    }

    private void loadData() {
        ApiInterface apiInterface = ApiClient.getInstance();
        Call<ResponseNews> call = apiInterface.getTech();
        call.enqueue(new Callback<ResponseNews>() {
            @Override
            public void onResponse(Call<ResponseNews> call, Response<ResponseNews> response) {
                if (response.body().getStatus().equals("ok")) {
                    List<ArticlesItem> articlesItems = response.body().getArticles();
                    Log.e("Response", response.body().getStatus());

                    recyclerTech.setHasFixedSize(true);
                    recyclerTech.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                    recyclerTech.setAdapter(new AdapterNews(articlesItems, MainActivity.this));

                }
            }

            @Override
            public void onFailure(Call<ResponseNews> call, Throwable t) {
                Log.e("Error/Anzuyy", "", t);
            }
        });
    }
}
