package fr.xebia.cpele.xebiablog;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;
import retrofit2.http.GET;

public class MainActivity extends AppCompatActivity {

    private static final String FEED_URL = "http://blog.xebia.fr";

    private ItemAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAdapter = new ItemAdapter();
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.main_rv_items);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(FEED_URL)
                .addConverterFactory(SimpleXmlConverterFactory.createNonStrict())
                .build();

        BlogApi blogApi = retrofit.create(BlogApi.class);

        blogApi.fetchFeed().enqueue(new Callback<Feed>() {

            @Override
            public void onResponse(@NonNull final Call<Feed> call, @NonNull final Response<Feed> response) {
                Feed feed = response.body();
                Log.d(MainActivity.this.getClass().getSimpleName(), "Feed: " + feed);
                if (feed != null && feed.channel.items != null) {
                    for (Item item : feed.channel.items) {
                        Log.d(MainActivity.this.getClass().getSimpleName(), String.valueOf(item));
                    }

                    mAdapter.addAll(feed.channel.items);
                    mAdapter.notifyDataSetChanged();
                }
                Toast.makeText(MainActivity.this, "It's working", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(@NonNull final Call<Feed> call, @NonNull final Throwable t) {
                Toast.makeText(MainActivity.this, "There was an error", Toast.LENGTH_SHORT).show();
                Log.e(MainActivity.this.getClass().getSimpleName(), "Here is an error", t);
            }
        });
    }

    private interface BlogApi {
        @GET("/feed")
        Call<Feed> fetchFeed();
    }

    private static class ItemAdapter extends RecyclerView.Adapter<ItemViewHolder> {

        @NonNull
        private List<Item> mItems = new ArrayList<>();

        @Override
        public ItemViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, final int viewType) {

            Context context = parent.getContext();
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
            View view = layoutInflater.inflate(R.layout.layout_item, parent, false);
            return new ItemViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull final ItemViewHolder holder, final int position) {
            holder.bind(mItems.get(position));
        }

        @Override
        public int getItemCount() {
            return mItems.size();
        }

        void addAll(@NonNull final List<Item> items) {
            mItems.addAll(items);
        }
    }

    private static class ItemViewHolder extends RecyclerView.ViewHolder {

        private final TextView mTitle;

        ItemViewHolder(final View itemView) {
            super(itemView);

            mTitle = (TextView) itemView.findViewById(R.id.item_tv_title);
        }

        void bind(final Item item) {
            mTitle.setText(item.title);
        }
    }
}
