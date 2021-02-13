package com.kastik.moriapaneladikon

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.Task
import com.google.firebase.database.*
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ListResult
import java.util.*

class UploadActivity : AppCompatActivity() {
    val mylog: String = "MyLog "
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.upload_layout)

        val startGelUploadButton = findViewById<Button>(R.id.start_up_gel_button)
        startGelUploadButton.setOnClickListener { baseisUpload("Gel") }

        val startEpalUpoloadButton = findViewById<Button>(R.id.start_epal_upload_button)
        startEpalUpoloadButton.setOnClickListener { baseisUpload("Epal") }

        val startAlogenisUploadButton = findViewById<Button>(R.id.startAlogenisUploadButton)
        startAlogenisUploadButton.setOnClickListener { baseisUpload("Alogenis") }

        val startIdikotitesUpoload = findViewById<Button>(R.id.start_idikotita_upoload_button)
        startIdikotitesUpoload.setOnClickListener {
            idikotitesUpload()
        }
        val startThemataUpload = findViewById<Button>(R.id.themataUpload)
        val paok = FirebaseFirestore.getInstance().collection("Themata")
        startThemataUpload.setOnClickListener {
            val databaseReference = FirebaseDatabase.getInstance().getReference("/Themata")
            databaseReference.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (child in snapshot.children) {
                        Log.d(mylog, "Started With child1" + child.key)
                        for (child2 in child.children) {

                            Log.d(mylog, "Started With child2" + child2.key)
                            for (child3 in child2.children) {
                                Log.d(mylog, "Started With child3" + child3.key)
                                val extension = child3.child("FileExtension").value.toString()
                                val path = child3.child("Link").value.toString()
                                val lessonName = child3.child("LessonName").value.toString()
                                val schoolType = child3.child("SchoolType").value.toString()
                                val year = child3.child("Year").value.toString()
                                paok.document(child.key!!).collection(child2.key!!).add(
                                        ThemataModel(year, lessonName, schoolType, extension, path)).addOnCompleteListener { Log.d(mylog, "Finished Uploading child1 " + child.key + "child2 " + child2.key) }
                            }
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {}
            })
        }

        val startPediaUpload = findViewById<Button>(R.id.uploadPedia)
        startPediaUpload.setOnClickListener {
            pediaUpload()
        }


        val createStorageTemplateButton = findViewById<Button>(R.id.storageTemplateButton)
        val ref = FirebaseFirestore.getInstance().collection("Themata").document("2019").collection("Gel_Imerisia")
        createStorageTemplateButton.setOnClickListener {
            Log.d(mylog, "Mpike onClick")
            val storageReference = FirebaseStorage.getInstance().getReference("/2019/Gel_Esperina")
            storageReference.listAll().addOnSuccessListener { listResult: ListResult ->
                Log.d(mylog, "teliose listAll()")
                for (item in listResult.prefixes) {
                    Log.d(mylog, item.toString())
                    ref.document(item.name).update(
                            "fileExtension", item.toString(), "lessonName", item.name.replace("gs://ypologismosmorion.appspot.com/", ""),
                            "link", item.child("link").name,
                            "schoolType", item.child("schoolType").name,
                            "year", item.child("year").name
                    )
                }
            }.addOnFailureListener { e: Exception -> Log.d(mylog, e.message!!) }
        }
    }

    private fun baseisUpload(SchoolType: String) {
        val firestoreRefrence: CollectionReference
        val databaseReference: DatabaseReference
        val isEpal: Boolean
        if (SchoolType == "Epal") {
            isEpal = true
            firestoreRefrence = FirebaseFirestore.getInstance().collection("Baseis")
            databaseReference = FirebaseDatabase.getInstance().getReference("/EpalData")
        } else {
            isEpal = false
            firestoreRefrence = FirebaseFirestore.getInstance().collection("Baseis")
            databaseReference = FirebaseDatabase.getInstance().getReference("/GelData")
        }
        databaseReference.addValueEventListener(object : ValueEventListener {
            @RequiresApi(api = Build.VERSION_CODES.N)
            override fun onDataChange(childTop: DataSnapshot) {
                for (childMid in childTop.children) {
                    for (modelChild in childMid.children) {
                        val model: BaseisModel = if (isEpal) {
                            getEpalModel(modelChild)
                        } else {
                            getGelModel(modelChild)
                        }
                        Log.d(mylog, "Done Constructing Model for ${modelChild.key}")
                        firestoreRefrence.document("2019").collection(childMid.key!!).document(model.schoolId.toString()).set(model)
                                .addOnCompleteListener { Log.d(mylog, "Done Uploading Model ${modelChild.key}") }
                                .addOnFailureListener { e: Exception -> Log.d(mylog, "######Failed With Exception " + e.message) }
                    }
                    Log.d(mylog, "################## Finished Uploading of ${childMid.key} ##################")
                }
                Log.d(mylog, "################## Finished Uploading Of All Models ##################")
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d(mylog, error.details + error.message)
            }
        })
    }

    private fun stringToKritiriaIsobathmiasArrayList(stringVal: String): ArrayList<Double> {
        var string = stringVal
        string = string.replace("paok", "")
        string = string.replace("/", "")
        string = string.replace(",", ".")
        if (string == "null") {
            val paok = ArrayList<Double>()
            paok.add(0.0)
            return paok
        }
        var temp = StringBuilder()
        val finalValue = ArrayList<Double>()
        if (string != temp.toString()) {
            for (i in 0 until string.length + 1) {
                if (i < string.length) {
                    if (string[i] != ' ') {
                        temp.append(string[i])
                    } else {
                        finalValue.add(temp.toString().toDouble())
                        temp = StringBuilder()
                    }
                } else {
                    finalValue.add(temp.toString().toDouble())
                    temp = StringBuilder()
                }
            }
        }
        return finalValue
    }

    private fun stringToInt(stringVal: String): Int {
        var string = stringVal
        string = string.replace("paok", "")
        return if (string != "" && string != "null") {
            string.toInt()
        } else {
            -1
        }
    }

    private fun pediaToArrayList(stringVal: String): ArrayList<Int> {
        var string = stringVal
        string = string.replace("paok", "")
        string = string.replace("/", "")
        string = string.replace(",", " ")
        var pedio = ArrayList<Int>()
        for (i in 0..string.length - 1) {
            if (!string[i].equals(' ')) {
                pedio.add(string[i].toString().toInt())
            }
        }
        return pedio
    }

    private fun getGelModel(child: DataSnapshot): BaseisModel {
        Log.d(mylog, "Started with " + child.key)

        return getGenericData(child, "Gel")
    }

    private fun getEpalModel(child: DataSnapshot): BaseisModel {
        return getGenericData(child, "Epal")
    }

    private fun getGenericData(child: DataSnapshot, schoolType: String): BaseisModel {
        val arxikesThesis = stringToInt(child.child("ArxikesThesis").value.toString())
        val epitixontes = stringToInt(child.child("Epitixontes").value.toString())
        val idrima = child.child("Idrima").value.toString()
        val kritiriaIsobathmiasProtou = stringToKritiriaIsobathmiasArrayList(child.child("KritiriaIsobathmiasProtou").value.toString())
        val kritiriaIsobathmiasTelefteou = stringToKritiriaIsobathmiasArrayList(child.child("KritiriaIsobathmiasTelefteou").value.toString())
        val schoolId = stringToInt(Objects.requireNonNull(child.child("SchoolId").value).toString())
        val schoolName = Objects.requireNonNull(child.child("SchoolName").value).toString()
        val thesisKatopinMetaforas = stringToInt(Objects.requireNonNull(child.child("ThesisKatopinMetaforas").value).toString())
        val type = Objects.requireNonNull(child.child("Type").value).toString()
        val moriaProtou = stringToInt(Objects.requireNonNull(child.child("MoriaProtou").value.toString()).replace(",", ""))
        val moriaTelefteou = stringToInt(Objects.requireNonNull(child.child("MoriaTelefteou").value.toString()).replace(",", ""))
        Log.d(mylog, "Done Getting ${child.key} Model Info")
        return if (schoolType == "Epal") {
            BaseisModel(schoolId, idrima, schoolName, type, arxikesThesis, thesisKatopinMetaforas, epitixontes, moriaProtou, kritiriaIsobathmiasProtou, moriaTelefteou, kritiriaIsobathmiasTelefteou, "Epal")
        } else {

            //This is used in 2020+ only val pedio = pediaToArrayList(child.child("Pedio").toString())
            BaseisModel(schoolId, idrima, schoolName, type, arxikesThesis, thesisKatopinMetaforas, epitixontes, moriaProtou, kritiriaIsobathmiasProtou, moriaTelefteou, kritiriaIsobathmiasTelefteou, "Gel")
        }
    }

    private fun IdikotitaToArrayList(idikotita: String): ArrayList<String>? {
        var idikotita = replaceIdikotitaChars(idikotita)
        var temp = java.lang.StringBuilder()
        var finalValue: ArrayList<String>? = ArrayList()
        if (idikotita != temp.toString()) {
            for (i in 0 until idikotita.length + 1) {
                if (i < idikotita.length) {
                    if (idikotita[i] != ' ') {
                        temp.append(idikotita[i])
                    } else {
                        finalValue!!.add(temp.toString())
                        temp = java.lang.StringBuilder()
                    }
                }
            }
        } else {
            finalValue = null
        }
        return finalValue
    }

    private fun replaceIdikotitaChars(string: String): String {
        var idikotita = string
        idikotita = idikotita.replace(" ", "")
        idikotita = idikotita.replace(",", " ")
        idikotita = idikotita.replace("  ", " ")
        idikotita = idikotita.replace("ΟΙΚΟΝΟΜΙΚΩΝ", "ikonomias")
        idikotita = idikotita.replace("ΥΓΕΙΑΣ", "igias")
        idikotita = idikotita.replace("ΚΟΙΝΗΣΟΜΑΔΑΣ", "kina")
        idikotita = idikotita.replace("ΤΕΧΝΩΝ", "texnis")
        idikotita = idikotita.replace("ΗΛΕΚΤΡΟΛΟΓΙΑΣ", "ilektroniki")
        idikotita = idikotita.replace("ΠΛΗΡΟΦΟΡΙΚΗ", "pliroforiki")
        idikotita = idikotita.replace("ΜΗΧΑΝΟΛΟΓΙΑΣ", "mixaniki")
        idikotita = idikotita.replace("ΝΑΥΤΙΛΙΑΚΩΝ", "naftiliaki")
        idikotita = idikotita.replace("ΔΟΜΙΚΩΝ", "domikon")
        idikotita = idikotita.replace("ΓΕΩΠΟΝΙΑΣ", "geoponias")
        return idikotita
    }

    private fun idikotitesUpload() {
        val firestoreRefrence = FirebaseFirestore.getInstance().collection("Baseis").document("2019").collection("Epal_Im_2018_10%")
        val databaseRefrence = FirebaseDatabase.getInstance().getReference("/IdikotitesAnaId")
        databaseRefrence.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                firestoreRefrence.get().addOnCompleteListener { task: Task<QuerySnapshot> ->
                    domorestuff(task, snapshot)
                }
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }

    private fun pediaUpload() {
        val firestoreRefrence = FirebaseFirestore.getInstance().collection("Baseis").document("2019").collection("Gel_Im_2018_10%")
        val databaseRefrence = FirebaseDatabase.getInstance().getReference("/IdikotitesAnaId")
        databaseRefrence.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                firestoreRefrence.get().addOnCompleteListener { task: Task<QuerySnapshot> ->
                    pediaSearch(task, snapshot)
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

    private fun domorestuff(task: Task<QuerySnapshot>, snapshot: DataSnapshot) {
        Log.d(mylog, "Finished Getting Firestore Data")
        for (firestoreSnapshot in task.result) {

            val firestoreSchoolId = firestoreSnapshot.id
            for (child in snapshot.children) {
                val databaseSchoolId = child.child("id").value.toString()
                val databaseSector = IdikotitaToArrayList(child.child("sector").value.toString())
                if (databaseSchoolId == firestoreSchoolId) {
                    if (databaseSector!!.isNotEmpty() && databaseSector[0] != "null") {
                        firestoreSnapshot.reference.update("Idikotita", databaseSector).addOnCompleteListener {
                            Log.d(mylog, "Finished Updating temp ${child.child("id").value.toString()} ${firestoreSnapshot.id} ############################")
                        }
                    } else {
                        Log.d(mylog, "##############Failed to upload ${child.child("id").value.toString()}and ${firestoreSnapshot.id}")
                    }
                }
            }
        }
    }

    private fun pediaSearch(task: Task<QuerySnapshot>, snapshot: DataSnapshot) {
        Log.d(mylog, "Finished Getting Firestore Data")
        for (firestoreSnapshot in task.result) {
            val firestoreSchoolId = firestoreSnapshot.id
            for (child in snapshot.children) {
                val databaseSchoolId = child.child("id").value.toString()
                val databasePedio = pediaToArrayList(child.child("field").value.toString())
                if (databaseSchoolId == firestoreSchoolId) {
                    firestoreSnapshot.reference.update("Pedio", FieldValue.delete())
                    firestoreSnapshot.reference.update("pedio", databasePedio).addOnCompleteListener {
                        Log.d(mylog, "Finished Updating temp ${child.child("id").value.toString()} ${firestoreSnapshot.id} ############################")
                    }.addOnFailureListener { e ->
                        Log.d(mylog, "##############Failed to upload ${child.child("id").value.toString()}and ${firestoreSnapshot.id} ${e.message}")
                    }
                }
            }
        }
    }


}