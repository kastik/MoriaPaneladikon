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

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Objects;

public class UploadActivity extends AppCompatActivity {
    final String IdtoIdikotitaReference = "/IdikotitesAnaId";
    final String EpalDataReference = "/EpalData";
    final String GelDataReference = "/GelData";
    /*########################################################
    ## Change this value to set Different path in Firestore ##
    ########################################################*/
    String firestorePath = "Baseis/2020/Test";
    CollectionReference firestoreRefrence = FirebaseFirestore.getInstance().collection(firestorePath);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upload_gel);

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
            CollectionReference firestoreRefrence = FirebaseFirestore.getInstance().collection(firestorePath);
            firestoreRefrence.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Log.d("MyLog", "Finished Getting Firestore data");
                    for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference(IdtoIdikotitaReference);
                        FieldPath fieldPath = FieldPath.of("schoolId");
                        String firestoreId = String.valueOf(documentSnapshot.get(fieldPath));
                        Log.d("MyLog", "firestoreId " + firestoreId);
                        databaseReference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                Log.d("MyLog", "Finished Getting realtimeDatabase Data");
                                for (DataSnapshot child : snapshot.getChildren()) {
                                    String databaseId = String.valueOf(child.child("id").getValue());
                                    ArrayList<String> databaseSector = IdikotitaToArrayList(String.valueOf(child.child("sector").getValue()));
                                    Log.d("MyLog", "databaseId " + databaseId);
                                    Log.d("Mylog", "databaseSector " + databaseSector);
                                    if (databaseId.equals(firestoreId)) {
                                        if (databaseSector != null) {
                                            documentSnapshot.getReference().update("Idikotita", databaseSector);
                                            Log.d("MyLog", "Finished Updating temp " + snapshot.getKey());
                                        }
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                }
            });
        });
    }

    private ArrayList<Double> StringToKritiriaIsobathmiasArrayList(@NotNull String string) {
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
        if (!string.equals("")) {
            return Integer.parseInt(string);
        } else {
            return -1;
        }
    }

    private @NotNull
    ArrayList<Integer> PediaToArrayList(String string) {
        ArrayList<Integer> Pedio = new ArrayList<>();
        string = string.replace("/", "");
        for (int i = 0; i < string.length(); i++) {
            Pedio.add(Integer.parseInt(String.valueOf(string.charAt(i))));
        }
        return Pedio;
    }

    private @NotNull
    BaseisModel GetGelModel(@NotNull DataSnapshot child) {
        Log.d("MyLog", "Started with " + child.getKey());
        int ArxikesThesis = StringToInt(Objects.requireNonNull(child.child("ArxikesThesis").getValue()).toString());
        int Epitixontes = StringToInt(Objects.requireNonNull(child.child("Epitixontes").getValue()).toString());
        String Idrima = Objects.requireNonNull(child.child("Idrima").getValue()).toString();

        ArrayList<Integer> Pedio;
        String PedioTemp = Objects.requireNonNull(child.child("Pedio").getValue()).toString();
        Pedio = PediaToArrayList(PedioTemp);


        ArrayList<Double> kritiria_isobathmias_protou;
        String kritiria_isobathmias_protou_temp = Objects.requireNonNull(child.child("KritiriaIsobathmiasProtou").getValue()).toString();
        kritiria_isobathmias_protou_temp = kritiria_isobathmias_protou_temp.replace("/", "");
        kritiria_isobathmias_protou_temp = kritiria_isobathmias_protou_temp.replace(",", ".");
        kritiria_isobathmias_protou = StringToKritiriaIsobathmiasArrayList(kritiria_isobathmias_protou_temp);

        ArrayList<Double> kritiria_isobathmias_telefteou;
        String kritiria_isobathmias_telefteou_temp = Objects.requireNonNull(child.child("KritiriaIsobathmiasTelefteou").getValue()).toString();
        kritiria_isobathmias_telefteou_temp = kritiria_isobathmias_telefteou_temp.replace("/", "");
        kritiria_isobathmias_telefteou_temp = kritiria_isobathmias_telefteou_temp.replace(",", ".");
        kritiria_isobathmias_telefteou = StringToKritiriaIsobathmiasArrayList(kritiria_isobathmias_telefteou_temp);


        int SchoolId = StringToInt(Objects.requireNonNull(child.child("SchoolId").getValue()).toString());
        String SchoolName = Objects.requireNonNull(child.child("SchoolName").getValue()).toString();
        int ThesisKatopinMetaforas = StringToInt(Objects.requireNonNull(child.child("ThesisKatopinMetaforas").getValue()).toString());
        String Type = Objects.requireNonNull(child.child("Type").getValue()).toString();
        int moria_protou = StringToInt(Objects.requireNonNull(child.child("MoriaProtou").getValue()).toString().replace(",", ""));
        int moria_telefteou = StringToInt(Objects.requireNonNull(child.child("MoriaTelefteou").getValue()).toString().replace(",", ""));
        Log.d("MyLog", "done getting model info");
        return new BaseisModel(SchoolId, Idrima, SchoolName, Type, Pedio, ArxikesThesis, ThesisKatopinMetaforas, Epitixontes, moria_protou, kritiria_isobathmias_protou, moria_telefteou, kritiria_isobathmias_telefteou);
    }

    private @NotNull
    BaseisModel GetEpalModel(@NotNull DataSnapshot child) {
        Log.d("MyLog", "Started with " + child.getKey());
        int ArxikesThesis = StringToInt(Objects.requireNonNull(child.child("ArxikesThesis").getValue()).toString());
        int Epitixontes = StringToInt(Objects.requireNonNull(child.child("Epitixontes").getValue()).toString());
        String Idrima = Objects.requireNonNull(child.child("Idrima").getValue()).toString();

        ArrayList<Double> kritiria_isobathmias_protou;
        String kritiria_isobathmias_protou_temp = Objects.requireNonNull(child.child("KritiriaIsobathmiasProtou").getValue()).toString();
        kritiria_isobathmias_protou_temp = kritiria_isobathmias_protou_temp.replace("/", "");
        kritiria_isobathmias_protou_temp = kritiria_isobathmias_protou_temp.replace(",", ".");
        kritiria_isobathmias_protou = StringToKritiriaIsobathmiasArrayList(kritiria_isobathmias_protou_temp);

        ArrayList<Double> kritiria_isobathmias_telefteou;
        String kritiria_isobathmias_telefteou_temp = Objects.requireNonNull(child.child("KritiriaIsobatmiasTelefteou").getValue()).toString();
        kritiria_isobathmias_telefteou_temp = kritiria_isobathmias_telefteou_temp.replace("/", "");
        kritiria_isobathmias_telefteou_temp = kritiria_isobathmias_telefteou_temp.replace(",", ".");
        kritiria_isobathmias_telefteou = StringToKritiriaIsobathmiasArrayList(kritiria_isobathmias_telefteou_temp);

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
