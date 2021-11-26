package com.kusitms.kusitmsmarket.ui.home;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.kusitms.kusitmsmarket.response.Image;
import com.kusitms.kusitmsmarket.MainActivity;
import com.kusitms.kusitmsmarket.response.MarketList;
import com.kusitms.kusitmsmarket.R;
import com.kusitms.kusitmsmarket.RetrofitClient;
import com.kusitms.kusitmsmarket.adapter.ViewPagerAdapter;
import com.kusitms.kusitmsmarket.databinding.FragmentHomeBinding;
import com.kusitms.kusitmsmarket.response.StoreList;
import com.kusitms.kusitmsmarket.ui.market.MarketDetailFragment;
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

import java.io.IOException;
import java.util.List;
import java.util.Vector;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment implements OnMapReadyCallback {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;
    private static final String TAG = "MainActivity";

    private static final int PERMISSION_REQUEST_CODE = 100;
    private static final String[] PERMISSIONS = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };

    private RadioGroup radioGroup1;
    private RadioGroup radioGroup2;

    private FusedLocationSource mLocationSource;
    private NaverMap mNaverMap;
    private MapView mapView;
    private Geocoder geocoder;
    private View root;
    // 마커 정보 저장시킬 변수들 선언
    private Vector<LatLng> markersPosition;
    private Vector<Marker> activeMarkers;
    private Vector<String> storeName;
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

        // SearchLayout 위에 Status Bar 간격 주기
        LinearLayout linearLayout = (LinearLayout) root.findViewById(R.id.search);
        linearLayout.setPadding(0, getStatusBarHeight() + 30, 0, 0);

        // 검색 창 받아오기
        SearchView searchMarket = (SearchView) root.findViewById(R.id.searchMarket);
        SearchView searchStore = (SearchView) root.findViewById(R.id.searchStore);

        // 지도 객체 생성
        mapView = (MapView) root.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);

        // getMapAsync를 호출하여 비동기로 onMapReady 콜백 메서드 호출
        // onMapReady에서 NaverMap 객체를 받음
        mapView.getMapAsync(this);

        // 지도 사용권한을 받아 옴
        mLocationSource =
                new FusedLocationSource(this, PERMISSION_REQUEST_CODE);

        // 시장 검색 쿼리
        searchMarket.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                freeActiveMarkers();
                RetrofitClient.getAPIService().getSearchMarketData(s).enqueue(new Callback<MarketList>() {
                    @Override
                    public void onResponse(Call<MarketList> call, Response<MarketList> response) {
                        Log.d("TAG", response.code() + "");

                        if (response.isSuccessful() && response.body() != null) {
                            MarketList resource = response.body();
                            List<MarketList.MarketData> dataList = resource.data;
                            List<Address> addressList = null;
                            try {
                                // 받아온 주소를 지오 코딩을 이용해 변환
                                if (dataList.get(0).getMarketAddress() != null)
                                    addressList = geocoder.getFromLocationName(
                                            dataList.get(0).getMarketAddress(), // 주소
                                            1); // 최대 검색 결과 개수
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            if (addressList != null && addressList.size() != 0) {
                                double latitude = addressList.get(0).getLatitude();
                                double longitude = addressList.get(0).getLongitude();

                                // 좌표(위도, 경도) 생성
                                LatLng point = new LatLng(latitude, longitude);
                                markersPosition.add(point);
                                storeName.add("시장");
                                CameraUpdate cameraUpdate = CameraUpdate.scrollTo(point);
                                mNaverMap.moveCamera(cameraUpdate);
                            }
                            Log.d("test", "성공");
                        }
                    }

                    @Override
                    public void onFailure(Call<MarketList> call, Throwable t) {
                        Log.d("test", "실패");
                        t.printStackTrace();
                    }
                });
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });

        // 점포 검색 쿼리
        searchStore.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                freeActiveMarkers();
                RetrofitClient.getAPIService().getSearchStoreData(s).enqueue(new Callback<StoreList>() {
                    @Override
                    public void onResponse(Call<StoreList> call, Response<StoreList> response) {
                        Log.d("TAG", response.code() + "");

                        if (response.isSuccessful() && response.body() != null) {
                            StoreList resource = response.body();
                            List<StoreList.StoreData> dataList = resource.data;
                            List<Address> addressList = null;
                            try {
                                // 받아온 주소를 지오 코딩을 이용해 변환
                                if (dataList.get(0).getStoreAddress() != null)
                                    addressList = geocoder.getFromLocationName(
                                            dataList.get(0).getStoreAddress(), // 주소
                                            1); // 최대 검색 결과 개수
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            if (addressList != null && addressList.size() != 0) {
                                double latitude = addressList.get(0).getLatitude();
                                double longitude = addressList.get(0).getLongitude();

                                // 좌표(위도, 경도) 생성
                                LatLng point = new LatLng(latitude, longitude);
                                markersPosition.add(point);
                                storeName.add(dataList.get(0).getStoreName());
                                CameraUpdate cameraUpdate = CameraUpdate.scrollTo(point);
                                mNaverMap.moveCamera(cameraUpdate);
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
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });


        // 필터 받아오기
        radioGroup1 = (RadioGroup) root.findViewById(R.id.market_size);
        radioGroup2 = (RadioGroup) root.findViewById(R.id.market_char);

        // 필터 1
        radioGroup1.clearCheck();
        radioGroup1.setOnCheckedChangeListener(listener1);
        // 필터2
        radioGroup2.clearCheck();
        radioGroup2.setOnCheckedChangeListener(listener2);

        // 현재 위치 버튼
        ImageButton myLocationButton = (ImageButton) root.findViewById(R.id.my_location);
        myLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                freeActiveMarkers();
                RetrofitClient.getAPIService().getMarketData().enqueue(new Callback<MarketList>() {
                    @Override
                    public void onResponse(Call<MarketList> call, Response<MarketList> response) {
                        Log.d("TAG", response.code() + "");

                        MarketList resource = response.body();
                        List<MarketList.MarketData> dataList = resource.data;

                        for (MarketList.MarketData data : dataList) {
                            List<Address> addressList = null;
                            try {
                                // 받아온 주소를 지오 코딩을 이용해 변환
                                if (data.getMarketAddress() != null)
                                    addressList = geocoder.getFromLocationName(
                                            data.getMarketAddress(), // 주소
                                            1); // 최대 검색 결과 개수
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            if (addressList != null && addressList.size() != 0) {
                                double latitude = addressList.get(0).getLatitude();
                                double longitude = addressList.get(0).getLongitude();

                                // 좌표(위도, 경도) 생성
                                LatLng point = new LatLng(latitude, longitude);
                                markersPosition.add(point);
                                storeName.add("시장");
                            }
                        }

                        Log.d("test", "성공");
                    }


                    @Override
                    public void onFailure(Call<MarketList> call, Throwable t) {
                        Log.d("test", "실패");
                        t.printStackTrace();
                    }
                });
                mNaverMap.setLocationTrackingMode(LocationTrackingMode.Follow);
            }

        });

        ImageButton bookmarkButton = (ImageButton) root.findViewById(R.id.bookmark);
        bookmarkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                freeActiveMarkers();
                RetrofitClient.getAPIService().getStoreLike(((MainActivity) getActivity()).getUserToken()).enqueue(new Callback<StoreList>() {
                    @Override
                    public void onResponse(Call<StoreList> call, Response<StoreList> response) {
                        Log.d("TAG", response.code() + "");

                        StoreList resource = response.body();
                        List<StoreList.StoreData> dataList = resource.data;

                        for (StoreList.StoreData data : dataList) {
                            List<Address> addressList = null;
                            try {
                                // 받아온 주소를 지오 코딩을 이용해 변환
                                if (data.getStoreAddress() != null)
                                    addressList = geocoder.getFromLocationName(
                                            data.getStoreAddress(), // 주소
                                            1); // 최대 검색 결과 개수
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            if (addressList != null && addressList.size() != 0) {
                                double latitude = addressList.get(0).getLatitude();
                                double longitude = addressList.get(0).getLongitude();

                                // 좌표(위도, 경도) 생성
                                LatLng point = new LatLng(latitude, longitude);
                                markersPosition.add(point);
                                storeName.add(data.getStoreName());
                            }
                        }

                        Log.d("test", "성공");
                    }


                    @Override
                    public void onFailure(Call<StoreList> call, Throwable t) {
                        Log.d("test", "실패");
                        t.printStackTrace();
                    }
                });
            }
        });

        RetrofitClient.getAPIService().

                getEventImage().

                enqueue(new Callback<Image>() {
                    @Override
                    public void onResponse(Call<Image> call, Response<Image> response) {
                        Log.d("TAG", response.code() + "");

                        if (response.isSuccessful() && response.body() != null) {
                            Image resource = response.body();
                            List<String> img = resource.data;
                            ViewPager2 viewpager = (ViewPager2) root.findViewById(R.id.event_image);

                            viewpager.setOffscreenPageLimit(1);
                            viewpager.setAdapter(new ViewPagerAdapter(getContext(), img));
                            Log.d("test", "성공");
                        }
                    }

                    @Override
                    public void onFailure(Call<Image> call, Throwable t) {
                        Log.d("test", "실패");
                        t.printStackTrace();
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
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
    }

    @Override
    public void onStop() {
        super.onStop();
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
    }

    //status bar의 높이 계산
    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0)
            result = getResources().getDimensionPixelSize(resourceId);

        return result;
    }

    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {
        Log.d(TAG, "onMapReady");

        // NaverMap 객체 받아서 NaverMap 객체에 위치 소스 지정
        this.mNaverMap = naverMap;
        geocoder = new Geocoder(getContext());

        // NaverMap에 locationSource를 set하면 위치 추적 기능을 사용 가능
        mNaverMap.setLocationSource(mLocationSource);

        // UI 컨트롤 재배치
        UiSettings uiSettings = mNaverMap.getUiSettings();
        uiSettings.setCompassEnabled(false); // 기본값 : true
        uiSettings.setScaleBarEnabled(false); // 기본값 : true
        uiSettings.setZoomControlEnabled(false); // 기본값 : true
        uiSettings.setLocationButtonEnabled(false); // 기본값 : false
        uiSettings.setLogoGravity(Gravity.LEFT | Gravity.BOTTOM);

        LatLng initialPosition = new LatLng(37.506855, 127.066242);
        CameraUpdate cameraUpdate = CameraUpdate.scrollTo(initialPosition);
        naverMap.moveCamera(cameraUpdate);

        markersPosition = new Vector<LatLng>();
        storeName = new Vector<String>();

        RetrofitClient.getAPIService().getMarketData().enqueue(new Callback<MarketList>() {
            @Override
            public void onResponse(Call<MarketList> call, Response<MarketList> response) {
                Log.d("TAG", response.code() + "");

                MarketList resource = response.body();
                List<MarketList.MarketData> dataList = resource.data;

                for (MarketList.MarketData data : dataList) {
                    List<Address> addressList = null;
                    try {
                        // 받아온 주소를 지오 코딩을 이용해 변환
                        if (data.getMarketAddress() != null)
                            addressList = geocoder.getFromLocationName(
                                    data.getMarketAddress(), // 주소
                                    1); // 최대 검색 결과 개수
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    if (addressList != null && addressList.size() != 0) {
                        double latitude = addressList.get(0).getLatitude();
                        double longitude = addressList.get(0).getLongitude();

                        // 좌표(위도, 경도) 생성
                        LatLng point = new LatLng(latitude, longitude);
                        markersPosition.add(point);
                        storeName.add("시장");
                    }
                }

                Log.d("test", "성공");
            }

            @Override
            public void onFailure(Call<MarketList> call, Throwable t) {
                Log.d("test", "실패");
                t.printStackTrace();
            }

        });
        // 위치 추적 모드 지정 가능 내 위치로 이동
        mNaverMap.setLocationTrackingMode(LocationTrackingMode.Follow);
        // 카메라 이동되면 호출되는 이벤트
        naverMap.addOnCameraChangeListener(new NaverMap.OnCameraChangeListener() {

            @Override
            public void onCameraChange(int reason, boolean animated) {
                freeActiveMarkers();
                // 정의된 마커위치들 중 가시거리 내에 있는 것들만 마커 생성
                // 정의된 마커위치들중 가시거리 내에있는것들만 마커 생성
                LatLng currentPosition = getCurrentPosition(naverMap);
                for (int i = 0; i < markersPosition.size(); i++) {
                    if (!withinSightMarker(currentPosition, markersPosition.get(i)))
                        continue;
                    Marker marker = new Marker();
                    marker.setPosition(markersPosition.get(i));
                    marker.setMap(naverMap);
                    String sn = "진미반찬";
                    marker.setOnClickListener(new Overlay.OnClickListener() {
                        @Override
                        public boolean onClick(@NonNull Overlay overlay) {
                            // StoreName 넘기기
                            if (sn.equals("시장")) {
                                MarketDetailFragment.setStoreName(sn);
                                ((MainActivity) MainActivity.mContext).moveToMarker();
                            }
                            return false;
                        }
                    });
                    marker.setIcon(OverlayImage.fromResource(R.drawable.ic_location_marker));
                    activeMarkers.add(marker);
                }
            }
        });
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
        if (activeMarkers == null) {
            activeMarkers = new Vector<Marker>();
            return;
        }
        for (Marker activeMarker : activeMarkers) {
            activeMarker.setMap(null);
        }
        storeName = new Vector<String>();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // request code와 권한획득 여부 확인
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mNaverMap.setLocationTrackingMode(LocationTrackingMode.Follow);
            }
        }
    }

    private RadioGroup.OnCheckedChangeListener listener1 = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int i) {
            if (i != -1) {
                radioGroup1.setOnCheckedChangeListener(null);
                radioGroup2.clearCheck();
                radioGroup2.setOnCheckedChangeListener(listener2);
            }
            // checkedId is the RadioButton selected
            switch (i) {
                case R.id.big_radioBtn:
                    freeActiveMarkers();
                    RetrofitClient.getAPIService().getBigMarketData().enqueue(new Callback<MarketList>() {
                        @Override
                        public void onResponse(Call<MarketList> call, Response<MarketList> response) {
                            Log.d("TAG", response.code() + "");

                            MarketList resource = response.body();
                            List<MarketList.MarketData> dataList = resource.data;

                            for (MarketList.MarketData data : dataList) {
                                List<Address> addressList = null;
                                try {
                                    // 받아온 주소를 지오 코딩을 이용해 변환
                                    if (data.getMarketAddress() != null)
                                        addressList = geocoder.getFromLocationName(
                                                data.getMarketAddress(), // 주소
                                                1); // 최대 검색 결과 개수
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                                if (addressList != null && addressList.size() != 0) {
                                    double latitude = addressList.get(0).getLatitude();
                                    double longitude = addressList.get(0).getLongitude();

                                    // 좌표(위도, 경도) 생성
                                    LatLng point = new LatLng(latitude, longitude);
                                    markersPosition.add(point);
                                    storeName.add("시장");
                                }
                            }

                            Log.d("test", "성공");
                        }

                        @Override
                        public void onFailure(Call<MarketList> call, Throwable t) {
                            Log.d("test", "실패");
                            t.printStackTrace();
                        }
                    });
                    // 위치 추적 모드 지정 가능 내 위치로 이동
                    mNaverMap.setLocationTrackingMode(LocationTrackingMode.Follow);
                    break;
                case R.id.normal_radioBtn:
                    freeActiveMarkers();
                    RetrofitClient.getAPIService().getNormalMarketData().enqueue(new Callback<MarketList>() {
                        @Override
                        public void onResponse(Call<MarketList> call, Response<MarketList> response) {
                            Log.d("TAG", response.code() + "");

                            MarketList resource = response.body();
                            List<MarketList.MarketData> dataList = resource.data;

                            for (MarketList.MarketData data : dataList) {
                                List<Address> addressList = null;
                                try {
                                    // 받아온 주소를 지오 코딩을 이용해 변환
                                    if (data.getMarketAddress() != null)
                                        addressList = geocoder.getFromLocationName(
                                                data.getMarketAddress(), // 주소
                                                1); // 최대 검색 결과 개수
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                                if (addressList != null && addressList.size() != 0) {
                                    double latitude = addressList.get(0).getLatitude();
                                    double longitude = addressList.get(0).getLongitude();

                                    // 좌표(위도, 경도) 생성
                                    LatLng point = new LatLng(latitude, longitude);
                                    markersPosition.add(point);
                                    storeName.add("시장");
                                }
                            }

                            Log.d("test", "성공");
                        }

                        @Override
                        public void onFailure(Call<MarketList> call, Throwable t) {
                            Log.d("test", "실패");
                            t.printStackTrace();
                        }
                    });
                    // 위치 추적 모드 지정 가능 내 위치로 이동
                    mNaverMap.setLocationTrackingMode(LocationTrackingMode.Follow);
                    break;
                case R.id.small_radioBtn:
                    freeActiveMarkers();
                    RetrofitClient.getAPIService().getSmallMarketData().enqueue(new Callback<MarketList>() {
                        @Override
                        public void onResponse(Call<MarketList> call, Response<MarketList> response) {
                            Log.d("TAG", response.code() + "");

                            MarketList resource = response.body();
                            List<MarketList.MarketData> dataList = resource.data;

                            for (MarketList.MarketData data : dataList) {
                                List<Address> addressList = null;
                                try {
                                    // 받아온 주소를 지오 코딩을 이용해 변환
                                    if (data.getMarketAddress() != null)
                                        addressList = geocoder.getFromLocationName(
                                                data.getMarketAddress(), // 주소
                                                1); // 최대 검색 결과 개수
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                                if (addressList != null && addressList.size() != 0) {
                                    double latitude = addressList.get(0).getLatitude();
                                    double longitude = addressList.get(0).getLongitude();

                                    // 좌표(위도, 경도) 생성
                                    LatLng point = new LatLng(latitude, longitude);
                                    markersPosition.add(point);
                                    storeName.add("시장");
                                    CameraUpdate cameraUpdate = CameraUpdate.scrollTo(point);
                                    mNaverMap.moveCamera(cameraUpdate);
                                }
                            }

                            Log.d("test", "성공");
                        }

                        @Override
                        public void onFailure(Call<MarketList> call, Throwable t) {
                            Log.d("test", "실패");
                            t.printStackTrace();
                        }
                    });
                    // 위치 추적 모드 지정 가능 내 위치로 이동
                    mNaverMap.setLocationTrackingMode(LocationTrackingMode.Follow);
                    break;
            }
        }
    };

    private RadioGroup.OnCheckedChangeListener listener2 = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int i) {
            if (i != -1) {
                radioGroup1.setOnCheckedChangeListener(null);
                radioGroup1.clearCheck();
                radioGroup1.setOnCheckedChangeListener(listener1);
            }
            switch (i) {
                case R.id.ticket_radioBtn:
                    freeActiveMarkers();
                    RetrofitClient.getAPIService().getTicketStoreData().enqueue(new Callback<StoreList>() {
                        @Override
                        public void onResponse(Call<StoreList> call, Response<StoreList> response) {
                            Log.d("TAG", response.code() + "");

                            StoreList resource = response.body();
                            List<StoreList.StoreData> dataList = resource.data;

                            for (StoreList.StoreData data : dataList) {
                                List<Address> addressList = null;
                                try {
                                    // 받아온 주소를 지오 코딩을 이용해 변환
                                    if (data.getStoreAddress() != null)
                                        addressList = geocoder.getFromLocationName(
                                                data.getStoreAddress(), // 주소
                                                1); // 최대 검색 결과 개수
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                                if (addressList != null && addressList.size() != 0) {
                                    double latitude = addressList.get(0).getLatitude();
                                    double longitude = addressList.get(0).getLongitude();

                                    // 좌표(위도, 경도) 생성
                                    LatLng point = new LatLng(latitude, longitude);
                                    markersPosition.add(point);
                                    storeName.add(data.getStoreName());
                                }
                            }

                            Log.d("test", "성공");
                        }

                        @Override
                        public void onFailure(Call<StoreList> call, Throwable t) {
                            Log.d("test", "실패");
                            t.printStackTrace();
                        }
                    });
                    // 위치 추적 모드 지정 가능 내 위치로 이동
                    mNaverMap.setLocationTrackingMode(LocationTrackingMode.Follow);
                    break;
                case R.id.everyday_radioBtn:
                    freeActiveMarkers();
                    RetrofitClient.getAPIService().getEverydayMarketData().enqueue(new Callback<MarketList>() {
                        @Override
                        public void onResponse(Call<MarketList> call, Response<MarketList> response) {
                            Log.d("TAG", response.code() + "");

                            MarketList resource = response.body();
                            List<MarketList.MarketData> dataList = resource.data;

                            for (MarketList.MarketData data : dataList) {
                                List<Address> addressList = null;
                                try {
                                    // 받아온 주소를 지오 코딩을 이용해 변환
                                    if (data.getMarketAddress() != null)
                                        addressList = geocoder.getFromLocationName(
                                                data.getMarketAddress(), // 주소
                                                1); // 최대 검색 결과 개수
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                                if (addressList != null && addressList.size() != 0) {
                                    double latitude = addressList.get(0).getLatitude();
                                    double longitude = addressList.get(0).getLongitude();

                                    // 좌표(위도, 경도) 생성
                                    LatLng point = new LatLng(latitude, longitude);
                                    markersPosition.add(point);
                                    storeName.add("시장");
                                }
                            }

                            Log.d("test", "성공");
                        }

                        @Override
                        public void onFailure(Call<MarketList> call, Throwable t) {
                            Log.d("test", "실패");
                            t.printStackTrace();
                        }
                    });
                    // 위치 추적 모드 지정 가능 내 위치로 이동
                    mNaverMap.setLocationTrackingMode(LocationTrackingMode.Follow);
                    break;
                case R.id.fiveDays_radioBtn:
                    freeActiveMarkers();
                    RetrofitClient.getAPIService().getFiveDaysMarketData().enqueue(new Callback<MarketList>() {
                        @Override
                        public void onResponse(Call<MarketList> call, Response<MarketList> response) {
                            Log.d("TAG", response.code() + "");

                            MarketList resource = response.body();
                            List<MarketList.MarketData> dataList = resource.data;

                            for (MarketList.MarketData data : dataList) {
                                List<Address> addressList = null;
                                try {
                                    // 받아온 주소를 지오 코딩을 이용해 변환
                                    if (data.getMarketAddress() != null)
                                        addressList = geocoder.getFromLocationName(
                                                data.getMarketAddress(), // 주소
                                                1); // 최대 검색 결과 개수
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                                if (addressList != null && addressList.size() != 0) {
                                    double latitude = addressList.get(0).getLatitude();
                                    double longitude = addressList.get(0).getLongitude();

                                    // 좌표(위도, 경도) 생성
                                    LatLng point = new LatLng(latitude, longitude);
                                    markersPosition.add(point);
                                    storeName.add("시장");
                                }
                            }

                            Log.d("test", "성공");
                        }

                        @Override
                        public void onFailure(Call<MarketList> call, Throwable t) {
                            Log.d("test", "실패");
                            t.printStackTrace();
                        }
                    });
                    // 위치 추적 모드 지정 가능 내 위치로 이동
                    mNaverMap.setLocationTrackingMode(LocationTrackingMode.Follow);
                    break;
            }
        }
    };
}