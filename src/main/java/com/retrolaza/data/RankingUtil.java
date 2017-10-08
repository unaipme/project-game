package com.retrolaza.data;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Scanner;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.HttpClientBuilder;

import com.jayway.jsonpath.JsonPath;
import com.retrolaza.game.exception.PlayerNotFoundException;

import net.minidev.json.JSONArray;

/**
 * Clase utilizada para llevar a cabo la manipulación y recuperación de los datos del ranking.
 * @author Unai P. Mendizabal (@unaipme)
 * 
 */
public class RankingUtil {
	
	private static final String RANKING_ROOT_URL = "https://r9ovtf8cli.execute-api.eu-west-1.amazonaws.com/alpha/ranking/";
	private static final String SCORE_URL = "https://r9ovtf8cli.execute-api.eu-west-1.amazonaws.com/alpha/score/";
	
	private RankingUtil() {}
	
	/**
	 * Extrae el JSON de una respuesta HTTP y la almacena en un String
	 * @param url Dirección al la cual hacer la petición
	 * @return String con el JSON
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	private static String getJson(String url) throws ClientProtocolException, IOException {
		HttpClient http = HttpClientBuilder.create().build();
		HttpGet request = new HttpGet(url);
		HttpResponse response = http.execute(request);
		Scanner scanner = new Scanner(response.getEntity().getContent(), "UTF-8");
		String json = scanner.useDelimiter("\\A").next();
		scanner.close();
		return json;
	}
	
	/**
	 * Interpreta un JSON y lo interpreta de forma que se pueda utilizar
	 * @param json JSON a interpretar
	 * @return Lista de entradas del ranking propiamente interpretadas
	 * @throws PlayerNotFoundException
	 */
	@SuppressWarnings("unchecked")
	private static List<Record> getRanking(String json) throws PlayerNotFoundException {
		JSONArray array = JsonPath.read(json, "$[*]");
		ListIterator<Object> objectList = array.listIterator();
		if (!objectList.hasNext()) throw new PlayerNotFoundException("algo");
		List<Record> list = new ArrayList<>();
		while (objectList.hasNext()) {
			Map<String, Object> info = (Map<String, Object>) objectList.next();
			list.add(new Record().withPosition((Integer) info.get("position")).withScore((Integer) info.get("score")).withUsername(info.get("username").toString()));
		}
		return list;
	}
	
	/**
	 * Añade una puntuación a la base de datos. Si el usuario ya está en el ranking, actualiza su puntuación.
	 * @param username Usuario a actualizar
	 * @param score Puntuación del usuario 
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static void putScore(String username, Integer score) throws ClientProtocolException, IOException {
		HttpClient http = HttpClientBuilder.create().build();
		HttpPut request = new HttpPut(SCORE_URL + username + "/" + score);
		request.addHeader("Content-Type", "application/json");
		/* HttpResponse response = */ http.execute(request);
	}
	
	/**
	 * Descarga e interpreta las 5 primeras entradas del ranking
	 * @return Las 5 primeras entradas del ranking
	 * @throws UnsupportedOperationException
	 * @throws IOException
	 * @throws PlayerNotFoundException
	 */
	public static List<Record> loadRanking() throws UnsupportedOperationException, IOException, PlayerNotFoundException {
		return getRanking(getJson(RANKING_ROOT_URL));
	}
	
	/**
	 * Descarga e interpreta 5 entradas del ranking, las correspondientes al usuario definido, los dos usuarios de delante, y los dos de atrás
	 * @param username Usuario a buscar
	 * @return Lista de las 5 entradas
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @throws PlayerNotFoundException
	 */
	public static List<Record> loadRanking(String username) throws ClientProtocolException, IOException, PlayerNotFoundException {
		return getRanking(getJson(RANKING_ROOT_URL + username));
	}

}
