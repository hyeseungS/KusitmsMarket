package com.kusitms.kusitmsmarket.ui.mypage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kusitms.kusitmsmarket.R;

public class ReportActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        Intent intent = getIntent();
        String address = intent.getStringExtra("address");

        TextView addressView = findViewById(R.id.reportLocation);
        addressView.setText(address);
        LinearLayout linearLayout = findViewById(R.id.reportLayout);
        linearLayout.setPadding(0, getStatusBarHeight(), 0, 0);

        Button editAddress = findViewById(R.id.edit_address);

        editAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ReportActivity.this, ReportLocationActivity.class);
                startActivity(intent);
            }
        });
    }

    //status bar의 높이 계산
    public int getStatusBarHeight()
    {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0)
            result = getResources().getDimensionPixelSize(resourceId);

        return result;
    }
}