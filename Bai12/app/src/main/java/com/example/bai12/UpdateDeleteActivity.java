package com.example.bai12;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.bai12.SQLDatabase.Database;
import com.example.bai12.model.Item;

import java.util.Calendar;

public class UpdateDeleteActivity extends AppCompatActivity {
    private Spinner sp;
    private EditText eTitle, ePrice, eDate;
    private Button btUpdate, btDelete, btBack;

    private Item item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_delete);

        sp = findViewById(R.id.spCategory);
        eTitle = findViewById(R.id.eTitle);
        ePrice = findViewById(R.id.ePrice);
        eDate = findViewById(R.id.eDate);

        btUpdate = findViewById(R.id.btUpdate);
        btDelete = findViewById(R.id.btDelete);
        btBack = findViewById(R.id.btBack);

        Intent intent = getIntent();
        item = (Item) intent.getSerializableExtra("item");

        eTitle.setText(item.getTitle());
        ePrice.setText(item.getPrice());
        eDate.setText(item.getDate());

        int p = 0;
        for (int i = 0; i < sp.getCount(); i++) {
            if(sp.getItemAtPosition(i).toString().equalsIgnoreCase(item.getCategory())) {
                p = i;
                break;
            }
        }
        sp.setSelection(p);

        sp.setAdapter(new ArrayAdapter<String>(this, R.layout.item_spinner,
                getResources().getStringArray(R.array.category)));

        Database db = new Database(this);

        btUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String t = eTitle.getText().toString();
                String c = sp.getSelectedItem().toString();
                String p = ePrice.getText().toString();
                String d = eDate.getText().toString();

                if(!t.isEmpty() && !d.isEmpty() && p.matches("\\d+")) {
                    item.setTitle(t);
                    item.setCategory(c);
                    item.setPrice(p);
                    item.setDate(d);

                    db.update(item);
                    finish();
                }
                else
                    Toast.makeText(UpdateDeleteActivity.this, "Title and Date are not allow null, \nPrice must be number", Toast.LENGTH_SHORT).show();


            }
        });

        btDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("Thong bao xoa");
                builder.setMessage("Ban co chac muon xoa " + item.getTitle() + "khong?");
                builder.setIcon(R.drawable.ic_delete);
                builder.setPositiveButton("Co", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        db.delete(item.getId());
                        finish();
                    }
                });
                builder.setNegativeButton("Khong", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        eDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(UpdateDeleteActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int y, int m, int d) {
                        String date = "";
                        if(m > 8) {
                            if(d > 9)
                                date += d+"/"+(m+1)+"/"+y;
                            else
                                date += "0"+d+"/"+(m+1)+"/"+y;
                        }
                        else {
                            if(d > 9)
                                date += d+"/0"+(m+1)+"/"+y;
                            else
                                date += "0"+d+"/0"+(m+1)+"/"+y;
                        }

                        eDate.setText(date);
                    }
                }, year, month, day);

                dialog.show();
            }
        });
    }
}