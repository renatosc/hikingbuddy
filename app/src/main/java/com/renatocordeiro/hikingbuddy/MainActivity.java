package com.renatocordeiro.hikingbuddy;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import static com.renatocordeiro.hikingbuddy.Utils.print;

public class MainActivity extends AppCompatActivity {

    private Fragment currFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // setting the context
        Utils.setContext(this);


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        selectFragment(new MyHikeFragment());
    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment frag = null;
            switch (item.getItemId()) {
                case R.id.navigation_my_hike:
                    frag = new MyHikeFragment();
                    break;

                case R.id.navigation_map:
                    frag = new MapFragment();
                    break;

                default:
                    return false;
            }
            return selectFragment(frag);
        }
    };


    private boolean selectFragment(Fragment frag) {
        if (frag != null && currFragment != frag) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.add(R.id.frame, frag, frag.getTag());
            if (currFragment != null){
                print("removing previous fragment");
                ft.remove(currFragment);
                if (currFragment instanceof MapFragment) {
                    print("manually destroying map");
                    ((MapFragment) currFragment).mapView.onDestroy();
                }
            }
            ft.commit();
            currFragment = frag;
            return true;
        }
        return false;
    }


}
