package com.kastik.moriapaneladikon;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class BaseisView extends AppCompatActivity {
    BaseisAdapter adapter;

    @Override
    protected void onCreate(Bundle SavedInstanceState) {
        super.onCreate(SavedInstanceState);
        setContentView(R.layout.recycler_view);
        RecyclerView recyclerView = findViewById(R.id.RecycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        final FieldPath fieldPath = FieldPath.of("moriaTelefteou");
        final String Year = getIntent().getStringExtra("Year");
        final String SchoolType = getIntent().getStringExtra("SchoolType");
        final boolean isEpal = getIntent().getBooleanExtra("isEpal", false);
        final FirebaseFirestore db = FirebaseFirestore.getInstance();

        Query query;

        if (isEpal) {
            String Idikotita = getIntent().getStringExtra("Idikotita");
            query = db.collection("Baseis").document(Year).collection(SchoolType).whereArrayContains("Idikotita", Idikotita).orderBy(fieldPath, Query.Direction.DESCENDING);
        } else {
            int Pedio = getIntent().getIntExtra("Pedio", 0);
            if (Pedio != 0) {
                query = db.collection("Baseis").document(Year).collection(SchoolType).whereArrayContains("pedio", Pedio).orderBy(fieldPath, Query.Direction.DESCENDING);
            } else {
                query = db.collection("Baseis").document(Year).collection(SchoolType).orderBy(fieldPath, Query.Direction.DESCENDING);
            }
        }


        FirestoreRecyclerOptions<BaseisModel> options = new FirestoreRecyclerOptions.Builder<BaseisModel>()
                .setQuery(query, BaseisModel.class)
                .build();
        adapter = new BaseisAdapter(options);
        recyclerView.setAdapter(adapter);

    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}