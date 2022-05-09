package com.manh.petshopdemo1;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.manh.petshopdemo1.db.DBHelper;

public class SearchViewActivity extends AppCompatActivity {
    EditText edtsearch;
    TextView tvsearch;
    DBHelper helper;
    ListView lv_search_history;
    ArrayAdapter<String> list_search_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_view);
        edtsearch = findViewById(R.id.edtsearch);
        tvsearch = findViewById(R.id.tvsearch);
        lv_search_history = findViewById(R.id.lv_search_history);
        helper = new DBHelper(this, "petshop.db", null, 1);
        getData();
        tvsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteData();
                String insert = "INSERT INTO search(search_history) VALUES('" + edtsearch.getText().toString() + "')";
                helper.queryData(insert);
                Intent intent=new Intent(SearchViewActivity.this,ItemSearchView.class);
                intent.putExtra("key",edtsearch.getText().toString());
                startActivity(intent);
            }
        });
        lv_search_history.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String key=list_search_adapter.getItem(i);
                edtsearch.setText(key);
            }
        });
    }

    private void deleteData() {
        String select = "SELECT * FROM search";
        Cursor cursor = helper.getData(select);
        int count = cursor.getCount();
        if (count >= 8) {
            int a = count - 7;
            String top = "SELECT * FROM search LIMIT "+a;
            Cursor cur = helper.getData(top);
            cur.moveToFirst();
            while (!cur.isAfterLast()){
                @SuppressLint("Range") int id=cur.getInt(cur.getColumnIndex("id"));
                String delete="DELETE FROM search WHERE id="+id;
                helper.queryData(delete);
                cur.moveToNext();
            }
        }
    }

    @SuppressLint("Range")
    private void getData() {
        int count = 0;
        String sql = "SELECT * FROM search";
        Cursor cursor = helper.getData(sql);
        String[] search_history = new String[cursor.getCount()];
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            search_history[count] = cursor.getString(cursor.getColumnIndex("search_history")).trim();
            count++;
            cursor.moveToNext();
        }
        list_search_adapter = new ArrayAdapter<String>(this,R.layout.lv_search, search_history);
        lv_search_history.setAdapter(list_search_adapter);
        list_search_adapter.notifyDataSetChanged();
    }
}