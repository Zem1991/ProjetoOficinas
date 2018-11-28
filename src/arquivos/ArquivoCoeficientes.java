
package arquivos;

import java.io.*;
import javax.swing.*;

/**
 * Classe responsável por importar o arquivo de calibração
 */
public class ArquivoCoeficientes
{
    private double coeficientes[];
    private File file;

    public ArquivoCoeficientes()
    {
        coeficientes = new double[2];
    }
    public double[] getCoeficientes()
    {
        JFileChooser abrir = new JFileChooser();
        abrir.setMultiSelectionEnabled(false);
        abrir.setFileSelectionMode(JFileChooser.FILES_ONLY);
        if(abrir.showOpenDialog(null) == JFileChooser.CANCEL_OPTION)
            return null;
        //Abre a janela para localizar o arquivo, caso o usuário clique em cancelar, o método retorna
        file = abrir.getSelectedFile();//Caso contrário, carrega as informações do arquivo selecionados
        return getDados();//chama o método getDados e retorna os valores lidos
    }
    public double[] getCoeficientes(String fileName)
    {
        /*Metodo sobrecarregado, utilizado para permitir que o
         programa importe o arquivo de calibração automaticamente*/
        file = new File(fileName);
        return getDados();
    }
    public double[] getDados()
    {
        /*
         Método responsável por verificar se o arquivo é valido e importar os dados para o programa
         */
        BufferedReader arquivo= null;
        try
        {
            arquivo =  new BufferedReader(new FileReader(file));
            String verifica = arquivo.readLine();
            boolean arquivoValido = false;
            String dados = null;
            if(verifica.equals("<calibration>"))//Verifica se o arquivo selecionado possui o formato do arquivo de calibração
            {
                dados = arquivo.readLine();
                verifica = arquivo.readLine();
                if(verifica.equals("</calibration>"))
                    arquivoValido = true;
            }
            arquivo.close();
            if(!arquivoValido)//Caso o arquivo não seja válido, retorna para o usuário uma mensagem de erro.
            {
                JOptionPane.showMessageDialog(null,Lang.message("invalid_file"),Lang.message("error"),JOptionPane.ERROR_MESSAGE);
            }
            else
            {
                String valores[] = dados.split(",");
                for (int i= 0; i < valores.length; i++)
                {
                   coeficientes[i] = Double.parseDouble(valores[i]);
                }
                return coeficientes;//retorna os dados lidos do programa.
            }
        }
        catch (FileNotFoundException e)//Exceptions para caso o arquivo não seja localizado, necessária caso o usuário
        {                              //configure o programa para importar os dados automaticamente e delete o arquivo.
            JOptionPane.showMessageDialog(null,Lang.message("err_file_not_found"),Lang.message("error"),JOptionPane.ERROR_MESSAGE);
            return null;
        }
        catch (Exception e)
        {
            JOptionPane.showMessageDialog(null,Lang.message("err_file_open"),Lang.message("error"),JOptionPane.ERROR_MESSAGE);
            return null;
        }

        return null;
    }
 }