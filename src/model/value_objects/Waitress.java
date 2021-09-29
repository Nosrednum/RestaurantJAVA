package model.value_objects;

import DataStructures.RedBlackBST;

public class Waitress implements Comparable<Waitress>{

	private String name;
	private RedBlackBST<Bill> bills;

	public Waitress(String name) {
		this.name = name;
		this.bills = new RedBlackBST<>();
	}

	public void addBill(Bill b) {bills.put(b);	}

	public int billsCant() {return bills.size();}
	public String name() {return name;			}
	public Iterable<Bill> bills(){return bills.values(); }

	@Override public int compareTo(Waitress w) {return this.name.compareTo(w.name);}
}
