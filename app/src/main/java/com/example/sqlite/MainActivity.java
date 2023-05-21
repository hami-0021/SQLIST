package com.example.sqlite;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper myDB;
    EditText editName,editSurname,editMarks,editTextid;
    Button btnAddData;
    Button btnviewAll;
    Button btnviewUpdate;
    Button btnDelete;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDB = new DatabaseHelper(this);

        editName = (EditText) findViewById(R.id.editText_name);
        editSurname = (EditText) findViewById(R.id.editText_surname);
        editMarks = (EditText) findViewById(R.id.editText_Marks);
        editTextid = (EditText) findViewById(R.id.editText_id);
        btnAddData = (Button) findViewById(R.id.button_add);
        btnviewAll = (Button) findViewById(R.id.button_viewAll);
        btnviewUpdate = (Button) findViewById(R.id.button_update);
        btnDelete = (Button) findViewById(R.id.button_delete);
        viewAll();
        AddData();
        UpdateData();
        DeleteData();

    }

    public  void DeleteData(){
        btnDelete.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Integer deleteRows = myDB.deleteData(editTextid.getText().toString());
                        if (deleteRows > 0)
                            Toast.makeText(MainActivity.this, "DATA DELETED", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(MainActivity.this, "DATA not DELETED", Toast.LENGTH_LONG).show();

                    }
                }
        );
    }

    public void UpdateData(){
        btnviewUpdate.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        boolean isupdate = myDB.updateData(editTextid.getText().toString(), editName.getText().toString(), editSurname.getText().toString(), editMarks.getText().toString());
                        if (isupdate == true)
                            Toast.makeText(MainActivity.this, "DATA UPDATED", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(MainActivity.this, "DATA not Updated", Toast.LENGTH_LONG).show();

                    }

                }
        );
    }

    public void AddData(){
        btnAddData.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                       boolean isInerted = myDB.insertData(editName.getText().toString(),
                                editSurname.getText().toString(), editMarks.getText().toString());
                       if(isInerted == true)
                           Toast.makeText(MainActivity.this,"DATA INSERTED",Toast.LENGTH_LONG).show();
                       else
                           Toast.makeText(MainActivity.this,"DATA not INSERTED",Toast.LENGTH_LONG).show();

                    }
                }
        );
    }

    public void viewAll() {
        btnviewAll.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Cursor res = myDB.getAllData();
                        if (res.getCount() == 0) {
                            showMessage("Error","Nothing Found");

                            return;
                        }
                        StringBuffer buffer = new StringBuffer();
                        while (res.moveToNext()) {
                            buffer.append("id : " + res.getString(0) + "\n");
                            buffer.append("Name : " + res.getString(1) + "\n");
                            buffer.append("Surname : " + res.getString(2) + "\n");
                            buffer.append("Marks : " + res.getString(3) + "\n\n");
                        }
                        showMessage("Data",buffer.toString());
                    }

    }
    );
    }
    public void showMessage(String title, String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();

    }
}
