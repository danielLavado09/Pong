package controller;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;

public class Actions {
	//Declaraciones
	private boolean arribaJ1 = false;
	private boolean arribaJ2 = false;
	private boolean abajoJ1 = false;
	private boolean abajoJ2 = false;
	
	//Actions de las teclas
	//Arriba
	Action presionadoArribaJ1 = new AbstractAction() {
		@Override
		public void actionPerformed(ActionEvent e) {
			arribaJ1 = true;
		}
	};

	Action presionadoArribaJ2 = new AbstractAction() {
		@Override
		public void actionPerformed(ActionEvent e) {
			arribaJ2 = true;
		}
	};

	Action liberadoArribaJ1 = new AbstractAction() {
		@Override
		public void actionPerformed(ActionEvent e) {
			arribaJ1 = false;
		}
	};

	Action liberadoArribaJ2 = new AbstractAction() {
		@Override
		public void actionPerformed(ActionEvent e) {
			arribaJ2 = false;
		}
	};

	//Abajo
	Action presionadoAbajoJ1 = new AbstractAction() {
		@Override
		public void actionPerformed(ActionEvent e) {
			abajoJ1 = true;
		}
	};

	Action presionadoAbajoJ2 = new AbstractAction() {
		@Override
		public void actionPerformed(ActionEvent e) {
			abajoJ2 = true;
		}
	};

	Action liberadoAbajoJ1 = new AbstractAction() {
		@Override
		public void actionPerformed(ActionEvent e) {
			abajoJ1 = false;
		}
	};

	Action liberadoAbajoJ2 = new AbstractAction() {
		@Override
		public void actionPerformed(ActionEvent e) {
			abajoJ2 = false;
		}
	};
	
	//Getters
	public boolean isArribaJ1() {
		return arribaJ1;
	}

	public boolean isArribaJ2() {
		return arribaJ2;
	}

	public boolean isAbajoJ1() {
		return abajoJ1;
	}

	public boolean isAbajoJ2() {
		return abajoJ2;
	}
}
