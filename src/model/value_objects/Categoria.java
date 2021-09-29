package model.value_objects;

import java.util.ArrayList;

public class Categoria implements Comparable<Categoria>{

	private String name;
	private ArrayList<Product> inCategory;

	public Categoria(String name) {
		this.name = name;
		inCategory = new ArrayList<>();
	}

	public String name() {return this.name;}
	public void addProduct(Product p) {this.inCategory.add(p);}

	public Iterable<Product> products(){return this.inCategory;}

	@Override public int compareTo(Categoria o) {return this.name.compareTo(o.name);}

	public String darRutaImagen() {	return "./data/image.png";}
}
