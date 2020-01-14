package com.example.kopi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import javax.annotation.Nullable;

public class Profile extends AppCompatActivity {
    TextView fullName, email, phone;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        fullName = findViewById(R.id.profileName);
        email = findViewById(R.id.profileEmail);
        phone = findViewById(R.id.profilePhone);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        userId = fAuth.getCurrentUser().getUid();

        DocumentReference documentReference = fStore.collection("Users").document(userId);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                phone.setText(documentSnapshot.getString("Phone Number"));
                fullName.setText(documentSnapshot.getString("Full Name"));
                email.setText(documentSnapshot.getString("E-mail"));
            }
        });



    }

    public void logOut(View view) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(),Login.class));
        finish();
    }

    public void homeScreen(View view) {
        startActivity(new Intent(getApplicationContext(),MainActivity.class));
        finish();
    }
}
