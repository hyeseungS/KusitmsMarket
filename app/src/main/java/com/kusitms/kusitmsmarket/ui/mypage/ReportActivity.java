package com.kusitms.kusitmsmarket.ui.mypage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kusitms.kusitmsmarket.R;
import com.kusitms.kusitmsmarket.RetrofitAPI;
import com.kusitms.kusitmsmarket.RetrofitClient;
import com.kusitms.kusitmsmarket.request.ReportRequest;
import com.kusitms.kusitmsmarket.response.QuestionResponse;
import com.kusitms.kusitmsmarket.response.ReportResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReportActivity extends AppCompatActivity {

    TextView tvStoreLocation;
    EditText etMarketName, etStoreName;
    Button btnReport;
    RadioGroup rgMarketType;
    RadioButton rbEvery,rbNotEvery;
    CheckBox chkCash, chkCard, chkTransfer, chkGiftCard, chkZeroPay;

    int rbChecked = 0;

    String token;

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

        // 토큰 받기
        Bundle bundle = intent.getExtras();
        token = bundle.getString("user_token");

        Intent intentReportLocation = new Intent(this, ReportLocationActivity.class);

        // xml 연결
        tvStoreLocation = findViewById(R.id.reportLocation);
        etMarketName = findViewById(R.id.et_report_market_name);
        etStoreName = findViewById(R.id.et_report_store_name);
        rgMarketType = findViewById(R.id.market_radioGroup);
        rbEvery = findViewById(R.id.rg_every);
        rbNotEvery = findViewById(R.id.rg_every_not);
        chkCash = findViewById(R.id.checkbox_cash);
        chkCard = findViewById(R.id.checkbox_card);
        chkTransfer = findViewById(R.id.checkbox_account_transfer);
        chkGiftCard = findViewById(R.id.checkbox_gift_card);
        chkZeroPay = findViewById(R.id.checkbox_zero_pay);
        btnReport = findViewById(R.id.report_btn);

        tvStoreLocation.setText(address);

        // 상설, 비상설 버튼을 누르면
        rgMarketType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                switch (i) {
                    case R.id.rg_every:
                        rbChecked = 0;
                        break;
                    case R.id.rg_every_not:
                        rbChecked = 5;
                        break;
                }

            }
        });

        // 제보하기 버튼을 누르면 -> 등록
        btnReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String address = tvStoreLocation.getText().toString();

                String marketName = etMarketName.getText().toString();
                String storeName = etStoreName.getText().toString();

                int marketType = rbChecked;

                boolean giftCard = false;
                if(chkGiftCard.isChecked()) {
                    giftCard = true;
                } else{
                    giftCard = false;
                }

                ReportRequest request = new ReportRequest(marketName,address, giftCard,storeName);

                Call<ReportResponse> call = RetrofitClient.getAPIService().postReport(token,request);

                call.enqueue(new Callback<ReportResponse>() {
                    @Override
                    public void onResponse(Call<ReportResponse> call, Response<ReportResponse> response) {
                        if(response.isSuccessful()) {
                            Log.d("연결이 성공적 : ", response.body().toString());

                        } else {
                            Log.e("연결이 비정상적 : ", "error code : " + response.code());
                        }
                    }

                    @Override
                    public void onFailure(Call<ReportResponse> call, Throwable t) {
                        Log.e("연결실패", t.getMessage());
                    }
                });


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