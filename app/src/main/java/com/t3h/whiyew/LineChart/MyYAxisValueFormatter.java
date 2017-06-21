package com.t3h.whiyew.LineChart;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.formatter.YAxisValueFormatter;

import java.text.DecimalFormat;

/**
 * Created by hoadu on 08/06/2017.
 */

public class MyYAxisValueFormatter implements IAxisValueFormatter, YAxisValueFormatter {

    private DecimalFormat mFormat;

    public MyYAxisValueFormatter() {

        // format values to 1 decimal digit
        mFormat = new DecimalFormat("###,###,##0");
    }


    @Override
    public String getFormattedValue(float value, YAxis yAxis) {
        return mFormat.format(value)+"%" ;
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        return null;
    }

    /** this is only needed if numbers are returned, else return 0 */

//    @Override
//    public int getDecimalDigits() { return 1; }

}