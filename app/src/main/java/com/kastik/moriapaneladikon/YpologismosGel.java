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

public class YpologismosGel extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ypologismos_gel_view);

        final Button calcButton = findViewById(R.id.calcBtn);
        final EditText specialLessonEditText1 = findViewById(R.id.gelSpecialLessonEditText1);
        final EditText specialLessonEditText2 = findViewById(R.id.gelSpecialLessonEditText2);
        final EditText specialLessonEditText3 = findViewById(R.id.gelSpecialLessonEditText3);
        final Spinner dropdown = findViewById(R.id.spinner);

        calcButton.setOnClickListener(view -> {
            double bathmos = calc(dropdown.getSelectedItemPosition());
            if (bathmos != -1) {
                showGrade(bathmos);
            } else {
                ShowEmptyFieldError();
            }
        });

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.IdikaMathimata, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dropdown.setAdapter(adapter);

        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                switch (position) {
                    case 0:  //Kanena
                        specialLessonEditText1.setVisibility(View.GONE);
                        specialLessonEditText2.setVisibility(View.GONE);
                        specialLessonEditText3.setVisibility(View.GONE);
                        break;

                    case 1:
                        specialLessonEditText1.setHint("Ξενες Γλωσσες");
                        specialLessonEditText1.setVisibility(View.VISIBLE);
                        specialLessonEditText2.setVisibility(View.GONE);
                        specialLessonEditText3.setVisibility(View.GONE);
                        break;

                    case 2:
                        specialLessonEditText1.setHint("Ελεφθερω σχεδιο");
                        specialLessonEditText1.setVisibility(View.VISIBLE);
                        specialLessonEditText2.setVisibility(View.GONE);
                        specialLessonEditText3.setVisibility(View.GONE);
                        break;

                    case 3:
                        specialLessonEditText1.setHint("Ελεφθερω σχεδιο");
                        specialLessonEditText2.setHint("Γραμικο σχεδιο");
                        specialLessonEditText1.setVisibility(View.VISIBLE);
                        specialLessonEditText2.setVisibility(View.VISIBLE);
                        specialLessonEditText3.setVisibility(View.GONE);
                        break;

                    case 4:
                        specialLessonEditText1.setHint("Αρμονια");
                        specialLessonEditText2.setHint("Ελγχος");
                        specialLessonEditText1.setVisibility(View.VISIBLE);
                        specialLessonEditText2.setVisibility(View.VISIBLE);
                        specialLessonEditText3.setVisibility(View.GONE);
                        break;

                    case 5:
                        specialLessonEditText1.setVisibility(View.VISIBLE);
                        specialLessonEditText2.setVisibility(View.VISIBLE);
                        specialLessonEditText3.setVisibility(View.VISIBLE);
                        break;

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });


    }

    private double calc(int specialLessonType) {
        final EditText mathima1EditText = findViewById(R.id.mathima1_gel);
        final EditText mathima2EditText = findViewById(R.id.mathima2_gel);
        final EditText mathima3EditText = findViewById(R.id.mathima3_gel);
        final EditText mathima4EditText = findViewById(R.id.mathima4_gel);
        final EditText specialLessonEditText1 = findViewById(R.id.gelSpecialLessonEditText1);
        final EditText specialLessonEditText2 = findViewById(R.id.gelSpecialLessonEditText2);
        final EditText specialLessonEditText3 = findViewById(R.id.gelSpecialLessonEditText3);
        try {
            double mathima1 = Double.parseDouble(mathima1EditText.getText().toString());
            double mathima2 = Double.parseDouble(mathima2EditText.getText().toString());
            double mathima3 = Double.parseDouble(mathima3EditText.getText().toString());
            double mathima4 = Double.parseDouble(mathima4EditText.getText().toString());
            switch (specialLessonType) {
                case 0: {
                    return (int) (mathima1 * 250 + mathima2 * 250 + mathima3 * 250 + mathima4 * 250);
                }
                case 1:
                case 2: {
                    double specialLesson1 = Double.parseDouble(specialLessonEditText1.getText().toString());
                    return (int) (mathima1 * 250 + mathima2 * 250 + mathima3 * 250 + mathima4 * 250 + specialLesson1 * 200);
                }
                case 3:
                case 4: {
                    double specialLesson1 = Double.parseDouble(specialLessonEditText1.getText().toString());
                    double specialLesson2 = Double.parseDouble(specialLessonEditText2.getText().toString());
                    return (int) (mathima1 * 250 + mathima2 * 250 + mathima3 * 250 + mathima4 * 250 + specialLesson1 * 100 + specialLesson2 * 100);
                }
                case 5: {
                    double specialLesson1 = Double.parseDouble(specialLessonEditText1.getText().toString());
                    double specialLesson2 = Double.parseDouble(specialLessonEditText2.getText().toString());
                    double specialLesson3 = Double.parseDouble(specialLessonEditText3.getText().toString());
                    return (int) (mathima1 * 250 + mathima2 * 250 + mathima3 * 250 + mathima4 * 250 + ((specialLesson1 + specialLesson2 + specialLesson3) / 3) * 2);
                }
            }
        } catch (NumberFormatException emptyString) {
            return -1;
        }
        return -1;
    }

    private void showGrade(double bathmos) {
        AlertDialog.Builder gradeDialog = new AlertDialog.Builder(this);
        gradeDialog.setMessage(bathmos + "Μόρια")
                .setCancelable(false)
                .setPositiveButton("ok", null);
        gradeDialog.show();
    }

    private void ShowEmptyFieldError() {
        AlertDialog.Builder emptyErrorDialog = new AlertDialog.Builder(this);
        emptyErrorDialog.setMessage("Πρεπει να σημπληρωσεις ολλα τα πεδια")
                .setCancelable(false)
                .setPositiveButton("OK", null);
        emptyErrorDialog.show();
    }
}