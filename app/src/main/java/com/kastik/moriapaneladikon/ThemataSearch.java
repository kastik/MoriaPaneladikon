package com.kastik.moriapaneladikon;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

public class ThemataSearch extends AppCompatActivity {
    private String SchoolPath;
    private String Year;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.themata_search);

        final Spinner xroniaSpinner = findViewById(R.id.themataYearSpinner);
        final Spinner schoolTypeSpinner = findViewById(R.id.themataSchoolTypeSpinner);
        final Button searchButton = findViewById(R.id.themataSearchButton);

        Context context = this;

        xroniaSpinner.setAdapter(new ArrayAdapter<>(this, R.layout.spinner_style, getResources().getStringArray(R.array.AvailableYears)));
        xroniaSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                switch (position) {
                    case 0: {
                        Year = "2020";
                        ArrayAdapter<? extends String> SchoolsAdapter = new ArrayAdapter<>(context, R.layout.spinner_style, getResources().getStringArray(R.array.ThemataSchoolsToShow2020));
                        schoolTypeSpinner.setAdapter(SchoolsAdapter);
                        break;
                    }

                    case 1: {
                        Year = "2019";
                        ArrayAdapter<? extends String> paok = new ArrayAdapter<>(context, R.layout.spinner_style, getResources().getStringArray(R.array.ThemataSchools2019));
                        schoolTypeSpinner.setAdapter(paok);
                        break;
                    }

                    case 2: {
                        Year = "2018";
                        ArrayAdapter<? extends String> paok = new ArrayAdapter<>(view.getContext(), R.layout.spinner_style, getResources().getStringArray(R.array.ThemataSchools2018));
                        schoolTypeSpinner.setAdapter(paok);
                        break;
                    }
                    case 3: {
                        //TODO
                        Year = "2017";
                        break;
                    }
                    case 4: {
                        //TODO
                        Year = "2016";
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
                if (Year.equals("2020")) {
                    SchoolPath = getResources().getStringArray(R.array.ThemataSchoolsPath2020)[position];
                } else if (Year.equals("2019")) {
                    SchoolPath = getResources().getStringArray(R.array.ThemataSchoolsPath2019)[position];
                } else {
                    SchoolPath = getResources().getStringArray(R.array.ThemataSchoolsPath2018)[position];
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        searchButton.setOnClickListener(view -> {
            Intent themataViewActivity = new Intent(view.getContext(), ThemataView.class);
            themataViewActivity.putExtra("path", "Themata/" + Year + SchoolPath);
            startActivity(themataViewActivity);

        });
    }
}