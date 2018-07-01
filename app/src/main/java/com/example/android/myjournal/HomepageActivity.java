package com.example.android.myjournal;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.android.myjournal.Authentication.SignUpActivity;
import com.example.android.myjournal.time.GeoTime;
import com.example.android.myjournal.viewholder.ViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class HomepageActivity extends AppCompatActivity {

    private static final String TAG = "HomepageActivity";
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private RecyclerView notesListRecyclerView;

    private DatabaseReference notesDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1, LinearLayoutManager.VERTICAL, false);
        gridLayoutManager.setItemPrefetchEnabled(false);
        notesListRecyclerView = findViewById(R.id.notes_list_recycler);

        notesListRecyclerView.setHasFixedSize(true);
        notesListRecyclerView.setLayoutManager(gridLayoutManager);


        firebaseAuth = FirebaseAuth.getInstance();


        if (firebaseAuth.getCurrentUser() != null) {
            notesDatabase = FirebaseDatabase.getInstance().getReference().child("Notes").child(firebaseAuth.getCurrentUser().getUid());
        }
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                Intent intent = new Intent(getApplicationContext(), NewNote.class);
                startActivity(intent);
            }
        });




            setupFirebaseAdadapter();




        updateUI();
    }

    public void setupFirebaseAdadapter() {


        Query query = notesDatabase.orderByChild("timestamp");
        FirebaseRecyclerAdapter<JournalModel, ViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<JournalModel, ViewHolder>(
                JournalModel.class,
                R.layout.each_list_item,
                ViewHolder.class,
                query
        ) {
            @Override
            protected void populateViewHolder(final ViewHolder viewHolder, JournalModel model, int position) {

                final String noteId = getRef(position).getKey();

                notesDatabase.child(noteId).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChild("title") && dataSnapshot.hasChild("timestamp")) {
                            String title = dataSnapshot.child("title").getValue().toString();
                            String time = dataSnapshot.child("timestamp").getValue().toString();
                            String description = dataSnapshot.child("content").getValue().toString();

//                            viewHolder.setNoteTime(time);
                            viewHolder.setNoteTitle(title);
                            viewHolder.setTextDesCription(description);

                            //GeoTime geoTime = new GeoTime();
                            viewHolder.setNoteTime(GeoTime.getTime(Long.parseLong(time), getApplicationContext()));

                            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Log.d(TAG, "onClick: Item clicked");
                                    Intent intent = new Intent(getApplicationContext(), NewNote.class);
                                    intent.putExtra("noteID", noteId);
                                    startActivity(intent);
                                }
                            });
                            viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                                @Override
                                public boolean onLongClick(View v) {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(HomepageActivity.this);
                                    builder.setTitle("Delete")
                                            .setMessage("Are you sure you want to delete this note?")
                                            .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    notesDatabase.child(noteId).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            Toast.makeText(HomepageActivity.this, "Note successfully deleted", Toast.LENGTH_LONG).show();
                                                            if (task.isSuccessful()) {


                                                            } else {
                                                                Toast.makeText(HomepageActivity.this, "Note can't be deleted at this time", Toast.LENGTH_SHORT).show();
                                                                Log.e(TAG, "onComplete: " + task.getException().toString());
                                                            }
                                                        }
                                                    });
                                                }
                                            })
                                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    recreate();
                                                }
                                            }).create();
                                    builder.show();
                                    return true;
                                }
                            });

                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        };
        notesListRecyclerView.setAdapter(firebaseRecyclerAdapter);
    }
    private void updateUI() {
        if (firebaseAuth.getCurrentUser() != null) {
            Log.i(TAG, "updateUI: Firebase Auth != null");
        } else {
            Intent startIntent = new Intent(HomepageActivity.this, SignUpActivity.class);
            startActivity(startIntent);
//            finish();
            Log.i(TAG, "updateUI: Auth == null");
        }
    }
    @Override
    public void onStart() {
        super.onStart();


            setupFirebaseAdadapter();

    }

    private void signOut() {
        // Firebase sign out
        firebaseAuth.signOut();
        Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
        startActivity(intent);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        getMenuInflater().inflate(R.menu.menu_notes, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        switch (item.getItemId()) {
            case R.id.notesLogout_menubtn:
                AlertDialog.Builder builder = new AlertDialog.Builder(HomepageActivity.this);
                builder.setTitle("Logout")
                        .setMessage("Are you sure you want to log out?")
                        .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                signOut();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                recreate();
                            }
                        }).create();
                builder.show();

                break;
        }
        return true;
    }
}
