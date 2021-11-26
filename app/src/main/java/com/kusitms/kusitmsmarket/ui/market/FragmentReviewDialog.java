package com.kusitms.kusitmsmarket.ui.market;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.DialogFragment;

import com.kusitms.kusitmsmarket.MainActivity;
import com.kusitms.kusitmsmarket.R;
import com.kusitms.kusitmsmarket.RetrofitClient;
import com.kusitms.kusitmsmarket.response.UserInfoResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentReviewDialog extends DialogFragment implements View.OnClickListener{
    private static final String TAG = "CustomDialogFragment";
    private static final String ARG_DIALOG_MAIN_MSG = "dialog_review_msg";

    private String mMainMsg;
    private EditText editContent;
    private RatingBar reviewRating;
    private String storeName;
    private static String userName;
    public static FragmentReviewDialog newInstance(String mainMsg) {
        Bundle bundle = new Bundle();
        bundle.putString(ARG_DIALOG_MAIN_MSG, mainMsg);

        FragmentReviewDialog fragment = new FragmentReviewDialog();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mMainMsg = getArguments().getString(ARG_DIALOG_MAIN_MSG);
        }
    }


    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        View view = getActivity().getLayoutInflater().inflate(R.layout.edit_review_dialog, null);

        view.findViewById(R.id.dialog_review_btn).setOnClickListener(this);
        view.findViewById(R.id.dialog_cancel_btn).setOnClickListener(this);
        builder.setView(view);
        Dialog dialog = builder.create();

        AppCompatButton registerImg = (AppCompatButton) view.findViewById(R.id.registerImage);
        registerImg.setOnClickListener(this);
        TextView userName = (TextView) view.findViewById(R.id.reviewUserName);
        editContent = (EditText) view.findViewById(R.id.reviewContent);
        reviewRating = (RatingBar) view.findViewById(R.id.registerRating);
        // 가게 이름 가져오기
        Bundle b = getArguments();
        if(b.getString("storeName") != null)
            storeName = b.getString("storeName");

        System.out.println(storeName + ((ReviewActivity) getActivity()).getUserToken());
        // 사용자 닉네임 가져오기
        Call<UserInfoResponse> callUser = RetrofitClient.getAPIService().getUserInfoData(((ReviewActivity) getActivity()).getUserToken());

        callUser.enqueue(new Callback<UserInfoResponse>() {
            @Override
            public void onResponse(Call<UserInfoResponse> call, Response<UserInfoResponse> response) {
                if(response.body() != null) {
                    UserInfoResponse userInfoResponse = response.body();
                    if (userInfoResponse.getUserInfo().getNickname() != null)
                        userName.setText(userInfoResponse.getUserInfo().getNickname());

                    if (response.isSuccessful()) {
                        Log.d("연결이 성공적 : ", response.body().toString());

                    } else {
                        Log.e("연결이 비정상적 : ", "error code : " + response.code());
                    }
                }
            }

            @Override
            public void onFailure(Call<UserInfoResponse> call, Throwable t) {
                Log.e("연결실패", t.getMessage());
            }
        });
        return dialog;
    }

/*
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_custom_dialog, container, false);
        ((TextView)view.findViewById(R.id.dialog_confirm_msg)).setText(mMainMsg);
        view.findViewById(R.id.dialog_confirm_btn).setOnClickListener(this);
        Dialog dialog = getDialog();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(false);
        return view;
    }
*/
    public static void setUserName(String u) {userName = u;}

    private void dismissDialog() {
        this.dismiss();
    }

    private void finishDialog() {
        this.dismiss();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.registerImage:
                break;

            case R.id.dialog_review_btn:
                // storeName, memo
                String memo = editContent.getText().toString();
                double score = Double.parseDouble(String.valueOf(reviewRating.getRating()));
                // 토큰, 리뷰 보내기 -> 응답
                String token = ((ReviewActivity) getActivity()).getUserToken();

                Call<Void> call = RetrofitClient.getAPIService().setStoreReview(token, memo, score, storeName);

                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if(response.isSuccessful()) {
                            Log.d("연결이 성공적 : ", "리뷰 전송");

                        } else {
                            Log.e("연결이 비정상적 : ", "error code : " + response.code());
                        }
                    }
                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        Log.e("연결실패", t.getMessage());
                    }
                });
                finishDialog();
                break;

            case R.id.dialog_cancel_btn:
                dismissDialog();
                break;
        }
    }
}

