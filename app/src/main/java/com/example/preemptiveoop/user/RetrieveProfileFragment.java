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
import com.example.preemptiveoop.user.model.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class RetrieveProfileFragment extends DialogFragment{

    private TextView tvRetrieveResult;
    private EditText etUsername;
    private Button btRetrieveUserProfile;
    CollectionReference usrCol = FirebaseFirestore.getInstance().collection("Users");

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_retrieve_profile, null);

        etUsername = view.findViewById(R.id.EditText_search_username);
        btRetrieveUserProfile = view.findViewById(R.id.Button_retrieve_user);
        tvRetrieveResult = view.findViewById(R.id.TextView_retrieve_result);

        btRetrieveUserProfile.setOnClickListener(this::onClick);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(view);
        builder.setTitle("Retrieve User Profile");
        builder.setPositiveButton("Confirm", null);
        builder.setNegativeButton("Cancel", null);
        return builder.create();
    }

    public void onClick(View v) {
        String search_usr_name = etUsername.getText().toString();
        if (search_usr_name.equals("")) {
            tvRetrieveResult.setText("Invalid Username");
            return;
        }
        usrCol.document(search_usr_name).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (!documentSnapshot.exists()) {
                            tvRetrieveResult.setText("Invalid Username");
                            return;
                        }

                        User user = documentSnapshot.toObject(User.class);
                        tvRetrieveResult.setText(user.getContact());
                        Log.d("USER_PROFILE", "User profile retrieve successfully !");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("USER_PROFILE", "Retrieve Request fail!");
                    }
                });
    }
}
