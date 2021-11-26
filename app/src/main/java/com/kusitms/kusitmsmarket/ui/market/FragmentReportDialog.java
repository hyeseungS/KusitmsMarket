package com.kusitms.kusitmsmarket.ui.market;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

import com.kusitms.kusitmsmarket.MainActivity;
import com.kusitms.kusitmsmarket.R;
import com.kusitms.kusitmsmarket.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentReportDialog extends DialogFragment implements View.OnClickListener{
    private static final String TAG = "CustomDialogFragment";
    private static final String ARG_DIALOG_MAIN_MSG = "dialog_report_msg";

    private String mMainMsg;

    private static String userName;
    private static String reviewContent;
    private EditText editContent;

    public static FragmentReportDialog newInstance(String mainMsg) {
        Bundle bundle = new Bundle();
        bundle.putString(ARG_DIALOG_MAIN_MSG, mainMsg);

        FragmentReportDialog fragment = new FragmentReportDialog();
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
        View view = getActivity().getLayoutInflater().inflate(R.layout.review_report_dialog, null);
        view.findViewById(R.id.dialog_report_btn).setOnClickListener(this);
        view.findViewById(R.id.dialog_cancel_btn).setOnClickListener(this);
        builder.setView(view);
        Dialog dialog = builder.create();

        TextView name = (TextView) view.findViewById(R.id.finalUserName);
        editContent = (EditText) view.findViewById(R.id.reportContent);

        name.setText(userName);

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

    public static void setReviewContent(String c) {reviewContent = c;}

    private void dismissDialog() {
        this.dismiss();
    }

    private void finishDialog() {
        this.dismiss();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.dialog_review_btn:
                // storeName, memo
                String memo = editContent.getText().toString();

                // 토큰, 신고 내용 보내기 -> 응답
                String token = ((MainActivity) getActivity()).getUserToken();

                Call<Void> call = RetrofitClient.getAPIService().setStoreReviewReport(token, memo, reviewContent);

                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if(response.isSuccessful()) {
                            Log.d("연결이 성공적 : ", "수정 사항 전송");

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

