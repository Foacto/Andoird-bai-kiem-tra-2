package com.example.bai12.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bai12.R;
import com.example.bai12.SQLDatabase.Database;
import com.example.bai12.UpdateDeleteActivity;
import com.example.bai12.adapter.RecycleViewAdapter;
import com.example.bai12.model.Item;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class FragmentHome extends Fragment implements RecycleViewAdapter.ItemListener {
    private RecyclerView recyclerView;
    private RecycleViewAdapter adapter;
    private Database db;
    private TextView tvTong;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recycleView);
        tvTong = view.findViewById(R.id.tvTong);

        adapter = new RecycleViewAdapter();

        db = new Database(getContext());

//        db.addItem(new Item("Mua quan bo", "Mua sam", "500", "17/04/2023"));

        Date d = new Date();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

        List<Item> list = db.getByDate(format.format(d));

        adapter.setList(list);

        tvTong.setText("Tong tien: " + tong(list) + "K");

        LinearLayoutManager manager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        adapter.setItemListener(this);
    }

    private int tong(List<Item> list) {
        int sum = 0;
        for(Item i:list) {
            sum += Integer.parseInt(i.getPrice());
        }

        return sum;
    }

    @Override
    public void onItemClick(View view, int position) {
        Item item = adapter.getItem(position);

        Intent intent = new Intent(getActivity(), UpdateDeleteActivity.class);
        intent.putExtra("item", item);

        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();

        Date d = new Date();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

        List<Item> list = db.getByDate(format.format(d));

        adapter.setList(list);

        tvTong.setText("Tong tien: " + tong(list));
    }
}
