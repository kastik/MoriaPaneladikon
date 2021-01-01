package com.kastik.moriapaneladikon;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

public class BaseisSearch extends AppCompatActivity {

    private int Pedio;
    private String Year;
    private String SchoolType;
    private String Idikotita;
    private boolean isEpal;

    @Override
    protected void onCreate(Bundle SavedInstanceState) {
        super.onCreate(SavedInstanceState);
        setContentView(R.layout.baseis_search);

        final Spinner xroniaSpinner = findViewById(R.id.baseisYearSpinner);
        final Spinner schoolTypeSpinner = findViewById(R.id.baseisSchoolTypeSpinner);
        final Spinner idikotitaSpinner = findViewById(R.id.baseisIdikotitaPedioSpinner);
        final Button searchButton = findViewById(R.id.baseisSearchButton);

        Context context = this;
        xroniaSpinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.AvailableYears)));
        xroniaSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                Year = getResources().getStringArray(R.array.AvailableYears)[position];
                switch (position) {
                    case 0: {
                        ArrayAdapter<? extends String> paok = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.BaseisSchoolsShow2020));
                        schoolTypeSpinner.setAdapter(paok);
                        break;
                    }
                    case 1:
                    case 2: {
                        ArrayAdapter<? extends String> paok = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.Schools2019));
                        schoolTypeSpinner.setAdapter(paok);
                        break;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        schoolTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                SchoolType = getResources().getStringArray(R.array.BaseisSchoolsPaths2020)[position];
                switch (position) {
                    case 0:
                    case 1:
                    case 2:
                    case 3: {
                        ArrayAdapter<? extends String> paoka = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.PediaToShow));
                        idikotitaSpinner.setAdapter(paoka);
                        isEpal = false;
                        break;
                    }
                    case 4:
                    case 5: {
                        ArrayAdapter<? extends String> paoka = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.TomisToShow));
                        idikotitaSpinner.setAdapter(paoka);
                        isEpal = true;
                        break;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        idikotitaSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (isEpal) {
                    Idikotita = getResources().getStringArray(R.array.TomisPath)[position];
                } else {
                    Pedio = getResources().getIntArray(R.array.Pedia)[position];
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        searchButton.setOnClickListener(view -> {
            Intent themataViewActivity = new Intent(view.getContext(), BaseisView.class);
            themataViewActivity.putExtra("Year", Year);
            themataViewActivity.putExtra("Pedio", Pedio);
            themataViewActivity.putExtra("SchoolType", SchoolType);
            themataViewActivity.putExtra("isEpal", isEpal);
            themataViewActivity.putExtra("Idikotita", Idikotita);
            startActivity(themataViewActivity);
            Log.d("path", "Baseis" + Year + SchoolType + Pedio);
        });

    }
}