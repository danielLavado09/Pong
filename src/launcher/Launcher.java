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
					Window frame = new Window(panel);
					Controlador controlador = new Controlador(panel);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}	