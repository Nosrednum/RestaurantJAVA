package clienteInterfaz;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class PanelButtons  extends JPanel implements ActionListener{

	private JButton salida;
	private InterfazClienteBasico padre;

	public PanelButtons (InterfazClienteBasico padre) {
		this.padre = padre;

		setLayout(new FlowLayout());

		mesas = new JList<>();

		salida =new JButton();
		salida.setPreferredSize(new Dimension(25, 25));
		salida.setText("X");
		salida.setFont(new Font("Arial", 1, 15));
		salida.setBorder(null);
		salida.setIcon(new ImageIcon());
		salida.addActionListener(this);
		salida.setActionCommand("EXIT");
		add(salida);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getActionCommand().compareTo("EXIT") == 0)
			padre.salida();
	}
}
