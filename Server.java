import java.net.*;
import java.io.*;
import java.time.*;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.*;
//import java.lang.management.*;
public class Server{

	public static void main(String args[]){
		try {

			int puerto = 5060;
			ServerSocket s = new ServerSocket(puerto);
			String comandoSalir = "Exit";
			String entrada = "";
			InetAddress address = InetAddress.getLocalHost();
			NetworkInterface inNet = NetworkInterface.getByIndex(1);
			Process p = Runtime.getRuntime().exec(new String[] { "bash", "-c", "ps aux | grep nuestroProceso" });
			Date date = new Date();
			DateFormat hourdateFormat = new SimpleDateFormat("HH:mm:ss dd/MM/yyyy");
			Runtime r=Runtime.getRuntime();  
			System.out.println("Servidor iniciado en el puerto " + puerto + "...");
			while(true){
				Socket s1 = s.accept();
				System.out.println("Aceptando conexión...");
				PrintWriter out = new PrintWriter(s1.getOutputStream(), true);
				BufferedReader in = new BufferedReader(new InputStreamReader(s1.getInputStream()));

				while ((entrada = in.readLine()) != null) {
				    System.out.println(entrada);
				    out.println("Nombre del servidor: " + address.getHostName() +
				    	" | Usuario del server " + System.getProperty("user.name") +
				    	" | Sistema Operativo: " + System.getProperty("os.name") +
				    	" | Dirección IP: " + address.getHostAddress() +
				    	" | Dirección MAC: " + getMacAddressByNetworkInterface() +
				    	" | Procesos: " + p +
				    	" | Fecha y hora: " + hourdateFormat.format(date) +
				    	" | Total de procesadores: " + r.availableProcessors() + " - Velocidad del procesador: " + r.maxMemory() +
				    	" | Discos: " + getDiscos() 
				    	);
				    out.println("");
				    if (entrada.trim().equals(comandoSalir))
				        return;
				}
				s1.close();

			}

		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}

private static String getDiscos(){
	File[] drives = File.listRoots();
	String discosLista = "";
	if (drives != null && drives.length > 0) {
	    for (File aDrive : drives) {
	        //System.out.println(aDrive);
	        discosLista = discosLista + aDrive + " - Total de espacio: " + aDrive.getTotalSpace() + " - Total de espacio libre: " + aDrive.getFreeSpace();
	    }
	}
	return discosLista;
}

private static String getMacAddressByNetworkInterface() {
    try {
        List<NetworkInterface> nis = Collections.list(NetworkInterface.getNetworkInterfaces());
        for (NetworkInterface ni : nis) {
            if (!ni.getName().equalsIgnoreCase("wlan0")) continue;
            byte[] macBytes = ni.getHardwareAddress();
            if (macBytes != null && macBytes.length > 0) {
                StringBuilder res1 = new StringBuilder();
                for (byte b : macBytes) {
                    res1.append(String.format("%02x:", b));
                }
                return res1.deleteCharAt(res1.length() - 1).toString();
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return Long.toHexString(System.currentTimeMillis());
}
 

}
