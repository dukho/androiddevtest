package com.nomadworks.devtest.data;

import com.nomadworks.devtest.screens.scenario1.ButtonColor;

import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

/**
 * Created by choidukho on 17/06/2016.
 */
public class ColorTest {
    @Test
    public void testColor() {
        assertThat(ButtonColor.BLUE.getName(), is("BLUE"));
        assertThat(ButtonColor.BLUE.getColorCode(), is("#0000FF"));
        assertThat(ButtonColor.GREEN.getName(), is("GREEN"));
        assertThat(ButtonColor.GREEN.getColorCode(), is("#00FF00"));
        assertThat(ButtonColor.RED.getName(), is("RED"));
        assertThat(ButtonColor.RED.getColorCode(), is("#FF0000"));
    }
}
