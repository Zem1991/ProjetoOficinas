package serial;

import gui.Janela;
import java.io.*;
import gnu.io.*;
import java.util.*;
import javax.swing.*;
public class SerialComm implements SerialPortEventListener
{
    private SerialPort serialPort;
    /** Buffered input stream from the port */
    private InputStream input;
    /** Milliseconds to block while waiting for port open */
    private static final int TIME_OUT = 2000;
    /** Default bits per second for COM port. */
    private static final int DATA_RATE = 9600;
    /** Stores the received value */
    private double value;
    /** Stores the received String to concat if necessary **/
    private String temp;
    private boolean isOpen = false;
    private Janela janela;
    public boolean isConected()
    {
        return isOpen;
    }
    public void initialize(String portName)
    {
        CommPortIdentifier portId = null;
        Enumeration portEnum = CommPortIdentifier.getPortIdentifiers();
        // iterate through, looking for the port
        while (portEnum.hasMoreElements())
        {
            CommPortIdentifier currPortId = (CommPortIdentifier) portEnum.nextElement();
            if (currPortId.getName().equals(portName))
            {
                portId = currPortId;
                break;
            }
        }
        if (portId == null)
        {
            JOptionPane.showMessageDialog(null, "Porta serial não encontrada", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try
        {
            // open serial port, and use class name for the appName.
            serialPort = (SerialPort) portId.open(
            this.getClass().getName(), TIME_OUT);
            // set port parameters
            serialPort.setSerialPortParams(DATA_RATE, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
            // open the stream
            input = serialPort.getInputStream();
            // add event listeners
            serialPort.addEventListener(this);
            serialPort.notifyOnDataAvailable(true);
            // set port is open
            isOpen = true;
        }
        catch (Exception e)
        {
            janela.verificaArduino();
             JOptionPane.showMessageDialog(null, "Erro ao conectar", "Erro", 1);
             this.close();
        }
    }
    /**
    * This should be called when you stop using the port.
    * This will prevent port locking on platforms like Linux.
    */
    public synchronized void close()
    {
        if (serialPort != null)
        {
            serialPort.removeEventListener();
            serialPort.close();
            isOpen = false;
        }
    }
    /**
    * Handle an event on the serial port. Read the data and print it.
    */
    public void setJanela(Janela janela)
    {
        this.janela = janela;
    }
    public synchronized void serialEvent(SerialPortEvent oEvent)
    {
    if (oEvent.getEventType() == SerialPortEvent.DATA_AVAILABLE)
    {
        try
        {
            int available;
            byte[] chunk;
            String read;
            available = input.available();
            if(available > 0)
            {
                chunk = new byte[available];
                input.read(chunk, 0, available);
                read = new String(chunk);
                if(isReadComplete(read))
                {
                    value = Double.parseDouble(temp);
                    temp = null;
                }
            }
            }
            catch (IOException ex)
            {
                 janela.verificaArduino();
                 JOptionPane.showMessageDialog(null, "Dispositivo desconectado", "Erro", 1);
                 this.close();
            }
        }
    }
    public boolean isReadComplete(String read)
    {
        try
        {
            int lenght = read.length();
            if(lenght > 1 && read.charAt(0) == '*' && read.charAt(lenght-1) == '*')
            {
                temp = read.substring(1, lenght - 1);
                return true;
            }
            else
            {
                if(temp == null)
                {
                    temp = read;
                }
                else
                {
                    temp += read;
                    if(lenght > 1 && temp.charAt(0) == '*' && temp.charAt(lenght-1) == '*')
                    {
                        temp = temp.substring(1, lenght - 1);
                        return true;
                    }
                }
            }
        }
        catch(Exception e)
        {
            janela.verificaArduino();
             JOptionPane.showMessageDialog(null, "Dispositivo desconectado", "Erro", 1);
             this.close();
        }
            return false;
    }
    public synchronized double readValue()
    {
        if (isOpen)
        {
            //System.out.println("Arduino: " + value);
            
            return value;
        }
        else 
        {
            return -1;
        }
    }
    public ArrayList <String> list()
    {
        ArrayList <String> nomeSerial = new ArrayList<String>();
        Enumeration<CommPortIdentifier> idPorta = CommPortIdentifier.getPortIdentifiers();
        while(idPorta.hasMoreElements())
        {
            nomeSerial.add(idPorta.nextElement().getName());
        }
        return nomeSerial;
    }
}