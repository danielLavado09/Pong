package launcher;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import view.GameFrame;

public class Controlador {
	//Declaraciones
	GameFrame panel;
	
	//Constructor
	public Controlador(GameFrame panel) {
		this.panel = panel;
		
		ManejadorEventos manejador = new ManejadorEventos();
	}
	
	//Eventos
	class ManejadorEventos{
		
	}
}
