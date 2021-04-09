package com.example.preemptiveoop.user;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.preemptiveoop.R;
import com.example.preemptiveoop.uiwidget.MyDialog;
import com.example.preemptiveoop.user.model.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * The RetrieveProfileFragment class is the fragment class that builds and manages the UI for the user
 * to retrieve another user's profile.
 */
public class RetrieveProfileFragment extends DialogFragment{
    private TextView tvResult;
    private EditText etUsername;
    private Button btRetrieve;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_retrieve_profile, null);

        etUsername = view.findViewById(R.id.EditText_username);
        btRetrieve = view.findViewById(R.id.Button_retrieve);
        tvResult = view.findViewById(R.id.TextView_result);

        btRetrieve.setOnClickListener(this::btRetrieveOnClick);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(view)
                .setTitle("Retrieve User Profile")
                .setPositiveButton("OK", null);
        return builder.create();
    }

    public void btRetrieveOnClick(View v) {
        String username = etUsername.getText().toString();
        if (username.isEmpty()) {
            MyDialog.messageDialog(getActivity(), "Empty Field", "Please enter a username.");
            return;
        }

        CollectionReference userCol = FirebaseFirestore.getInstance().collection("Users");
        userCol.document(username).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (!documentSnapshot.exists()) {
                            MyDialog.messageDialog(getActivity(),
                                    "User Not Found",
                                    "The provide username was not found in the DB.");
                            return;
                        }

                        User user = documentSnapshot.toObject(User.class);
                        tvResult.setText(user.getContact());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) { Log.d("ReProfileFragment.DB", e.toString()); }
                });
    }
}
