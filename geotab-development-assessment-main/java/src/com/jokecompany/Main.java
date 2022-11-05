package com.jokecompany;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.util.Hashtable;

public class Main {

    static String[] results = new String[50];
    static char key;
    static Hashtable<String, String> names = new Hashtable<>();
    static ConsolePrinter printer = new ConsolePrinter();
    static int rndmName = 0;


    public static void main(String[] args) throws InterruptedException, IOException, URISyntaxException {
        BufferedReader c = new BufferedReader(new InputStreamReader(System.in));
        printer.Value("Press ? to get instructions.").toString();
        String input = c.readLine();
        
        if (input.equals("?")) {
            while (true) {
                printer.Value("Press c to get categories").toString();
                printer.Value("Press r to get random jokes").toString();
                printer.Value("Press q to quit").toString(); //added to quit
//                getEnteredKey(c.readLine());
                key = c.readLine().charAt(0);		//better way to get char
                
                if (key == 'q') //added to quit out of loop
                {
                    printer.Value("See you Soon!").toString();
                    break;
                }
                
                if (key == 'c')
                {
                    getCategories();
                    PrintResults();
                }
                if (key == 'r')
                {
                    printer.Value("Want to use a random name? y/n").toString();
//                    getEnteredKey(c.readLine());
                    key = c.readLine().charAt(0);		//better way to get char

                    if (key == 'y')
                    {
                    	rndmName = 1;
                        getNames();
                    }
                    else if (key == 'n')
                    	rndmName = 0;
                    
                    printer.Value("Want to specify a category? y/n").toString();
//                    getEnteredKey(c.readLine()); //added this line
                    key = c.readLine().charAt(0);		//better way to get char

                    if (key == 'y')	//for category
                    {
                    	printer.Value("The Categories are as follows").toString();//added this line
                        getCategories();											//this too
                        PrintResults();												//and this
                        printer.Value("Enter a category:").toString();
                        String category = c.readLine();
                        
                        printer.Value("How many jokes do you want? (1-9)").toString();
                        int n = Integer.parseInt(c.readLine());
                        
                        for (int i = 0; i < n; i++) //for loop
                    	{
	                        getRandomJokes(category, rndmName);
	                        PrintResults();
                    	}
                        Main.names.entrySet().clear();
                    }
                    else	//random category
                    {
                        printer.Value("How many jokes do you want? (1-9)").toString();
                        int n = Integer.parseInt(c.readLine());
                        for (int i = 0; i < n; i++) //for loop
                    	{
                        	getRandomJokes(null, rndmName);
                        	PrintResults();
                    	}
                        Main.names.entrySet().clear();
                    }
                }
                
            }
        }
    }

    private static void PrintResults()
    {
        printer.Value("[" + String.join(",", results) + "]").toString();
    }

    //no need to use
//    private static void getEnteredKey(String k) {
//        switch (k.substring(0,1))
//        {
//            case "c":
//                key = 'c';
//                break;
//            case "0" :
//                key = '0';
//                break;
//            case "1":
//                key = '1';
//                break;
//            case "3":
//                key = '3';
//                break;
//            case "4":
//                key = '4';
//                break;
//            case "5":
//                key = '5';
//                break;
//            case "6":
//                key = '6';
//                break;
//            case "7":
//                key = '7';
//                break;
//            case "8":
//                key = '8';
//                break;
//            case "9":
//                key = '9';
//                break;
//            case "r":
//                key = 'r';
//                break;
//            case "y":
//                key = 'y';
//                break;
//        }
//    }

    private static void getRandomJokes(String category, int number) throws InterruptedException, IOException, URISyntaxException {
    	//added if else
    	new JsonFeed("https://api.chucknorris.io/jokes/", number);
	    	if(number == 0) {
	    			results = JsonFeed.getRandomJokes(null, null, category);
	    	}
	    	else 
	    	{
	        	var var1 = Main.names.entrySet().iterator().next();
	            results = JsonFeed.getRandomJokes(var1.getKey(), var1.getValue(), category);
	    	}
    }

    private static void getCategories() throws InterruptedException, IOException, URISyntaxException {
        new JsonFeed("https://api.chucknorris.io/jokes/", 0);					
        results = JsonFeed.getCategories();
    }

    private static void getNames() throws InterruptedException, IOException, URISyntaxException {
    	new JsonFeed("https://api.namefake.com/english-united-states/male/", 0); // wrong domain name please check the api
        Dto dto = JsonFeed.getnames();
        try {
        	Main.names.put(dto.getName(), dto.getSurname());
        }
        catch(Exception e)
        {
        	e.printStackTrace();
        }
    }
}
