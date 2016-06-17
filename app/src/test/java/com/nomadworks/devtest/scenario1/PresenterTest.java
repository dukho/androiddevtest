package com.nomadworks.devtest.scenario1;

import com.nomadworks.devtest.screens.scenario1.ButtonColor;
import com.nomadworks.devtest.screens.scenario1.Scenario1Contract;
import com.nomadworks.devtest.screens.scenario1.Scenario1Presenter;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import static org.mockito.Mockito.*;

/**
 * Created by choidukho on 17/06/2016.
 */
public class PresenterTest {
    Scenario1Contract.Presenter mPresenter;
    Scenario1Contract.ViewListener mView;

    @Before
    public void setup() {
        mView = mock(Scenario1Contract.ViewListener.class);
        mPresenter = new Scenario1Presenter(mView);
    }

    @Test
    public void testScenario1PresenterData() {
        assertThat(mPresenter.getPagerTitles().size(), is(4));
        assertThat(mPresenter.getScrollableItems().size(), is(5));
    }

    @Test
    public void testPageClicked() {
        final String PAGE_TITLE = "Page #1";
        mPresenter.onPageClicked(PAGE_TITLE);
        verify(mView).showToastMessage(PAGE_TITLE);
    }

    @Test
    public void testScrollItemClicked() {
        final String ITEM_TITLE = "Item #1";
        mPresenter.onScrollItemClicked(ITEM_TITLE);
        verify(mView).setText(ITEM_TITLE);
    }

    @Test
    public void testButtonClicked() {
        ButtonColor color = ButtonColor.BLUE;
        mPresenter.onButtonClicked(color);
        verify(mView).setButtonAreaBackgroundColor(color.getColorCode());
    }
}
