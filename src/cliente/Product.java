package cliente;

public class Product implements Comparable<Product>{

	private String name, category;
	private int price, cant;

	public Product(String name, int price) {
		this.cant = 1;
		this.name 	= name	;
		this.price	= price	;
	}

	public Product(String name, int price, String category) {
		this.name 	= name	;
		this.price	= price	;
		this.category   = category;
	}

	public String name		() {return this.name	;}
	public String category	() {return this.category;}
	public int cant			() {return this.cant	;}
	public int price		() {return this.price	;}
	public boolean isProduct() {return this.price>0 ;}
	public void setCant	(int cant){this.cant = cant ;}

	@Override public int compareTo(Product p) {return this.name.compareTo(p.name);}
}
