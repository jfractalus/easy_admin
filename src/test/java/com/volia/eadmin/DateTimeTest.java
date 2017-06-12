package com.volia.eadmin;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;
import static com.volia.eadmin.util.DateTimeUtil.to_dd_MM_yyyy_hh_mm_ss;

public class DateTimeTest {
    @Test
    public void dateTimeTest(){
        DateTime expected = new DateTime(2017, 2, 17, 15, 19, 41, 0);
        Assert.assertEquals(expected, to_dd_MM_yyyy_hh_mm_ss("17-02-2017 15:19:41"));
    }
}
