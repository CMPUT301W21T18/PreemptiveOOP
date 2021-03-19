package com.example.preemptiveoop.user;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
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
import com.google.firebase.firestore.FirebaseFirestore;

public class UserProfileFragment extends DialogFragment {

    private TextView usr_name;
    private EditText contact;
    private User user;
    CollectionReference usrCol = FirebaseFirestore.getInstance().collection("User");

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_edit_userprofile, null);

        usr_name = view.findViewById(R.id.textView_usrname);
        contact = view.findViewById(R.id.editTextTextEmailAddress);

        Bundle bundle = getArguments();
        if (bundle != null) {
            user = (User) bundle.get("user");
        }

        usr_name.setText(user.getUsername());
        contact.setText(user.getContact());

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(view);
        builder.setTitle("User Profile");
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String new_contact = contact.getText().toString();
                usrCol.document(user.getUsername()).update("contact", new_contact)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d("USER_PROFILE", "Contact successfully updated!");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d("USER_PROFILE", "Contact updated fail!");
                            }
                        });
            }
        });
        builder.setNegativeButton("Cancel", null);
        return builder.create();
    }
}
