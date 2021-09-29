package clienteInterfaz;

import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import cliente.Categoria;
import cliente.Cliente;

@SuppressWarnings("serial")
public class PanelCentral extends JPanel{

	private JTabbedPane productos;
	private Cliente mundo;
	private ArrayList<JPanel> categorias;
	private PanelButtons botones;

	public PanelCentral(Cliente pMundo, InterfazClienteBasico padre) {
		this.mundo = pMundo;

		botones = new PanelButtons(padre);

		productos = new JTabbedPane();		
		categorias = new ArrayList<>();

		for (Categoria c: mundo.categorias()) {

		}

		add(botones, BorderLayout.NORTH);

	}

	public String getAction() {
		//TODO Auto-generated method stub
		return "MAKE";
	}

	public String getMesa() {
		// TODO Auto-generated method stub
		return null;
	}
}
