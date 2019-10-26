package com.shantanu.car4sale;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    EditText e1,e2;
    Button b1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        e1 = findViewById(R.id.LoginUsername);
        e2 = findViewById(R.id.LoginPassword);
        b1 = findViewById(R.id.LoginButton);
        b1.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.LoginButton) {
            String user = String.valueOf(e1.getText());
            String pass = String.valueOf(e2.getText());
            if (user.equals(getResources().getString(R.string.Email)))
                if (pass.equals(getResources().getString(R.string.Password))) {
                    startActivity(new Intent(this, Main.class));
                    finish();
                }
            if (user.equals(getResources().getString(R.string.EmailReq)))
                if (pass.equals(getResources().getString(R.string.Password))) {
                    startActivity(new Intent(this, ViewRequests.class));
                    finish();
                }
        }
    }
}