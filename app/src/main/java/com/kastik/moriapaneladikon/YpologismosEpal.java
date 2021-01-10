package com.kastik.moriapaneladikon;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class YpologismosEpal extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ypologismos_epal_view);

        final EditText epalSpecialLessonEditText1 = findViewById(R.id.epalSpecialLessonEditText1);
        final EditText epalSpecialLessonEditText2 = findViewById(R.id.epalSpecialLessonEditText2);
        final EditText epalSpecialLessonEditText3 = findViewById(R.id.epalSpecialLessonEditText3);
        final Button calcButton = findViewById(R.id.calcButton);
        final Spinner dropdown = findViewById(R.id.epalSpinner);

        calcButton.setOnClickListener(view -> {
            double grade = calc(dropdown.getSelectedItemPosition());
            if (grade != -1) {
                showGrade(grade);
            } else {
                showEmpyFieldError();
            }
        });


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.IdikaMathimata, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dropdown.setAdapter(adapter);

        dropdown.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                switch (position) {
                    case 0: { //Kanena
                        epalSpecialLessonEditText1.setVisibility(View.GONE);
                        epalSpecialLessonEditText2.setVisibility(View.GONE);
                        epalSpecialLessonEditText3.setVisibility(View.GONE);
                        break;

                    }
                    case 1: { // Glosses/sa
                        epalSpecialLessonEditText1.setHint("Ξενες Γλωσσες");
                        epalSpecialLessonEditText1.setVisibility(View.VISIBLE);
                        epalSpecialLessonEditText2.setVisibility(View.GONE);
                        epalSpecialLessonEditText3.setVisibility(View.GONE);
                        break;
                    }
                    case 2: { //elefthero sxedio
                        epalSpecialLessonEditText1.setHint("Ελεφθερω σχεδιο");
                        epalSpecialLessonEditText1.setVisibility(View.VISIBLE);
                        epalSpecialLessonEditText2.setVisibility(View.GONE);
                        epalSpecialLessonEditText3.setVisibility(View.GONE);
                        break;
                    }
                    case 3: { // elefthero kai gramiko sxedio
                        epalSpecialLessonEditText1.setHint("Ελεφθερω σχεδιο");
                        epalSpecialLessonEditText2.setHint("Γραμικο σχεδιο");
                        epalSpecialLessonEditText1.setVisibility(View.VISIBLE);
                        epalSpecialLessonEditText2.setVisibility(View.VISIBLE);
                        epalSpecialLessonEditText3.setVisibility(View.GONE);
                        break;
                    }
                    case 4: { // armonia kai elngos
                        epalSpecialLessonEditText1.setHint("Αρμονια");
                        epalSpecialLessonEditText2.setHint("Ελγχος");
                        epalSpecialLessonEditText1.setVisibility(View.VISIBLE);
                        epalSpecialLessonEditText2.setVisibility(View.VISIBLE);
                        epalSpecialLessonEditText3.setVisibility(View.GONE);
                        break;
                    }
                    case 5: {
                        epalSpecialLessonEditText1.setHint("Αθλημα 1");
                        epalSpecialLessonEditText2.setHint("Αθλημα 2");
                        epalSpecialLessonEditText3.setHint("Άθλημα 3");
                        epalSpecialLessonEditText1.setVisibility(View.VISIBLE);
                        epalSpecialLessonEditText2.setVisibility(View.VISIBLE);
                        epalSpecialLessonEditText3.setVisibility(View.VISIBLE);
                        break;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private double calc(int specialLessonType) {
        final EditText moriogenikis1EditText = findViewById(R.id.morio_genikis_1);
        final EditText moriogenikis2EditText = findViewById(R.id.morio_genikis_2);
        final EditText morioidik1EditText = findViewById(R.id.morio_idikotitas_1);
        final EditText morioidik2EditText = findViewById(R.id.morio_idikotitas_2);
        EditText epalSpecialLessonEditText1 = findViewById(R.id.epalSpecialLessonEditText1);
        EditText epalSpecialLessonEditText2 = findViewById(R.id.epalSpecialLessonEditText2);
        EditText specialLessonEditText3 = findViewById(R.id.epalSpecialLessonEditText3);
        try {
            double moriogenikis1 = Double.parseDouble(moriogenikis1EditText.getText().toString());
            double moriogenikis2 = Double.parseDouble(moriogenikis2EditText.getText().toString());
            double morioidik1 = Double.parseDouble(morioidik1EditText.getText().toString());
            double morioidik2 = Double.parseDouble(morioidik2EditText.getText().toString());
            switch (specialLessonType) {
                case 0: {
                    return (int) (moriogenikis1 * 1.5 + moriogenikis2 * 150 + morioidik1 * 350 + morioidik2 * 350);
                }
                case 1:
                case 2: {
                    double specialLesson1 = Double.parseDouble(epalSpecialLessonEditText1.getText().toString());
                    return (int) (moriogenikis1 * 150 + moriogenikis2 * 150 + morioidik1 * 350 + morioidik2 * 350 + specialLesson1 * 200);
                }
                case 3:
                case 4: {
                    double specialLesson1 = Double.parseDouble(epalSpecialLessonEditText1.getText().toString());
                    double specialLesson2 = Double.parseDouble(epalSpecialLessonEditText2.getText().toString());
                    return (int) (moriogenikis1 * 150 + moriogenikis2 * 150 + morioidik1 * 350 + morioidik2 * 350 + specialLesson1 * 100 + specialLesson2 * 100);
                }
                case 5: {
                    double specialLesson1 = Double.parseDouble(epalSpecialLessonEditText1.getText().toString());
                    double specialLesson2 = Double.parseDouble(epalSpecialLessonEditText2.getText().toString());
                    double specialLesson3 = Double.parseDouble(specialLessonEditText3.getText().toString());
                    return (int) (moriogenikis1 * 150 + moriogenikis2 * 150 + morioidik1 * 350 + morioidik2 * 350 + ((specialLesson1 + specialLesson2 + specialLesson3) / 3) * 2);
                }
            }
        } catch (NumberFormatException emptyString) {
            return -1;
        }
        return -1;
    }

    private void showGrade(double bathmos) {
        AlertDialog.Builder gradeDialog = new AlertDialog.Builder(this);
        gradeDialog.setMessage(bathmos + " Μορια")
                .setCancelable(false)
                .setPositiveButton("ok", null);
        gradeDialog.show();
    }

    private void showEmpyFieldError() {
        AlertDialog.Builder emptyErrorDialog = new AlertDialog.Builder(this);
        emptyErrorDialog.setMessage("Πρεπει να σημπληρωσεις ολλα τα πεδια")
                .setCancelable(false)
                .setPositiveButton("OK", null);
        emptyErrorDialog.show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}

