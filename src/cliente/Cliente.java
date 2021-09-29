package cliente;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;

public class Cliente {

	public static final String SEPARADOR_COMANDO 	= "//";
	public static final String SEPARADOR_PRODUCTO 	= ";;";
	public static final String SEPARADOR_INFO		= "::";
	public static final String SEPARADOR_ESPECIAL	= "!!";

	public static final String CREAR_CUENTA			= "MAKE"	;
	public static final String BORRAR_PRODUCTO		= "DELETE"	;
	public static final String ANIADIR_PRODUCTO		= "ADD"		;
	public static final String CONSULTAR			= "FETCH"	;
	public static final String SALIDA				= "EXIT"	;
	public static final String INFO_INICIO			= "INFO"	;

	public final String SERVER = "localhost";
	public final int PORT = 6977;

	private BufferedReader input;
	private PrintStream output;

	private  Socket socket;
	private ArrayList<Categoria> menu;
	private ArrayList<String> personal;


	public Cliente() {
		menu = new ArrayList<>();
		personal = new ArrayList<>();
		try {
			socket = new Socket(SERVER, PORT);
			input = new BufferedReader( new InputStreamReader(socket.getInputStream()));                
			output = new PrintStream(socket.getOutputStream()); 

			output.println(INFO_INICIO); 
			processInfo(input.readLine());
		} catch (Exception e) {}
	}

	public String enviarPeticion(String toSend) {
		String toRet = "";
		try {
			socket = new Socket(SERVER, PORT);	input = new BufferedReader( new InputStreamReader(socket.getInputStream()));
			output = new PrintStream(socket.getOutputStream()); 

			output.println(toSend); 	toRet = input.readLine();

			socket.close();	input.close();	output.close();
		} catch (IOException e) {}
		return toRet;
	}

	private void processInfo(String info) {
		String[] categoriasYEmpleados = info.split(SEPARADOR_ESPECIAL);
		String[] categorias = categoriasYEmpleados[0].split(SEPARADOR_COMANDO);
		String[] empleados = categoriasYEmpleados[1].split(SEPARADOR_PRODUCTO);
		for (String c : categorias) {
			String[] nameAndProducts = c.split(SEPARADOR_PRODUCTO);
			Categoria temp = new Categoria(nameAndProducts[0]);
			String[] prodsAndPrices = nameAndProducts[1].split(SEPARADOR_INFO);
			for (String pr : prodsAndPrices) {
				String[] nombreYprecio = pr.split(":");
				int precio = Integer.parseInt(nombreYprecio[1]);
				Product p = new Product(nombreYprecio[0], precio, nameAndProducts[0]);
				temp.addProduct(p);
			}
			menu.add(temp);
		}
		for (String e : empleados)
			personal.add(e);
	}

	public Iterable<String> personal(){return this.personal;}
	public Iterable<Categoria> categorias(){return this.menu;}
}
