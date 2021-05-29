package com.soloqchallenge.demo.rest;

public class Jugador {
	private String nombre;
	private int wins;
	private int loses;
	private LigaEnum liga;
	private int lps;
	private boolean racha;
	private String nombreReal;
	
	public Jugador() {
		
	}
	
	public Jugador(String nombre, int wins, int loses, LigaEnum liga, int lps, boolean racha, String nombreReal) {		
		this.nombre = nombre;
		this.wins = wins;
		this.loses = loses;
		this.liga = liga;
		this.lps = lps;
		this.racha = racha;
		this.nombreReal = nombreReal;
	}
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public int getWins() {
		return wins;
	}
	public void setWins(int wins) {
		this.wins = wins;
	}
	public int getLoses() {
		return loses;
	}
	public void setLoses(int loses) {
		this.loses = loses;
	}

	public LigaEnum getLiga() {
		return liga;
	}

	public void setLiga(LigaEnum liga) {
		this.liga = liga;
	}

	public int getLps() {
		return lps;
	}

	public void setLps(int lps) {
		this.lps = lps;
	}

	public boolean getRacha() {
		return racha;
	}

	public void setRacha(boolean racha) {
		this.racha = racha;
	}

	public String getNombreReal() {
		return nombreReal;
	}

	public void setNombreReal(String nombreReal) {
		this.nombreReal = nombreReal;
	}
	
	
	

	
	
}
