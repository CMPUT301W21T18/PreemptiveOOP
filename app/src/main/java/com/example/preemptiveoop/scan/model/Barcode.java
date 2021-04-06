package com.example.preemptiveoop.scan.model;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import org.json.JSONObject;

public class Barcode {

    private String dbID;
    private String jsonString;
    private String fuzzyString;

    public Barcode(){}
    public Barcode(JSONObject jsonObject) {
        this.dbID = dbID;
        this.jsonString = jsonObject.toString();
    }

    public String getDbID() {
        return dbID;
    }

    public String getJsonString() {
        return jsonString;
    }

    public String getFuzzyString() {
        return fuzzyString;
    }

    public void writeToDatabase() {
        CollectionReference barCol = FirebaseFirestore.getInstance().collection("Barcodes");

        if (dbID == null) {
            dbID = barCol.document().getId();
            fuzzyString = dbID.substring(0,7);
        }
        barCol.document(dbID).set(this, SetOptions.merge());
    }

}
