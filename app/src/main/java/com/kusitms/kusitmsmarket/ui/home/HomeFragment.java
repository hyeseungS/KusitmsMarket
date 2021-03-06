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
    // ?????? ?????? ???????????? ????????? ??????
    private Vector<LatLng> markersPosition;
    private Vector<Marker> activeMarkers;
    private Vector<String> storeName;
    // ????????? ????????? ????????? ????????????(???????????? ???????????? ?????? ?????? 3km ???)??? ????????? ??????
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

        // SearchLayout ?????? Status Bar ?????? ??????
        LinearLayout linearLayout = (LinearLayout) root.findViewById(R.id.search);
        linearLayout.setPadding(0, getStatusBarHeight() + 30, 0, 0);

        // ?????? ??? ????????????
        SearchView searchMarket = (SearchView) root.findViewById(R.id.searchMarket);
        SearchView searchStore = (SearchView) root.findViewById(R.id.searchStore);

        // ?????? ?????? ??????
        mapView = (MapView) root.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);

        // getMapAsync??? ???????????? ???????????? onMapReady ?????? ????????? ??????
        // onMapReady?????? NaverMap ????????? ??????
        mapView.getMapAsync(this);

        // ?????? ??????????????? ?????? ???
        mLocationSource =
                new FusedLocationSource(this, PERMISSION_REQUEST_CODE);

        // ?????? ?????? ??????
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
                                // ????????? ????????? ?????? ????????? ????????? ??????
                                if (dataList.get(0).getMarketAddress() != null)
                                    addressList = geocoder.getFromLocationName(
                                            dataList.get(0).getMarketAddress(), // ??????
                                            1); // ?????? ?????? ?????? ??????
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            if (addressList != null && addressList.size() != 0) {
                                double latitude = addressList.get(0).getLatitude();
                                double longitude = addressList.get(0).getLongitude();

                                // ??????(??????, ??????) ??????
                                LatLng point = new LatLng(latitude, longitude);
                                markersPosition.add(point);
                                storeName.add("??????");
                                CameraUpdate cameraUpdate = CameraUpdate.scrollTo(point);
                                mNaverMap.moveCamera(cameraUpdate);
                            }
                            Log.d("test", "??????");
                        }
                    }

                    @Override
                    public void onFailure(Call<MarketList> call, Throwable t) {
                        Log.d("test", "??????");
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

        // ?????? ?????? ??????
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
                                // ????????? ????????? ?????? ????????? ????????? ??????
                                if (dataList.get(0).getStoreAddress() != null)
                                    addressList = geocoder.getFromLocationName(
                                            dataList.get(0).getStoreAddress(), // ??????
                                            1); // ?????? ?????? ?????? ??????
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            if (addressList != null && addressList.size() != 0) {
                                double latitude = addressList.get(0).getLatitude();
                                double longitude = addressList.get(0).getLongitude();

                                // ??????(??????, ??????) ??????
                                LatLng point = new LatLng(latitude, longitude);
                                markersPosition.add(point);
                                storeName.add(dataList.get(0).getStoreName());
                                CameraUpdate cameraUpdate = CameraUpdate.scrollTo(point);
                                mNaverMap.moveCamera(cameraUpdate);
                            }
                            Log.d("test", "??????");
                        }
                    }

                    @Override
                    public void onFailure(Call<StoreList> call, Throwable t) {
                        Log.d("test", "??????");
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


        // ?????? ????????????
        radioGroup1 = (RadioGroup) root.findViewById(R.id.market_size);
        radioGroup2 = (RadioGroup) root.findViewById(R.id.market_char);

        // ?????? 1
        radioGroup1.clearCheck();
        radioGroup1.setOnCheckedChangeListener(listener1);
        // ??????2
        radioGroup2.clearCheck();
        radioGroup2.setOnCheckedChangeListener(listener2);

        // ?????? ?????? ??????
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
                                // ????????? ????????? ?????? ????????? ????????? ??????
                                if (data.getMarketAddress() != null)
                                    addressList = geocoder.getFromLocationName(
                                            data.getMarketAddress(), // ??????
                                            1); // ?????? ?????? ?????? ??????
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            if (addressList != null && addressList.size() != 0) {
                                double latitude = addressList.get(0).getLatitude();
                                double longitude = addressList.get(0).getLongitude();

                                // ??????(??????, ??????) ??????
                                LatLng point = new LatLng(latitude, longitude);
                                markersPosition.add(point);
                                storeName.add("??????");
                            }
                        }

                        Log.d("test", "??????");
                    }


                    @Override
                    public void onFailure(Call<MarketList> call, Throwable t) {
                        Log.d("test", "??????");
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
                                // ????????? ????????? ?????? ????????? ????????? ??????
                                if (data.getStoreAddress() != null)
                                    addressList = geocoder.getFromLocationName(
                                            data.getStoreAddress(), // ??????
                                            1); // ?????? ?????? ?????? ??????
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            if (addressList != null && addressList.size() != 0) {
                                double latitude = addressList.get(0).getLatitude();
                                double longitude = addressList.get(0).getLongitude();

                                // ??????(??????, ??????) ??????
                                LatLng point = new LatLng(latitude, longitude);
                                markersPosition.add(point);
                                storeName.add(data.getStoreName());
                            }
                        }

                        Log.d("test", "??????");
                    }


                    @Override
                    public void onFailure(Call<StoreList> call, Throwable t) {
                        Log.d("test", "??????");
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
                            Log.d("test", "??????");
                        }
                    }

                    @Override
                    public void onFailure(Call<Image> call, Throwable t) {
                        Log.d("test", "??????");
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

    //status bar??? ?????? ??????
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

        // NaverMap ?????? ????????? NaverMap ????????? ?????? ?????? ??????
        this.mNaverMap = naverMap;
        geocoder = new Geocoder(getContext());

        // NaverMap??? locationSource??? set?????? ?????? ?????? ????????? ?????? ??????
        mNaverMap.setLocationSource(mLocationSource);

        // UI ????????? ?????????
        UiSettings uiSettings = mNaverMap.getUiSettings();
        uiSettings.setCompassEnabled(false); // ????????? : true
        uiSettings.setScaleBarEnabled(false); // ????????? : true
        uiSettings.setZoomControlEnabled(false); // ????????? : true
        uiSettings.setLocationButtonEnabled(false); // ????????? : false
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
                        // ????????? ????????? ?????? ????????? ????????? ??????
                        if (data.getMarketAddress() != null)
                            addressList = geocoder.getFromLocationName(
                                    data.getMarketAddress(), // ??????
                                    1); // ?????? ?????? ?????? ??????
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    if (addressList != null && addressList.size() != 0) {
                        double latitude = addressList.get(0).getLatitude();
                        double longitude = addressList.get(0).getLongitude();

                        // ??????(??????, ??????) ??????
                        LatLng point = new LatLng(latitude, longitude);
                        markersPosition.add(point);
                        storeName.add("??????");
                    }
                }

                Log.d("test", "??????");
            }

            @Override
            public void onFailure(Call<MarketList> call, Throwable t) {
                Log.d("test", "??????");
                t.printStackTrace();
            }

        });
        // ?????? ?????? ?????? ?????? ?????? ??? ????????? ??????
        mNaverMap.setLocationTrackingMode(LocationTrackingMode.Follow);
        // ????????? ???????????? ???????????? ?????????
        naverMap.addOnCameraChangeListener(new NaverMap.OnCameraChangeListener() {

            @Override
            public void onCameraChange(int reason, boolean animated) {
                freeActiveMarkers();
                // ????????? ??????????????? ??? ???????????? ?????? ?????? ????????? ?????? ??????
                // ????????? ?????????????????? ???????????? ????????????????????? ?????? ??????
                LatLng currentPosition = getCurrentPosition(naverMap);
                for (int i = 0; i < markersPosition.size(); i++) {
                    if (!withinSightMarker(currentPosition, markersPosition.get(i)))
                        continue;
                    Marker marker = new Marker();
                    marker.setPosition(markersPosition.get(i));
                    marker.setMap(naverMap);
                    String sn = "????????????";
                    marker.setOnClickListener(new Overlay.OnClickListener() {
                        @Override
                        public boolean onClick(@NonNull Overlay overlay) {
                            // StoreName ?????????
                            if (!sn.equals("??????")) {
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
        // ????????????. ????????? onRequestPermissionsResult ?????? ????????? ??????
        ActivityCompat.requestPermissions(getActivity(), PERMISSIONS, PERMISSION_REQUEST_CODE);
    }

    // ?????? ???????????? ???????????? ??????
    public LatLng getCurrentPosition(NaverMap naverMap) {
        CameraPosition cameraPosition = naverMap.getCameraPosition();
        return new LatLng(cameraPosition.target.latitude, cameraPosition.target.longitude);
    }

    public boolean withinSightMarker(LatLng currentPosition, LatLng markerPosition) {
        boolean withinSightMarkerLat = Math.abs(currentPosition.latitude - markerPosition.latitude) <= REFERANCE_LAT_X3;
        boolean withinSightMarkerLng = Math.abs(currentPosition.longitude - markerPosition.longitude) <= REFERANCE_LNG_X3;
        return withinSightMarkerLat && withinSightMarkerLng;
    }

    // ???????????? ???????????? ?????? ????????? ???????????? ??????
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

        // request code??? ???????????? ?????? ??????
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
                                    // ????????? ????????? ?????? ????????? ????????? ??????
                                    if (data.getMarketAddress() != null)
                                        addressList = geocoder.getFromLocationName(
                                                data.getMarketAddress(), // ??????
                                                1); // ?????? ?????? ?????? ??????
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                                if (addressList != null && addressList.size() != 0) {
                                    double latitude = addressList.get(0).getLatitude();
                                    double longitude = addressList.get(0).getLongitude();

                                    // ??????(??????, ??????) ??????
                                    LatLng point = new LatLng(latitude, longitude);
                                    markersPosition.add(point);
                                    storeName.add("??????");
                                }
                            }

                            Log.d("test", "??????");
                        }

                        @Override
                        public void onFailure(Call<MarketList> call, Throwable t) {
                            Log.d("test", "??????");
                            t.printStackTrace();
                        }
                    });
                    // ?????? ?????? ?????? ?????? ?????? ??? ????????? ??????
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
                                    // ????????? ????????? ?????? ????????? ????????? ??????
                                    if (data.getMarketAddress() != null)
                                        addressList = geocoder.getFromLocationName(
                                                data.getMarketAddress(), // ??????
                                                1); // ?????? ?????? ?????? ??????
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                                if (addressList != null && addressList.size() != 0) {
                                    double latitude = addressList.get(0).getLatitude();
                                    double longitude = addressList.get(0).getLongitude();

                                    // ??????(??????, ??????) ??????
                                    LatLng point = new LatLng(latitude, longitude);
                                    markersPosition.add(point);
                                    storeName.add("??????");
                                }
                            }

                            Log.d("test", "??????");
                        }

                        @Override
                        public void onFailure(Call<MarketList> call, Throwable t) {
                            Log.d("test", "??????");
                            t.printStackTrace();
                        }
                    });
                    // ?????? ?????? ?????? ?????? ?????? ??? ????????? ??????
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
                                    // ????????? ????????? ?????? ????????? ????????? ??????
                                    if (data.getMarketAddress() != null)
                                        addressList = geocoder.getFromLocationName(
                                                data.getMarketAddress(), // ??????
                                                1); // ?????? ?????? ?????? ??????
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                                if (addressList != null && addressList.size() != 0) {
                                    double latitude = addressList.get(0).getLatitude();
                                    double longitude = addressList.get(0).getLongitude();

                                    // ??????(??????, ??????) ??????
                                    LatLng point = new LatLng(latitude, longitude);
                                    markersPosition.add(point);
                                    storeName.add("??????");
                                    CameraUpdate cameraUpdate = CameraUpdate.scrollTo(point);
                                    mNaverMap.moveCamera(cameraUpdate);
                                }
                            }

                            Log.d("test", "??????");
                        }

                        @Override
                        public void onFailure(Call<MarketList> call, Throwable t) {
                            Log.d("test", "??????");
                            t.printStackTrace();
                        }
                    });
                    // ?????? ?????? ?????? ?????? ?????? ??? ????????? ??????
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
                                    // ????????? ????????? ?????? ????????? ????????? ??????
                                    if (data.getStoreAddress() != null)
                                        addressList = geocoder.getFromLocationName(
                                                data.getStoreAddress(), // ??????
                                                1); // ?????? ?????? ?????? ??????
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                                if (addressList != null && addressList.size() != 0) {
                                    double latitude = addressList.get(0).getLatitude();
                                    double longitude = addressList.get(0).getLongitude();

                                    // ??????(??????, ??????) ??????
                                    LatLng point = new LatLng(latitude, longitude);
                                    markersPosition.add(point);
                                    storeName.add(data.getStoreName());
                                }
                            }

                            Log.d("test", "??????");
                        }

                        @Override
                        public void onFailure(Call<StoreList> call, Throwable t) {
                            Log.d("test", "??????");
                            t.printStackTrace();
                        }
                    });
                    // ?????? ?????? ?????? ?????? ?????? ??? ????????? ??????
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
                                    // ????????? ????????? ?????? ????????? ????????? ??????
                                    if (data.getMarketAddress() != null)
                                        addressList = geocoder.getFromLocationName(
                                                data.getMarketAddress(), // ??????
                                                1); // ?????? ?????? ?????? ??????
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                                if (addressList != null && addressList.size() != 0) {
                                    double latitude = addressList.get(0).getLatitude();
                                    double longitude = addressList.get(0).getLongitude();

                                    // ??????(??????, ??????) ??????
                                    LatLng point = new LatLng(latitude, longitude);
                                    markersPosition.add(point);
                                    storeName.add("??????");
                                }
                            }

                            Log.d("test", "??????");
                        }

                        @Override
                        public void onFailure(Call<MarketList> call, Throwable t) {
                            Log.d("test", "??????");
                            t.printStackTrace();
                        }
                    });
                    // ?????? ?????? ?????? ?????? ?????? ??? ????????? ??????
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
                                    // ????????? ????????? ?????? ????????? ????????? ??????
                                    if (data.getMarketAddress() != null)
                                        addressList = geocoder.getFromLocationName(
                                                data.getMarketAddress(), // ??????
                                                1); // ?????? ?????? ?????? ??????
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                                if (addressList != null && addressList.size() != 0) {
                                    double latitude = addressList.get(0).getLatitude();
                                    double longitude = addressList.get(0).getLongitude();

                                    // ??????(??????, ??????) ??????
                                    LatLng point = new LatLng(latitude, longitude);
                                    markersPosition.add(point);
                                    storeName.add("??????");
                                }
                            }

                            Log.d("test", "??????");
                        }

                        @Override
                        public void onFailure(Call<MarketList> call, Throwable t) {
                            Log.d("test", "??????");
                            t.printStackTrace();
                        }
                    });
                    // ?????? ?????? ?????? ?????? ?????? ??? ????????? ??????
                    mNaverMap.setLocationTrackingMode(LocationTrackingMode.Follow);
                    break;
            }
        }
    };
}