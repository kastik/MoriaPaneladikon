package com.kastik.moriapaneladikon;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;

public class ThemataAdapter extends FirebaseRecyclerAdapter<ThemataModel, ThemataAdapter.LeasonViewHolder> {
    private final Context context;

    public ThemataAdapter(@NonNull FirebaseRecyclerOptions<ThemataModel> options, Context context) {
        super(options);
        this.context = context;
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onBindViewHolder(@NonNull LeasonViewHolder holder, int position, @NonNull ThemataModel model) {

        holder.SchoolType.setText(model.getSchoolType());
        holder.LeassonName.setText(model.getLessonName());
        holder.Year.setText(model.getYear());

        File file = new File(context.getCacheDir(), model.getLessonName().replace(" ", "_") + "-" + model.getYear() + model.getFileExtension());
        if (file.exists()) {
            holder.downloadIcon.setVisibility(View.VISIBLE);
            holder.progressBar.setVisibility(View.GONE);
            holder.downloadIcon.setImageDrawable(getDoneImage());
            holder.downloadIcon.setOnClickListener(v -> fileOpener(file));
        } else {
            holder.downloadIcon.setImageDrawable(getDownloadImage());

            holder.downloadIcon.setOnClickListener(view -> {
                holder.downloadIcon.setVisibility(View.INVISIBLE);
                holder.progressBar.setVisibility(View.VISIBLE);
                holder.progressBar.setMax(100);

                FirebaseStorage storage = FirebaseStorage.getInstance();
                StorageReference storageRef = storage.getReferenceFromUrl(model.getLink());
                FileDownloadTask DownloadTask = storageRef.getFile(file);

                DownloadTask.addOnSuccessListener(taskSnapshot -> {
                    holder.downloadIcon.setImageDrawable(getDoneImage());
                    fileOpener(file);
                }).addOnFailureListener(e -> {

                }).addOnProgressListener(snapshot -> {
                    double progress = (100.0 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                    holder.progressBar.setProgress((int) progress);
                    holder.progressBar.setOnClickListener(view1 -> {
                        DownloadTask.cancel();
                        holder.downloadIcon.setVisibility(View.VISIBLE);
                        holder.progressBar.setVisibility(View.GONE);
                    });
                });

            });
        }
    }


    @NonNull
    @Override
    public LeasonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lesson_card, parent, false);
        return new LeasonViewHolder(view);
    }

    private Drawable getDoneImage() {
        int nightModeFlags = context.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        switch (nightModeFlags) {
            case Configuration.UI_MODE_NIGHT_YES:
                return (AppCompatResources.getDrawable(context, R.drawable.ic_done_white));

            case Configuration.UI_MODE_NIGHT_NO:
            case Configuration.UI_MODE_NIGHT_UNDEFINED:
                return (AppCompatResources.getDrawable(context, R.drawable.ic_done_black));
        }
        return null;
    }

    private Drawable getDownloadImage() {
        int nightModeFlags = context.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        switch (nightModeFlags) {

            case Configuration.UI_MODE_NIGHT_YES:
                return AppCompatResources.getDrawable(context, R.drawable.ic_download_white);

            case Configuration.UI_MODE_NIGHT_NO:

            case Configuration.UI_MODE_NIGHT_UNDEFINED:
                return AppCompatResources.getDrawable(context, R.drawable.ic_download_black);
        }
        return null;
    }

    private void fileOpener(File file) {
        Uri uri = FileProvider.getUriForFile(context, context.getString(R.string.provider), file);
        String mime = context.getContentResolver().getType(uri);
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setDataAndType(uri, mime);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        context.startActivity(intent);
    }

    static class LeasonViewHolder extends RecyclerView.ViewHolder {
        TextView LeassonName, SchoolType, Year;
        ImageView downloadIcon;
        ProgressBar progressBar;

        @SuppressLint("UseCompatLoadingForDrawables")
        public LeasonViewHolder(@NonNull View itemView) {
            super(itemView);
            LeassonName = itemView.findViewById(R.id.idrima_view);
            SchoolType = itemView.findViewById(R.id.title_view);
            Year = itemView.findViewById(R.id.moria_view);
            downloadIcon = itemView.findViewById(R.id.download_button);
            progressBar = itemView.findViewById(R.id.progressBar);
        }
    }

}
