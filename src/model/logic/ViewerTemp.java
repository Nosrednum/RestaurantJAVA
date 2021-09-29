package model.logic;

import java.util.Scanner;

import DataStructures.QueStack;
import model.value_objects.Categoria;
import model.value_objects.Product;

public class ViewerTemp {

	private static Manager m = new Manager();
	static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) {
		System.out.println("Esperando conexiones");
		m.recibirConexiones();
	}
}
