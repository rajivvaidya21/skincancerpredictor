package com.bca.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Lesion {
    @SerializedName("Vascularlesions")
    @Expose
    private String Vascularlesions;

    @SerializedName("Basalcellcarcinoma")
    @Expose
    private String Basalcellcarcinoma;


    @SerializedName("Dermatofibroma")
    @Expose
    private String Dermatofibroma;


    @SerializedName("Actinickeratoses")
    @Expose
    private String Actinickeratoses;


    @SerializedName("Melanocyticnevi")
    @Expose
    private String Melanocyticnevi;

    @SerializedName("Melanoma")
    @Expose
    private String Melanoma;

    public String getMelanoma() {
        return Melanoma;
    }

    public void setMelanoma(String melanoma) {
        Melanoma = melanoma;
    }

    public String getBenignkeratosis() {
        return Benignkeratosis;
    }

    public void setBenignkeratosis(String benignkeratosis) {
        Benignkeratosis = benignkeratosis;
    }

    @SerializedName("Benignkeratosis")
    @Expose
    private String Benignkeratosis;


    public String getVascularlesions() {
        return Vascularlesions;
    }

    public void setVascularlesions(String vascularlesions) {
        Vascularlesions = vascularlesions;
    }

    public String getBasalcellcarcinoma() {
        return Basalcellcarcinoma;
    }

    public void setBasalcellcarcinoma(String basalcellcarcinoma) {
        Basalcellcarcinoma = basalcellcarcinoma;
    }

    public String getDermatofibroma() {
        return Dermatofibroma;
    }

    public void setDermatofibroma(String dermatofibroma) {
        Dermatofibroma = dermatofibroma;
    }

    public String getActinickeratoses() {
        return Actinickeratoses;
    }

    public void setActinickeratoses(String actinickeratoses) {
        Actinickeratoses = actinickeratoses;
    }

    public String getMelanocyticnevi() {
        return Melanocyticnevi;
    }

    public void setMelanocyticnevi(String melanocyticnevi) {
        Melanocyticnevi = melanocyticnevi;
    }
}
