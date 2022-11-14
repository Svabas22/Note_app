package com.example.note_app;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashSet;

public class MainActivity extends AppCompatActivity {

    ListView notesView;
    FloatingActionButton btnAdd;

    static ArrayList<String> note_list = new ArrayList<>();
    static ArrayAdapter adapter;

    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = this.getSharedPreferences("notes", MODE_PRIVATE);
        notesView = findViewById(R.id.menu_notes);

        btnAdd = findViewById(R.id.btnAdd);

        note_list = new ArrayList<>();

        HashSet<String> noteSet = (HashSet<String>) sharedPreferences.getStringSet("notes", null);

        if(!noteSet.isEmpty() || noteSet != null){
            note_list = new ArrayList<>();
        }

        adapter = new ArrayAdapter(this, R.layout.row_view, R.id.note_content, note_list);
        notesView.setAdapter(adapter);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), NoteActivity.class);
                //intent.putExtra("noteId", 1);
                startActivity(intent);
            }
        });
        notesView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                int item = i;
                new AlertDialog.Builder(MainActivity.this).setTitle("Delete this note?").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        note_list.remove(item);
                        adapter.notifyDataSetChanged();

                        HashSet<String> noteSet = new HashSet<>(note_list);
                        sharedPreferences.edit().putStringSet("notes", noteSet).apply();
                    }
                }).setNegativeButton("No", null).show();

                return true;
            }
        });

    }
}