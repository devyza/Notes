package edu.harvard.cs50.notes;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class NoteActivity extends AppCompatActivity {
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        Intent intent = getIntent();
        editText = findViewById(R.id.note_edit_text);
        editText.setText(intent.getStringExtra("content"));
    }

    @Override
    protected void onPause() {
        super.onPause();

        Intent intent = getIntent();
        int id = intent.getIntExtra("id", 0);
        MainActivity.database.noteDao().save(editText.getText().toString(), id);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem deleteItem = menu.findItem(R.id.menu_delete);

        deleteItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {

                // Creating AlertDialog to confirm delete
                new AlertDialog.Builder(NoteActivity.this)
                        .setTitle("Warning")
                        .setMessage("Are you sure do you want to delete?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                // Delete note from Database
                                int id = getIntent().getIntExtra("id", 0);
                                MainActivity.database.noteDao().delete(id);

                                Toast.makeText(NoteActivity.this, "Note has been deleted", Toast.LENGTH_SHORT).show();

                                // Go Back to MainActivity
                                finish();
                            }})
                        .setNegativeButton("No", null)
                        .show();

                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }
}
