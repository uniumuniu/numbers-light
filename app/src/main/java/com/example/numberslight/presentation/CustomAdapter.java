package com.example.numberslight.presentation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.numberslight.R;
import com.example.numberslight.domain.model.NumberModel;
import com.example.numberslight.presentation.customcontrols.CheckableCardView;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder> {

    private final Context context;
    private NumbersViewModel viewModel;
    private List<NumberModel> data;
    private NumberModel selectedNumber;

    public CustomAdapter(Context context, List<NumberModel> data, NumbersViewModel viewModel) {
        this.context = context;
        this.data = data;
        this.viewModel = viewModel;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_row, viewGroup, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder customViewHolder, int i) {
        NumberModel item = data.get(i);
        customViewHolder.itemTextView.setText(item.name);
        customViewHolder.itemView.setOnClickListener(view -> viewModel.setSelectedNumber(item));
        String correctedUrl = item.image.replace("http", "https");
        Picasso.get().load(correctedUrl).resize(300, 0).into(customViewHolder.itemImageView);
        CheckableCardView checkableCardView = (CheckableCardView) customViewHolder.itemView;
        checkableCardView.setChecked(selectedNumber != null && selectedNumber.name.equals(item.name));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void setData(List<NumberModel> newData) {
        data = newData;
        notifyDataSetChanged();
    }

    public void setSelectedNumber(NumberModel model) {
        if (selectedNumber != null) {
            notifyItemChanged(data.indexOf(selectedNumber));
        }

        selectedNumber = model;
        notifyItemChanged(data.indexOf(selectedNumber));
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        TextView itemTextView;
        ImageView itemImageView;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);

            itemTextView = itemView.findViewById(R.id.itemNameTextView);
            itemImageView = itemView.findViewById(R.id.itemImageView);
        }
    }
}
