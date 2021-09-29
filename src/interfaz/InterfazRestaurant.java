package interfaz;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.UIManager;

import model.logic.Manager;

@SuppressWarnings("serial")
public class InterfazRestaurant extends JFrame{

	private Manager mundo;

	public InterfazRestaurant() {
		mundo = new Manager();
		setTitle( "Caldo Parao °69" );
		setLayout(new BorderLayout());

		setUndecorated(false);
		setSize(1080, 720);
		setLocationRelativeTo( null );
		setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		setResizable( false );

		//		products = new PanelProducts(mundo.categorias(), this);

		getContentPane( ).removeAll( );
		//		setContentPane( products );
		revalidate( );
	}

	public void startNewMode() {
		//		panelEditor = new PanelEditor( this );
		getContentPane( ).removeAll( );
		//		setContentPane( panelEditor );
		revalidate( );
		repaint( );
	}

	public static void main( String[] args ){
		try	{// Unifica la interfaz para Mac y para Windows.
			UIManager.setLookAndFeel( UIManager.getCrossPlatformLookAndFeelClassName( ) );
		}catch( Exception e ) {e.printStackTrace( );}
		InterfazRestaurant interfaz = new InterfazRestaurant( );
		interfaz.setVisible( true );
	}
}
