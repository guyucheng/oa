package com.example.oa;


import android.app.ActionBar;
import android.content.Intent;
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

import com.example.oa.GData;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView mMainNav;
    private FrameLayout mMainFrame;
    private View mMainNavBg;

    private MassageFragment massageFragment;
    private CalendarFragment calendarFragment;
    private ContactsFragment contactsFragment;
    private AccountFragment accountFragment;
    private ActionBar actionBar;
    public Account mAccount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // 账户信息
        mAccount = new Account();

        // 检查是否已经登录，如果没登录，跳转到登录界面
        if (mAccount.isLoginStatus() == false) {
            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); //注意本行的FLAG设置
//            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK); // 关闭之前的Activity
            startActivity(intent);
        }


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
                // 导航选项卡切换页面
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
     * @param fragment
     */
    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame, fragment);
        fragmentTransaction.commit();
    }


    public class Account {
        private boolean loginStatus;
        private String username;
        private String name;
        private String token;

        public Account() {
            SharedPreferences shared = getSharedPreferences("LoginData", MODE_PRIVATE);
            token = shared.getString("token", "");
            username = shared.getString("username", "");
            name = shared.getString("name", "");
            GData.setToken(token);
            if (token.isEmpty()) {
                loginStatus = false;
            } else {
                loginStatus = true;
            }
        }


        public boolean isLoginStatus() {
            return loginStatus;
        }

        public void setLoginStatus(boolean loginStatus) {
            this.loginStatus = loginStatus;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getToken() {
            return token;
        }


    }





}
