package src.p03.c01;

import java.util.Enumeration;
import java.util.Hashtable;

public class Parque implements IParque{


	// TODO 
	static final long MIN = 0; // mínimo valor permitido
	static final long MAX = 40; // máximo valor permitido
	private int contadorPersonasTotales;
	private Hashtable<String, Integer> contadoresPersonasPuerta;
	
	
	public Parque() {	// TODO
		contadorPersonasTotales = 0;
		contadoresPersonasPuerta = new Hashtable<String, Integer>();
		// TODO
	}


	@Override
	public synchronized void entrarAlParque(String puerta){		// TODO
		
		// Si no hay entradas por esa puerta, inicializamos
		if (contadoresPersonasPuerta.get(puerta) == null){
			contadoresPersonasPuerta.put(puerta, 0);
		}
		
		try {
			comprobarAntesDeEntrar();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		
		// Aumentamos el contador total y el individual
		contadorPersonasTotales++;		
		contadoresPersonasPuerta.put(puerta, contadoresPersonasPuerta.get(puerta)+1);
		
		// Imprimimos el estado del parque
		imprimirInfo(puerta, "Entrada");
		checkInvariante();
		notifyAll();
		
		
		// TODO
		
	}
	
	// 
	// TODO MÃ©todo salirDelParque
	public synchronized void salirDelParque(String puerta) {
		if (contadoresPersonasPuerta.get(puerta) == null){
			contadoresPersonasPuerta.put(puerta, 0);
		}
		try {
			comprobarAntesDeSalir();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Aumentamos el contador total y el individual
				contadorPersonasTotales--;		
				contadoresPersonasPuerta.put(puerta, contadoresPersonasPuerta.get(puerta)-1);
				
				// Imprimimos el estado del parque
				imprimirInfo(puerta, "Salida");
				checkInvariante();
				notifyAll();
	}
	
	
	private void imprimirInfo (String puerta, String movimiento){
		System.out.println(movimiento + " por puerta " + puerta);
		System.out.println("--> Personas en el parque " + contadorPersonasTotales); //+ " tiempo medio de estancia: "  + tmedio);
		
		// Iteramos por todas las puertas e imprimimos sus entradas
		for(String p: contadoresPersonasPuerta.keySet()){
			System.out.println("----> Por puerta " + p + " " + contadoresPersonasPuerta.get(p));
		}
		System.out.println(" ");
	}
	
	private int sumarContadoresPuerta() {
		int sumaContadoresPuerta = 0;
			Enumeration<Integer> iterPuertas = contadoresPersonasPuerta.elements();
			while (iterPuertas.hasMoreElements()) {
				sumaContadoresPuerta += iterPuertas.nextElement();
			}
		return sumaContadoresPuerta;
	}
	
	protected void checkInvariante() {
		assert sumarContadoresPuerta() == contadorPersonasTotales : "INV: La suma de contadores de las puertas debe ser igual al valor del contador del parte";
		if(contadorPersonasTotales > MAX || contadorPersonasTotales < MIN ) {
			assert contadorPersonasTotales > MAX :"No caben mas personas en el parque";
			assert contadorPersonasTotales < MIN:"No puede haber antipersonas";
		}
		
	}

	protected void comprobarAntesDeEntrar() throws InterruptedException{	// TODO
		//
		// TODO
		while(contadorPersonasTotales == MAX) {
			wait();
		}
	}

	protected void comprobarAntesDeSalir() throws InterruptedException{		// TODO
		//
		// TODO
		while(contadorPersonasTotales == MIN) {
			wait();
		}
	}


}
