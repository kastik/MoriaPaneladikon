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

import java.util.ArrayList;
import java.util.List;

public class ThemataSearch extends AppCompatActivity {
    private final List<String> availableSchools = new ArrayList<>();
    private final List<String> pathToSchool = new ArrayList<>();
    private String SchoolPath = "";
    private String YearPath = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.themata_search);

        final Spinner xroniaSpinner = findViewById(R.id.xronologiaSpinner);
        final Spinner schoolTypeSpinner = findViewById(R.id.idikotitaSpinner);
        final Button searchButton = findViewById(R.id.searchbutton);

        Context context = this;

        xroniaSpinner.setAdapter(new ArrayAdapter<>(this, R.layout.spinner_style, new String[]{"2020", "2019", "2018", "2017", "2016"}));
        xroniaSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                pathToSchool.clear();
                availableSchools.clear();
                SchoolPath = "";
                switch (position) {
                    case 0: {
                        YearPath = "2020";
                        availableSchools.add("ΓΕΛ Ημερίσια & Εσπερινά Νέο Σύστημα");
                        availableSchools.add("ΓΕΛ Ημερίσια Παλαιό Σύστημα");
                        availableSchools.add("ΓΕΛ Εσπερινά Παλαιό Σύστημα");
                        availableSchools.add("ΕΠΑ.Λ. Ημερίσια & Εσπερινά");
                        availableSchools.add("Ειδικα Μαθηματα");
                        pathToSchool.add("/Gel_Neo");
                        pathToSchool.add("/Gel_Imerisia_Palio");
                        pathToSchool.add("/Gel_Esperina_Palio");
                        pathToSchool.add("/Epal");
                        pathToSchool.add("/Special");
                        ArrayAdapter<? extends String> paok = new ArrayAdapter<>(context, R.layout.spinner_style, availableSchools);
                        schoolTypeSpinner.setAdapter(paok);
                        break;
                    }

                    case 1: {
                        YearPath = "2019";
                        availableSchools.add("ΓΕ.Λ. Ημερίσια & Εσπερινά");
                        availableSchools.add("ΕΠΑ.Λ. Ημερίσια & Εσπερινά");
                        availableSchools.add("Ειδικα Μαθηματα");
                        pathToSchool.add("/Gel");
                        pathToSchool.add("/Epal");
                        pathToSchool.add("/Special");
                        ArrayAdapter<? extends String> paok = new ArrayAdapter<>(context, R.layout.spinner_style, availableSchools);
                        schoolTypeSpinner.setAdapter(paok);
                        break;
                    }

                    case 2: {
                        YearPath = "2018";
                        availableSchools.add("ΓΕ.Λ. Ημερίσια");
                        availableSchools.add("ΓΕ.Λ. Εσπερινά");
                        availableSchools.add("ΕΠΑ.Λ. Ημερίσια & Εσπερινά");
                        availableSchools.add("Ειδικα Μαθηματα");
                        pathToSchool.add("/Gel_Imerisia");
                        pathToSchool.add("/Gel_Esperina");
                        pathToSchool.add("/Epal");
                        pathToSchool.add("/Special");
                        ArrayAdapter<? extends String> paok = new ArrayAdapter<>(view.getContext(), R.layout.spinner_style, availableSchools);
                        schoolTypeSpinner.setAdapter(paok);
                        break;
                    }
                    case 3: {
                        //TODO
                        YearPath = "2017";
                        break;
                    }
                    case 4: {
                        //TODO
                        YearPath = "2016";
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
                SchoolPath = pathToSchool.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        searchButton.setOnClickListener(view -> {
            Intent themataViewActivity = new Intent(view.getContext(), ThemataView.class);
            themataViewActivity.putExtra("path", "Themata/" + YearPath + SchoolPath);
            startActivity(themataViewActivity);

        });
    }
}