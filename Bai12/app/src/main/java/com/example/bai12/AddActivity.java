package com.example.bai12;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
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

public class AddActivity extends AppCompatActivity {
    private Spinner sp;
    private EditText eTitle, ePrice, eDate;
    private Button btAdd, btCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        sp = findViewById(R.id.spCategory);
        eTitle = findViewById(R.id.eTitle);
        ePrice = findViewById(R.id.ePrice);
        eDate = findViewById(R.id.eDate);

        btAdd = findViewById(R.id.btAdd);
        btCancel = findViewById(R.id.btCancel);

        sp.setAdapter(new ArrayAdapter<String>(this, R.layout.item_spinner,
                getResources().getStringArray(R.array.category)));

        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String t = eTitle.getText().toString();
                String c = sp.getSelectedItem().toString();
                String p = ePrice.getText().toString();
                String d = eDate.getText().toString();

                if(!t.isEmpty() && !d.isEmpty() && p.matches("\\d+")) {
                    Item item = new Item(t, c, p, d);

                    Database db = new Database(AddActivity.this);

                    db.addItem(item);
                    finish();
                }
                else
                    Toast.makeText(AddActivity.this, "Title and Date are not allow null, \nPrice must be number", Toast.LENGTH_SHORT).show();
            }
        });

        btCancel.setOnClickListener(new View.OnClickListener() {
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

                DatePickerDialog dialog = new DatePickerDialog(AddActivity.this, new DatePickerDialog.OnDateSetListener() {
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