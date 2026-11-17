package com.example.famer_records;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.Serializable;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "LIFECYCLE";

    // View references
    private EditText etFarmerRef;
    private EditText etCrop;
    private EditText etQuantity;
    private Button btnAddRecord;
    private TextView tvRecordCount;
    private TextView tvError;
    private ListView lvRecords;

    // Data
    private ArrayList<FarmerRecord> records;
    private FarmerRecordAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate" + (savedInstanceState == null ? " - fresh start" : " - restored"));

        WindowCompat.enableEdgeToEdge(getWindow());
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initViews();
        setupAdapter();
        setupClickListener();

        if (savedInstanceState != null) {
            restoreState(savedInstanceState);
        }
    }

    private void initViews() {
        etFarmerRef = findViewById(R.id.etFarmerRef);
        etCrop = findViewById(R.id.etCrop);
        etQuantity = findViewById(R.id.etQuantity);
        btnAddRecord = findViewById(R.id.btnAddRecord);
        tvRecordCount = findViewById(R.id.tvRecordCount);
        tvError = findViewById(R.id.tvError);
        lvRecords = findViewById(R.id.lvRecords);
    }

    private void setupAdapter() {
        records = new ArrayList<>();
        adapter = new FarmerRecordAdapter(this, records);
        lvRecords.setAdapter(adapter);
        updateRecordCount();
    }

    private void setupClickListener() {
        btnAddRecord.setOnClickListener(v -> handleAddRecord());
    }

    private void handleAddRecord() {
        String farmer = etFarmerRef.getText().toString().trim();
        String crop = etCrop.getText().toString().trim();
        String qtyStr = etQuantity.getText().toString().trim();

        String error = validate(farmer, crop, qtyStr);
        if (error != null) {
            showError(error);
            return;
        }

        double quantity = Double.parseDouble(qtyStr);
        FarmerRecord record = new FarmerRecord(farmer, crop, quantity);
        records.add(record);
        adapter.notifyDataSetChanged();
        updateRecordCount();
        clearFields();
        hideError();
    }

    /**
     * Validates input fields.
     * Returns null if valid, otherwise returns an error message.
     */
    private String validate(String farmer, String crop, String qty) {
        if (farmer.isEmpty()) {
            return "Farmer reference/name is required";
        }
        if (crop.isEmpty()) {
            return "Crop is required";
        }
        if (qty.isEmpty()) {
            return "Quantity is required";
        }

        double quantity;
        try {
            quantity = Double.parseDouble(qty);
        } catch (NumberFormatException e) {
            return "Quantity must be a valid number";
        }

        // Reject NaN and Infinity (covers inputs like "NaN" or "1e999")
        if (Double.isNaN(quantity) || Double.isInfinite(quantity)) {
            return "Quantity must be a finite number";
        }

        // Reject negative quantity
        // ZERO IS ALLOWED (a zero weekly harvest is a valid business event)
        if (quantity < 0) {
            return "Quantity cannot be negative";
        }

        return null;
    }

    private void showError(String message) {
        tvError.setText(message);
        tvError.setVisibility(View.VISIBLE);
    }

    private void hideError() {
        tvError.setVisibility(View.GONE);
        tvError.setText("");
    }

    private void updateRecordCount() {
        tvRecordCount.setText("Records: " + records.size());
    }

    private void clearFields() {
        etFarmerRef.setText("");
        etCrop.setText("");
        etQuantity.setText("");
    }

    private void restoreState(Bundle savedInstanceState) {
        // Restore draft text
        String farmerDraft = savedInstanceState.getString("farmer_draft", "");
        String cropDraft = savedInstanceState.getString("crop_draft", "");
        String qtyDraft = savedInstanceState.getString("qty_draft", "");
        etFarmerRef.setText(farmerDraft);
        etCrop.setText(cropDraft);
        etQuantity.setText(qtyDraft);

        // Restore records list
        @SuppressWarnings("unchecked")
        ArrayList<FarmerRecord> restoredRecords =
                (ArrayList<FarmerRecord>) savedInstanceState.getSerializable("records_list");
        if (restoredRecords != null) {
            records.clear();
            records.addAll(restoredRecords);
            adapter.notifyDataSetChanged();
            updateRecordCount();
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "onSaveInstanceState");

        // Save draft text
        outState.putString("farmer_draft", etFarmerRef.getText().toString());
        outState.putString("crop_draft", etCrop.getText().toString());
        outState.putString("qty_draft", etQuantity.getText().toString());

        // Save records list (Serializable)
        outState.putSerializable("records_list", records);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.d(TAG, "onRestoreInstanceState");
    }

    // Lifecycle logging
    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }

    /*
     * State lives in memory and the saved-instance Bundle only.
     * It survives rotation but NOT process death or app restart.
     */
}