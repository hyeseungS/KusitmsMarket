package com.kusitms.kusitmsmarket.ui.home;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.kusitms.kusitmsmarket.R;
import com.kusitms.kusitmsmarket.databinding.FragmentHomeBinding;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.LocationTrackingMode;
import com.naver.maps.map.MapView;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.UiSettings;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.overlay.Overlay;
import com.naver.maps.map.overlay.OverlayImage;
import com.naver.maps.map.util.FusedLocationSource;
import com.naver.maps.map.widget.LocationButtonView;

public class HomeFragment extends Fragment implements OnMapReadyCallback {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;
    private static final String TAG = "MainActivity";

    private static final int PERMISSION_REQUEST_CODE = 100;
    private static final String[] PERMISSIONS = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };

    private FusedLocationSource mLocationSource;
    private NaverMap mNaverMap;
    private MapView mapView;
    private View root;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        root = binding.getRoot();

        SearchView searchView = (SearchView) root.findViewById(R.id.search);
        RelativeLayout relativeLayout = (RelativeLayout) root.findViewById(R.id.mapLayout);
        RelativeLayout.LayoutParams plControl = (RelativeLayout.LayoutParams) searchView.getLayoutParams();

        // 해당 margin값 변경
        plControl.topMargin = getStatusBarHeight()+30;

        // 변경된 값의 파라미터를 해당 레이아웃 파라미터 값에 셋팅
        searchView.setLayoutParams(plControl);

        // 지도 객체 생성
        mapView = (MapView) root.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);

        // getMapAsync를 호출하여 비동기로 onMapReady 콜백 메서드 호출
        // onMapReady에서 NaverMap 객체를 받음
        mapView.getMapAsync(this);

        // 위치를 반환하는 구현체인 FusedLocationSource 생성
        mLocationSource =
                new FusedLocationSource(this, PERMISSION_REQUEST_CODE);

        ImageButton imageButton = (ImageButton) root.findViewById(R.id.my_location);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNaverMap.setLocationTrackingMode(LocationTrackingMode.Follow);
            }
        });
        return root;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
    }
    @Override
    public void onStop() {
        super.onStop();
        ((AppCompatActivity)getActivity()).getSupportActionBar().show();
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

    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {
        Log.d( TAG, "onMapReady");

        // 지도상에 마커 표시
        Marker marker = new Marker();
        marker.setPosition(new LatLng(37.5670135, 126.9783740));
        marker.setMap(naverMap);
        marker.setWidth(100);
        marker.setHeight(100);
        marker.setIcon(OverlayImage.fromResource(R.drawable.ic_location_marker));
        marker.setOnClickListener(new Overlay.OnClickListener() {
            @Override
            public boolean onClick(@NonNull Overlay overlay)
            {
                Toast.makeText(getContext(), "마커 클릭", Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        // NaverMap 객체 받아서 NaverMap 객체에 위치 소스 지정
        mNaverMap = naverMap;
        mNaverMap.setLocationSource(mLocationSource);

        mNaverMap.setLocationTrackingMode(LocationTrackingMode.Follow);

        // UI 컨트롤 재배치
        UiSettings uiSettings = mNaverMap.getUiSettings();
        uiSettings.setCompassEnabled(false); // 기본값 : true
        uiSettings.setScaleBarEnabled(false); // 기본값 : true
        uiSettings.setZoomControlEnabled(false); // 기본값 : true
        uiSettings.setLocationButtonEnabled(false); // 기본값 : false
        uiSettings.setLogoGravity(Gravity.LEFT| Gravity.BOTTOM);

//        LocationButtonView locationButtonView = root.findViewById(R.id.location);
//        locationButtonView.setMap(mNaverMap);
//        locationButtonView.setVisibility(View.GONE);

        // 권한확인. 결과는 onRequestPermissionsResult 콜백 매서드 호출
        ActivityCompat.requestPermissions(getActivity(), PERMISSIONS, PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // request code와 권한획득 여부 확인
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mNaverMap.setLocationTrackingMode(LocationTrackingMode.Follow);
            }
        }
    }
}