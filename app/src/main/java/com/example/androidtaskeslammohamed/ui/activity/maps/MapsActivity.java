package com.example.androidtaskeslammohamed.ui.activity.maps;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.PopupMenu;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.androidtaskeslammohamed.R;
import com.example.androidtaskeslammohamed.databinding.ActivityMapsBinding;
import com.example.androidtaskeslammohamed.firebase.IReadLocations;
import com.example.androidtaskeslammohamed.model.findPlace.response.Candidate;
import com.example.androidtaskeslammohamed.model.findPlace.response.ResponseFindPlaces;
import com.example.androidtaskeslammohamed.model.location.SourceLocation;
import com.example.androidtaskeslammohamed.ui.adapter.destnationLocation.DestnationLocationAdapter;
import com.example.androidtaskeslammohamed.ui.adapter.destnationLocation.DestnationLocationClicked;
import com.example.androidtaskeslammohamed.ui.adapter.sourceLocation.SourceLocationAdapter;
import com.example.androidtaskeslammohamed.ui.adapter.sourceLocation.SourceLocationClicked;
import com.example.androidtaskeslammohamed.utilits.application.App;
import com.example.androidtaskeslammohamed.utilits.constants.Constants;
import com.example.androidtaskeslammohamed.utilits.helperFunction.HelperFunctions;
import com.example.androidtaskeslammohamed.utilits.utilits.Utilis;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class MapsActivity extends FragmentActivity implements
        OnMapReadyCallback, PopupMenu.OnMenuItemClickListener, IReadLocations, SourceLocationClicked, DestnationLocationClicked {

    private static final String TAG = "MapsActivity";
    private ActivityMapsBinding binding;
    private FusedLocationProviderClient mFusedLocationClient;
    private GoogleMap googleMap;
    private final LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location mLastLocation = locationResult.getLastLocation();
            setCurrentLatLong(mLastLocation);
        }
    };
    private SourceLocationAdapter locationAdapter;
    private DestnationLocationAdapter destnationLocationAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDataBinding();
        initFusedLocationClient();
        initMap();
        initClick();
        initRecyclerAndAdapterSourceLocation();
        initRecyclerAndAdapterDestnationLocation();


        // App.getFirebaseUtilits().addLocationDummyToFireStore();
    }

    //region initdataBinding

    private void initDataBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_maps);
        binding.setLifecycleOwner(this);

    }
    //endregion

    //region click

    private void initClick() {
        binding.locationLayout.imMenu.setOnClickListener(this::showMenu);
        binding.locationLayout.edYourLocation.setOnClickListener(v -> {
            setVisableSource(true);
            getDummyLocation();
        });

        binding.locationLayout.edDestnation.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    initFindPlaceViewModel(s.toString());
                } else {
                    setVisableDestination(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    //endregion

    //region get 10 dummy locations

    private void getDummyLocation() {
        App.getFirebaseUtilits().getSourceLocation(this);
    }

    //endregion

    //region menu
    private void showMenu(View view) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.inflate(R.menu.hamburger_menu);
        Menu menu = popupMenu.getMenu();
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.show();
    }


    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_setting_menu:
                Utilis.toastMessage("Settings");
                break;
            case R.id.action_logout:
                Utilis.toastMessage("Logout");
                break;
        }
        return false;
    }

    //endregion

    //region get current location
    private void initFusedLocationClient() {
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
    }

    @SuppressLint("MissingPermission")
    private void getLastLocation() {
        if (HelperFunctions.isServicesOk(this)) {
            if (HelperFunctions.checkGPSEnabled(this)) {
                if (HelperFunctions.checkLocationPermission(this)) {
                    mFusedLocationClient.getLastLocation().addOnCompleteListener(task -> {
                                Location location = task.getResult();
                                if (location == null) {
                                    requestNewLocationData();
                                } else {
                                    setCurrentLatLong(location);
                                }
                            }
                    ).addOnFailureListener(e -> Log.d(TAG, "onFailure: " + e.getMessage()));
                } else {
                    HelperFunctions.askLocationPermission(this);
                }
            } else {
                HelperFunctions.initAlertDialogForGPS(this);
            }

        }
    }

    @SuppressLint("MissingPermission")
    private void requestNewLocationData() {
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(5000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setNumUpdates(1);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mFusedLocationClient.requestLocationUpdates(
                mLocationRequest, mLocationCallback,
                Looper.myLooper()
        );

    }

    private void setCurrentLatLong(Location currentLatLong) {
        LatLng currentLocation = new LatLng(currentLatLong.getLatitude(), currentLatLong.getLongitude());
        googleMap.addMarker(new MarkerOptions().position(currentLocation).title("You Are Here"));
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(currentLatLong.getLatitude(), currentLatLong.getLongitude()), 12.0f));

    }
    //endregion

    //region result permission
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == Constants.PERMISSION_REQUEST_CODE && grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            getLastLocation();
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.GPS_REQUEST_CODE) {
            getLastLocation();
        }
    }

    //endregion

    //region maps
    private void initMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.clear();
        this.googleMap = googleMap;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        googleMap.setMyLocationEnabled(true);
    }

    //endregion

    //region init recycler view and adapter
    private void initRecyclerAndAdapterSourceLocation() {
        locationAdapter = new SourceLocationAdapter(this);
        binding.layoutRecyclerView.setLocationAdapter(locationAdapter);
    }

    private void initRecyclerAndAdapterDestnationLocation() {
        destnationLocationAdapter = new DestnationLocationAdapter(this);
        binding.layoutRecyclerViewDestnation.setLocationAdapter(destnationLocationAdapter);
    }
    //endregion

    //region set name in editText

    private void setYourLocationText(String locationName) {
        binding.locationLayout.edYourLocation.setText(locationName);
    }

    private void setLocationDestnationText(String locationName) {
        binding.locationLayout.edDestnation.setText(locationName);
    }

    //endregion

    //region init findPlace ViewModel
    private void initFindPlaceViewModel(String name) {
        FindPlaceViewModel findPlaceViewModel = new ViewModelProvider(this).get(FindPlaceViewModel.class);
        findPlaceViewModel.getNearbyRestaurantsLiveData(name, Constants.PARAM_INPUT_TYPE, getResources().getString(R.string.google_maps_key)).observe(this, new Observer<ResponseFindPlaces>() {
            @Override
            public void onChanged(ResponseFindPlaces responseFindPlaces) {
                if (responseFindPlaces.getStatus().equals("OK")) {
                    if (responseFindPlaces.getCandidates() != null && responseFindPlaces.getCandidates().size() > 0) {
                        setVisableDestination(true);
                        destnationLocationAdapter.submitList(responseFindPlaces.getCandidates());
                    } else {
                        Utilis.toastMessage("No Result");
                    }
                } else {
                    Utilis.toastMessage("No Result");
                }

            }
        });
    }
    //endregion


    //region visiabilty recycler

    private void setVisableDestination(boolean visable) {
        if (visable) {
            binding.layoutRecyclerViewDestnation.getRoot().setVisibility(View.VISIBLE);
        } else {
            binding.layoutRecyclerViewDestnation.getRoot().setVisibility(View.GONE);
        }
    }

    private void setVisableSource(boolean visable) {
        if (visable) {
            binding.layoutRecyclerView.getRoot().setVisibility(View.VISIBLE);
        } else {
            binding.layoutRecyclerView.getRoot().setVisibility(View.GONE);
        }
    }
    //endregion

    @Override
    protected void onResume() {
        super.onResume();
        getLastLocation();

    }


    //region impelement interfaces

    @Override
    public void readLocations(List<SourceLocation> sourceLocation) {
        locationAdapter.submitList(sourceLocation);
    }

    @Override
    public void onLocationClicked(SourceLocation sourceLocation) {
        setYourLocationText(sourceLocation.getName());
        setVisableSource(false);
    }

    @Override
    public void onLocationDestnationClicked(Candidate candidate) {
        setLocationDestnationText(candidate.getName());
        addDestinationToFireStore(candidate.getName(),
                candidate.getGeometry().getLocation().getLat(),
                candidate.getGeometry().getLocation().getLng());
        setVisableDestination(false);
    }
    //endregion


    //region add destination to firestore

    private void addDestinationToFireStore(String name, Double lat, Double lont) {
        SourceLocation sourceLocation = new SourceLocation(name, lat, lont);
        App.getFirebaseUtilits().addLocationDestnationToFireStore(sourceLocation);
    }

    //endregion
}