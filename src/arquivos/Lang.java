/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package arquivos;
import java.io.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

/**
 * @author Raphael Zagonel Moletta
 *
 */
public class Lang
{

	private static ArrayList<Message> mList = new ArrayList<Message>();
	private static boolean bLoad;
        private static int langSelected = 0;
        private static String language;

        public static int getLangSelected()
        {
            return langSelected;
        }
	public static String message(String value)
        {
            if (!bLoad)
                    load("pt_br");
            for (Message m : mList)
            {
                    if(value.trim().compareToIgnoreCase(m.getId().trim())==0)
                            return m.getMessage();
            }
            return "";
	}
        public static ArrayList<String> languages()
        {
            
                File folder = new File("src/languages/");
                String [] files = folder.list();
                ArrayList<String> lang = new ArrayList<String>();
                for(int count = 0; count < files.length; count++)
                {
                    if(files[count].contains(".ini"))

                    {
                        BufferedReader bReader;
                        try
                        {
                            bReader = new BufferedReader(new FileReader("src/languages/" + files[count]));
                            String sTmp;
                            String temp0 = null;
                            String temp1 = null;
                            while ((sTmp = bReader.readLine()) != null)
                            {
                                if(temp0 != null  & temp1 != null)
                                   break;
                                if (!sTmp.startsWith(";") && !sTmp.startsWith("[") && !sTmp.isEmpty())
                                {
                                   String m[]=sTmp.split("=");
                                   if(m[0].equals("language"))
                                   {
                                       temp0 = m[1];
                                   }
                                   else if(m[0].equals("language_name"))
                                   {
                                        temp1 = m[1];
                                   }
                                }
                            }
                            if(temp0.equals(language.replace('_', '-')))
                                langSelected = lang.size();
                            lang.add(temp0 + " - " + temp1);
                        }
                        catch (Exception ex) {}
                    }
                }
            return lang;
        }
	public static boolean load(String language)
        {
            bLoad = false;
            
            languages();
            try
            {
                File lang = new File("src/languages/"+language+".ini");
                //System.out.print(lang.getAbsolutePath());
                if (lang.exists() && lang.canRead())
                {
                    Lang.language = language;
                    BufferedReader bReader = new BufferedReader(new FileReader(lang));
                    String sTmp;
                    while ((sTmp = bReader.readLine()) != null)
                    {
                        if (!sTmp.startsWith(";") && !sTmp.startsWith("[") && !sTmp.isEmpty())
                        {
                            if(sTmp.contains("\\n"))
                                sTmp = sTmp.replace("\\n", "\n");
                            mList.add(new Message(sTmp.split("=")));
                        }
                    }
                    bLoad = true; 
                }
            }
            catch (FileNotFoundException e)
            {
                JOptionPane.showMessageDialog(null, "Language file not found!","Error 0x0101", JOptionPane.ERROR_MESSAGE);
                System.exit(0);
            }
            catch (EOFException e)
            {
                JOptionPane.showMessageDialog(null, "End of language file unexpected!","Error 0x0102", JOptionPane.ERROR_MESSAGE);
                System.exit(0);
                // alert the user that the end of the file
                // was reached
            }
            catch (ObjectStreamException e)
            {
                JOptionPane.showMessageDialog(null, "Language file is corrupted!","Error 0x0103", JOptionPane.ERROR_MESSAGE);
                System.exit(0);
                // alert the user that the file is corrupted
            }
            catch (IOException e)
            {
                JOptionPane.showMessageDialog(null, "Language file cannot be accessed!","Error 0x0104", JOptionPane.ERROR_MESSAGE);
                System.exit(0);
                // alert the user that some other I/O
                // error occurred
            }
            catch (Exception e) {
                JOptionPane.showMessageDialog(null, "An unexpected error occurred with Language class","Error 0x0105", JOptionPane.ERROR_MESSAGE);
                System.exit(0);
            }
            return bLoad;
	}

	public static boolean isLoad()
        {
		return bLoad;
	}

}
