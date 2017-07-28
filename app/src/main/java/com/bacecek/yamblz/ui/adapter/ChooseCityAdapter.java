package com.bacecek.yamblz.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bacecek.yamblz.R;
import com.bacecek.yamblz.data.presentation.City;
import com.bacecek.yamblz.ui.fragment.ChooseCityFragment;

import java.util.ArrayList;
import java.util.List;

public class ChooseCityAdapter extends RecyclerView.Adapter<ChooseCityAdapter.ChooseCityViewHolder> {

    @NonNull
    private final ChooseCityFragment.OnChooseCityListener listener;

    @NonNull
    private final List<City> cities = new ArrayList<>();

    public ChooseCityAdapter(@NonNull ChooseCityFragment.OnChooseCityListener listener) {
        this.listener = listener;
    }

    @Override
    public int getItemCount() {
        return cities.size();
    }

    @Override
    public ChooseCityViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ChooseCityViewHolder(LayoutInflater.from(parent.getContext()).inflate(
                R.layout.layout_suggestion_row, parent, false));
    }

    @Override
    public void onBindViewHolder(ChooseCityViewHolder holder, int position) {
        City suggestion = cities.get(position);
        holder.setCity(suggestion);
    }

    public void add(@NonNull List<City> suggestions) {
        this.cities.addAll(suggestions);
        notifyDataSetChanged();
    }

    public void clear() {
        int count = getItemCount();
        this.cities.clear();
        notifyItemRangeRemoved(0, count);
    }

    class ChooseCityViewHolder extends RecyclerView.ViewHolder {

        private TextView cityName;

        ChooseCityViewHolder(View itemView) {
            super(itemView);

            cityName = (TextView) itemView.findViewById(R.id.txt_suggestion);
            itemView.setOnClickListener((view) -> listener.onChooseCity(cities.get(getAdapterPosition())));
        }

        public void setCity(@NonNull City suggestion) {
            cityName.setText(suggestion.getName());
        }
    }
}
