package com.nomadworks.devtest.repository;

import android.content.Context;
import android.content.res.AssetManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nomadworks.devtest.model.PlaceInfo;
import com.nomadworks.devtest.utils.ResourceUtil;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by choidukho on 18/06/2016.
 */
public class FakeRepository implements DataRepository {
    private Context mContext;
    Gson gson = new Gson();

    public FakeRepository(Context context) {
        mContext = context;
    }

    @Override
    public Void getPlaceList(PlaceListCallback callback, boolean needRefresh) {
        try {
            String jsonString = ResourceUtil.getStringFromAssetFile(mContext, "json/mocklocation.json");
            Type dataType = new TypeToken<List<PlaceInfo>>() {}.getType();
            List<PlaceInfo> list = gson.fromJson(jsonString, dataType);
            if(callback != null) {
                callback.onPlaceDataSuccess(list);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            if(callback != null) {
                callback.onPlaceDataError(e.toString());
            }
        }

        return (Void)null;
    }

}
