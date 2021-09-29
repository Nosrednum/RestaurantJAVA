package clienteInterfaz;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.*;
import javax.swing.border.LineBorder;

import cliente.Cliente;

@SuppressWarnings("serial")
public class PanelMeseras extends JPanel{

	private JLabel imagen;
	private JLabel textoMeseras;
	private JScrollPane contenedor;
	private JList<String> meseras;
	private DefaultListModel<String> modelo;
	private JPanel encabezado;

	private Cliente mundo;

	private final String FONT ="Microsoft JhengHei Light";


	public PanelMeseras(Cliente mundo) {

		this.mundo = mundo;

		modelo = new DefaultListModel<>();

		setBackground(Color.WHITE);
		setLayout(new BorderLayout());
		setBorder(new LineBorder(new Color(204, 204, 204)));

		imagen = new JLabel();
		textoMeseras = new JLabel();
		contenedor = new JScrollPane();
		meseras = new JList<>();
		encabezado = new JPanel();
		encabezado.setLayout(new GridLayout(1, 2, 15,15));
		encabezado.setBackground(Color.WHITE);

		imagen.setBackground(Color.WHITE);
		imagen.setHorizontalAlignment(SwingConstants.LEFT);
		imagen.setIcon(new ImageIcon("./data/icons8-cocinero-de-sexo-femenino-40.png")); 

		textoMeseras.setBackground(Color.WHITE);
		textoMeseras.setFont(new Font(FONT, 0, 24));
		textoMeseras.setForeground(new Color(255, 102, 0));
		textoMeseras.setHorizontalAlignment(SwingConstants.LEFT);
		textoMeseras.setText("MESERAS");

		encabezado.add(imagen, BorderLayout.WEST);
		encabezado.add(textoMeseras, BorderLayout.WEST);

		meseras.setFont(new Font(FONT, 0, 36));
		meseras.setForeground(new Color(204, 102, 0));
		meseras.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		meseras.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		meseras.setSelectionBackground(new Color(255, 153, 51));
		meseras.setVisibleRowCount(12);

		agregarMeseras();
		meseras.setModel(modelo);
		contenedor.setViewportView(meseras);

		add(encabezado, BorderLayout.NORTH);
		add(contenedor, BorderLayout.CENTER);

		setVisible(true);
	}

	public void agregarMeseras(){
		for (String m : mundo.personal())
			modelo.addElement(m);
	}

	public String getMesera() {
		return meseras.getSelectedValue();
	}
}
