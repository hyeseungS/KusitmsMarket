package com.kusitms.kusitmsmarket.ui.home;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RadioGroup;
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
import com.naver.maps.map.CameraPosition;
import com.naver.maps.map.CameraUpdate;
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

import java.util.Vector;

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
    private Geocoder geocoder;
    private View root;
    // 마커 정보 저장시킬 변수들 선언
    private Vector<LatLng> markersPosition;
    private Vector<Marker> activeMarkers;
    // 선택한 마커의 위치가 가시거리(카메라가 보고있는 위치 반경 3km 내)에 있는지 확인
    public final static double REFERANCE_LAT = 1 / 109.958489129649955;
    public final static double REFERANCE_LNG = 1 / 88.74;
    public final static double REFERANCE_LAT_X3 = 3 / 109.958489129649955;
    public final static double REFERANCE_LNG_X3 = 3 / 88.74;

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

        // 지도 사용권한을 받아 옴
        mLocationSource =
                new FusedLocationSource(this, PERMISSION_REQUEST_CODE);

        RadioGroup radioGroup1 = (RadioGroup) root.findViewById(R.id.market_size);

        radioGroup1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
                switch (checkedId) {
                    case R.id.big_radioBtn:
                        //Toast.makeText(getActivity().getApplicationContext(), "1", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.normal_radioBtn:
                        //Toast.makeText(getActivity().getApplicationContext(), "2", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.small_radioBtn:
                        //Toast.makeText(getActivity().getApplicationContext(), "3", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });

        RadioGroup radioGroup2 = (RadioGroup) root.findViewById(R.id.market_char);

        radioGroup2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
                switch (checkedId) {
                    case R.id.ticket_radioBtn:
                        //Toast.makeText(getActivity().getApplicationContext(), "1", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.everyday_radioBtn:
                        //Toast.makeText(getActivity().getApplicationContext(), "2", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.fiveDays_radioBtn:
                        //Toast.makeText(getActivity().getApplicationContext(), "3", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });

        ImageButton myLocationButton = (ImageButton) root.findViewById(R.id.my_location);
        myLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNaverMap.setLocationTrackingMode(LocationTrackingMode.Follow);
            }
        });

        ImageButton bookmarkButton = (ImageButton) root.findViewById(R.id.bookmark);
        bookmarkButton.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        }));

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

        // NaverMap 객체 받아서 NaverMap 객체에 위치 소스 지정
        this.mNaverMap = naverMap;
        geocoder = new Geocoder(getContext());

        // NaverMap에 locationSource를 set하면 위치 추적 기능을 사용 가능
        mNaverMap.setLocationSource(mLocationSource);

        // 위치 추적 모드 지정 가능 내 위치로 이동
       mNaverMap.setLocationTrackingMode(LocationTrackingMode.Follow);

        // UI 컨트롤 재배치
        UiSettings uiSettings = mNaverMap.getUiSettings();
        uiSettings.setCompassEnabled(false); // 기본값 : true
        uiSettings.setScaleBarEnabled(false); // 기본값 : true
        uiSettings.setZoomControlEnabled(false); // 기본값 : true
        uiSettings.setLocationButtonEnabled(false); // 기본값 : false
        uiSettings.setLogoGravity(Gravity.LEFT| Gravity.BOTTOM);

        LatLng initialPosition = new LatLng(37.506855, 127.066242);
        CameraUpdate cameraUpdate = CameraUpdate.scrollTo(initialPosition);
        naverMap.moveCamera(cameraUpdate);

        // 마커들 위치 정의 (1km 간격 동서남북 방향으로 만개씩, 총 4만개)
        // 1km 간격으로 바둑판 형식으로 마커가 생김.
        // 여기서 데이터를 받아 온 다음에 마커 생성
        // 스팟의 정보를 여기서 전부 받아 온 다음에 한번에 마커로 생성
        // 밑에 for 문에 전체 스팟 리스트로 사이즈를 주고 만들자!
        markersPosition = new Vector<LatLng>();
        for(int x = 0; x<100; ++x) {
            for(int y = 0; y<100; ++y) {
                markersPosition.add(new LatLng(
                        initialPosition.latitude - (REFERANCE_LAT*x),
                        initialPosition.longitude + (REFERANCE_LNG*y)
                ));

                markersPosition.add(new LatLng(
                        initialPosition.latitude + (REFERANCE_LAT*x),
                        initialPosition.longitude - (REFERANCE_LNG*y)
                ));

                markersPosition.add(new LatLng(
                        initialPosition.latitude + (REFERANCE_LAT*x),
                        initialPosition.longitude + (REFERANCE_LNG*y)
                ));

                markersPosition.add(new LatLng(
                        initialPosition.latitude - (REFERANCE_LAT*x),
                        initialPosition.longitude - (REFERANCE_LNG*y)
                ));
            }

            // 카메라 이동되면 호출되는 이벤트
            naverMap.addOnCameraChangeListener(new NaverMap.OnCameraChangeListener() {

                @Override
                public void onCameraChange(int reason, boolean animated) {
                    freeActiveMarkers();
                    // 정의된 마커위치들 중 가시거리 내에 있는 것들만 마커 생성
                    // 정의된 마커위치들중 가시거리 내에있는것들만 마커 생성
                    LatLng currentPosition = getCurrentPosition(naverMap);
                    for (LatLng markerPosition: markersPosition) {
                        if (!withinSightMarker(currentPosition, markerPosition))
                            continue;
                        Marker marker = new Marker();
                        marker.setPosition(markerPosition);
                        marker.setMap(naverMap);
                        marker.setIcon(OverlayImage.fromResource(R.drawable.ic_location_marker));
                        activeMarkers.add(marker);
                }
            }
        });
        }
        // 권한확인. 결과는 onRequestPermissionsResult 콜백 매서드 호출
        ActivityCompat.requestPermissions(getActivity(), PERMISSIONS, PERMISSION_REQUEST_CODE);
    }

    // 현재 카메라가 보고있는 위치
    public LatLng getCurrentPosition(NaverMap naverMap) {
        CameraPosition cameraPosition = naverMap.getCameraPosition();
        return new LatLng(cameraPosition.target.latitude, cameraPosition.target.longitude);
    }

    public boolean withinSightMarker(LatLng currentPosition, LatLng markerPosition) {
        boolean withinSightMarkerLat = Math.abs(currentPosition.latitude - markerPosition.latitude) <= REFERANCE_LAT_X3;
        boolean withinSightMarkerLng = Math.abs(currentPosition.longitude - markerPosition.longitude) <= REFERANCE_LNG_X3;
        return withinSightMarkerLat && withinSightMarkerLng;
    }

    // 지도상에 표시되고 있는 마커들 지도에서 삭제
    private void freeActiveMarkers() {
        if(activeMarkers == null) {
            activeMarkers = new Vector<Marker>();
            return;
        }
        for(Marker activeMarker: activeMarkers) {
            activeMarker.setMap(null);
        }
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