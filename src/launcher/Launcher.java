package launcher;

import java.awt.EventQueue;

import controller.Controller;

public class Launcher {
	public static void main(String[] args) {
		//Se lanza la aplicacion
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new Controller();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}	