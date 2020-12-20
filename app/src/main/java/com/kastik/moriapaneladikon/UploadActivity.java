package com.kastik.moriapaneladikon;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Objects;

public class UploadActivity extends AppCompatActivity {

    final String IdtoIdikotitaReference = "/IdikotitesAnaId";
    final String EpalDataReference = "/EpalData";
    final String GelDataReference = "/GelData";
    final String BaseisData = "/Themata";
    /*########################################################
    ## Change this value to set Different path in Firestore ##
    ########################################################*/
    final String firestorePath = "Baseis/2020/Epal_2019_10%";
    final FieldPath schoolIdPath = FieldPath.of("schoolId");
    final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference(IdtoIdikotitaReference);
    final CollectionReference firestoreRefrence = FirebaseFirestore.getInstance().collection(firestorePath);
    DataSnapshot databaseSnapshot;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upload_layout);

        final Button startGelUploadButton = findViewById(R.id.start_up_gel_button);
        startGelUploadButton.setOnClickListener(v -> {
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference(GelDataReference);
            databaseReference.addValueEventListener(new ValueEventListener() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot child : snapshot.getChildren()) {
                        BaseisModel model = GetGelModel(child);
                        Log.d("MyLog", "done constructing model");
                        firestoreRefrence.add(model)
                                .addOnSuccessListener(documentReference ->
                                        Log.d("MyLog", "done uploading model " + child.getKey()))
                                .addOnFailureListener(e ->
                                        Log.d("MyLog", "######Failed With Exception " + e.getMessage()));
                    }
                    Log.d("MyLog", "################## Finished Uploading Of All Models##################");
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.d("MyLog", error.getDetails() + error.getMessage());
                }
            });
        });

        final Button startEpalUpoloadButton = findViewById(R.id.start_epal_upload_button);
        startEpalUpoloadButton.setOnClickListener(v -> {
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference(EpalDataReference);
            databaseReference.addValueEventListener(new ValueEventListener() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot child : snapshot.getChildren()) {
                        BaseisModel model = GetEpalModel(child);
                        Log.d("MyLog", "done constructing model");
                        firestoreRefrence.add(model).addOnCompleteListener(task ->
                                Log.d("MyLog", "done uploading model " + child.getKey()));
                    }
                    Log.d("MyLog", "################## Finished Uploading Of All Models##################");
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.d("MyLog", error.getDetails() + error.getMessage());
                }
            });
        });

        final Button startIdikotitesUpoload = findViewById(R.id.start_idikotita_upoload_button);
        startIdikotitesUpoload.setOnClickListener(v -> {


            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Log.d("MyLog", "Finished Getting realtimeDatabase Data");
                    databaseSnapshot = snapshot;
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            }); //GET SNAPSHOT
            firestoreRefrence.get().addOnCompleteListener(task -> {
                Log.d("MyLog", "Finished Getting Firestore Data");
                for (QueryDocumentSnapshot firestoreSnapshot : task.getResult()) {
                    String firestoreSchoolId = String.valueOf(firestoreSnapshot.get(schoolIdPath));
                    Log.d("MyLog", "firestoreSchoolId " + firestoreSchoolId);
                    for (DataSnapshot child : databaseSnapshot.getChildren()) {
                        String databaseSchoolId = String.valueOf(child.child("id").getValue());
                        ArrayList<String> databaseSector = IdikotitaToArrayList(String.valueOf(child.child("sector").getValue()));
                        Log.d("MyLog", "databaseSchoolId " + databaseSchoolId);
                        Log.d("Mylog", "databaseSector " + databaseSector);
                        if (databaseSchoolId.equals(firestoreSchoolId)) {
                            if (databaseSector.get(0).equals("null") || databaseSector == null) {
                                firestoreSnapshot.getReference().update("Idikotita", databaseSector).
                                        addOnCompleteListener(task1 -> Log.d("MyLog", "Finished Updating temp " + databaseSnapshot.getKey()));
                            }
                        }
                    }
                }
            });
        });

        final Button startThemataUpload = findViewById(R.id.themataUpload);
        CollectionReference paok = FirebaseFirestore.getInstance().collection("Themata");
        startThemataUpload.setOnClickListener(v -> {
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference(BaseisData);
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot child : snapshot.getChildren()) {
                        Log.d("MyLog", "Started With child1" + child.getKey());
                        for (DataSnapshot child2 : child.getChildren()) {
                            Log.d("MyLog", "Started With child2" + child2.getKey());
                            for (DataSnapshot child3 : child2.getChildren()) {
                                Log.d("MyLog", "Started With child3" + child3.getKey());
                                String extension = child3.child("FileExtension").getValue().toString();
                                String path = child3.child("Link").getValue().toString();
                                String LessonName = child3.child("LessonName").getValue().toString();
                                String SchoolType = child3.child("SchoolType").getValue().toString();
                                String Year = child3.child("Year").getValue().toString();
                                paok.document(child.getKey()).collection(child2.getKey()).add(
                                        new ThemataModel(Year, LessonName, SchoolType, extension, path)).addOnCompleteListener(task -> Log.d("MyLog", "Finished Uploading child1 " + child.getKey() + "child2 " + child2.getKey()));
                            }
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        });

        final Button createStorageTemplateButton = findViewById(R.id.storageTemplateButton);
        CollectionReference ref = FirebaseFirestore.getInstance().collection("Themata").document("2019").collection("Gel_Imerisia");
        createStorageTemplateButton.setOnClickListener(v -> {
            Log.d("MyLog", "Mpike onClick");
            StorageReference storageReference = FirebaseStorage.getInstance().getReference("/2019/Gel_Esperina");
            storageReference.listAll().addOnSuccessListener(listResult -> {
                Log.d("MyLog", "teliose listAll()");
                for (StorageReference item : listResult.getPrefixes()) {
                    Log.d("MyLog", item.toString());
                    ref.document(item.getName()).update(
                            "fileExtension", String.valueOf(item)
                            , "lessonName", item.getName().replace("gs://ypologismosmorion.appspot.com/", ""),
                            "link", item.child("link").getName(),
                            "schoolType", item.child("schoolType").getName(),
                            "year", item.child("year").getName()
                    );
                }
            }).addOnFailureListener(e -> Log.d("MyLog", e.getMessage()));
        });
    }

    private ArrayList<Double> StringToKritiriaIsobathmiasArrayList(@NotNull String string) {
        string = string.replace("paok", "");
        string = string.replace("/", "");
        string = string.replace(",", ".");
        StringBuilder temp = new StringBuilder();
        ArrayList<Double> finalValue = new ArrayList<>();
        if (!string.equals(temp.toString())) {
            for (int i = 0; i < string.length() + 1; i++) {
                if (i < string.length()) {
                    if (string.charAt(i) != ' ') {
                        temp.append(string.charAt(i));
                    } else {
                        finalValue.add(Double.parseDouble(temp.toString()));
                        temp = new StringBuilder();
                    }
                } else {
                    finalValue.add(Double.parseDouble(temp.toString()));
                    temp = new StringBuilder();
                }
            }
        } else {
            finalValue = null;
        }
        return finalValue;
    }

    private int StringToInt(@NotNull String string) {
        string = string.replace("paok", "");
        if (!string.equals("")) {
            return Integer.parseInt(string);
        } else {
            return -1;
        }
    }

    private @NotNull ArrayList<Integer> PediaToArrayList(String string) {
        string = string.replace("paok", "");
        ArrayList<Integer> Pedio = new ArrayList<>();
        string = string.replace("/", "");
        for (int i = 0; i < string.length(); i++) {
            Pedio.add(Integer.parseInt(String.valueOf(string.charAt(i))));
        }
        return Pedio;
    }

    private @NotNull BaseisModel GetGelModel(@NotNull DataSnapshot child) {
        Log.d("MyLog", "Started with " + child.getKey());
        int ArxikesThesis = StringToInt(child.child("ArxikesThesis").getValue().toString());
        int Epitixontes = StringToInt(child.child("Epitixontes").getValue().toString());
        String Idrima = child.child("Idrima").getValue().toString();

        ArrayList<Integer> Pedio = PediaToArrayList(child.child("Pedio").getValue().toString());


        ArrayList<Double> kritiria_isobathmias_protou = StringToKritiriaIsobathmiasArrayList(child.child("KritiriaIsobathmiasProtou").getValue().toString());
        ArrayList<Double> kritiria_isobathmias_telefteou = StringToKritiriaIsobathmiasArrayList(child.child("KritiriaIsobathmiasTelefteou").getValue().toString());

        int SchoolId = StringToInt(Objects.requireNonNull(child.child("SchoolId").getValue()).toString());
        String SchoolName = Objects.requireNonNull(child.child("SchoolName").getValue()).toString();
        int ThesisKatopinMetaforas = StringToInt(Objects.requireNonNull(child.child("ThesisKatopinMetaforas").getValue()).toString());
        String Type = Objects.requireNonNull(child.child("Type").getValue()).toString();
        int moria_protou = StringToInt(Objects.requireNonNull(child.child("MoriaProtou").getValue()).toString().replace(",", ""));
        int moria_telefteou = StringToInt(Objects.requireNonNull(child.child("MoriaTelefteou").getValue()).toString().replace(",", ""));
        Log.d("MyLog", "done getting model info");
        return new BaseisModel(SchoolId, Idrima, SchoolName, Type, Pedio, ArxikesThesis, ThesisKatopinMetaforas, Epitixontes, moria_protou, kritiria_isobathmias_protou, moria_telefteou, kritiria_isobathmias_telefteou);
    }

    private @NotNull BaseisModel GetEpalModel(@NotNull DataSnapshot child) {
        Log.d("MyLog", "Started with " + child.getKey());
        int ArxikesThesis = StringToInt(Objects.requireNonNull(child.child("ArxikesThesis").getValue()).toString());
        int Epitixontes = StringToInt(child.child("Epitixontes").getValue().toString());
        String Idrima = Objects.requireNonNull(child.child("Idrima").getValue()).toString();

        ArrayList<Double> kritiria_isobathmias_protou = StringToKritiriaIsobathmiasArrayList(child.child("KritiriaIsobathmiasProtou").getValue().toString());
        ArrayList<Double> kritiria_isobathmias_telefteou = StringToKritiriaIsobathmiasArrayList(child.child("KritiriaIsobathmiasTelefteou").getValue().toString());

        int SchoolId = StringToInt(Objects.requireNonNull(child.child("SchoolId").getValue()).toString());
        String SchoolName = Objects.requireNonNull(child.child("SchoolName").getValue()).toString();
        int ThesisKatopinMetaforas = StringToInt(Objects.requireNonNull(child.child("ThesisKatopinMetaforas").getValue()).toString());

        String Type = Objects.requireNonNull(child.child("Type").getValue()).toString();
        int moria_protou = StringToInt(Objects.requireNonNull(child.child("MoriaProtou").getValue()).toString().replace(",", ""));
        int moria_telefteou = StringToInt(Objects.requireNonNull(child.child("MoriaTelefteou").getValue()).toString().replace(",", ""));
        Log.d("MyLog", "done getting model info");
        return new BaseisModel(SchoolId, Idrima, SchoolName, Type, ArxikesThesis, ThesisKatopinMetaforas, Epitixontes, moria_protou, kritiria_isobathmias_protou, moria_telefteou, kritiria_isobathmias_telefteou);
    }

    private ArrayList<String> IdikotitaToArrayList(String idikotita) {
        idikotita = idikotita.replace(" ", "");
        idikotita = idikotita.replace(",", " ");
        idikotita = idikotita.replace("  ", " ");
        idikotita = idikotita.replace("ΟΙΚΟΝΟΜΙΚΩΝ", "ikonomias");
        idikotita = idikotita.replace("ΥΓΕΙΑΣ", "igias");
        idikotita = idikotita.replace("ΚΟΙΝΗΣΟΜΑΔΑΣ", "kina");
        idikotita = idikotita.replace("ΤΕΧΝΩΝ", "texnis");
        idikotita = idikotita.replace("ΗΛΕΚΤΡΟΛΟΓΙΑΣ", "ilektroniki");
        idikotita = idikotita.replace("ΠΛΗΡΟΦΟΡΙΚΗ", "pliroforiki");
        idikotita = idikotita.replace("ΜΗΧΑΝΟΛΟΓΙΑΣ", "mixaniki");
        idikotita = idikotita.replace("ΝΑΥΤΙΛΙΑΚΩΝ", "naftiliaki");
        idikotita = idikotita.replace("ΔΟΜΙΚΩΝ", "domikon");
        idikotita = idikotita.replace("ΓΕΩΠΟΝΙΑΣ", "geoponias");
        StringBuilder temp = new StringBuilder();
        ArrayList<String> finalValue = new ArrayList<>();
        if (!idikotita.equals(temp.toString())) {
            for (int i = 0; i < idikotita.length() + 1; i++) {
                if (i < idikotita.length()) {
                    if (idikotita.charAt(i) != ' ') {
                        temp.append(idikotita.charAt(i));
                    } else {
                        finalValue.add(temp.toString());
                        temp = new StringBuilder();
                    }
                }
            }
        } else {
            finalValue = null;
        }
        return finalValue;
    }

}
