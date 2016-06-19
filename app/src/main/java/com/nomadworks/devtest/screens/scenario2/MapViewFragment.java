package com.nomadworks.devtest.screens.scenario2;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.nomadworks.devtest.R;

import butterknife.Bind;

/**
 * Created by choidukho on 19/06/2016.
 */
public abstract class MapViewFragment extends Fragment {
    @Bind(R.id.mapView)
    protected MapView mapView;

    protected GoogleMap mMap;
    protected static final float DEFAULT_ZOOM = 12f;

    private static LatLng DEFAULT_MAP_LOCATION = new LatLng(-33.861523,151.210884);

    protected void initMap(@Nullable Bundle savedInstanceState) {
        mapView.onCreate(null); //bug? putting custom state in the bundle crashes
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
        setMapLocation(DEFAULT_MAP_LOCATION, false);
    }

    protected void setMapLocation(LatLng latLng, boolean animate) {
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

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        if(mapView != null) {
            mapView.onLowMemory();
        }
    }
}
