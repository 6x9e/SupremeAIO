package ch.makery.address.selenium;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Writer;
<<<<<<< HEAD
import java.net.HttpCookie;
=======
>>>>>>> 624df5e6a043c3d053df7badcb64811464921010
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;
<<<<<<< HEAD
import java.util.List;
=======
>>>>>>> 624df5e6a043c3d053df7badcb64811464921010

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ch.makery.address.view.SupremeBotOverviewController;

public class Request implements Runnable {
	
	//Main bot overview controller
	private final SupremeBotOverviewController controller;
	
	//Task variables
	private int taskNumber;
	private String keyword;
	private String size;
	private String category;
	private String color;
	private String profileLoader;
	
	//Printer Writer for log text file, declared globally so other methods can use them
	private PrintWriter printWriter;
	
	//Create global variables to connect to supremenewyork for all methods
	private HttpURLConnection mainConnection;
	
	//Variables
<<<<<<< HEAD
	private final String mainShop = "https://www.supremenewyork.com";
	private final String mobile_stock = "https://www.supremenewyork.com/mobile_stock.json";
	private final String checkout_url = "https://www.supremenewyork.com/checkout";
=======
	private final String mainShop = "http://www.supremenewyork.com";
	private final String mobile_stock = "http://www.supremenewyork.com/mobile_stock.json";
>>>>>>> 624df5e6a043c3d053df7badcb64811464921010

	//Variants from product data
	private String keywordProductID;
	private String keyword_style_colour;
	private String keyword_size;

	
	//Get the references from the main controller and store into variables
	public Request(SupremeBotOverviewController controller, int taskNumber, String keyword, String size, String category, String color, String profileLoader) {
		this.controller = controller;
		this.taskNumber = taskNumber;
		this.keyword = keyword;
		this.size = size;
		this.category = category;
		this.color = color;
		this.profileLoader = profileLoader;
	}
	
