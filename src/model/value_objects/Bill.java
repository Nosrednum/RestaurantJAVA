package model.value_objects;

import java.util.ArrayList;

public class Bill implements Comparable<Bill>{

	private String 	 myTable	;
	private Waitress myWaitres	;
	private String   id   		;
	private boolean  state		; // false if not payed
	private int 	 price		;
	private ArrayList<Product> products;

	public Bill(String id) {
		this.id 	= id	;
		this.state  = false ;
		products	= new ArrayList<>();
		this.price = 0;
	}
	public Bill(String id, Iterable<Product> products, Waitress w, String table) {
		this(id); 
		this.myTable = table;
		this.myWaitres = w;
		for (Product p : products) {price += (p.price()*p.cant());	this.products.add(p);}
	}

	public void addProduct(Product p) {
		if(p == null || !p.isProduct())return;
		this.price += (p.price() * p.cant());
		this.products.add(p);
	}

	public void deleteProduct(Product p) {
		if(products.contains(p)) {
			price -= p.price();
			products.remove(p);
		}
		else throw new IllegalArgumentException();
	}

	public void payBill  () {this.state = true; }

	public String table() {return this.myTable;}
	public Waitress waitress() {return this.myWaitres;}

	public String  id	 () {return this.id;	}
	public boolean payed () {return this.state;	}
	public int  price () {return this.price;	}

	@Override public int compareTo(Bill b) {return this.id.compareTo(b.id);}

}
