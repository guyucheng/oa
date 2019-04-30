package com.example.oa;


import android.app.ActionBar;
import android.app.Activity;
import android.content.SharedPreferences;
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
    public SharedPreferences userMsg; // 声明一个共享参数对象

    private BottomNavigationView mMainNav;
    private FrameLayout mMainFrame;
    private View mMainNavBg;

    private MassageFragment massageFragment;
    private CalendarFragment calendarFragment;
    private ContactsFragment contactsFragment;
    private AccountFragment accountFragment;
    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        actionBar = getActionBar();


        mMainFrame = findViewById(R.id.main_frame);
        mMainNav = findViewById(R.id.main_nav);
        mMainNavBg = findViewById(R.id.main_nav_bg);

        massageFragment = new MassageFragment();
        calendarFragment = new CalendarFragment();
        contactsFragment = new ContactsFragment();
        accountFragment = new AccountFragment();

//        初始化设置为第一页
        setFragment(massageFragment);
//        初始化导航栏为红色
        mMainNav.setItemBackgroundResource(R.color.colorMassage);
//        初始化导航栏背景填充为红色
        mMainNavBg.setBackgroundResource(R.color.colorMassage);
        mMainNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                /**
                 * 导航选项卡切换页面
                 */
                switch (menuItem.getItemId()) {
                    case R.id.nav_massage:
                        mMainNav.setItemBackgroundResource(R.color.colorMassage);
                        mMainNavBg.setBackgroundResource(R.color.colorMassage);
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
     *
     * @param fragment
     */
    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame, fragment);
        fragmentTransaction.commit();
    }

}
