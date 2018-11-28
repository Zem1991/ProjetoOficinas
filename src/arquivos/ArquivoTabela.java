package arquivos;

import java.io.*;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class ArquivoTabela
{
    private double tabela[];
    private int valorMax;

    public void setDados(double tabela[], int valorMax)//seta os dados da tabela
    {
        this.tabela = tabela;
        this.valorMax = valorMax;
    }
    public double[] getTabela()
    {
        return tabela;
    }
    public int getValorMax()
    {
        return valorMax;
    }
    public double[] importaTabela()//Método para importar medidas salvas
    {
        JFileChooser abrir = new JFileChooser();
        abrir.setMultiSelectionEnabled(false);
        abrir.setFileSelectionMode(JFileChooser.FILES_ONLY);
        if(abrir.showOpenDialog(null) == JFileChooser.CANCEL_OPTION)
            return null;
        File file = abrir.getSelectedFile();
        BufferedReader arquivo= null;
        try
        {
            arquivo =  new BufferedReader(new FileReader(file));
            String verifica = arquivo.readLine();
            String dados = null;
            if(verifica.equals("Dados de calibracao"))//Verifica se o arquivo é valido e importa os dados
            {
                valorMax = Integer.parseInt(arquivo.readLine().replace(',', '.'));
                tabela = new double[Integer.parseInt(arquivo.readLine())];
                arquivo.readLine();
                for(int count = 0; count<tabela.length; count++)
                {
                    String valor[] = arquivo.readLine().split("\t");
                    valor[1] = valor[1].replace(',', '.');
                    tabela[count] = Double.parseDouble(valor[1]);
                }
            }
            else
            {
                JOptionPane.showMessageDialog(null,Lang.message("invalid_file"),Lang.message("error"),JOptionPane.ERROR_MESSAGE);
                arquivo.close();
                return null;
            }
            arquivo.close();
            return tabela;
        }
        catch (Exception e)
        {
            JOptionPane.showMessageDialog(null,Lang.message("err_file_open"),Lang.message("error"),JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }
    public void salva()//Método para salvar os dados aferidos em um arquivo de texto
    {
        JFileChooser salvar = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files", "txt");
        salvar.addChoosableFileFilter(filter);
        salvar.setAcceptAllFileFilterUsed(false);
        if(salvar.showSaveDialog(null)  == JFileChooser.CANCEL_OPTION)
            return;
        File file = salvar.getSelectedFile();
        if(!file.getName().contains(".txt"))
        {
            file.renameTo(file = new File(file.getAbsolutePath() + ".txt"));
            //System.out.println(file.getName());
        }
        try
        {
            PrintWriter saida = new PrintWriter(new FileWriter(file));
            saida.println("Dados de calibracao");
            saida.println(valorMax);
            saida.println(tabela.length);
            saida.println("Distancia\tIntensidade");
            double valor = (double)valorMax/((double)tabela.length-1);
            for(int count = 0; count<tabela.length; count++)
                saida.printf("%.2f\t%.3f\n",valor*count ,tabela[count]);
            saida.close();
        }
        catch (Exception ex)
        {
            System.out.println("erro");
        }
    }
}