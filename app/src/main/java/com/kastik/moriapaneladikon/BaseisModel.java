package com.kastik.moriapaneladikon;

import java.util.ArrayList;

@SuppressWarnings("unused")
public class BaseisModel {
    private ArrayList<Double> KritiriaIsobathmiasProtou, KritiriaIsobathmiasTelefteou;
    private ArrayList<Integer> Pedio;
    private int SchoolId, ArxikesThesis, ThesisKatopinMetaforas, Epitixontes, MoriaProtou, MoriaTelefteou, spProtou, spTelefteou;
    private String Idrima, SchoolName, Type, SchoolType;

    public BaseisModel() {
    }

    //Epal
    public BaseisModel(int SchoolId, String Idrima, String SchoolName, String Type, int ArxikesThesis, int ThesisKatopinMetaforas, int Epitixontes, int MoriaProtou, ArrayList<Double> KritiriaIsobathmiasProtou, int MoriaTelefteou, ArrayList<Double> KritiriaIsobathmiasTelefteou, String schoolType) {
        this.SchoolId = SchoolId;
        this.Idrima = Idrima;
        this.SchoolName = SchoolName;
        this.Type = Type;
        this.ArxikesThesis = ArxikesThesis;
        this.ThesisKatopinMetaforas = ThesisKatopinMetaforas;
        this.Epitixontes = Epitixontes;
        this.MoriaProtou = MoriaProtou;
        this.KritiriaIsobathmiasProtou = KritiriaIsobathmiasProtou;
        this.MoriaTelefteou = MoriaTelefteou;
        this.KritiriaIsobathmiasTelefteou = KritiriaIsobathmiasTelefteou;
        this.SchoolType = schoolType;
    }

    //Gel
    public BaseisModel(int SchoolId, String Idrima, String SchoolName, String Type, ArrayList<Integer> Pedio, int ArxikesThesis, int ThesisKatopinMetaforas, int Epitixontes, int MoriaProtou, ArrayList<Double> KritiriaIsobathmiasProtou, int MoriaTelefteou, ArrayList<Double> KritiriaIsobathmiasTelefteou, String schoolType) {
        this.SchoolId = SchoolId;
        this.Idrima = Idrima;
        this.SchoolName = SchoolName;
        this.Type = Type;
        this.Pedio = Pedio;
        this.ArxikesThesis = ArxikesThesis;
        this.ThesisKatopinMetaforas = ThesisKatopinMetaforas;
        this.Epitixontes = Epitixontes;
        this.MoriaProtou = MoriaProtou;
        this.KritiriaIsobathmiasProtou = KritiriaIsobathmiasProtou;
        this.MoriaTelefteou = MoriaTelefteou;
        this.KritiriaIsobathmiasTelefteou = KritiriaIsobathmiasTelefteou;
        this.SchoolType = schoolType;
    }

    // Alogenis, MoriaProtou-Telefteou == bathmosApolitiriou
    public BaseisModel(String Idrima, String SchoolName, String Type, int ArxikesThesis, int Epitixontes, int MoriaProtou, int MoriaTelefteou, int spProtou, int spTelefteou) {
        this.Idrima = Idrima;
        this.SchoolName = SchoolName;
        this.Type = Type;
        this.ArxikesThesis = ArxikesThesis;
        this.Epitixontes = Epitixontes;
        this.MoriaProtou = MoriaProtou;
        this.MoriaTelefteou = MoriaTelefteou;
        this.spProtou = spProtou;
        this.spTelefteou = spTelefteou;
        SchoolType = "Alogenis";
    }

    public ArrayList<Integer> getPedio() {
        return Pedio;
    }

    public void setPedio(ArrayList<Integer> Pedio) {
        this.Pedio = Pedio;
    }

    public int getSchoolId() {
        return SchoolId;
    }

    public void setSchoolId(int SchoolId) {
        this.SchoolId = SchoolId;
    }

    public String getIdrima() {
        return Idrima;
    }

    public void setIdrima(String Idrima) {
        this.Idrima = Idrima;
    }

    public String getSchoolName() {
        return SchoolName;
    }

    public void setSchoolName(String SchoolName) {
        this.SchoolName = SchoolName;
    }

    public String getType() {
        return Type;
    }

    public void setType(String Type) {
        this.Type = Type;
    }

    public int getArxikesThesis() {
        return ArxikesThesis;
    }

    public void setArxikesThesis(int ArxikesThesis) {
        this.ArxikesThesis = ArxikesThesis;
    }

    public int getThesisKatopinMetaforas() {
        return ThesisKatopinMetaforas;
    }

    public void setThesisKatopinMetaforas(int ThesisKatopinMetaforas) {
        this.ThesisKatopinMetaforas = ThesisKatopinMetaforas;
    }

    public int getEpitixontes() {
        return Epitixontes;
    }

    public void setEpitixontes(int Epitixontes) {
        this.Epitixontes = Epitixontes;
    }

    public int getMoriaProtou() {
        return MoriaProtou;
    }

    public void setMoriaProtou(int MoriaProtou) {
        this.MoriaProtou = MoriaProtou;
    }

    public ArrayList<Double> getKritiriaIsobathmiasProtou() {
        return KritiriaIsobathmiasProtou;
    }

    public void setKritiriaIsobathmiasProtou(ArrayList<Double> KritiriaIsobathmiasProtou) {
        this.KritiriaIsobathmiasProtou = KritiriaIsobathmiasProtou;
    }

    public int getMoriaTelefteou() {
        return MoriaTelefteou;
    }

    public void setMoriaTelefteou(int MoriaTelefteou) {
        this.MoriaTelefteou = MoriaTelefteou;
    }

    public ArrayList<Double> getKritiriaIsobathmiasTelefteou() {
        return KritiriaIsobathmiasTelefteou;
    }

    public void setKritiriaIsobathmiasTelefteou(ArrayList<Double> KritiriaIsobathmiasTelefteou) {
        this.KritiriaIsobathmiasTelefteou = KritiriaIsobathmiasTelefteou;
    }


    public String getSchoolType() {
        return SchoolType;
    }


    public void setSchoolType(String schoolType) {
        this.SchoolType = schoolType;
    }


    public int getSpProtou() {
        return spProtou;
    }


    public void setSpProtou(int spProtou) {
        this.spProtou = spProtou;
    }


    public int getSpTelefteou() {
        return spTelefteou;
    }


    public void setSpTelefteou(int spTelefteou) {
        this.spTelefteou = spTelefteou;
    }
}
