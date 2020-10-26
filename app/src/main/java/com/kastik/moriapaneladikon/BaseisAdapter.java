package com.kastik.moriapaneladikon;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class BaseisAdapter extends FirestoreRecyclerAdapter<BaseisModel, BaseisAdapter.BaseisViewHolder> {
    public BaseisAdapter(@NonNull FirestoreRecyclerOptions<BaseisModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull BaseisAdapter.BaseisViewHolder holder, int position, @NonNull BaseisModel model) {
        holder.IdrimaView.setText(String.valueOf(model.getIdrima()));
        holder.CityView.setText(String.valueOf(model.getSchoolId()));
        holder.BaseView.setText(String.valueOf(model.getMoriaTelefteou()));
        holder.TitlosSpoudon.setText(String.valueOf(model.getSchoolName()));
        holder.textView.setText(model.getType());
    }

    @NonNull
    @Override
    public BaseisAdapter.BaseisViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.baseis_card, parent, false);
        return new BaseisViewHolder(view);
    }

    static class BaseisViewHolder extends RecyclerView.ViewHolder {
        TextView IdrimaView, TitlosSpoudon, BaseView, CityView, textView;

        public BaseisViewHolder(@NonNull View itemView) {
            super(itemView);
            IdrimaView = itemView.findViewById(R.id.idrima_view);
            TitlosSpoudon = itemView.findViewById(R.id.title_view);
            BaseView = itemView.findViewById(R.id.moria_view);
            CityView = itemView.findViewById(R.id.city_view);
            textView = itemView.findViewById(R.id.textView);
        }
    }
}
