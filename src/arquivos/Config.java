package arquivos;

import java.io.*;


public class Config
{
    private boolean salvCalibr = false;
    private String calibracao;
    private String idioma;
    private String filename = "Config.dat";

    public boolean getSalvCalibracao()
    {
        return salvCalibr;
    }
    public String getCalibracao()
    {
        return calibracao;
    }
    public String getIdioma()
    {
        return idioma;
    }

    public void setCalibracao(String calibracao)
    {
        this.calibracao = calibracao;
    }

    public void setIdioma(String idioma)
    {
        this.idioma = idioma;
    }

    public void setSalvCalibracao(boolean salvCalibracao)
    {
        this.salvCalibr = salvCalibracao;
    }

    public void salvarConfig()
    {
        /*
         * Salva as configurações no seguinte formato
         * Boolean -> indica se o programa irá carregar o arquivo de calibração automaticamente
         * String  -> guarda o nome do idioma, no formato xx_yy, xx a sigla do idioma, e yy a sigla do país
         * String  -> Esta String só é adicionada ao arquivo caso o boolean seja true.
                      É responsável por guardar o endereço da pasta do arquivo de calibração
         */
        try
        {
            ObjectOutputStream file = new ObjectOutputStream(new FileOutputStream(filename));
            file.writeBoolean(salvCalibr);
            file.writeUTF(idioma);
            if(salvCalibr == true)
                file.writeUTF(calibracao);
            file.close();
        }
        catch (Exception e) {}
    }

    public Config()//Construtora da classe, determina o valor dos atrbutos de acordo com o arquivo.
    {
        if(new File(filename).exists())
        {
            try
            {
                ObjectInputStream file = new ObjectInputStream(new FileInputStream(filename));
                salvCalibr = file.readBoolean();
                idioma =  file.readUTF();
                if(salvCalibr == true)
                    calibracao = file.readUTF();
                file.close();
            }
            catch (Exception e) {}
        }
        else
        {
            idioma = "pt-br";
            salvCalibr = false;
        }
    }
}


