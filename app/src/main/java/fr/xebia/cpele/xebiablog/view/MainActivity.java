package fr.xebia.cpele.xebiablog.view;

import android.arch.lifecycle.LifecycleActivity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
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

import fr.xebia.cpele.xebiablog.viewmodel.FeedViewModel;
import fr.xebia.cpele.xebiablog.R;
import fr.xebia.cpele.xebiablog.model.api.Item;

public class MainActivity extends LifecycleActivity {

    private ItemAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.d(getClass().getSimpleName(), "Life: MainActivity: create");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAdapter = new ItemAdapter();
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.main_rv_items);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FeedViewModel feedViewModel = ViewModelProviders.of(this).get(FeedViewModel.class);

        feedViewModel.get().observe(this, feed -> {

            Toast.makeText(MainActivity.this, "Feed has changed", Toast.LENGTH_SHORT).show();

            if (feed != null && feed.channel != null && feed.channel.items != null) {
                for (Item item : feed.channel.items) {
                    Log.d(MainActivity.class.getSimpleName(), String.valueOf(item));
                    mAdapter.clear();
                    mAdapter.addAll(feed.channel.items);
                    mAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.d(getClass().getSimpleName(), "Life: MainActivity: destroy");
    }

    private static class ItemAdapter extends RecyclerView.Adapter<ItemViewHolder> {

        @NonNull
        private final List<Item> mItems = new ArrayList<>();

        @Override
        public ItemViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, final int viewType) {

            Context context = parent.getContext();
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
            assert layoutInflater != null;
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

        void clear() {
            mItems.clear();
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

            itemView.setOnClickListener(v -> DetailActivity.launch(itemView.getContext(), item.link));
        }
    }
}
