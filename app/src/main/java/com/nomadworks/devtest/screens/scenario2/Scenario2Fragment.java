package com.nomadworks.devtest.screens.scenario2;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.nomadworks.devtest.R;
import com.nomadworks.devtest.api.GetPlaceInfoApi;
import com.nomadworks.devtest.model.PlaceInfo;
import com.nomadworks.devtest.provider.ServiceProvider;
import com.nomadworks.devtest.repository.DataRepository;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by choidukho on 19/06/2016.
 */
public class Scenario2Fragment extends Fragment implements Scenario2Contract.ViewListener{
    @Bind(R.id.spinnerPlace)
    Spinner spinnerPlace;

    @Bind(R.id.waitSpinner)
    ProgressBar waitSpinner;

    @Bind(R.id.txtPlaceInfo)
    TextView txtPlaceInfo;

    @Bind(R.id.btnNavigate)
    Button btnNavigate;

    @Bind(R.id.mapView)
    MapView mapView;

    GetPlaceInfoApi mApi;
    DataRepository mRepository;
    Scenario2Contract.Presenter mPresenter;

    //map related
    GoogleMap mMap;
    private static final float DEFAULT_ZOOM = 12f;

    //view data
    private List<PlaceInfo> viewListPlace;
    ArrayAdapter<String> mPlaceDataAdapter;

    public Scenario2Fragment() {}

    public static Scenario2Fragment newInstance(){
        Scenario2Fragment fragment = new Scenario2Fragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_scenario2, container, false);

        ButterKnife.bind(this, view);

        mRepository = ServiceProvider.provideDataRepository(getContext());
        mPresenter = new Scenario2Presenter(this, mRepository);

        initUI(savedInstanceState);

        return view;
    }

    private void initUI(@Nullable Bundle savedInstanceState) {
        startInitSpinner();
        initMap(savedInstanceState);
        btnNavigate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.onNavigateRequested();
            }
        });
    }

    DataRepository.PlaceListCallback mPlaceListCallback = new DataRepository.PlaceListCallback() {
        @Override
        public void onPlaceDataSuccess(List<PlaceInfo> placeList) {
            viewListPlace = placeList;
            setSpinner();
        }

        @Override
        public void onPlaceDataError(String error) {
            showError(error);
        }
    };

    //getting a list of items can be asynchronous (api driven)
    private void startInitSpinner() {
        mRepository.getPlaceList(mPlaceListCallback, false);
    }

    private void setSpinner() {
        List<String> list = new ArrayList<>();
        for(PlaceInfo place : viewListPlace) {
            list.add(place.getName());
        }
        mPlaceDataAdapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_item, list);

        mPlaceDataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPlace.setAdapter(mPlaceDataAdapter);
        spinnerPlace.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                PlaceInfo selectedPlace = viewListPlace.get(position);
                mPresenter.onLocationSelected(selectedPlace);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });
    }

    private void initMap(@Nullable Bundle savedInstanceState) {
        mapView.onCreate(savedInstanceState);
        if (mMap != null) {
            setUpMap();
        } else {
            mapView.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(final GoogleMap googleMap) {
                    mMap = googleMap;
                    if (mMap != null) {
                        setUpMap();
                    }
                }
            });
        }
    }

    private void setUpMap() {
        mMap.getUiSettings().setCompassEnabled(false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mapView != null) {
            mapView.onDestroy();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mapView != null) {
            mapView.onPause();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mapView != null) {
            mapView.onResume();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (mapView != null) {
            mapView.onSaveInstanceState(outState);
        }
    }


    //--------------------------------


    @Override
    public void moveToLocationOnMap(PlaceInfo placeInfo) {
        if(mMap == null) return; //sanity check

        LatLng latLng = new LatLng(placeInfo.getLocation().getLatitude(),
                placeInfo.getLocation().getLongitude());
        setMapLocation(latLng, true);
    }

    private void setMapLocation(LatLng latLng, boolean animate) {
        final CameraPosition.Builder builder = CameraPosition.builder()
                .target(latLng)
                .zoom(DEFAULT_ZOOM);
        final CameraUpdate camera = CameraUpdateFactory.newCameraPosition(builder.build());

        if(animate) {
            mMap.animateCamera(camera);
        } else {
            mMap.moveCamera(camera);
        }
    }

    private static final String TAB = "    ";
    private static final char NEW_LINE = '\n';
    private static final String NOT_AVAILABLE = "N/A";
    @Override
    public void setInformation(PlaceInfo placeInfo) {
        StringBuilder builder = new StringBuilder();
        builder.append("Mode of transport:").append(NEW_LINE);

        if(placeInfo.getFromcentral() == null) {
            builder.append(TAB).append("Info N/A").append(NEW_LINE);
        } else {
            builder.append(TAB).append("Car - ");
            if(placeInfo.getFromcentral().getCar() != null) {
                builder.append(placeInfo.getFromcentral().getCar()).append(NEW_LINE);
            } else {
                builder.append(NOT_AVAILABLE).append(NEW_LINE);
            }

            builder.append(TAB).append("Train - ");
            if(placeInfo.getFromcentral().getTrain() != null) {
                builder.append(placeInfo.getFromcentral().getTrain()).append(NEW_LINE);
            } else {
                builder.append(NOT_AVAILABLE).append(NEW_LINE);
            }
        }

        txtPlaceInfo.setText(builder.toString());
    }

    @Override
    public void setUiLock(boolean locked) {
        if(locked) {
            spinnerPlace.setEnabled(false);
            btnNavigate.setEnabled(false);
        } else {
            spinnerPlace.setEnabled(true);
            btnNavigate.setEnabled(true);
        }
    }

    @Override
    public void setWaitState(boolean wait) {
        if(wait) {
            waitSpinner.setVisibility(View.VISIBLE);
        } else {
            waitSpinner.setVisibility(View.GONE);
        }
    }

    @Override
    public void showError(String error) {
        Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
    }
}
