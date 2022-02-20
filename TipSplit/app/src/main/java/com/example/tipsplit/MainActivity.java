package com.example.tipsplit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText edtBillTotal,edtNumberOfPeople;
    private RadioGroup radioGroup;
    private TextView txtTipAmount,txtTotalTip,txtTotal,txtOverage;

    private Button btnGo,btnClear;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewInitilization();
        onClicks();
    }

    public void viewInitilization(){
        txtTipAmount = findViewById(R.id.textViewTipAmount);
        btnClear = findViewById(R.id.buttonClear);
        txtTotalTip = findViewById(R.id.textViewTotalWithTip);
        edtNumberOfPeople = findViewById(R.id.editTextNumberOfPeople);
        txtTotal = findViewById(R.id.textViewTotal);

        edtBillTotal = findViewById(R.id.editTextBillTotal);

        btnGo = findViewById(R.id.buttonGo);

        radioGroup = findViewById(R.id.radioGroup);
        txtOverage = findViewById(R.id.textViewOverage);
    }

    @SuppressLint("SetTextI18n")
    public void onClicks(){

        btnClear.setOnClickListener(v -> {
            txtTipAmount.setText("");
            txtTotalTip.setText("");
            txtTotal.setText("");
            txtOverage.setText("");
            edtBillTotal.setText("");
            edtNumberOfPeople.setText("");
            RadioButton r1 = findViewById(R.id.radiogroup12);
            RadioButton r2 = findViewById(R.id.radiogroup15);
            RadioButton r3 = findViewById(R.id.radiogroup18);
            RadioButton r4 = findViewById(R.id.radiogroup20);
            r1.setChecked(false);
            r2.setChecked(false);
            r3.setChecked(false);
            r4.setChecked(false);
        });

        btnGo.setOnClickListener(v -> {
            if(TextUtils.isEmpty(edtBillTotal.getText().toString())){
                edtBillTotal.setError("Enter Bill Total with tax");
                edtBillTotal.requestFocus();
                return;
            }
            else{
                edtBillTotal.setError(null);
            }

            if(radioGroup.getCheckedRadioButtonId() == -1){
                Toast.makeText(getApplicationContext(),"Select The Tip",Toast.LENGTH_LONG).show();
                return;
            }

            if(TextUtils.isEmpty(edtNumberOfPeople.getText().toString())){
                edtNumberOfPeople.setError("Enter Number of people");
                edtNumberOfPeople.requestFocus();
                return;
            }
            else{
                edtNumberOfPeople.setError(null);
            }

            double totalAmount = Double.parseDouble(txtTotalTip.getText().toString().substring(1));
            double perPerson = totalAmount / Double.parseDouble(edtNumberOfPeople.getText().toString());
//System.out.print(perPerson);
//            DecimalFormat df_obj = new DecimalFormat("#.##");
//            int newperPerson = (int) perPerson;

//            String hwdf = df_obj.format(newperPerson);
            double newPerson = Math.ceil(perPerson * 100.0) / 100.0;
            txtTotal.setText("$"+String.format("%.2f", newPerson));
//System.out.print(newPerson);

            double overageTot = Double.parseDouble(txtTotal.getText().toString().substring(1)) * Double.parseDouble(edtNumberOfPeople.getText().toString()) ;
            double overage = overageTot - Double.parseDouble(txtTotalTip.getText().toString().substring(1)) ;
            txtOverage.setText("$"+String.format("%.2f", overage));
//            txtOverage.setText("$"+String.format("%.2f",(newPerson*edtNumberOfPeople) - txtTotalTip));
        });

        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if(edtBillTotal.getText().toString().isEmpty()){
                RadioButton r1 = findViewById(R.id.radiogroup12);
                RadioButton r2 = findViewById(R.id.radiogroup15);
                RadioButton r3 = findViewById(R.id.radiogroup18);
                RadioButton r4 = findViewById(R.id.radiogroup20);
                r1.setChecked(false);
                r2.setChecked(false);
                r3.setChecked(false);
                r4.setChecked(false);
            }
            else if(checkedId != -1){
                double amount = Double.parseDouble(edtBillTotal.getText().toString());
                double tipAmount,totalAmount;
                if(checkedId == R.id.radiogroup12){
                    tipAmount = amount*0.12;
                    txtTipAmount.setText("$"+String.format("%.2f", tipAmount));
                    totalAmount = amount + tipAmount;
                    txtTotalTip.setText("$"+String.format("%.2f", totalAmount));
                }
                else  if(checkedId == R.id.radiogroup15){
                    tipAmount = amount*0.15;
                    txtTipAmount.setText("$"+String.format("%.2f", tipAmount));
                    totalAmount = amount + tipAmount;
                    txtTotalTip.setText("$"+String.format("%.2f", totalAmount));
                }
                else  if(checkedId == R.id.radiogroup18){
                    tipAmount = amount*0.18;
                    txtTipAmount.setText("$"+String.format("%.2f", tipAmount));
                    totalAmount = amount + tipAmount;
                    txtTotalTip.setText("$"+String.format("%.2f", totalAmount));
                }
                else  if(checkedId == R.id.radiogroup20){
                    tipAmount = amount*0.20;
                    txtTipAmount.setText("$"+String.format("%.2f", tipAmount));
                    totalAmount = amount + tipAmount;
                    txtTotalTip.setText("$"+String.format("%.2f", totalAmount));
                }
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {

        outState.putString("tipAmount", txtTipAmount.getText().toString());
        outState.putString("totalTip", txtTotalTip.getText().toString());
        outState.putString("totalPerPerson", txtTotal.getText().toString());
        outState.putString("overage", txtOverage.getText().toString());

        super.onSaveInstanceState(outState);

    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        txtTipAmount.setText(savedInstanceState.getString("tipAmount"));
        txtTotalTip.setText(savedInstanceState.getString("totalTip"));
        txtTotal.setText(savedInstanceState.getString("totalPerPerson"));
        txtOverage.setText(savedInstanceState.getString("overage"));
    }

}