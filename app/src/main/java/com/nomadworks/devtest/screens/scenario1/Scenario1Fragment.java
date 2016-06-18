package com.nomadworks.devtest.screens.scenario1;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nomadworks.devtest.R;
import com.nomadworks.devtest.utils.Logging;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by choidukho on 17/06/2016.
 */
public class Scenario1Fragment extends Fragment implements Scenario1Contract.ViewListener{
    @Bind(R.id.topScroller) HorizontalScrollView topScroller;
    @Bind(R.id.scrollableItemContainer) LinearLayout scrollableItemContainer;
    @Bind(R.id.viewPager) ViewPager viewPager;
    @Bind(R.id.txtItemInfo) TextView txtItemInfo;

    @Bind(R.id.buttonPanel) View buttonPanel;
    @Bind(R.id.btn1) Button btn1;
    @Bind(R.id.btn2) Button btn2;
    @Bind(R.id.btn3) Button btn3;

    private List<View> scrollItemViewList;
    private Scenario1Contract.Presenter mPresenter;
    private PagerAdapter pagerAdapter;

    public Scenario1Fragment() {} //dummy constructor

    public static Scenario1Fragment newInstance() {
        Scenario1Fragment fragment = new Scenario1Fragment();
        return fragment;
    }

    PageFragment.PageClickListener mPageClickedListener = new PageFragment.PageClickListener() {
        @Override
        public void onPageClicked(String title) {
            mPresenter.onPageClicked(title);
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_scenario1, container, false);
        ButterKnife.bind(this, view);

        mPresenter = new Scenario1Presenter(this); //set presenter

        initView();

        return view;
    }

    private void initView() {
        initTopScroller();
        initPager();
        initColorButtons();
    }

    private void initColorButtons() {
        btn1.setText(ButtonColor.RED.getName());
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.onButtonClicked(ButtonColor.RED);
            }
        });

        btn2.setText(ButtonColor.GREEN.getName());
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.onButtonClicked(ButtonColor.GREEN);
            }
        });

        btn3.setText(ButtonColor.BLUE.getName());
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.onButtonClicked(ButtonColor.BLUE);
            }
        });
    }

    private void initPager() {
        pagerAdapter = new PagerAdapter(getChildFragmentManager(), mPresenter.getPagerTitles());
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                mPresenter.onPageSelected(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) { }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) { }
        });
    }

    private void initTopScroller() {
        scrollItemViewList = new ArrayList<>();
        List<String> itemList = mPresenter.getScrollableItems();
        LayoutInflater inflater = LayoutInflater.from(scrollableItemContainer.getContext());


        for(int i=0;i<itemList.size();i++) {
            final String item = itemList.get(i);
            View view = inflater.inflate(R.layout.view_item, null);

            //set background
            int backgroundColor = i%2 == 0 ? R.color.style_darker_grey : R.color.style_light_grey;
            view.setBackgroundResource(backgroundColor);

            //set text
            ((TextView)view.findViewById(R.id.txtItem)).setText(item);

            //set click event
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mPresenter.onScrollItemClicked(item);
                }
            });

            //add to container
            scrollItemViewList.add(view);
            scrollableItemContainer.addView(view);
        }

        //for rearranging scroll item size
        topScroller.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                topScroller.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                int itemHeight = topScroller.getHeight();
                int itemWidth = topScroller.getWidth() / 3;

                for(View view : scrollItemViewList) {
                    view.getLayoutParams().width = itemWidth;
                    view.getLayoutParams().height = itemHeight;
                }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.setViewListener(this);
        mPresenter.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mPresenter.onPause();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Logging.log("onViewCreated");

        if(savedInstanceState != null) {
            mPresenter.onRestoreInstanceState(savedInstanceState);
        } else {
            //set default
            mPresenter.onButtonClicked(ButtonColor.RED);
            mPresenter.onPageSelected(0);
            mPresenter.onScrollItemClicked(mPresenter.getScrollableItems().get(0));
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        scrollItemViewList.clear();
        Logging.log("onDestroyView()");
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        mPresenter.onSaveInstanceState(outState);
    }


    //------------------------------------------------------------------------


    @Override
    public void setButtonAreaBackgroundColor(final String colorCode) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                buttonPanel.setBackgroundColor(Color.parseColor(colorCode));
            }
        });
    }

    @Override
    public void setCurrentPage(final int index) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                viewPager.setCurrentItem(index);
            }
        });
    }

    @Override
    public void setText(final String text) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                txtItemInfo.setText(text);
            }
        });
    }

    @Override
    public void showToastMessage(final String message) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    class PagerAdapter extends FragmentStatePagerAdapter {
        private List<String> mTitleList;

        public PagerAdapter(FragmentManager fm, List<String> list) {
            super(fm);
            mTitleList = list;
        }

        @Override
        public int getCount() {
            if(mTitleList == null) {
                return 0;
            } else {
                return mTitleList.size();
            }
        }

        @Override
        public Fragment getItem(int position) {
            int colorRes = position % 2 == 0 ? R.color.style_dark_grey : R.color.style_white;

            PageFragment fragment =  PageFragment.newInstance(mTitleList.get(position), colorRes);
            fragment.setPageClickListener(mPageClickedListener);
            return fragment;
        }
    }
}
