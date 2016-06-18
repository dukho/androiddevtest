package com.nomadworks.devtest.screens.scenario1;

import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nomadworks.devtest.R;

import java.lang.ref.WeakReference;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by choidukho on 18/06/2016.
 */
public class PageFragment extends Fragment {
    private String mPageTitle;
    private @ColorRes int mBackgroundColorRes;

    private static final String KEY_TITLE = "key.title";
    private static final String KEY_COLOR_RES = "key.color.res";

    @Bind(R.id.container) View rootContainer;
    @Bind(R.id.title) TextView title;

    WeakReference<PageClickListener> mListener;

    public PageFragment() {} //dummy

    public static PageFragment newInstance(String title, @ColorRes int backgroundColorRes) {

        Bundle args = new Bundle();
        args.putString(KEY_TITLE, title);
        args.putInt(KEY_COLOR_RES, backgroundColorRes);

        PageFragment fragment = new PageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPageTitle = getArguments().getString(KEY_TITLE);
        mBackgroundColorRes = getArguments().getInt(KEY_COLOR_RES);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_page, container, false);
        ButterKnife.bind(this, view);

        title.setText(mPageTitle);
        rootContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mListener != null && mListener.get() != null) {
                    mListener.get().onPageClicked(mPageTitle);
                }
            }
        });
        rootContainer.setBackgroundResource(mBackgroundColorRes);

        return view;
    }


    public void setPageClickListener(PageClickListener listener) {
        mListener = new WeakReference<PageClickListener>(listener);
    }

    public interface PageClickListener {
        void onPageClicked(String title);
    }
}
