package model.logic;

import java.io.FileReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Properties;

import DataStructures.RedBlackBST;
import model.value_objects.*;

public class Manager {

	private final String DIRECCION_MENU 	= "./data/menu.properties"		;
	private final String DIRECCION_MESERAS 	= "./data/meseras.properties"	;
	private final String DIRECCION_MESAS 	= "./data/mesas.properties"		;


	private RedBlackBST<Table> 		tables	;
	private RedBlackBST<Waitress> 	meseras	;
	private RedBlackBST<Categoria>	menu	;
	private RedBlackBST<Bill> 		bills	;

	private String sMenu	;
	private String sMesers	;

	private int puerto;

	private ServerSocket receptor;

	public Manager() {
		bills 	= new RedBlackBST<>();
		meseras = new RedBlackBST<>();
		tables 	= new RedBlackBST<>();
		menu 	= new RedBlackBST<>();
		sMenu 	= ""	;
		sMesers = ""	;
		puerto 	= load();
	}


	public void recibirConexiones( ){
		try{
			receptor = new ServerSocket( puerto );
			while( true ){
				// Esperar una nueva conexión.
				Socket socketNuevoCliente = receptor.accept( );
				// Maneja la peticion del cliente
				ManagerThread mt = new ManagerThread(socketNuevoCliente, this);
				mt.start();
			}
		}
		catch( IOException e ){e.printStackTrace();}//problemas de conección
		finally{try {receptor.close( );}catch( IOException e ){e.printStackTrace();}}//problemas cerrando el canal
	}

	public Categoria getCategory(String name) {
		return menu.get(new Categoria(name));
	}

	private  int load() {
		Properties p = new Properties();

		String aux = "";

		try {p.load(new FileReader(DIRECCION_MENU));}
		catch (IOException  e1) {e1.printStackTrace();}

		Enumeration<Object> e = p.keys();
		while(e.hasMoreElements()) {
			String name = e.nextElement().toString();
			if(p.getProperty(name).equals(p.getProperty("servidor.puerto"))) { aux = p.getProperty( "servidor.puerto" ); continue;}
			String[] pricecategory = p.getProperty(name).split(";");
			int price = Integer.parseInt(pricecategory[0]);
			Product pTemp = new Product(name, price, pricecategory[1]);
			Categoria toAdd = new Categoria(pTemp.category());
			Categoria temp = menu.get(toAdd);
			if(temp != null)temp.addProduct(pTemp);
			else{
				toAdd.addProduct(pTemp);
				menu.put(toAdd);
			}
		}
		int puerto = Integer.parseInt( aux );

		try {p.clear();
		p.load(new FileReader(DIRECCION_MESERAS));}
		catch (IOException  e1) {e1.printStackTrace() ;}

		e = p.keys();
		while(e.hasMoreElements())
			meseras.put(new Waitress(p.getProperty(e.nextElement().toString())));

		try {p.clear();p.load(new FileReader(DIRECCION_MESAS));}
		catch (IOException  e1) {e1.printStackTrace() ;}

		e = p.keys();
		while(e.hasMoreElements())
			tables.put(new Table(p.getProperty(e.nextElement().toString())));	

		return puerto;
	}

	public void addBill(Bill toAdd, Waitress w) {
		this.bills.put(toAdd);
		Table temp = this.tables.get(new Table(toAdd.table()));
		temp.setBill(toAdd);
		temp.setWaitress(w);
	}

	public Bill getBill(String id) {
		if(!bills.isEmpty())return bills.get(new Bill(id));
		return null;
	}

	public void addWaitress(String wId) {
		this.meseras.put(new Waitress(wId));
	}

	public Waitress getWaitress(String wId) {
		return this.meseras.get(new Waitress(wId));
	}

	public boolean ocupedTable(String Id) {
		Table temp = this.tables.get(new Table(Id));
		return (temp != null)?temp.ocuped():false;
	}

	public boolean makedMenu() 		{return !this.sMenu.equals("")	;}
	public boolean makedWaitress()	{return !this.sMesers.equals("");}
	public void setSMenu(String newMenu) {this.sMenu = newMenu;}
	public void setSWaitress(String newWaitresses) {this.sMesers = newWaitresses;}

	public String getMenu() {return this.sMenu;}
	public String getSMeseras() {return this.sMesers;}

	public Iterable<String> meseras(){
		ArrayList<String> toRet = new ArrayList<>();
		for (Waitress w : meseras.values())
			toRet.add(w.name());
		return toRet;}
	public Iterable<Categoria> categorias(){return this.menu.values();}

}
