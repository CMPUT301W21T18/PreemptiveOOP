package com.example.preemptiveoop.user;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.preemptiveoop.R;
import com.example.preemptiveoop.user.model.User;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

/**
 * The UserProfileFragment class is the fragment class that builds and manages the UI for the user
 * to retrieve its own profile.
 */
public class UserProfileFragment extends DialogFragment {

    private TextView username;
    private EditText contact;
    private User user;
    CollectionReference usrCol = FirebaseFirestore.getInstance().collection("User");

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_edit_user_profile, null);

        username = view.findViewById(R.id.textView_usrname);
        contact = view.findViewById(R.id.editTextTextEmailAddress);

        Bundle bundle = getArguments();
        if (bundle != null) {
            user = (User) bundle.get("user");
        }

        username.setText(user.getUsername());
        contact.setText(user.getContact());

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(view);
        builder.setTitle("User Profile");
        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String new_contact = contact.getText().toString();
                user.setContact(new_contact);
                user.writeToDatabase();
            }
        });
        builder.setNegativeButton("Cancel", null);
        return builder.create();
    }
}
