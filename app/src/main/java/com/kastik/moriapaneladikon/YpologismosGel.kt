package com.kastik.moriapaneladikon

import android.os.Bundle
import android.view.View
import android.widget.*
import android.widget.AdapterView.OnItemSelectedListener
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class YpologismosGel : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ypologismos_gel_view)
        val calcButton = findViewById<Button>(R.id.calcBtn)
        val specialLessonEditText1 = findViewById<EditText>(R.id.gelSpecialLessonEditText1)
        val specialLessonEditText2 = findViewById<EditText>(R.id.gelSpecialLessonEditText2)
        val specialLessonEditText3 = findViewById<EditText>(R.id.gelSpecialLessonEditText3)
        val dropdown = findViewById<Spinner>(R.id.gelSpecialLessonSpinner)
        val isEpal = this.intent.getBooleanExtra("calcEpal", false)
        calcButton.setOnClickListener {
            val bathmos = calc(dropdown.selectedItemPosition, isEpal)
            if (bathmos != -1.0) {
                showGrade(bathmos)
            } else {
                showEmptyFieldError()
            }
        }
        val adapter = ArrayAdapter.createFromResource(this, R.array.IdikaMathimata, R.layout.custom_spinner)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        dropdown.adapter = adapter
        dropdown.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View, position: Int, l: Long) {
                when (position) {
                    0 -> {
                        specialLessonEditText1.visibility = View.GONE
                        specialLessonEditText2.visibility = View.GONE
                        specialLessonEditText3.visibility = View.GONE
                    }
                    1 -> {
                        specialLessonEditText1.hint = "Ξενες Γλωσσες"
                        specialLessonEditText1.visibility = View.VISIBLE
                        specialLessonEditText2.visibility = View.GONE
                        specialLessonEditText3.visibility = View.GONE
                    }
                    2 -> {
                        specialLessonEditText1.hint = "Ελεφθερω σχεδιο"
                        specialLessonEditText1.visibility = View.VISIBLE
                        specialLessonEditText2.visibility = View.GONE
                        specialLessonEditText3.visibility = View.GONE
                    }
                    3 -> {
                        specialLessonEditText1.hint = "Ελεφθερω σχεδιο"
                        specialLessonEditText2.hint = "Γραμικο σχεδιο"
                        specialLessonEditText1.visibility = View.VISIBLE
                        specialLessonEditText2.visibility = View.VISIBLE
                        specialLessonEditText3.visibility = View.GONE
                    }
                    4 -> {
                        specialLessonEditText1.hint = "Αρμονια"
                        specialLessonEditText2.hint = "Ελγχος"
                        specialLessonEditText1.visibility = View.VISIBLE
                        specialLessonEditText2.visibility = View.VISIBLE
                        specialLessonEditText3.visibility = View.GONE
                    }
                    5 -> {
                        specialLessonEditText1.visibility = View.VISIBLE
                        specialLessonEditText2.visibility = View.VISIBLE
                        specialLessonEditText3.visibility = View.VISIBLE
                    }
                }
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {}
        }
    }

    private fun calc(specialLessonType: Int, isEpal: Boolean): Double {
        val mathima1EditText = findViewById<EditText>(R.id.mathima1Gel)
        val mathima2EditText = findViewById<EditText>(R.id.mathima2Gel)
        val mathima3EditText = findViewById<EditText>(R.id.mathima3Gel)
        val mathima4EditText = findViewById<EditText>(R.id.mathima4Gel)
        val specialLessonEditText1 = findViewById<EditText>(R.id.gelSpecialLessonEditText1)
        val specialLessonEditText2 = findViewById<EditText>(R.id.gelSpecialLessonEditText2)
        val specialLessonEditText3 = findViewById<EditText>(R.id.gelSpecialLessonEditText3)
        try {
            val mathima1 = mathima1EditText.text.toString().toDouble()
            val mathima2 = mathima2EditText.text.toString().toDouble()
            val mathima3 = mathima3EditText.text.toString().toDouble()
            val mathima4 = mathima4EditText.text.toString().toDouble()
            when (specialLessonType) {
                0 -> {
                    return if (isEpal) {
                        ((mathima1 * 150) + (mathima2 * 150) + (mathima3 * 350) + (mathima4 * 350))
                    } else {
                        (mathima1 * 250 + mathima2 * 250 + mathima3 * 250 + mathima4 * 250)
                    }
                }
                1, 2 -> {
                    val specialLesson1 = specialLessonEditText1.text.toString().toDouble()

                    return if (isEpal) {
                        ((mathima1 * 150) + (mathima2 * 150) + (mathima3 * 350) + (mathima4 * 350) + (specialLesson1 * 200))
                    } else {
                        (mathima1 * 250 + mathima2 * 250 + mathima3 * 250 + mathima4 * 250 + specialLesson1 * 200)
                    }
                }
                3, 4 -> {
                    val specialLesson1 = specialLessonEditText1.text.toString().toDouble()
                    val specialLesson2 = specialLessonEditText2.text.toString().toDouble()

                    return if (isEpal) {
                        ((mathima1 * 150) + (mathima2 * 150) + (mathima3 * 350) + (mathima4 * 350) + (specialLesson1 * 100) + (specialLesson2 * 100))
                    } else {
                        ((mathima1 * 250) + (mathima2 * 250) + (mathima3 * 250) + (mathima4 * 250) + (specialLesson1 * 100) + (specialLesson2 * 100))
                    }
                }
                5 -> {
                    val specialLesson1 = specialLessonEditText1.text.toString().toDouble()
                    val specialLesson2 = specialLessonEditText2.text.toString().toDouble()
                    val specialLesson3 = specialLessonEditText3.text.toString().toDouble()

                    return if (isEpal) {
                        ((mathima1 * 150 + mathima2 * 150 + mathima3 * 150 + mathima4 * 150 + (specialLesson1 + specialLesson2 + specialLesson3) / 3 * 2))
                    } else {
                        ((mathima1 * 250 + mathima2 * 250 + mathima3 * 250 + mathima4 * 250 + (specialLesson1 + specialLesson2 + specialLesson3) / 3 * 2))
                    }
                }
            }
        } catch (emptyString: NumberFormatException) {
            return (-1).toDouble()
        }
        return (-1).toDouble()
    }

    private fun showGrade(bathmos: Double) {
        val gradeDialog = AlertDialog.Builder(this)
        gradeDialog.setMessage(bathmos.toString() + "Μόρια")
                .setCancelable(false)
                .setPositiveButton("ok", null)
        gradeDialog.show()
    }

    private fun showEmptyFieldError() {
        val emptyErrorDialog = AlertDialog.Builder(this)
        emptyErrorDialog.setMessage("Πρεπει να σημπληρωσεις ολλα τα πεδια")
                .setCancelable(false)
                .setPositiveButton("OK", null)
        emptyErrorDialog.show()
    }
}