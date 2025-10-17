package com.example.firstintent;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.Serializable;
import java.util.ArrayList;

import kotlin.jvm.internal.SerializedIr;

public class ProductsList extends AppCompatActivity {
    public ArrayList<Serializable> arr;
    public Intent intent;
    public TextView items, total_price;
    public double total_price_text = 0;
    public ListView lv;
    public ArrayAdapter<String> adapter;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_products_list);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        this.intent = getIntent();

        this.arr = (ArrayList<Serializable>) this.intent.getSerializableExtra("list");
        String[] prd = new String[this.arr.size()];
        for(int i = 0; i < this.arr.size(); i++)
        {
            product temp_prd = (product) this.arr.get(i);
            prd[i] = temp_prd.toString();
            total_price_text += temp_prd.calc_price();
        }

        this.lv = (ListView) findViewById(R.id.lv);
        this.adapter = new ArrayAdapter<String>(this, R.layout.listview,prd);
        this.lv.setAdapter(this.adapter);


        this.total_price = (TextView) findViewById(R.id.total_price);
        this.items = (TextView) findViewById(R.id.items);



        //this.items.setText(text);
        this.total_price.setText("" + total_price_text);





        
    }


}