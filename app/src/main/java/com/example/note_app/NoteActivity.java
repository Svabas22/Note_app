package com.example.note_app;

import androidx.annotation.ColorInt;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.HashSet;

public class NoteActivity extends AppCompatActivity {

    private EditText noteText;

    int noteId;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        sharedPreferences = this.getSharedPreferences("notes", MODE_PRIVATE);
        noteText = findViewById(R.id.et_note);
        Intent intent = getIntent();
        noteId = intent.getIntExtra("noteId", -1);

        if(noteId != -1)
        {
            noteText.setText(MainActivity.note_list.get(noteId));
        }
        else
        {
            MainActivity.note_list.add("");
            noteId = MainActivity.note_list.size() - 1;

        }

        noteText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {



            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                MainActivity.note_list.set(noteId, String.valueOf(charSequence));
                MainActivity.adapter.notifyDataSetChanged();

                HashSet<String> noteSet = new HashSet<>(MainActivity.note_list);
                sharedPreferences.edit().putStringSet("notes", noteSet).apply();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    public void SaveAndClose(View view) {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}