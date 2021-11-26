package com.kusitms.kusitmsmarket.ui.mypage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.kusitms.kusitmsmarket.AppTest;
import com.kusitms.kusitmsmarket.LoginActivity;
import com.kusitms.kusitmsmarket.MainActivity;
import com.kusitms.kusitmsmarket.R;
import com.kusitms.kusitmsmarket.RetrofitAPI;
import com.kusitms.kusitmsmarket.RetrofitClient;
import com.kusitms.kusitmsmarket.response.UserInfoResponse;
import com.kusitms.kusitmsmarket.response.UserToken;
import com.kusitms.kusitmsmarket.response.ValidateResponse;

import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserInfoActivity extends AppCompatActivity {

    TextView tvEmail;
    Button btnNicknameCheck, btnLogout, btnWithDraw, btnPhoneCheck, btnCertification, btnSave;
    Switch switchAlarm;

    ImageView ivImageProfile;
    EditText etNickname, etPw, etNewPw, etPhone, etCertification;


    boolean pass = false;
    String certification;

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

        etNickname = findViewById(R.id.user_info_nickname);
        btnNicknameCheck = findViewById(R.id.user_info_btn_check_nickname);
        btnLogout = findViewById(R.id.user_info_btn_logout);
        btnWithDraw = findViewById(R.id.user_info_btn_withdraw);
        switchAlarm = findViewById(R.id.user_info_switch_alarm);


        ivImageProfile = findViewById(R.id.user_info_iv_profileImage);
        tvEmail = findViewById(R.id.user_info_tv_email);
        etPw = findViewById(R.id.user_info_et_pw);
        etNewPw = findViewById(R.id.user_info_et_pw_new);
        etPhone = findViewById(R.id.user_info_et_phone);
        btnPhoneCheck = findViewById(R.id.user_info_btn_check_phone);

        etCertification = findViewById(R.id.user_info_et_certification);
        btnCertification = findViewById(R.id.user_info_btn_check_certification);

        btnSave = findViewById(R.id.user_info_btn_save);



        Intent loginActivity = new Intent(this, LoginActivity.class);

        // 먼저 사용자 정보 저장하고 띄어주기
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        token = bundle.getString("user_token");

        LinearLayout linearLayout = findViewById(R.id.userInfoLayout);
        linearLayout.setPadding(0, getStatusBarHeight(), 0, 0);

        Call<UserInfoResponse> callUser = RetrofitClient.getAPIService().getUserInfoData(token);

        callUser.enqueue(new Callback<UserInfoResponse>() {
            @Override
            public void onResponse(Call<UserInfoResponse> call, Response<UserInfoResponse> response) {
                UserInfoResponse userInfoResponse = response.body();
                username = userInfoResponse.getUserInfo().getUsername();
                nickname = userInfoResponse.getUserInfo().getNickname();
                phone = userInfoResponse.getUserInfo().getPhone();

                cnt = userInfoResponse.getCount();

                etNickname.setText(nickname);
                tvEmail.setText(username);

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

        // 닉네임 중복확인
        btnNicknameCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Call<ValidateResponse> call = RetrofitClient.getAPIService().getCheckDuplicateNickname(nickname);

                call.enqueue(new Callback<ValidateResponse>() {
                    @Override
                    public void onResponse(Call<ValidateResponse> call, Response<ValidateResponse> response) {
                        if(response.isSuccessful()) {
                            Log.d("연결이 성공적 : ", response.body().toString());

                            ValidateResponse.Validate validate = response.body().getValidate();

                            String bool = validate.getResponse();

                            System.out.println("bool : " + bool);

                            if(bool.equals("true")){
                                Toast.makeText(getApplicationContext(), "이미 존재하는 닉네임입니다. 다시 입력해주세요.", Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(getApplicationContext(), "가능한 닉네임입니다.", Toast.LENGTH_SHORT).show();
                                nickname = etNickname.getText().toString();
                            }

                        } else {
                            Log.e("연결이 비정상적 : ", "error code : " + response.code());
                        }
                    }

                    @Override
                    public void onFailure(Call<ValidateResponse> call, Throwable t) {
                        Log.e("연결실패", t.getMessage());
                    }
                });
            }
        });


        btnPhoneCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String phoneNumber = etPhone.getText().toString();
                certification = "" + (int)(Math.random()*10000);

                Map<String, String> querys = new HashMap<>();
                querys.put("phoneNumber", phoneNumber);
                querys.put("rand", certification);

                Call<ResponseBody> call = RetrofitClient.getAPIService().postSendSMS(querys);

                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.isSuccessful()) {
                            Log.d("연결이 성공적 : ", response.body().toString());

                        } else {
                            Log.e("연결이 비정상적 : ", "error code : " + response.code());
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e("연결실패", t.getMessage());
                    }
                });
            }
        });

        // 인증
        btnCertification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String check = etCertification.getText().toString();

                if(check.equals(certification)) {
                    Toast.makeText(getApplicationContext(), "인증되었습니다.", Toast.LENGTH_SHORT).show();
                    pass = true;
                } else {
                    Toast.makeText(getApplicationContext(), "잘못된 번호입니다.", Toast.LENGTH_SHORT).show();
                    pass = false;
                }
            }
        });


        // 로그아웃 버튼
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // 로그아웃 할 때 업데이트
                cnt = ((AppTest) getApplication()).getCount();

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


        // 정보저장
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /*String username;
                String token;
                String phone;
                String nickname;*/



                Call<ResponseBody> call = RetrofitClient.getAPIService().putModifyNickname(token, nickname);

                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.isSuccessful()) {
                            Log.d("연결이 성공적 : ", response.body().toString());

                            Toast.makeText(getApplicationContext(), "수정되었습니다.",Toast.LENGTH_SHORT).show();

                        } else {
                            Log.e("연결이 비정상적 : ", "error code : " + response.code());
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e("연결실패", t.getMessage());
                    }
                });

            }
        });

    }
    //status bar의 높이 계산
    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0)
            result = getResources().getDimensionPixelSize(resourceId);

        return result;
    }
}