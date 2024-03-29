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
import android.widget.TextView;

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
        xroniaSpinner.setAdapter(new ArrayAdapter<>(this, R.layout.custom_spinner, getResources().getStringArray(R.array.AvailableYears)));
        xroniaSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                Year = getResources().getStringArray(R.array.AvailableYears)[position];
                ArrayAdapter<? extends String> schoolTypeAdapter;
                if (Year.equals("2020")) {
                    schoolTypeAdapter = new ArrayAdapter<>(context, R.layout.custom_spinner, getResources().getStringArray(R.array.BaseisSchoolsShow2020));
                } else if (Year.equals("2019")) {
                    schoolTypeAdapter = new ArrayAdapter<>(context, R.layout.custom_spinner, getResources().getStringArray(R.array.Schools2019));
                } else {
                    schoolTypeAdapter = new ArrayAdapter<>(context, R.layout.custom_spinner, getResources().getStringArray(R.array.Schools2018));
                }
                schoolTypeSpinner.setAdapter(schoolTypeAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        schoolTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                ArrayAdapter<? extends String> tomeasIdikotitaAdapter;
                if (Year.equals("2020")) {
                    SchoolType = getResources().getStringArray(R.array.BaseisSchoolsPaths2020)[position];
                } else if (Year.equals("2019")) {
                    SchoolType = getResources().getStringArray(R.array.BaseisSchoolsPaths2019)[position];
                } else {
                    SchoolType = getResources().getStringArray(R.array.BaseisSchoolsPaths2018)[position];
                }

                TextView idikotitaPedioTextView = findViewById(R.id.baseisIdikotitaPedioTextView);
                if (SchoolType.contains("Gel")) {
                    idikotitaPedioTextView.setText("Πεδίο");
                    tomeasIdikotitaAdapter = new ArrayAdapter<>(context, R.layout.custom_spinner, getResources().getStringArray(R.array.PediaToShow));
                    isEpal = false;
                } else {
                    idikotitaPedioTextView.setText("Ειδικότητα");
                    tomeasIdikotitaAdapter = new ArrayAdapter<>(context, R.layout.custom_spinner, getResources().getStringArray(R.array.TomisToShow));
                    isEpal = true;
                }

                idikotitaSpinner.setAdapter(tomeasIdikotitaAdapter);
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
            Log.d("path", "Baseis" + Year + SchoolType + Pedio + Idikotita + isEpal);
            startActivity(themataViewActivity);
        });

    }
}