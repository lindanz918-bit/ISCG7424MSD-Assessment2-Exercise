package cs.linda.iscg7424assessment2exercise.exercise1;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Filter;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cs.linda.iscg7424assessment2exercise.R;

public class Exercise1Activity extends AppCompatActivity {
    EditText firstName, lastName, email, phone;
    Button add, update, delete, search;
    RecyclerView recyclerView;
    ContactAdapter adapter;
    List<Contact> contactsList;
    FirebaseFirestore db;
    ListenerRegistration contactsListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_exercise1);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.activity_exercise1), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        firstName = findViewById(R.id.et_first_name);
        lastName = findViewById(R.id.et_last_name);
        email = findViewById(R.id.et_email);
        phone = findViewById(R.id.et_phone);
        add = findViewById(R.id.btn_add);
        update = findViewById(R.id.btn_update);
        delete = findViewById(R.id.btn_delete);
        search = findViewById(R.id.btn_search);
        recyclerView = findViewById(R.id.recycler_view);
        db = FirebaseFirestore.getInstance();

        contactsList = new ArrayList<>();
        adapter = new ContactAdapter(contactsList);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        listenToContacts();

        add.setOnClickListener(v -> {
            // Create a new user with a first and last name
            String firstName = this.firstName.getText().toString();
            String lastName = this.lastName.getText().toString();
            String email = this.email.getText().toString();
            String phone = this.phone.getText().toString();

            if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || phone.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            Map<String, Object> contact = new HashMap<>();
            contact.put("first", firstName);
            contact.put("last", lastName);
            contact.put("email", email);
            contact.put("phone", phone);

            // Add a new document with a generated ID
            db.collection("contacts")
                    .add(contact)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w(TAG, "Error adding document", e);
                        }
                    });
        });

        update.setOnClickListener(v -> {
            String searchEmail = this.email.getText().toString();
            if (searchEmail.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            db.collection("contacts")
                    .whereEqualTo("email", searchEmail)
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot document : task.getResult()) {
                                String firstName = this.firstName.getText().toString();
                                String lastName = this.lastName.getText().toString();
                                String phone = this.phone.getText().toString();
                                if (firstName.isEmpty() || lastName.isEmpty() || phone.isEmpty()) {
                                    Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                Map<String, Object> contact = new HashMap<>();
                                contact.put("first", firstName);
                                contact.put("last", lastName);
                                contact.put("phone", phone);
                                db.collection("contacts").document(document.getId())
                                        .update(contact)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Log.d(TAG, "DocumentSnapshot successfully updated!");
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.w(TAG, "Error updating document", e);
                                            }
                                        });
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w(TAG, "Error getting documents: ", e);
                        }
                    });
        });

        delete.setOnClickListener(v -> {
            String searchEmail = this.email.getText().toString();
            if (searchEmail.isEmpty()) {
                Toast.makeText(this, "Please fill email fields", Toast.LENGTH_SHORT).show();
                return;
            }
            db.collection("contacts")
                    .whereEqualTo("email", searchEmail)
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot document : task.getResult()) {
                                db.collection("contacts").document(document.getId())
                                        .delete()
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Log.d(TAG, "DocumentSnapshot successfully deleted!");
                                                clearInputFields();
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.w(TAG, "Error deleting document", e);
                                            }
                                        });
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.w(TAG, "Error getting documents: ", e);
                        }
                    });
        });

        search.setOnClickListener(v -> {
            String searchFirstName = this.firstName.getText().toString().trim();

            if (searchFirstName.isEmpty()) {
                Toast.makeText(this, "Please enter First Name keyword to search", Toast.LENGTH_SHORT).show();
                return;
            }

            if (contactsListener != null) {
                contactsListener.remove();
            }

            Filter firstNameFilter = Filter.and(
                    Filter.greaterThanOrEqualTo("first", searchFirstName),
                    Filter.lessThan("first", searchFirstName + "\uf8ff")
            );

            db.collection("contacts")
                    .where(firstNameFilter)
                    .get()
                    .addOnSuccessListener(queryDocumentSnapshots -> {
                        contactsList.clear();
                        for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments()) {
                            Log.d(TAG, document.getId() + " => " + document.getData());
                            Contact contact = document.toObject(Contact.class);
                            if (contact != null) {
                                contactsList.add(contact);
                            }
                        }
                        adapter.notifyDataSetChanged();
                    })
                    .addOnFailureListener(e -> {
                        Log.w(TAG, "Error getting documents: ", e);
                        Toast.makeText(this, "Search failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        });

        adapter.setOnItemClickListener(contact -> {
            // Handle click on RecyclerView item
           if (contact != null) {
               firstName.setText(contact.getFirst());
               lastName.setText(contact.getLast());
               email.setText(contact.getEmail());
               phone.setText(contact.getPhone());
           }
        });
    }


    private void listenToContacts() {
        if (contactsListener != null) {
            contactsListener.remove();
        }
        contactsListener = db.collection("contacts")
                .addSnapshotListener((queryDocumentSnapshots, e) -> {
                    if (e != null) {
                        Log.w(TAG, "listen:error", e);
                        return;
                    }
                    if (queryDocumentSnapshots != null) {
                        contactsList.clear();
                        for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments()) {
                            Log.d(TAG, document.getId() + " => " + document.getData());
                            Contact contact = document.toObject(Contact.class);
                            if (contact != null) {
                                contactsList.add(contact);
                            }
                        }
                        adapter.notifyDataSetChanged();
                    }
                });
    }

    private void clearInputFields() {
        firstName.setText("");
        lastName.setText("");
        email.setText("");
        phone.setText("");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (contactsListener != null) {
            contactsListener.remove();
        }
    }
}