package com.kusitms.kusitmsmarket.ui.mypage;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.kusitms.kusitmsmarket.MainActivity;
import com.kusitms.kusitmsmarket.R;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraPosition;
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

public class ReportLocationActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final int PERMISSION_REQUEST_CODE = 100;
    private static final String[] PERMISSIONS = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };

    private FusedLocationSource mLocationSource;
    private NaverMap mNaverMap;
    private MapView mapView;
    private Geocoder geocoder;
    private TextView addressView;
    private String address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_location);


        addressView = findViewById(R.id.reportLocation);
        RelativeLayout relativeLayout = findViewById(R.id.reportMapLayout);
        relativeLayout.setPadding(0, getStatusBarHeight(), 0, 0);
        // ?????? ?????? ??????
        mapView = findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);

        // getMapAsync??? ???????????? ???????????? onMapReady ?????? ????????? ??????
        // onMapReady?????? NaverMap ????????? ??????
        mapView.getMapAsync(this);

        // ????????? ???????????? ???????????? FusedLocationSource ??????
        mLocationSource =
                new FusedLocationSource(this, PERMISSION_REQUEST_CODE);

        ImageButton imageButton = findViewById(R.id.my_location);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNaverMap.setLocationTrackingMode(LocationTrackingMode.Follow);
            }
        });

        Button locationSettingButton = findViewById(R.id.location_setting_btn);
        locationSettingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ReportLocationActivity.this, ReportActivity.class);
                intent.putExtra("address", address);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {
        Log.d( TAG, "onMapReady");

        // ???????????? ?????? ??????
        Marker marker = new Marker();
        marker.setPosition(new LatLng(37.5670135, 126.9783740));
        marker.setMap(naverMap);
        marker.setWidth(100);
        marker.setHeight(100);
        marker.setIcon(OverlayImage.fromResource(R.drawable.ic_location_marker));

        // NaverMap ?????? ????????? NaverMap ????????? ?????? ?????? ??????
        mNaverMap = naverMap;
        mNaverMap.setLocationSource(mLocationSource);
        geocoder = new Geocoder(this);

        mNaverMap.setLocationTrackingMode(LocationTrackingMode.Follow);

        // UI ????????? ?????????
        UiSettings uiSettings = mNaverMap.getUiSettings();
        uiSettings.setCompassEnabled(false); // ????????? : true
        uiSettings.setScaleBarEnabled(false); // ????????? : true
        uiSettings.setZoomControlEnabled(false); // ????????? : true
        uiSettings.setLocationButtonEnabled(false); // ????????? : false
        uiSettings.setLogoGravity(Gravity.LEFT| Gravity.BOTTOM);

//        LocationButtonView locationButtonView = root.findViewById(R.id.location);
//        locationButtonView.setMap(mNaverMap);
//        locationButtonView.setVisibility(View.GONE);
        // ????????? ???????????? ???????????? ?????????
        naverMap.addOnCameraChangeListener(new NaverMap.OnCameraChangeListener() {

            @Override
            public void onCameraChange(int reason, boolean animated) {
                marker.setMap(null);
                // ????????? ??????????????? ??? ???????????? ?????? ?????? ????????? ?????? ??????
                // ????????? ?????????????????? ???????????? ????????????????????? ?????? ??????
                LatLng currentPosition = getCurrentPosition(naverMap);
                marker.setPosition(currentPosition);
                marker.setMap(naverMap);
                List<Address> addressList=null;
                try {
                    addressList = geocoder.getFromLocation(currentPosition.latitude,currentPosition.longitude,10);
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.d("test","???????????????");
                }
                if(addressList!=null){
                    if(addressList.size()==0){
                        addressView.setText("???????????? ??????");
                        address = "";
                    }else{
                        Log.d("?????? ??????",addressList.get(0).toString());
                        String cut[] = addressList.get(0).getAddressLine(0).split(" ");
                        address = "";
                        for(int i=1; i<cut.length; i++)
                            address += cut[i] + " ";
                        addressView.setText(address);
                    }
                }
            }
        });

        // ????????????. ????????? onRequestPermissionsResult ?????? ????????? ??????
        ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_REQUEST_CODE);
    }

    // ?????? ???????????? ???????????? ??????
    public LatLng getCurrentPosition(NaverMap naverMap) {
        CameraPosition cameraPosition = naverMap.getCameraPosition();
        return new LatLng(cameraPosition.target.latitude, cameraPosition.target.longitude);
    }

    //status bar??? ?????? ??????
    public int getStatusBarHeight()
    {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0)
            result = getResources().getDimensionPixelSize(resourceId);

        return result;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // request code??? ???????????? ?????? ??????
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mNaverMap.setLocationTrackingMode(LocationTrackingMode.Follow);
            }
        }
    }
}