package model.value_objects;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;

import DataStructures.QueStack;
import model.logic.Manager;

public class ManagerThread extends Thread{

	public static final String SEPARADOR_COMANDO 	= "//";
	public static final String SEPARADOR_PRODUCTO 	= ";;";
	public static final String SEPARADOR_INFO		= "::";
	public static final String SEPARADOR_ESPECIAL	= "!!";

	public static final String CREAR_CUENTA			= "MAKE"	;
	public static final String BORRAR_PRODUCTO		= "DELETE"	;
	public static final String ANIADIR_PRODUCTO		= "ADD"		;
	public static final String CONSULTAR			= "FETCH"	;
	public static final String SALIDA				= "EXIT"	;
	public static final String INFO					= "INFO"	;
	public static final String CERRAR_CUENTA		= "CLOSE"	;
	public static final String PAGAR_CUENTA			= "PAY"		;
	public static final String PARA_LLEVAR			= "ORDER"	;


	private boolean fin;

	private Manager padre;

	private Socket coneccion;
	private PrintStream out;
	private BufferedReader in;

	public ManagerThread(Socket s, Manager pPadre) throws IOException {
		this.padre = pPadre;
		this.coneccion = s;

		out = new PrintStream( s.getOutputStream( ));
		in = new BufferedReader( new InputStreamReader( s.getInputStream( ) ) );

		fin = false;
	}

	public void run() {
		while(!fin)try {
			String peticion = in.readLine();

			if(peticion != null) procesarPeticion(peticion);
			if	( fin )			 terminarAccion();

		}catch(IOException ie) {ie.getMessage();}//ie.printStackTrace(); 
	}


	private void procesarPeticion(String readLine) {
		String[] instrucciones = readLine.split(SEPARADOR_COMANDO);

		System.out.println(readLine);//es por el momento para saber que le llega al servidor

		switch (instrucciones[0]) {
		case CREAR_CUENTA:
			makeBill(instrucciones[1], instrucciones[2], instrucciones [3], instrucciones[4]);
			out.println("se creó");
			break;
		case CONSULTAR:
			Bill temp = padre.getBill(instrucciones[3]);
			String toAdd = (!temp.payed())?" sin pagar":" paga";
			out.println((temp != null)?temp.price() + toAdd:"algo no salió muy bien");
			break;
		case SALIDA:
			this.fin = true;
			break;
		case INFO:
			out.println(catToString()+SEPARADOR_ESPECIAL+waiToString());
			break;
		default:
			System.out.println("mal formato");
			break;
		}
	}

	private void terminarAccion() {
		try {
			in.close( );
			out.close( );
			coneccion.close( );
		} catch (IOException e) {e.printStackTrace();}
	}

	public void makeBill(String waitress, String mesa, String billId, String elements) {

		if(padre.ocupedTable(mesa))System.err.println("Mesa actualmente ocupada");
		Waitress temp = padre.getWaitress(waitress);

		if(temp == null)  System.err.println("Inconsistencia en el nombre de una mesera");

		QueStack<Product> elementsQ = new QueStack<>();
		String[] products = elements.split(SEPARADOR_PRODUCTO);
		for (String p : products) {
			String[] nameAndPrice = p.split(SEPARADOR_INFO);
			int cant  = Integer.parseInt(nameAndPrice[0]);
			int price = Integer.parseInt(nameAndPrice[2]);
			Product ptemp = new Product	(nameAndPrice[1], price);
			ptemp.setCant(cant);
			elementsQ.enqueue(ptemp);
		}
		makeBill(temp, mesa,billId, elementsQ);
	}

	private void makeBill(Waitress waitress, String mesa, String billId, Iterable<Product> elements) {
		Bill newBill = new Bill(billId, elements, waitress, mesa);
		waitress.addBill(newBill); padre.addBill(newBill, waitress);
	}

	public String catToString() {
		if(padre.makedMenu())return padre.getMenu();
		String toRet = "";
		String toAdd = "";
		ArrayList<String> categoriasConProductos = new ArrayList<>();
		for (Categoria c : padre.categorias()) {
			toAdd = c.name() + SEPARADOR_PRODUCTO;
			for (Product p : c.products()) 
				toAdd += (p.name()+ ":" +p.price()+SEPARADOR_INFO);
			categoriasConProductos.add(toAdd.substring(0, toAdd.length()-2));
		}
		for (String c : categoriasConProductos)
			toRet += (c + SEPARADOR_COMANDO);
		String toRet2 = toRet.substring(0, toRet.length()-2);
		padre.setSMenu(toRet2);
		return toRet2;
	}

	public String waiToString() {
		if(padre.makedWaitress())return padre.getSMeseras();
		String toRet = "";
		for (String w : padre.meseras())
			toRet += (w + SEPARADOR_PRODUCTO);
		String toRet2 = toRet.substring(0, toRet.length()-2);
		padre.setSWaitress(toRet2);
		return toRet2;
	}

}
