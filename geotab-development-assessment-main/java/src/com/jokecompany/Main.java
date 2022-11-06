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
                key = c.readLine().charAt(0);		//better way to get char
                
                if (key == 'q') // Quit the application
                {
                    printer.Value("See you Soon!").toString();
                    break;
                }
                
                if (key == 'c')	// Display Categories
                {
                    getCategories();
                    PrintResults();
                }
                
                if (key == 'r') // Get Jokes
                {
                    printer.Value("Want to use a random name? y/n").toString();
                    key = c.readLine().charAt(0);		//better way to get char

                    if (key == 'y')	// Get Random Names
                    {
                    	rndmName = 1;	//setting flag as True
                        getNames();
                    }
                    else if (key == 'n')
                    	rndmName = 0;
                    
                    printer.Value("Want to specify a category? y/n").toString();
                    key = c.readLine().charAt(0);		//better way to get char

                    if (key == 'y')	//for specific category
                    {
                    	printer.Value("The Categories are as follows").toString(); //Displaying categories for the ease of users
                        getCategories();											
                        PrintResults();												//printing categories
                        
                        // Taking categories
                        printer.Value("Enter a category:").toString();
                        String category = c.readLine();
                        
                        
                        printer.Value("How many jokes do you want? (1-9)").toString();
                        int n = Integer.parseInt(c.readLine());
                        
                        for (int i = 0; i < n; i++) //for loop to get n number of jokes
                    	{
	                        getRandomJokes(category, rndmName);
	                        PrintResults();
                    	}
                        Main.names.entrySet().clear();	// clearing Hash map after execution
                    }
                    else	//random category
                    {
                        printer.Value("How many jokes do you want? (1-9)").toString();
                        int n = Integer.parseInt(c.readLine());
                        for (int i = 0; i < n; i++) //for loop to get n number of jokes
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


    private static void getRandomJokes(String category, int number) throws InterruptedException, IOException, URISyntaxException {
    	//added if else
    	new JsonFeed("https://api.chucknorris.io/jokes/", number);
	    	if(number == 0) // get jokes without random name
	    	{
	    			results = JsonFeed.getRandomJokes(null, null, category);
	    	}
	    	else //get joke with random name
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
    	new JsonFeed("https://api.namefake.com/english-united-states/male/", 0); // Given API url was wrong so changed the API
        Dto dto = JsonFeed.getnames();
        try {
        	Main.names.put(dto.getName(), dto.getSurname()); // adding names to Hash Map
        }
        catch(Exception e)
        {
        	e.printStackTrace();
        }
    }
}
