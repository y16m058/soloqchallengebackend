package com.soloqchallenge.demo.rest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.apache.tomcat.util.json.JSONParser;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SoloQRestController {

	private static HttpURLConnection connection;
	private static ArrayList<Jugador> jugadores = new ArrayList<Jugador>();

	@GetMapping("/nombre")
	public String nombre() {
		return jugadores.get(0).getNombre();
	}

	@GetMapping("/wins")
	public int wins() {
		return jugadores.get(0).getWins();
	}

	@GetMapping("/loses")
	public int loses() {
		return jugadores.get(0).getLoses();
	}

	@GetMapping("/jugadores")
	public ArrayList<Jugador> jugadores() {
		if (jugadores.isEmpty()) {
			inicializarJugadores();
		}
		return jugadores;
	}

	public void inicializarJugadores() {

		Jugador jose = getJugador("W5kuaFxov-IUGqs5yqJygyOsSopFXRBP7OkW_Jo7CkWttMsav5cp3yiStA","Maikel Schumaker");
		Jugador alex = getJugador("pOmllGjd6ei5-87VimXEKvFVO7cNCEzZ4d7sc7h1V54UcVtTdy0gMXSA5g","ManchaitoDeJager");
		Jugador rafa = getJugador("S5-WrcM9Bgs5wfIk4vrY5ravJb2kplFFbWLxdKsPaeY_nlAM4RKmjsf-hw","RandolphTre");
		Jugador alon = getJugador("cTUsLBcWUGoHjVLL-Jh0Q9xyGZxEP3osmLsPbeYdvIXyG2E","");
		Jugador ivan = getJugador("BcqLuy2Av5ViXlV-gtLFq_TIpORM0VXJLVxKSqa1TIZSsfk","");
		Jugador dario = getJugador("F9ub3iYrXpKXAd19tVkCM-kq-8EQ5_rCnBanfnXUzCEUK2o","");
		Jugador sillero = getJugador("fpaKzFuFsvA2cdNR2gjdXn57hGjtCJypTaNBcA_z3EtNYDnC","");
		Jugador juan = getJugador("3dgcgK9wJOvqdlYBQuqh7ldSxI9gX90dH6kPkiCV1vT_0hikU91lmJ8v8w","Alons Nightmare");
		jose.setNombreReal("Jose");
		alex.setNombreReal("Alex");
		rafa.setNombreReal("Rafa");
		alon.setNombreReal("Alon");
		ivan.setNombreReal("Ivan");
		dario.setNombreReal("ReyDiosPlata");
		sillero.setNombreReal("Sille");
		juan.setNombreReal("Juan");

		jugadores.add(jose);
		jugadores.add(alex);
		jugadores.add(rafa);
		jugadores.add(alon);
		jugadores.add(ivan);
		jugadores.add(dario);
		jugadores.add(sillero);
		jugadores.add(juan);
		try {
		jugadores.forEach(x -> System.out.println("valor: " + x.getLiga().getValue()));
		jugadores = (ArrayList<Jugador>) jugadores.stream().sorted(Comparator.comparing(x -> x.getLiga().getValue()))
				.collect(Collectors.toList());
		jugadores.forEach(x -> System.out.println("valor2: " + x.getLiga().getValue()));
		} catch(Exception e) {
			e.printStackTrace();
		}

	}

	public Jugador getJugador(String key,String nombre) {
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create("https://euw1.api.riotgames.com/lol/league/v4/entries/by-summoner/" + key
						+ "?api_key=RGAPI-e4903a40-8dae-46f0-a5fe-5c96d00bcb0d"))
				.build();
		String json = client.sendAsync(request, HttpResponse.BodyHandlers.ofString()).thenApply(HttpResponse::body)
				.join();
		Jugador jugador = new Jugador();
		if (json.equals("[]")) {
			System.out.println("vacio: ");
			jugador.setLiga(LigaEnum.UNRANKED);
			jugador.setNombre(nombre);
			jugador.setWins(0);
			jugador.setLoses(0);
			
		} else {
			JSONArray jsonArray = new JSONArray(json);
			ArrayList<JSONObject> jsonObjArray = new ArrayList<JSONObject>();
			ArrayList<JSONObject> jsonObjArray2 = new ArrayList<JSONObject>();
			jsonArray.forEach(x -> jsonObjArray.add((JSONObject) x));
			jsonObjArray2 = (ArrayList<JSONObject>) jsonObjArray.stream()
					.filter(x -> x.getString("queueType").equals("RANKED_SOLO_5x5")).collect(Collectors.toList());
			System.out.println(jsonObjArray2.get(0));
			

			for (int i = 0; i < jsonObjArray2.size(); i++) {
				JSONObject jugadorJson = jsonObjArray2.get(i);
				jugador.setNombre(jugadorJson.getString("summonerName"));
				jugador.setWins(jugadorJson.getInt("wins"));
				jugador.setLoses(jugadorJson.getInt("losses"));
				jugador.setLiga(
						LigaEnum.valueForCode(jugadorJson.getString("tier") + " " + jugadorJson.getString("rank")));
				jugador.setLps(jugadorJson.getInt("leaguePoints"));
				jugador.setRacha(jugadorJson.getBoolean("hotStreak"));
			}
		}
		return jugador;
	}

}
