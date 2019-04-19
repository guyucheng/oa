package com.example.oa;


import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView mMainNav;
    private FrameLayout mMainFrame;
    private View mMainNavBg;

    private MassageFragment massageFragment;
    private CalendarFragment calendarFragment;
    private ContactsFragment contactsFragment;
    private AccountFragment accountFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMainFrame = findViewById(R.id.main_frame);
        mMainNav = findViewById(R.id.main_nav);
        mMainNavBg = findViewById(R.id.main_nav_bg);

        massageFragment = new MassageFragment();
        calendarFragment = new CalendarFragment();
        contactsFragment = new ContactsFragment();
        accountFragment = new AccountFragment();


        mMainNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                /**
                 * 导航选项卡切换页面
                 */
                switch (menuItem.getItemId()){
                    case R.id.nav_massage:
                        mMainNav.setItemBackgroundResource(R.color.colorPrimary);
                        mMainNavBg.setBackgroundResource(R.color.colorPrimary);
                        setFragment(massageFragment);
                        return true;
                    case R.id.nav_calendar:
                        mMainNav.setItemBackgroundResource(R.color.colorAccent);
                        mMainNavBg.setBackgroundResource(R.color.colorAccent);
                        setFragment(calendarFragment);
                        return true;
                    case R.id.nav_contacts:
                        mMainNav.setItemBackgroundResource(R.color.colorPrimaryDark);
                        mMainNavBg.setBackgroundResource(R.color.colorPrimaryDark);
                        setFragment(contactsFragment);
                        return true;
                    case R.id.nav_account:
                        mMainNav.setItemBackgroundResource(R.color.appTheme);
                        mMainNavBg.setBackgroundResource(R.color.appTheme);
                        setFragment(accountFragment);
                        return true;

                        default:
                            return false;

                }
            }
        });

    }

    /**
     * 设置切换的页面
     * @param fragment
     */
    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame, fragment);
        fragmentTransaction.commit();
    }

}
