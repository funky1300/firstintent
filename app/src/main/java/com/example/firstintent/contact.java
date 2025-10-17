package com.example.firstintent;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

// Implement the OnClickListener interface to handle button clicks
public class contact extends AppCompatActivity implements View.OnClickListener {

    private Button call, email, whatsapp, sms;

    // Constants for contact info and the permission request
    private static final int PERMISSIONS_REQUEST_CODE = 1;
    private static final String PHONE_NUMBER = "0501234567"; // ⚠️ Replace with the real phone number
    private static final String EMAIL_ADDRESS = "your.email@example.com"; // ⚠️ Replace with the real email
    private static final String SMS_MESSAGE = "Hello from my new app!"; // Default SMS text

    private View clickedView = null; [cite_start]// Stores the button that was clicked [cite: 476, 560]

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // EdgeToEdge and WindowInsets code is standard setup and can be left as is
        setContentView(R.layout.activity_contact);

        // --- IMPORTANT ---
        // Make sure your buttons in activity_contact.xml have these exact IDs
        call = findViewById(R.id.call_button);
        email = findViewById(R.id.email_button);
        whatsapp = findViewById(R.id.whatsapp_button);
        sms = findViewById(R.id.sms_button);

        // Set this class as the click listener for each button
        call.setOnClickListener(this);
        email.setOnClickListener(this);
        whatsapp.setOnClickListener(this);
        sms.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        clickedView = v; [cite_start]// Save the clicked button view [cite: 540]

        // Actions requiring special permissions (Call and SMS)
        if (v.getId() == R.id.call_button || v.getId() == R.id.sms_button) {
            [cite_start]// Check if we already have the necessary permissions [cite: 543, 547]
            if (checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED ||
                    checkSelfPermission(Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {

                [cite_start]// If permissions are not granted, request them from the user [cite: 548]
                String[] permissions = {Manifest.permission.CALL_PHONE, Manifest.permission.SEND_SMS};
                requestPermissions(permissions, PERMISSIONS_REQUEST_CODE);
            } else {
                [cite_start]// Permissions are already granted, so we can perform the action [cite: 555]
                performAction(v);
            }
        } else {
            // For Email and WhatsApp, no special permissions are needed, so just perform the action
            performAction(v);
        }
    }

    // This method is automatically called after the user responds to the permission dialog
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults); [cite_start]// [cite: 564]

        if (requestCode == PERMISSIONS_REQUEST_CODE) {
            [cite_start]// Check if the user granted the permissions [cite: 567]
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                [cite_start]// Permission was granted, perform the stored action [cite: 569]
                performAction(clickedView);
            } else {
                [cite_start]// Permission was denied, inform the user [cite: 574]
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void performAction(View view) {
        if (view == null) return;

        if (view.getId() == R.id.call_button) {
            [cite_start]// Create an Intent to open the phone's dialer with the number pre-filled [cite: 619, 622]
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + PHONE_NUMBER)); [cite_start]// [cite: 624]
            startActivity(intent);

        } else if (view.getId() == R.id.email_button) {
            [cite_start]// Create an Intent to open an email app [cite: 602, 610]
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("*/*"); [cite_start]// Set the type to open any email client [cite: 608, 613]
            intent.putExtra(Intent.EXTRA_EMAIL, new String[]{EMAIL_ADDRESS}); [cite_start]// [cite: 603, 612]
            intent.putExtra(Intent.EXTRA_SUBJECT, "Contact from my App"); [cite_start]// [cite: 604]
            intent.putExtra(Intent.EXTRA_TEXT, "Hello there,"); [cite_start]// [cite: 605]
            startActivity(intent);

        } else if (view.getId() == R.id.whatsapp_button) {
            // Format the number for WhatsApp (with country code, no + or 00)
            String israelPhoneNumber = "972" + PHONE_NUMBER.substring(1);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("https://api.whatsapp.com/send?phone=" + israelPhoneNumber));
            startActivity(intent);

        } else if (view.getId() == R.id.sms_button) {
            try {
                [cite_start]// Get the default SMS manager and send the text message [cite: 589, 593]
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(PHONE_NUMBER, null, SMS_MESSAGE, null, null); [cite_start]// [cite: 595]
                Toast.makeText(this, "SMS sent successfully!", Toast.LENGTH_LONG).show(); [cite_start]// [cite: 590]
            } catch (Exception e) {
                Toast.makeText(this, "Failed to send SMS.", Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        }
    }
}