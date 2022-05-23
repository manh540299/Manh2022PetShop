package com.manh.petshopdemo1;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.manh.petshopdemo1.db.DBHelper;

public class SearchViewActivity extends AppCompatActivity {
    private EditText edtsearch;
    private TextView tvsearch;
    private DBHelper helper;
    private ListView lv_search_history;
    private ArrayAdapter<String> list_search_adapter;
    private String[] search_history;
    private TextView delete_his;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_view);
        initUI();
        getData();
        onClick();

    }

    private void initUI() {
        edtsearch = findViewById(R.id.edtsearch);
        tvsearch = findViewById(R.id.tvsearch);
        lv_search_history = findViewById(R.id.lv_search_history);
        helper = new DBHelper(this, "petshop.db", null, 1);
        delete_his=findViewById(R.id.tvsetting_delete_history_search);
    }

    @SuppressLint("Range")
    private void getData() {
        int count = 0;
        String sql = "SELECT * FROM search";
        Cursor cursor = helper.getData(sql);
        search_history = new String[cursor.getCount()];
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            search_history[count] = cursor.getString(cursor.getColumnIndex("search_history")).trim();
            count++;
            cursor.moveToNext();
        }
        String[] search_his=new String[search_history.length];
        int countz=0;
        for(int i=search_history.length-1;i>=0;i--){
            search_his[countz]=search_history[i];
            countz++;
        }
        list_search_adapter = new ArrayAdapter<>(this, R.layout.lv_search, search_his);
        lv_search_history.setAdapter(list_search_adapter);
    }

    private void deleteData() {
        String select = "SELECT * FROM search";
        Cursor cursor = helper.getData(select);
        int count = cursor.getCount();
        if (count >= 8) {
            int a = count - 7;
            String top = "SELECT * FROM search LIMIT " + a;
            Cursor cur = helper.getData(top);
            cur.moveToFirst();
            while (!cur.isAfterLast()) {
                @SuppressLint("Range") int id = cur.getInt(cur.getColumnIndex("id"));
                String delete = "DELETE FROM search WHERE id=" + id;
                helper.queryData(delete);
                cur.moveToNext();
            }
        }
    }


    private void onClick() {
        tvsearch.setOnClickListener(view -> onClickSearch());
        lv_search_history.setOnItemClickListener((adapterView, view, i, l) -> {
            String key = list_search_adapter.getItem(i);
            edtsearch.setText(key);
        });
        delete_his.setOnClickListener(view -> {
            helper.queryData("DELETE FROM search");
            getData();
        });
    }

    private void onClickSearch() {
        boolean check = false;
        String search = edtsearch.getText().toString();
        for (int i = 0; i < search_history.length; i++) {
            if (search.equalsIgnoreCase(search_history[i])) {
                check = true;
            }
        }
        Intent intent = new Intent(SearchViewActivity.this, ItemSearchView.class);
        intent.putExtra("key", edtsearch.getText().toString());
        startActivity(intent);
        if (!check) {
            deleteData();
            String insert = "INSERT INTO search(search_history) VALUES('" + search + "')";
            helper.queryData(insert);

        }
    }
}