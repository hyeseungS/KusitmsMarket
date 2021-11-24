package com.kusitms.kusitmsmarket.ui.market;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.kusitms.kusitmsmarket.Image;
import com.kusitms.kusitmsmarket.R;
import com.kusitms.kusitmsmarket.RetrofitClient;
import com.kusitms.kusitmsmarket.Review;
import com.kusitms.kusitmsmarket.StoreList;
import com.kusitms.kusitmsmarket.databinding.FragmentMarketDetailBinding;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MarketDetailFragment extends Fragment implements View.OnClickListener {

    private FragmentMarketDetailBinding binding;
    private static String storeName;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentMarketDetailBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        TextView store = (TextView) root.findViewById(R.id.store);
        store.setText(storeName);
        TextView address = (TextView) root.findViewById(R.id.address);
        TextView time = (TextView) root.findViewById(R.id.time);
        TextView link = (TextView) root.findViewById(R.id.link);
        TextView phone = (TextView) root.findViewById(R.id.phoneNumber);
        TextView userName = (TextView) root.findViewById(R.id.userName);
        TextView imgCnt = (TextView) root.findViewById(R.id.imgCnt);
        ImageView img1 = (ImageView) root.findViewById(R.id.storeImg1);
        ImageView img2 = (ImageView) root.findViewById(R.id.storeImg1);
        ImageButton img3 = (ImageButton) root.findViewById(R.id.storeImgMore);
        TextView reviewCnt = (TextView) root.findViewById(R.id.reviewCnt);
        TextView totalScore = (TextView) root.findViewById(R.id.totalScore);
        TextView firstUserName = (TextView) root.findViewById(R.id.firstUserName);
        TextView personalScore = (TextView) root.findViewById(R.id.personalScore);
        TextView reviewContent = (TextView) root.findViewById(R.id.reviewContent);


        RetrofitClient.getAPIService().getSearchStoreData(storeName).enqueue(new Callback<StoreList>() {
            @Override
            public void onResponse(Call<StoreList> call, Response<StoreList> response) {
                Log.d("TAG", response.code() + "");

                if (response.isSuccessful() && response.body() != null) {
                    StoreList resource = response.body();
                    List<StoreList.StoreData> dataList = resource.data;
                    if(dataList.size() != 0) {
                        StoreList.StoreData data = dataList.get(0);
                        if (data.getStoreAddress() != null)
                            address.setText(data.getStoreAddress());
//                    if(data.getStoreTime() != null)
//                        time.setText(data.getStoreTime());
//                    if(data.getStoreLink() != null)
//                        link.setText(data.getStoreLinke());
                        if (data.getStorePhone() != null)
                            phone.setText(data.getStorePhone());
                        if (data.getUserName() != null) {
                            userName.setText("최종 작성자(" + data.getUserName() + ")");
                            firstUserName.setText(data.getUserName());
                        }
                    }

                    Log.d("test", "성공");
                }
            }

            @Override
            public void onFailure(Call<StoreList> call, Throwable t) {
                Log.d("test", "실패");
                t.printStackTrace();
            }
        });

        RetrofitClient.getAPIService().getStoreImgData(storeName).enqueue(new Callback<Image>() {
            @Override
            public void onResponse(Call<Image> call, Response<Image> response) {
                Log.d("TAG", response.code() + "");

                if (response.isSuccessful() && response.body() != null) {
                    Image resource = response.body();
                    List<String> dataList = resource.data;
                    imgCnt.setText("(" + resource.count + "개)");
                    if(dataList.size() != 0) {
                        if (dataList.get(0) != null)
                            img1.setImageURI(Uri.parse(dataList.get(0)));
                        if (dataList.size() > 1 && dataList.get(1) != null)
                            img2.setImageURI(Uri.parse(dataList.get(1)));
                    }
                    Log.d("test", "성공");
                }
            }

            @Override
            public void onFailure(Call<Image> call, Throwable t) {
                Log.d("test", "실패");
                t.printStackTrace();
            }
        });

        RetrofitClient.getAPIService().getStoreReviewData(storeName).enqueue(new Callback<Review>() {
            @Override
            public void onResponse(Call<Review> call, Response<Review> response) {
                Log.d("TAG", response.code() + "");

                if (response.isSuccessful() && response.body() != null) {
                    Review resource = response.body();
                    List<Review.ReviewData> dataList = resource.data;
                    reviewCnt.setText("리뷰(" + resource.count + "개)");
                    if(dataList.size() != 0 && dataList.get(0) != null) {
                        personalScore.setText(dataList.get(0).getReviewScore());
                        reviewContent.setText(dataList.get(0).getReviewMemo());
                    }

                    Log.d("test", "성공");
                }
            }

            @Override
            public void onFailure(Call<Review> call, Throwable t) {
                Log.d("test", "실패");
                t.printStackTrace();
            }
        });

        AppCompatButton open = (AppCompatButton) root.findViewById(R.id.edit_button);
        open.setOnClickListener(this);
        return root;
    }

    public static void setStoreName(String s) {
        storeName = s;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override

    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.edit_button:

                FragmentManager fm = getActivity().getSupportFragmentManager();
                FragmentEditDialog dialogFragment = new FragmentEditDialog();
                dialogFragment.show(fm, "fragment_dialog_test");

                break;

        }

    }

}
