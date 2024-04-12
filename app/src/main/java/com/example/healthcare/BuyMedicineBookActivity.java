package com.example.healthcare;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class BuyMedicineBookActivity extends AppCompatActivity {
    EditText edName, edAddress, edContact, edPincode;
    Button btnBooking;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_buy_medicine_book);

        edName = findViewById(R.id.editTextBMBFullname);
        edAddress = findViewById(R.id.editTextBMBAddress);
        edContact = findViewById(R.id.editTextBMBContact);
        edPincode = findViewById(R.id.editTextBMBPincode);
        btnBooking = findViewById(R.id.buttonBMBBooking);

        Intent intent = getIntent();
        String[] price = intent.getStringExtra("price").toString().split(java.util.regex.Pattern.quote(":"));
        String date = intent.getStringExtra("date");

        btnBooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedpreferences = getSharedPreferences("shared_prefs", MODE_PRIVATE);
                String username = sharedpreferences.getString("username", "").toString();

                Database db = new Database(getApplicationContext(), "healthcare", null, 1);
                db.addOrder(username, edName.getText().toString(), edAddress.getText().toString(), edContact.getText().toString(), Integer.parseInt(edPincode.getText().toString()), date.toString(), "", Float.parseFloat(price[1].toString()), "medicine");
                Toast.makeText(getApplicationContext(), username, Toast.LENGTH_SHORT).show();
                db.removeCart(username, "medicine");
                Toast.makeText(getApplicationContext(), "Booking Successful", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(BuyMedicineBookActivity.this, HomeActivity.class));
            }
        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}