package clienteInterfaz;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import cliente.Cliente;

@SuppressWarnings("serial")
public class InterfazClienteBasico extends JFrame{

	public static final String SALIDA = "EXIT"	;

	private Cliente mundo;

	private PanelMeseras meseras;
	private PanelCentral centro;

	public InterfazClienteBasico() {
		mundo = new Cliente();
		meseras = new PanelMeseras(this.mundo);
		centro = new PanelCentral(mundo, this);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setUndecorated(true);
		setSize(1080, 660);
		this.setLocationRelativeTo(null);

		add(meseras, BorderLayout.WEST);
		add(centro, BorderLayout.CENTER);

	}

	public void salida() {
		this.mundo.enviarPeticion(SALIDA);
		System.exit(0);
	}

	public static void main(String args[]) {
		try {for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
			if ("Windows".equals(info.getName())) {
				javax.swing.UIManager.setLookAndFeel(info.getClassName());
				break;
			}}
		} catch (Exception ex) {
			java.util.logging.Logger.getLogger(InterfazClienteBasico.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);}

		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {	new InterfazClienteBasico().setVisible(true);}});
	}

	public String getMesagge() {
		//ACCION//mesera//mesa//cuenta//productos:cantidad,nombre,precio
		//make//mesera1//mesa1//cuenta1//3::carnes::2000;;2::chorizos::2000

		String temp = centro.getAction() + "//" + meseras.getMesera() + "//" + centro.getMesa();
		return temp;
	}
}
