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
		jugadores.clear();
		inicializarJugadores();
		return jugadores;
	}

	public void inicializarJugadores() {

		Jugador jose = getJugador("W5kuaFxov-IUGqs5yqJygyOsSopFXRBP7OkW_Jo7CkWttMsav5cp3yiStA","Maikel Schumaker");
		Jugador alex = getJugador("pOmllGjd6ei5-87VimXEKvFVO7cNCEzZ4d7sc7h1V54UcVtTdy0gMXSA5g","ManchaitoDeJager");
		Jugador rafa = getJugador("S5-WrcM9Bgs5wfIk4vrY5ravJb2kplFFbWLxdKsPaeY_nlAM4RKmjsf-hw","RandolphTre");
		Jugador alon = getJugador("olKOiSoJc1MQtAeTrY2xIws2P-ZLYOMnE-McgxwBLvBJVwnCn8xtpzuT6w","bot juan");
		Jugador ivan = getJugador("qFSdx7SdMaGPOmb_kpSwRumGmK26x2ov3Ix4SX4h36YIHx-P7ldWSAQ5Xg","xTupu");
		Jugador dario = getJugador("amByFKm6tGR0m9pY_fJFk-2Xrs4nnxT7YEqmWx88301jDtAcTmnkCg5V6Q","REY DIOS PLATA");
		Jugador sillero = getJugador("nyuwdgfkD_6DqvsXP4Pze0KO6uWOQGRqCy7rEpP9SW2O-ZYdLWHFBTROWw","WerlyBkristo");
		Jugador juan = getJugador("3dgcgK9wJOvqdlYBQuqh7ldSxI9gX90dH6kPkiCV1vT_0hikU91lmJ8v8w","Alons Nightmare");
		Jugador andres = getJugador("fvA2W0UF_xdAn1WBxMUMF27DtE6VMzz4ragGTvvdRSzoPHzWehDgmfs33A","dnxkuy");
		jose.setNombreReal("Jose");
		alex.setNombreReal("Alex");
		rafa.setNombreReal("Rafa");
		alon.setNombreReal("Alon");
		ivan.setNombreReal("Ivan");
		dario.setNombreReal("ReyDiosPlata");
		sillero.setNombreReal("Sille");
		juan.setNombreReal("Juan");
		andres.setNombreReal("Andres");

		jugadores.add(jose);
		jugadores.add(alex);
		jugadores.add(rafa);
		jugadores.add(alon);
		jugadores.add(ivan);
		jugadores.add(dario);
		jugadores.add(sillero);
		jugadores.add(juan);
		jugadores.add(andres);
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
		//prueba edicion
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create("https://euw1.api.riotgames.com/lol/league/v4/entries/by-summoner/" + key
						+ "?api_key=RGAPI-ecdc56d9-1dbb-400f-b3c1-ccdcf8ca8f32"))
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
			System.out.println("json: "+json);
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
