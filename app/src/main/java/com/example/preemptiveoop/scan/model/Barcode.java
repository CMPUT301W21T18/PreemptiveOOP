package com.example.preemptiveoop.scan.model;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import org.json.JSONObject;

/**
 * The Barcode class is used to store and manage the barcode representing a trial result to an experiment.
 * This class is Firestore compatible.
 */
public class Barcode {
    private String databaseId;
    private String jsonString;
    private String fuzzyString;

    public Barcode() {}
    public Barcode(String databaseId, JSONObject jsonObject) {
        this.databaseId = databaseId;
        this.jsonString = jsonObject.toString();
    }

    /**
     * Write all fields in this Barcode class to the Firestore database. Existing fields are
     * updated accordingly.
     */
    public void writeToDatabase() {
        CollectionReference barCol = FirebaseFirestore.getInstance().collection("Barcodes");

        if (databaseId == null) {
            databaseId = barCol.document().getId();
            fuzzyString = databaseId.substring(0,7);
        }
        barCol.document(databaseId).set(this, SetOptions.merge());
    }

    // getters
    public String getDatabaseId() {
        return databaseId;
    }
    public String getJsonString() {
        return jsonString;
    }
    public String getFuzzyString() {
        return fuzzyString;
    }
}
