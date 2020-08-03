package launcher;

import java.awt.EventQueue;

import view.GameFrame;
import view.Window;

public class Launcher {
	public static void main(String[] args) {
		//Se lanza la aplicacion
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GameFrame panel = new GameFrame();
					Logica logica = new Logica();
					Window frame = new Window(panel, logica);
					Controlador controlador = new Controlador(logica);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}	