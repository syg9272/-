package com.example.pizzaneck;

import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class Graph extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.graph);

        BarChart barChart = findViewById(R.id.graph_total);

        ArrayList<BarEntry> visitors = new ArrayList<>();
        visitors.add(new BarEntry(17,8));
        visitors.add(new BarEntry(18,3));

        visitors.add(new BarEntry(20,4));
        visitors.add(new BarEntry(21,7));

        visitors.add(new BarEntry(23,9));
        visitors.add(new BarEntry(24,3));

        visitors.add(new BarEntry(26,3));
        visitors.add(new BarEntry(27,5));

        visitors.add(new BarEntry(29,12));
        visitors.add(new BarEntry(30,6));

        visitors.add(new BarEntry(32,8));
        visitors.add(new BarEntry(33,1));

        visitors.add(new BarEntry(35,5));
        visitors.add(new BarEntry(36,8));


        ArrayList date = new ArrayList();
        date.add("mar,02");
        date.add("");

        date.add("mar,03");
        date.add("");

        date.add("mar,04");
        date.add("");

        date.add("mar,05");
        date.add("");

        date.add("mar,06");
        date.add("");

        date.add("mar,07");
        date.add("");

        date.add("mar,08");
        date.add("");


        BarDataSet barDataSet = new BarDataSet(visitors, "");
        barChart.animateY(5000);
        barDataSet.setColors(ColorTemplate.rgb("#091772"),ColorTemplate.rgb("#AF2525"));
        barDataSet.setValueTextColor(ColorTemplate.rgb("#104D9E"));
        barDataSet.setDrawValues(false);
        barDataSet.setValueTextSize(0f);
        BarData barData = new BarData(barDataSet);

        barChart.setFitBars(true);
        barChart.setDrawGridBackground(false);
        barChart.setData(barData);
        barChart.getAxisRight().setDrawGridLines(true);
        barChart.getAxisLeft().setLabelCount(6);



    }

}