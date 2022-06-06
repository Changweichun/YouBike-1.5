package com.android.youbike;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;

import com.google.android.gms.common.internal.AccountType;

public class navigationActivity extends AppCompatActivity {
    //Initialize variable
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        //assign variable
        drawerLayout = findViewById(R.id.drawer_layout);
    }

    public void ClickMenu(View view){
        //open drawer
        openDrawer(drawerLayout);
    }

    public static void openDrawer(DrawerLayout drawerLayout){
        //open drawer Layout
        drawerLayout.openDrawer(GravityCompat.START);
    }

    public void ClickLogo(View view){
        //close drawer
        closeDrawer(drawerLayout);
    }

    public static void closeDrawer(DrawerLayout drawerLayout) {
        //closed drawer layout
        //check condition

        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            // drawer is open
            //close drawer
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    public void ClickMap(View view){
        //recreate activity
        recreate();
    }

    public void ClickServiceCenter(View view){
        //redirect activity to service center
        redirectActivity(this,ServiceCenterActivity.class);
    }
    public void ClickInstructions(View view){
        redirectActivity(this,InstructionsActivity.class);
    }
    public void ClickLostAndFound(View view){
        redirectActivity(this,LostAndFoundActivity.class);
    }
    public void ClickFindBikes(View view){
        redirectActivity(this,FindBikeActivity.class);
    }
    public void ClickCardsManagement(View view){
        redirectActivity(this,CardManageActivity.class);
    }
    public void ClickRideTicket(View view){
        redirectActivity(this,RideTicketNewActivity.class);
    }
    public void ClickPayment(View view){

        redirectActivity(this,paymentActivity.class);
    }

    public static void redirectActivity(Activity activity ,Class aClass) {
        //initialize intent
        Intent intent = new Intent(activity,aClass);
        //set flag
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //start activity
        activity.startActivity(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //Closed drawer
        closeDrawer(drawerLayout);
    }
}