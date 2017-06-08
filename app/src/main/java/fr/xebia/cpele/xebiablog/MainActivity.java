package fr.xebia.cpele.xebiablog;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;
import retrofit2.http.GET;

public class MainActivity extends AppCompatActivity {

    private static final String FEED_URL = "http://blog.xebia.fr";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(FEED_URL)
                .addConverterFactory(SimpleXmlConverterFactory.createNonStrict())
                .build();

        BlogApi blogApi = retrofit.create(BlogApi.class);

        blogApi.fetchFeed().enqueue(new Callback<Channel>() {

            @Override
            public void onResponse(@NonNull final Call<Channel> call, @NonNull final Response<Channel> response) {
                Channel channel = response.body();
                if (channel != null && channel.items != null) {
                    for (Item item : channel.items) {
                        Log.d(MainActivity.this.getClass().getSimpleName(), String.valueOf(item));
                    }
                }
                Toast.makeText(MainActivity.this, "It's working", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(@NonNull final Call<Channel> call, @NonNull final Throwable t) {
                Toast.makeText(MainActivity.this, "There was an error", Toast.LENGTH_SHORT).show();
                Log.e(MainActivity.this.getClass().getSimpleName(), "Here is an error", t);
            }
        });
    }

    private interface BlogApi {
        @GET("/feed")
        Call<Channel> fetchFeed();
    }

}
