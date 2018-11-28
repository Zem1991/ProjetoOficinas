package gui;

import arquivos.Lang;
import java.awt.Color;
import javax.swing.*;
import org.jfree.chart.*;
import org.jfree.chart.plot.*;
import org.jfree.chart.renderer.xy.*;
import org.jfree.data.xy.*;

public class Grafico extends JPanel
{
    private double dados[];//guarda o valor da intensidade de cada medida
    private int valorMax;// distancia máxima
    private ChartPanel painel;
    private JFreeChart grafico;
    private XYLineAndShapeRenderer renderer;
    private XYDataset dataset;
    private XYPlot plotGrafico;   
    private XYSeries xy;
    public Grafico()
    {
        dados = new double[1];
        dados[0] = 0;//o programa inicia com apenas um ponto em (0,0)
        dataset = criaDataSet();
        grafico = createChart();
        painel = new ChartPanel(grafico);
    }
    private JFreeChart createChart()
    {
        JFreeChart chart = ChartFactory.createXYLineChart("",Lang.message("distance"),Lang.message("intensity"), dataset, PlotOrientation.VERTICAL, false,false, true);
        plotGrafico = chart.getXYPlot();
        plotGrafico.setBackgroundPaint(Color.black);
        renderer = new XYLineAndShapeRenderer();
        plotGrafico.setRenderer(renderer);//Gera o gráfico e retorna.
        return chart;

    }
    public void setIntensidade(double dados[], int valorMax)//recebe os dados coletados e o valor máximo.
    {
        this.dados = dados;
        this.valorMax = valorMax;
    }
    public void atualiza()
    {
        this.plotGrafico.setDataset(criaDataSet());

    }
    private XYDataset criaDataSet()//Gera o gráfico
    {
        xy = new XYSeries(Lang.message("data"));
        double valor = (double)valorMax/((double)dados.length-1);//Escala do eixo X
        for(int count = 0; count < dados.length; count++)
        {
            xy.add(valor*count,dados[ count]);//adiciona os valores de (x,y) do gráfico.
        }
        XYSeriesCollection dadosGrafico = new XYSeriesCollection();
        dadosGrafico.addSeries(xy);//adiciona a série à coleção
        return dadosGrafico;//retorna data
    }
    public ChartPanel getChartPanel()
    {
        return painel;
    }
}
