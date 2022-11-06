package com.jokecompany;

import com.google.gson.Gson;

import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
public class JsonFeed {
    static String url = "";

    public JsonFeed(String endpoint, int results) {
        url = endpoint;
    }

    public static String[] getRandomJokes(String firstname, String lastname, String category) throws URISyntaxException, IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        String temp_url= url;
        url += "random/";
        if (category != null)
            url += "?category="+category; // Added category query as per API document
        URI uri = new URI(url);
        
        HttpRequest request = HttpRequest.newBuilder().uri(uri).build();
        String joke = client.send(request, HttpResponse.BodyHandlers.ofString()).body();
        
        //separating joke from the API result
        int indx = joke.indexOf(",\"value\":");
        String jke = joke.substring(indx + 9);
        joke = jke;
        
        if (firstname != null && lastname != null) // if random name given
        {
        	//substuting the random name inplace of 'Chuck Norris'
            int index = joke.indexOf("Chuck Norris");
            String firstPart = joke.substring(0, index);
            String secondPart = joke.substring(index + "Chuck Norris".length());
            joke = firstPart + " " + firstname + " " + lastname + secondPart;
        }
        Gson jsonobject = new GsonBuilder().disableHtmlEscaping().create();
        url = temp_url;
        return new String[] {jsonobject.toJson(joke)};
    }

    public static Dto getnames() throws URISyntaxException, IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        URI uri = new URI(url);
        HttpRequest request = HttpRequest.newBuilder().uri(uri).build();
        String names = client.send(request, HttpResponse.BodyHandlers.ofString()).body();
        
        // Splitting the Name from the API result
        int name_index = names.indexOf("\"name\":\"");
        int end_index = names.indexOf("\",\"address\":");
        String name = names.substring(name_index+8,end_index);
        Dto dto = new Dto(name); // Storing the name in DTO
        return dto;
    }

    public static String[] getCategories() throws IOException, InterruptedException, URISyntaxException {
        HttpClient client = HttpClient.newHttpClient();
        url += "categories/";	//added "categories/" to the end of the URL to fetch the categories
        URI uri = new URI(url);
        
        HttpRequest request = HttpRequest.newBuilder().uri(uri).build();
        String responsebody = client.send(request, HttpResponse.BodyHandlers.ofString()).body();
        return new String[] {new Gson().toJson(responsebody)};
    }
}
