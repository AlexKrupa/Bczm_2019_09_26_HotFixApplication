package com.example.hotfix;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class SecActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sec);
    }

    @Override
    protected void onResume() {
        super.onResume();
        bugMethod();
    }

    public void  bugMethod(){
        int a = 666;
        int b = 0;
        Toast.makeText(this,""+(a/b),0).show();
    }
}
