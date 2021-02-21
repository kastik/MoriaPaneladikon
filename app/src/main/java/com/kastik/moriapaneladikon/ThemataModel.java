package com.kastik.moriapaneladikon;

import androidx.annotation.Keep;

@Keep
public class ThemataModel {
    private String SchoolType, LessonName, Link, Year, FileExtension;

    public ThemataModel() {
    }

    public ThemataModel(String Year, String LessonName, String SchoolType, String FileExtension, String Link) {
        this.SchoolType = SchoolType;
        this.LessonName = LessonName;
        this.Year = Year;
        this.FileExtension = FileExtension;
        this.Link = Link;
    }

    public String getLink() {
        return Link;
    }

    public void setLink(String Link) {
        this.Link = Link;
    }

    public String getSchoolType() {
        return SchoolType;
    }

    public void setSchoolType(String SchoolType) {
        this.SchoolType = SchoolType;
    }

    public String getLessonName() {
        return LessonName;
    }

    public void setLessonName(String LessonName) {
        this.LessonName = LessonName;
    }

    public String getYear() {
        return Year;
    }

    public void setYear(String Year) {
        this.Year = Year;
    }

    public String getFileExtension() {
        return FileExtension;
    }

    public void setFileExtension(String FileExtension) {
        this.FileExtension = FileExtension;
    }
}
