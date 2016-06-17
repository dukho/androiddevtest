package com.nomadworks.devtest.api.retrofitimpl;

import com.nomadworks.devtest.model.PlaceInfo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by choidukho on 17/06/2016.
 */
public interface PlaceService {
    @GET("sample.json")
    Call<List<PlaceInfo>> getPlaceList();
}
