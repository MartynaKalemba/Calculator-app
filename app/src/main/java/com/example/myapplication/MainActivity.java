package com.example.myapplication;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void goToAuthor(View view){
    Intent intent = new Intent(this, DisplayMessageActivity.class);
    startActivity(intent);
    }
    public void goToSimpleCalculator(View view){
        Intent intent = new Intent(this, SimpleCalculator.class);
        startActivity(intent);
    }
    public void goToAdvancedCalculator(View view){
        Intent intent = new Intent(this, AdvancedCalculator.class);
        startActivity(intent);
    }

}