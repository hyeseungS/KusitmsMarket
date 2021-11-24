package com.kusitms.kusitmsmarket.ui.mypage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.kusitms.kusitmsmarket.LoginActivity;
import com.kusitms.kusitmsmarket.R;
import com.kusitms.kusitmsmarket.RetrofitAPI;
import com.kusitms.kusitmsmarket.RetrofitClient;
import com.kusitms.kusitmsmarket.response.UserInfoResponse;
import com.kusitms.kusitmsmarket.response.UserToken;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserInfoActivity extends AppCompatActivity {

    TextView tvNickname;
    Button btnNicknameEdit, btnUserInfo, btnLogout, btnWithDraw;
    Switch switchAlarm;


    String username;
    String token;
    String phone;
    String nickname;
    boolean alarm;

    Integer cnt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        tvNickname = findViewById(R.id.user_info_nickname);
        btnNicknameEdit = findViewById(R.id.user_info_btn_nickname);
        btnUserInfo = findViewById(R.id.user_info_btn_info);
        btnLogout = findViewById(R.id.user_info_btn_logout);
        btnWithDraw = findViewById(R.id.user_info_btn_withdraw);
        switchAlarm = findViewById(R.id.user_info_switch_alarm);

        Intent loginActivity = new Intent(this, LoginActivity.class);

        // 먼저 사용자 정보 저장하고 띄어주기
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        token = bundle.getString("userToken");

        Call<UserInfoResponse> callUser = RetrofitClient.getAPIService().getUserInfoData(token);

        callUser.enqueue(new Callback<UserInfoResponse>() {
            @Override
            public void onResponse(Call<UserInfoResponse> call, Response<UserInfoResponse> response) {
                UserInfoResponse userInfoResponse = response.body();
                username = userInfoResponse.getUserInfo().getUsername();
                nickname = userInfoResponse.getUserInfo().getNickname();
                phone = userInfoResponse.getUserInfo().getPhone();

                cnt = userInfoResponse.getCount();

                tvNickname.setText(nickname);

                if(response.isSuccessful()) {
                    Log.d("연결이 성공적 : ", response.body().toString());

                } else {
                    Log.e("연결이 비정상적 : ", "error code : " + response.code());
                }
            }

            @Override
            public void onFailure(Call<UserInfoResponse> call, Throwable t) {
                Log.e("연결실패", t.getMessage());
            }
        });

        // 닉네임 수정 버튼
        btnNicknameEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });


        // 유저정보 버튼
        btnUserInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Call<UserInfoResponse> call = RetrofitClient.getAPIService().getUserInfoData(token);

                call.enqueue(new Callback<UserInfoResponse>() {
                    @Override
                    public void onResponse(Call<UserInfoResponse> call, Response<UserInfoResponse> response) {
                        if(response.isSuccessful()) {
                            Log.d("연결이 성공적 : ", response.body().toString());

                            UserInfoResponse userInfoResponse = response.body();

                            System.out.println("유저정보" + userInfoResponse.toString());

                        } else {
                            Log.e("연결이 비정상적 : ", "error code : " + response.code());
                        }

                    }

                    @Override
                    public void onFailure(Call<UserInfoResponse> call, Throwable t) {
                        Log.e("연결실패", t.getMessage());
                    }
                });
            }
        });

        // 로그아웃 버튼
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Call<String> call = RetrofitClient.getAPIService().putUserInfo(token, cnt);

                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if(response.isSuccessful()) {
                            Log.d("연결이 성공적 : ", response.body().toString());

                            startActivity(loginActivity);

                        } else {
                            Log.e("연결이 비정상적 : ", "error code : " + response.code());
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Log.e("연결실패", t.getMessage());
                    }
                });

            }
        });

        // 회원탈퇴 버튼
        btnWithDraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });

        // 푸시알람 버튼
        switchAlarm.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                Call<Boolean> call = RetrofitClient.getAPIService().onOffPushAlarm(username);

                call.enqueue(new Callback<Boolean>() {
                    @Override
                    public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                        if(response.isSuccessful()) {
                            Log.d("연결이 성공적 : ", response.body().toString());

                        } else {
                            Log.e("연결이 비정상적 : ", "error code : " + response.code());
                        }
                    }

                    @Override
                    public void onFailure(Call<Boolean> call, Throwable t) {
                        Log.e("연결실패", t.getMessage());
                    }
                });
            }
        });
    }
}