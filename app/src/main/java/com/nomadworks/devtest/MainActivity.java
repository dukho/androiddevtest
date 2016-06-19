package com.nomadworks.devtest;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.nomadworks.devtest.screens.scenario1.Scenario1Fragment;
import com.nomadworks.devtest.screens.scenario2.Scenario2Fragment;
import com.nomadworks.devtest.utils.Logging;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @Bind(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    @Bind(R.id.navigation_view)
    NavigationView navigationView;

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState == null) {
            switchToScenario1Fragment();
        }

        Logging.log("actionbar: " + getSupportActionBar());
        ButterKnife.bind(this);

        initUI();
    }

    private void initUI() {
        setSupportActionBar(toolbar);

        final ActionBar actionBar = getSupportActionBar();

        if(actionBar!=null) {
            actionBar.setHomeAsUpIndicator(R.mipmap.ic_menu_black_24dp);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                drawerLayout.closeDrawers();

                int id = menuItem.getItemId();

                switch (id){
                    case R.id.menu_scenario1:
                        Logging.log("Menu 1");
                        switchToScenario1Fragment();
                        return true;

                    case R.id.menu_scenario2:
                        Logging.log("Menu 2");
                        switchToScenario2Fragment();
                        return true;
                }

                return false;
            }
        });
    }

    private void switchToScenario1Fragment() {
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.container);
        if(currentFragment != null && currentFragment instanceof Scenario1Fragment) {
            return; //skip if it's already scenario 1
        }

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, Scenario1Fragment.newInstance());
        transaction.commit();
    }

    private void switchToScenario2Fragment() {
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.container);
        if(currentFragment != null && currentFragment instanceof Scenario2Fragment) {
            return; //skip if it's already scenario 2
        }

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, Scenario2Fragment.newInstance());
        transaction.commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            //for handling drawer
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
