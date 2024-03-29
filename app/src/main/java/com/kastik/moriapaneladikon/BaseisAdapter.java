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
        holder.TitlosSpoudon.setText(String.valueOf(model.getSchoolName()));
        holder.textView.setText(model.getType());
        int moriaTelefteou = model.getMoriaTelefteou();
        if (moriaTelefteou > 0) {
            holder.BaseView.setText(String.valueOf(moriaTelefteou));
        } else {
            holder.BaseView.setText(String.valueOf(0));
            holder.BaseView.setVisibility(View.INVISIBLE);
        }
    }

    @NonNull
    @Override
    public BaseisAdapter.BaseisViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.baseis_card, parent, false);
        return new BaseisViewHolder(view);
    }

    static class BaseisViewHolder extends RecyclerView.ViewHolder {
        final TextView IdrimaView;
        final TextView TitlosSpoudon;
        final TextView BaseView;
        final TextView CityView;
        final TextView textView;

        public BaseisViewHolder(@NonNull View itemView) {
            super(itemView);
            IdrimaView = itemView.findViewById(R.id.baseisCardIdrimaView);
            TitlosSpoudon = itemView.findViewById(R.id.baseisCardTitleView);
            BaseView = itemView.findViewById(R.id.baseisCardMoriaView);
            CityView = itemView.findViewById(R.id.baseisCardCityView);
            textView = itemView.findViewById(R.id.baseisCardTyposIsagogis);
        }
    }
}
