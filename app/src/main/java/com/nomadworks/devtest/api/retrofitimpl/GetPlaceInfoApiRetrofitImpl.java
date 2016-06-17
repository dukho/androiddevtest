package com.nomadworks.devtest.api.retrofitimpl;

import com.nomadworks.devtest.api.GetPlaceInfoApi;
import com.nomadworks.devtest.data.PlaceInfo;

import java.lang.ref.WeakReference;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by choidukho on 17/06/2016.
 */
public class GetPlaceInfoApiRetrofitImpl implements GetPlaceInfoApi {
    private String mBaseUrl;
    private Retrofit mRetrofit;

    private WeakReference<PlaceCallback> mRefPlaceCallback;

    public GetPlaceInfoApiRetrofitImpl(String baseUrl) {
        mBaseUrl = baseUrl;

        mRetrofit = new Retrofit.Builder()
                .baseUrl(mBaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Override
    public void requestPlace(PlaceCallback callback) {
        mRefPlaceCallback = new WeakReference<PlaceCallback>(callback);

        mRetrofit.create(PlaceService.class).getPlaceList().enqueue(new Callback<List<PlaceInfo>>() {
            @Override
            public void onResponse(Call<List<PlaceInfo>> call, Response<List<PlaceInfo>> response) {
                if(isCallbackValid()) {
                    mRefPlaceCallback.get().onPlaceInfo(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<PlaceInfo>> call, Throwable t) {
                if(isCallbackValid()) {
                    mRefPlaceCallback.get().onPlaceError(t.toString());
                }
            }
        });
    }

    private boolean isCallbackValid() {
        return mRefPlaceCallback != null && mRefPlaceCallback.get() != null;
    }
}
