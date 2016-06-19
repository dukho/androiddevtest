package com.nomadworks.devtest;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.nomadworks.devtest.screens.scenario1.Scenario1Fragment;
import com.nomadworks.devtest.screens.scenario2.Scenario2Fragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState == null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.container, Scenario2Fragment.newInstance());
            transaction.commit();
        }
    }
}
