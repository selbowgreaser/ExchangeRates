package org.selbowgreaser.visualization;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RectangleInsets;
import org.selbowgreaser.model.data.PredictedExchangeRate;
import org.selbowgreaser.request.RequestResult;

import java.awt.*;
import java.io.*;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;

public class Visualizer extends ApplicationFrame {

    private static final long serialVersionUID = 1L;

    static {
        ChartFactory.setChartTheme(new StandardChartTheme("JFree/Shadow",
                true));
    }

    public Visualizer(String title) {
        super(title);
    }

    public ByteArrayOutputStream createOutputStream(List<RequestResult> requestResults) {
        XYDataset dataset = createDataset(requestResults);

        JFreeChart jFreeChart = createChart(dataset);

        ByteArrayOutputStream outputStream;
        try {
            outputStream = new ByteArrayOutputStream();
            ChartUtilities.writeChartAsJPEG(outputStream, jFreeChart, 720, 480);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        return outputStream;
    }

    private XYDataset createDataset(List<RequestResult> requestResults) {
        TimeSeriesCollection dataset = new TimeSeriesCollection();
        for (RequestResult requestResult : requestResults) {
            TimeSeries timeSeries = new TimeSeries(MessageFormat.format("{0} {1}", requestResult.getCurrency(), requestResult.getAlgorithm()));
            for (PredictedExchangeRate predictedExchangeRate : requestResult.getPredictedExchangeRates()) {
                LocalDate date = predictedExchangeRate.getDate();
                float predictedExchangeRateValue = predictedExchangeRate.getExchangeRate().floatValue();
                timeSeries.add(new Day(date.getDayOfMonth(), date.getMonthValue(), date.getYear()),
                        predictedExchangeRateValue);
            }
            dataset.addSeries(timeSeries);
        }
        return dataset;
    }

    private JFreeChart createChart(XYDataset dataset) {
        JFreeChart chart = ChartFactory.createTimeSeriesChart(
                "Exchange Rate Forecast",  // title
                "",                            // x-axis label
                "Exchange rate",               // y-axis label
                dataset,                       // data
                true,                          // create legend
                true,                          // generate tooltips
                false                          // generate URLs
        );

        chart.setBackgroundPaint(Color.white);

        XYPlot plot = (XYPlot) chart.getPlot();
        plot.setBackgroundPaint(Color.lightGray);
        plot.setDomainGridlinePaint(Color.white);
        plot.setRangeGridlinePaint(Color.white);
        plot.setAxisOffset(new RectangleInsets(5.0, 5.0, 5.0, 5.0));
        plot.setDomainCrosshairVisible(true);
        plot.setRangeCrosshairVisible(true);

        XYItemRenderer r = plot.getRenderer();
        if (r instanceof XYLineAndShapeRenderer) {
            XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) r;
            renderer.setBaseShapesVisible(true);
            renderer.setBaseShapesFilled(true);
            renderer.setDrawSeriesLineAsPath(true);
        }
        DateAxis axis = (DateAxis) plot.getDomainAxis();
        axis.setDateFormatOverride(new SimpleDateFormat("dd.MM.yyyy"));
        return chart;
    }
}
