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
            url += "?category="+category; //this line
        URI uri = new URI(url);
        HttpRequest request = HttpRequest.newBuilder().uri(uri).build();
        String joke = client.send(request, HttpResponse.BodyHandlers.ofString()).body();
        
        //trying to separate joke
        int indx = joke.indexOf(",\"value\":");
        String jke = joke.substring(indx + 9);
        joke = jke;
        
//        System.out.println(jke);
        
        if (firstname != null && lastname != null)
        {
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
        
        
        int name_index = names.indexOf("\"name\":\"");
        int end_index = names.indexOf("\",\"address\":");
        String name = names.substring(name_index+8,end_index);
        Dto dto = new Dto(name);
        return dto;
    }

    public static String[] getCategories() throws IOException, InterruptedException, URISyntaxException {
        HttpClient client = HttpClient.newHttpClient();
        url += "categories/";	//this line to get categories
        URI uri = new URI(url);
        HttpRequest request = HttpRequest.newBuilder().uri(uri).build();
        String responsebody = client.send(request, HttpResponse.BodyHandlers.ofString()).body();
        return new String[] {new Gson().toJson(responsebody)};
    }
}
