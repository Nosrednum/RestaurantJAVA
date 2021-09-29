package model.value_objects;

public class Table implements Comparable<Table>{

	private String tableId;
	private Waitress Wtemp;
	private Bill Btemp;

	public Table(String tableId) {
		this.tableId = tableId;
	}

	public Table(String tableId, Waitress w, Bill b) {
		this.tableId = tableId;
		this.Btemp = b;
		this.Wtemp = w;
		w.addBill(b);
	}

	public String id() {return this.tableId;}
	public boolean state() {return this.Btemp.payed();}

	public Bill currentBill() {return this.Btemp;}
	public void setBill(Bill temp) {this.Btemp = temp;}

	public void setWaitress(Waitress temp) {this.Wtemp = temp;}
	public Waitress currentWaitress() {return this.Wtemp;}

	public boolean ocuped() {return this.Btemp != null;}

	@Override public int compareTo(Table t){return this.tableId.compareTo(t.tableId);}
}
