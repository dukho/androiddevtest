package com.nomadworks.devtest.provider;

import android.content.Context;

import com.nomadworks.devtest.R;
import com.nomadworks.devtest.api.GetPlaceInfoApi;
import com.nomadworks.devtest.api.retrofitimpl.GetPlaceInfoApiRetrofitImpl;
import com.nomadworks.devtest.repository.DataRepository;
import com.nomadworks.devtest.repository.DataRepositoryImpl;

/**
 * Created by choidukho on 19/06/2016.
 */
public class ServiceProvider {
    private static DataRepositoryImpl mRepository; //singleton

    public static synchronized DataRepository provideDataRepository(Context context) {
        if(mRepository == null) {
            GetPlaceInfoApi mApi = new GetPlaceInfoApiRetrofitImpl(context.getString(R.string.api_baseurl));
            mRepository = new DataRepositoryImpl(mApi);
        }

        return mRepository;
    }
}
