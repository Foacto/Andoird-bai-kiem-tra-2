package com.example.bai12.fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bai12.AddActivity;
import com.example.bai12.R;
import com.example.bai12.SQLDatabase.Database;
import com.example.bai12.adapter.RecycleViewAdapter;
import com.example.bai12.model.Item;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class FragmentSearch extends Fragment {
    private RecyclerView recyclerView;
    private TextView tvTong;
    private Button btSearch;
    private SearchView searchView;
    private EditText eFrom, eTo;
    private Spinner spCategory;
    private RecycleViewAdapter adapter;
    private Database db;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Init
        recyclerView = view.findViewById(R.id.recycleView);
        tvTong = view.findViewById(R.id.tvTong);
        btSearch = view.findViewById(R.id.btSearch);
        searchView = view.findViewById(R.id.search);
        eFrom = view.findViewById(R.id.eFrom);
        eTo = view.findViewById(R.id.eTo);
        spCategory = view.findViewById(R.id.spCategory);

        String[] arr = getResources().getStringArray(R.array.category);

        String[] ctArr = new String[arr.length + 1];
        ctArr[0] = "All";
        for (int i = 0; i < arr.length; i++) {
            ctArr[i + 1] = arr[i];
        }

        spCategory.setAdapter(new ArrayAdapter<String>(getContext(), R.layout.item_spinner, ctArr));

        // Logic
        adapter = new RecycleViewAdapter();
        db = new Database(getContext());

        List<Item> list = db.getAll();

        adapter.setList(list);

        tvTong.setText("Tong tien: " + tong(list) + "K");

        LinearLayoutManager manager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                List<Item> l = db.searchByTitle(s);

                tvTong.setText("Tong tien: " + tong(l) + "K");

                adapter.setList(l);

                return true;
            }
        });

        eFrom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
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

                        eFrom.setText(date);
                    }
                }, year, month, day);

                dialog.show();
            }
        });
        eTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
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

                        eTo.setText(date);
                    }
                }, year, month, day);

                dialog.show();
            }
        });

        btSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String from = eFrom.getText().toString();
                String to = eTo.getText().toString();

                if(!from.isEmpty() && !to.isEmpty()) {
                    List<Item> l = db.searchByDateFromTo(from, to);

                    tvTong.setText("Tong tien: " + tong(l) + "K");
                    adapter.setList(l);
                }
                else
                    Toast.makeText(getContext(), "From and To Date is not allow empty", Toast.LENGTH_SHORT).show();
            }
        });

        spCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                String cate = spCategory.getItemAtPosition(position).toString();

                List<Item> li;

                if(cate.equalsIgnoreCase("All"))
                    li = db.getAll();
                else
                    li = db.searchByCategory(cate);

                tvTong.setText("Tong tien: " + tong(li) + "K");

                adapter.setList(li);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private int tong(List<Item> list) {
        int sum = 0;
        for(Item i:list) {
            sum += Integer.parseInt(i.getPrice());
        }

        return sum;
    }
}
