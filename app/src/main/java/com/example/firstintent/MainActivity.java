package com.example.firstintent;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import androidx.appcompat.widget.Toolbar; // <-- Make sure to import this

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public EditText product_name, product_price, product_quantity;
    public Button next_bt, finsh_bt;
    public ArrayList<product> list;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemid = item.getItemId();
        if(itemid == R.id.add_products)
        {
            Intent gogu = new Intent(this, MainActivity.class);
            startActivity(gogu);
        }

        if(itemid == R.id.show_cart)
        {
            Intent gogu = new Intent(this, ProductsList.class);
            gogu.putExtra("list", this.list);
            startActivity(gogu);
        }
        if(itemid == R.id.contact)
        {
            Intent gogu = new Intent(this, contact.class);
            startActivity(gogu);
        }

        return super.onOptionsItemSelected(item);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Add these two lines
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        this.product_name     = findViewById(R.id.product_name);
        this.product_price    = findViewById(R.id.product_price);
        this.product_quantity = findViewById(R.id.product_quantity);

        this.next_bt   = (Button) findViewById(R.id.next_bt);
        this.finsh_bt = (Button) findViewById(R.id.finsh_bt);

        this.next_bt.setOnClickListener(this);
        this.finsh_bt.setOnClickListener(this);

        this.list = new ArrayList<>();
    }



    @Override
    public void onClick(View v) {
        if(v == this.next_bt)
        {
            if(TextUtils.isEmpty(this.product_name.getText().toString()) == true ||  TextUtils.isEmpty(this.product_price.getText().toString()) == true || TextUtils.isEmpty(this.product_quantity.getText().toString()) == true)
            {
                Toast.makeText(this, "u forget to enter something!!", Toast.LENGTH_LONG).show();
            }

            else {

                product p = new product(this.product_name.getText().toString(), Double.parseDouble(product_price.getText().toString()),  Integer.parseInt(this.product_quantity.getText().toString()));
                this.list.add(p);


                this.product_name.setText("");
                this.product_price.setText("");
                this.product_quantity.setText("");

            }

        }
        if(v == this.finsh_bt)
        {
            Toast.makeText(this, ""+this.list.size(), Toast.LENGTH_LONG).show();
            Intent go = new Intent(MainActivity.this, ProductsList.class);
            go.putExtra("list" ,this.list);
            startActivity(go);

        }

    }


}