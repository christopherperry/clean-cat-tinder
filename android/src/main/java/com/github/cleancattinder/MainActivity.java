package com.github.cleancattinder;

import com.github.cleancattinder.catswiping.CatSwipeActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.button_google)
    public void onGoogleClick() {
        startActivity(getIntent(CatSwipeActivity.SERVICE_GOOGLE));
    }

    @OnClick(R.id.button_imgur)
    public void onImgurClick() {
        startActivity(getIntent(CatSwipeActivity.SERVICE_IMGUR));
    }

    Intent getIntent(int service) {
        return CatSwipeActivity.getCallingIntent(this, service);
    }
}