	//Run this thread using Runnable interface
	@Override
	public void run() {
		try {
			this.main(null);
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
	

	public void main(String[] args) throws IOException {
		
		//Ensure status column is update
<<<<<<< HEAD
		controller.returnTasks().getItems().get(taskNumber - 1).setStatus("Running");
=======
		controller.statusColumnUpdateRunning();
>>>>>>> 624df5e6a043c3d053df7badcb64811464921010
		
		//Create log file
		this.log_creator();
		
		//Test printer writer logger
		printWriter.println("LOG [TASK: " + "1 - MODE[Requests] - " +  " Time: " + new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date()) + "]");
		printWriter.println();
		
		//finds keyword and saves 
		this.mobile_stock_checker();
		
		//Finds the correct colour and variant for the keyword
		this.variant_finder();
		
		//Create POST Request to add the item to the cart
		this.add_to_cart();
<<<<<<< HEAD
		
		this.checkout();
=======
>>>>>>> 624df5e6a043c3d053df7badcb64811464921010
	}
	
	public void mobile_stock_checker() throws JSONException, MalformedURLException, IOException {
		
		// Convert to supremnewyork stock url and store as json object
		JSONObject json = new JSONObject(IOUtils.toString(new URL(mobile_stock), Charset.forName("UTF-8")));
		
		JSONObject main = json.getJSONObject("products_and_categories");
		JSONArray categoryItem = main.getJSONArray(category);
		
		
		//Iterate throught the array objects until keyword is found
		for (Object productSearch : categoryItem) {
			if ( ((JSONObject) productSearch).optString("name").contains(keyword)) {
				//If keyword found outputs element object
				keywordProductID = ((JSONObject) productSearch).optString("id").toString();
				controller.statusColumnUpdateItemFound();
				controller.getConsole().appendText("[" + new SimpleDateFormat("HH:mm:ss:SS").format(new Date()) + "] - " + "Task - Item Found \n");
			} 
		}
	}
	
	public void variant_finder() throws JSONException, MalformedURLException, IOException {
		
		// Convert to supremnewyork stock url and store as json object
		JSONObject json = new JSONObject(IOUtils.toString(new URL(mainShop + "/shop/" + keywordProductID + ".json"), Charset.forName("UTF-8")));
		
		JSONArray styleJson = json.getJSONArray("styles");
		
		
		//Iterate throught the array objects until style colour is found
		for (Object productSearch : styleJson) {
			if ( ((JSONObject) productSearch).optString("name").contains(color)) {
				//If keyword found outputs element object
				keyword_style_colour = ((JSONObject) productSearch).optString("id").toString();

			} 
		}
		
		for (Object colorSearch: styleJson) {
			  Object sizes = ((JSONObject) colorSearch).get("sizes");
			for (Object djf : ((JSONArray) sizes)) {
				if (((JSONObject) djf).optString("name").contains(size)) {
					keyword_size = ((JSONObject) djf).optString("id").toString();
				}			
			}
		}
	
		//Console and Status update
<<<<<<< HEAD
		controller.returnTasks().getItems().get(taskNumber - 1).setStatus("Fetching variants");
=======
		controller.statusColumnUpdateFetchingVariants();
>>>>>>> 624df5e6a043c3d053df7badcb64811464921010
		controller.getConsole().appendText("[" + new SimpleDateFormat("HH:mm:ss:SS").format(new Date()) + "] - " + "Task - Fetching variants \n");
	}

		
	public void add_to_cart() throws IOException {

<<<<<<< HEAD
		URL cartPost = new URL("https://www.supremenewyork.com/shop/" + keywordProductID + "/add");
=======
		URL cartPost = new URL("http://www.supremenewyork.com/shop/" + keywordProductID + "/add");
>>>>>>> 624df5e6a043c3d053df7badcb64811464921010

		// Create POST Request
		mainConnection = (HttpURLConnection) cartPost.openConnection();
		mainConnection.setReadTimeout(5000);
		
		//Attach POST Paramters (sie and style)
		mainConnection.setRequestMethod("POST");
		mainConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.106 Safari/537.36");
		mainConnection.setRequestProperty("Accept-Language", "en-US,en;q=0.8");
		mainConnection.setRequestProperty("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");

		
		//Post Variables
		mainConnection.setRequestProperty("charset", "utf-8");
		mainConnection.setRequestProperty("style", keyword_style_colour);
		mainConnection.setRequestProperty("size", keyword_size);
		mainConnection.setRequestProperty("commit", "add+to+basket");
<<<<<<< HEAD
		mainConnection.setRequestProperty("Cookie", "1537904375880; pooky=76dca13a-1559-43d9-a5df-dad0fe1a7da2; pooky_ok=eyJ0b2hydV9vayI6IHRydWUsICJlbmFibGVkIjogdHJ1ZSwgIm1zX2RyYWciOiJvZmYifQ");
		mainConnection.connect();
		
		String cookiesHeader = mainConnection.getHeaderField("Set-Cookie");
		List<HttpCookie> cookies = HttpCookie.parse(cookiesHeader);
		
		System.out.println(cookies);
=======
		
		mainConnection.connect();
		
>>>>>>> 624df5e6a043c3d053df7badcb64811464921010
		//Get Page Source and print it
		BufferedReader r = new BufferedReader(new InputStreamReader(mainConnection.getInputStream(), Charset.forName("UTF-8")));

		StringBuilder sb = new StringBuilder();
		String line;
		while ((line = r.readLine()) != null) {
		    sb.append(line);
		}
		
		System.out.println(sb.toString());

		//Check if add to cart was successful, Status code 200 = OK
		if(mainConnection.getResponseCode()>=200) {
			System.out.println(mainConnection.getResponseCode());
			
			//Console and Status update
<<<<<<< HEAD
			controller.returnTasks().getItems().get(taskNumber - 1).setStatus("Adding to cart...");
			controller.getConsole().appendText("[" + new SimpleDateFormat("HH:mm:ss:SS").format(new Date()) + "] - " + "Task - Adding to cart \n");
		}
=======
			controller.statusColumnUpdateAddingToCart();
			controller.getConsole().appendText("[" + new SimpleDateFormat("HH:mm:ss:SS").format(new Date()) + "] - " + "Task - Adding to cart \n");
		}
		
		try {
			Thread.currentThread().wait();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
>>>>>>> 624df5e6a043c3d053df7badcb64811464921010
	}
	
	public void checkout() throws IOException {
		URL cartPost = new URL(checkout_url);

		mainConnection = (HttpURLConnection) cartPost.openConnection();

<<<<<<< HEAD
		// Get Page Source and print it
		BufferedReader r = new BufferedReader(new InputStreamReader(mainConnection.getInputStream(), Charset.forName("UTF-8")));

		StringBuilder sb = new StringBuilder();
		String line;
		while ((line = r.readLine()) != null) {
			sb.append(line);
		}

		System.out.println(sb.toString());
	}

	
	public void log_creator() throws IOException {
		//Create Log File
		try (Writer file = new FileWriter(System.getProperty("user.dir")+ "/resources/Logs/" + "/Log_Task_" + "1" + ".txt")) {
			file.flush();
			controller.getConsole().appendText("[" + new SimpleDateFormat("HH.mm.ss.SSS").format(new Date()) +  "]" + " - " + "Successfully created log file \n");
		}
				
		//Start the print writer to Log to the file
		FileWriter rawLogOutput = new FileWriter(System.getProperty("user.dir")+ "/resources/Logs/Log_Task_1.txt");
		printWriter = new PrintWriter(rawLogOutput);
	}
=======
	
	public void log_creator() throws IOException {
		//Create Log File
		try (Writer file = new FileWriter(System.getProperty("user.dir")+ "/resources/Logs/" + "/Log_Task_" + "1" + ".txt")) {
			file.flush();
			controller.getConsole().appendText("[" + new SimpleDateFormat("HH.mm.ss.SSS").format(new Date()) +  "]" + " - " + "Successfully created log file \n");
		}
				
		//Start the print writer to Log to the file
		FileWriter rawLogOutput = new FileWriter(System.getProperty("user.dir")+ "/resources/Logs/Log_Task_1.txt");
		printWriter = new PrintWriter(rawLogOutput);
	}
>>>>>>> 624df5e6a043c3d053df7badcb64811464921010
}
