package launcher;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.KeyStroke;

import view.GameFrame;

public class Controlador {
	//Declaraciones
	Logica logica;
	
	//Constructor
	public Controlador(Logica logica) {
		this.logica = logica;
		
		ManejadorEventos manejador = new ManejadorEventos();
	}
	
	//Eventos
	class ManejadorEventos {
		
	}
}
