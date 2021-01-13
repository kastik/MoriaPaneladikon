package com.kastik.moriapaneladikon;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class ThemataView extends AppCompatActivity {
    ThemataAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_view);

        String path = getIntent().getStringExtra("path");
        assert path != null;



        /*
        Context context = this;

        DatabaseReference connectedRef = FirebaseDatabase.getInstance().getReference(".info/connected");
        connectedRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                boolean connected = snapshot.getValue(Boolean.class);
                if(!connected){

                }
                else{
                    AlertDialog.Builder noInternetDialog = new AlertDialog.Builder(context);
                    noInternetDialog.setCancelable(false)
                            .setMessage("no internet connection")
                            .setPositiveButton("ok",null);
                    noInternetDialog.show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


         */

        Query query = FirebaseDatabase.getInstance().getReference(path);


       /* String pathaa = "/Themata"+"/2020"+"/Gel_Imerisia_Palio";
        DatabaseReference mbase2 = FirebaseDatabase.getInstance().getReference(pathaa);
        Query query2 = mbase2.orderByChild(pathaa);

        */
        RecyclerView recyclerView = findViewById(R.id.RecycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        FirebaseRecyclerOptions<ThemataModel> options
                = new FirebaseRecyclerOptions.Builder<ThemataModel>()
                .setQuery(query, ThemataModel.class)
                .build();
        adapter = new ThemataAdapter(options, this);
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
    }

}