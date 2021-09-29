package clienteInterfaz;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import cliente.Cliente;

public class PanelSend extends JPanel implements ActionListener{

	private JButton send;
	private String command = "ENVIAR";
	private Cliente mundo;
	private InterfazClienteBasico padre;
	private String toSend;

	public PanelSend(Cliente mundo, InterfazClienteBasico padre) {
		this.mundo = mundo;
		this.padre = padre;

		toSend = new String();

		send = new JButton();
		send.setActionCommand(command);
		send.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getActionCommand().compareTo(command) == 0) {
			mundo.enviarPeticion(padre.getMesagge());
		}
	}
}
