package com.kusitms.kusitmsmarket;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.kusitms.kusitmsmarket.request.LoginRequest;
import com.kusitms.kusitmsmarket.response.UserToken;
import com.kusitms.kusitmsmarket.ui.market.ReviewActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {


    EditText editId, editPassword;
    Button btnLogin, btnSignUp;

    // token
    String token;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_window_login);

        editId = findViewById(R.id.et_id);
        editPassword = findViewById(R.id.et_password);
        btnLogin = findViewById(R.id.btn_login);
        btnSignUp = findViewById(R.id.btn_register);

        // MainActivity
        Intent intentMainActivity = new Intent(this, MainActivity.class);
        // RegisterActivity
        Intent intentRegisterActivity = new Intent(this, RegisterActivity.class);
        // RegisterActivity
        Intent intentReviewActivity = new Intent(this, ReviewActivity.class);

        // 로그인 버튼 누를 때
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // id, pw
                String id = editId.getText().toString();
                String pw = editPassword.getText().toString();

                // id, pw보내기 -> 응답받기, 토큰 저장
                LoginRequest request = new LoginRequest(pw,id);

                // retrofit2
                Call<UserToken> call = RetrofitClient.getAPIService().postUserTokenData(request);

                call.enqueue(new Callback<UserToken>() {
                    @Override
                    public void onResponse(Call<UserToken> call, Response<UserToken> response) {
                        if(response.isSuccessful()) {
                            Log.d("연결이 성공적 : ", response.body().toString());
                            UserToken userToken = response.body();
                            token = userToken.getToken();

                            // 로그인 할 때 cnt 불러와
                            ((AppTest) getApplication()).setCount(userToken.getClick_cnt());


                            System.out.println("토큰 : " + token + "이 저장되었습니다.");

                            Bundle bundle = new Bundle();
                            bundle.putString("userToken", token);
                            intentMainActivity.putExtras(bundle);

                            intentMainActivity.putExtra("user_token", token);
                            startActivity(intentMainActivity);

                        } else {
                            Log.e("연결이 비정상적 : ", "error code : " + response.code());
                        }
                    }
                    @Override
                    public void onFailure(Call<UserToken> call, Throwable t) {
                        Log.e("연결실패", t.getMessage());
                    }
                });

            }
        });  // 로그인 end

        // 회원가입 버튼 누를 때
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //move To RegisterActivity
                startActivity(intentRegisterActivity);
            }
        });

    }


}