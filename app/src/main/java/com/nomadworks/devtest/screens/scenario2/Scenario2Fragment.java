package com.nomadworks.devtest.screens.scenario2;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.nomadworks.devtest.R;
import com.nomadworks.devtest.api.GetPlaceInfoApi;
import com.nomadworks.devtest.api.retrofitimpl.GetPlaceInfoApiRetrofitImpl;
import com.nomadworks.devtest.model.PlaceInfo;
import com.nomadworks.devtest.repository.DataRepository;
import com.nomadworks.devtest.repository.DataRepositoryImpl;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by choidukho on 19/06/2016.
 */
public class Scenario2Fragment extends Fragment implements Scenario2Contract.ViewListener{
    @Bind(R.id.spinnerPlace)
    Spinner spinnerPlace;

    @Bind(R.id.txtPlaceInfo)
    TextView txtPlaceInfo;

    @Bind(R.id.btnNavigate)
    Button btn_navigate;

    @Bind(R.id.mapView)
    MapView mapView;

    GetPlaceInfoApi mApi;
    DataRepository mRepository;
    Scenario2Contract.Presenter mPresenter;

    //map related
    GoogleMap mMap;


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

        mApi = new GetPlaceInfoApiRetrofitImpl(getString(R.string.api_baseurl));
        mRepository = new DataRepositoryImpl(mApi);
        mPresenter = new Scenario2Presenter(this, mRepository);

        initUI(savedInstanceState);

        return view;
    }

    private void initUI(@Nullable Bundle savedInstanceState) {
        initMap(savedInstanceState);
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

    }

    @Override
    public void setInformation(PlaceInfo placeInfo) {

    }

    @Override
    public void setUiLock(boolean locked) {

    }

    @Override
    public void setWaitState(boolean wait) {

    }

    @Override
    public void showError(String error) {
        Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
    }
}
