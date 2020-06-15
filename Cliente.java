import java.io.*;
import java.net.*;

public class Cliente{
    public static void main(String[] args) {
      String servidor = "ec2-18-221-192-18.us-east-2.compute.amazonaws.com";
      int puerto = 5060;
      try{
        Socket socket= new Socket (servidor,puerto);
        BufferedReader in = new BufferedReader (new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()),true);

	String c = "El cliente ha solicitado sus datos";
        out.println(c);
	String line = "";
	while  ((line = in.readLine()) != null){
		System.out.println(line);
		break;
	}
	socket.close();
      } catch (IOException e)
      {
       		System.out.println("¡Error en la conexión!");
      }

    }
}
