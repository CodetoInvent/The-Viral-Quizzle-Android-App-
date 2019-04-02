package com.dream.malik.theviralquizzle;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
        import android.view.Gravity;
        import android.view.View;
        import android.widget.Toast;

        import java.util.Calendar;

        import mehdi.sakout.aboutpage.AboutPage;
        import mehdi.sakout.aboutpage.Element;

public class AboutUS extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        simulateDayNight(/* DAY */ 0);
        Element adsElement = new Element();
        adsElement.setTitle("The Viral Quizzle");

        View aboutPage = new AboutPage(this)
                .isRTL(false)
                .setImage(com.dream.malik.theviralquizzle.R.drawable.logoapp)
                .addItem(new Element().setTitle("Version 1.0"))
                .addItem(adsElement)
                .addGroup("Connect with us")
                .addEmail("092abdulmalik@gmail.com")
                .addItem(getCopyRightsElement())
                .setDescription("ABOUT THE APPLICATION:\n" +
                        "It contains a set of questions on viral topics like Current Affairs, General knowledge,Programming Language,Technical questions etc. \n User has to sign in using their Google Account and start the quiz .\nHigh Score of each game they play will be saved in the LeaderBoard." +
                      "After LOGGING OUT  from our App.\n When You LogIn You Need Not to Choose your Account Once Again\nIn Case if You Want to LogIn via Different Account just clear App Data From Settings (OR) You can Delete Your Particular Account from \n Settings->Accounts->Google->option menu ->remove Account"
                        )
                .create();

        setContentView(aboutPage);
    }


    Element getCopyRightsElement() {
        Element copyRightsElement = new Element();
        final String copyrights = String.format(getString(com.dream.malik.theviralquizzle.R.string.copy_right), Calendar.getInstance().get(Calendar.YEAR));
        copyRightsElement.setTitle(copyrights);
        copyRightsElement.setIconDrawable(com.dream.malik.theviralquizzle.R.drawable.about_icon_copy_right);
        copyRightsElement.setIconTint(mehdi.sakout.aboutpage.R.color.about_item_icon_color);
        copyRightsElement.setIconNightTint(android.R.color.white);
        copyRightsElement.setGravity(Gravity.CENTER);
        copyRightsElement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AboutUS.this, copyrights, Toast.LENGTH_SHORT).show();
            }
        });
        return copyRightsElement;
    }

    void simulateDayNight(int currentSetting) {
        final int DAY = 0;
        final int NIGHT = 1;
        final int FOLLOW_SYSTEM = 3;

        int currentNightMode = getResources().getConfiguration().uiMode
                & Configuration.UI_MODE_NIGHT_MASK;
        if (currentSetting == DAY && currentNightMode != Configuration.UI_MODE_NIGHT_NO) {
            AppCompatDelegate.setDefaultNightMode(
                    AppCompatDelegate.MODE_NIGHT_NO);
        } else if (currentSetting == NIGHT && currentNightMode != Configuration.UI_MODE_NIGHT_YES) {
            AppCompatDelegate.setDefaultNightMode(
                    AppCompatDelegate.MODE_NIGHT_YES);
        } else if (currentSetting == FOLLOW_SYSTEM) {
            AppCompatDelegate.setDefaultNightMode(
                    AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
        }
    }
}
