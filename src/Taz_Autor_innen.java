

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class Taz_Autor_innen {

	public static void main(String[] args) throws Exception {
		BufferedReader br = null;
		InputStreamReader isr = null;
		URL url = null;
		
		String urlString = "https://taz.de/";
		
		try {
			url = new URL(urlString);
			isr = new InputStreamReader(url.openStream());
			br = new BufferedReader(isr);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		br = new BufferedReader(isr);
		
		String line = "";
		ArrayList<String> allLinks = new ArrayList<String>();
		try {
			while((line = br.readLine()) != null){
				String y = line;
					String x = "";
					String[] arr = y.split("author\">");
					for(int i = 2; i < arr.length; i++) {
						x = arr[i].substring(0, arr[i].indexOf("</span>"));
						allLinks.add(x);
					}
				}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String result = "";
		for(int i = 0; i < allLinks.size(); i++) {
			result = result + allLinks.get(i) + "\n";
		}
		
		log(result);
		System.out.println("fertig!");
		
		
		
		
		
	}
	
	 public static void log(String message) throws Exception { 
	      PrintWriter out = new PrintWriter(new FileWriter("output.txt", false), true);
	      out.write(message);
	      out.close();
	    }

}