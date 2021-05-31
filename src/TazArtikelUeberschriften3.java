import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class TazArtikelUeberschriften3 {
	public static void main(String[] args) throws Exception {
		BufferedReader br = null;
		InputStreamReader isr = null;
		URL url = null;
		String result = "";

		String[] urlList = { "https://taz.de/" };
//		String[] urlList = { 
//				"https://taz.de/", 
//				"https://taz.de/Politik/!p4615/", 
//				"https://taz.de/Oeko/!p4610/",
//				"https://taz.de/Gesellschaft/!p4611/", 
//				"https://taz.de/Kultur/!p4639/" 
//				};
		for (String urlString : urlList) {
			try {
				url = new URL(urlString);
				isr = new InputStreamReader(url.openStream());
				br = new BufferedReader(isr);
			} catch (IOException e) {
				e.printStackTrace();
			}

			String line = "";
			ArrayList<String> allLinks = new ArrayList<String>();
			allLinks.add(urlString);
			while ((line = br.readLine()) != null) {
				String y = line;
				while (y.contains("<h4>")) {
					String x = y;
					try {
						x = x.substring(x.indexOf("<h4>") + 4, x.indexOf("</h3><p>") + 200);
						x = formatieren(x);
						allLinks.add(x);
					} catch (Exception e) {
						System.err.println("Fehler in Zeile: " + line);
					}
					y = y.replaceFirst("<h4>", "");
					y = y.replaceFirst("</h3><p>", "");
				}
			}

			for (int i = 0; i < allLinks.size(); i++) {
				result += allLinks.get(i) + "\n";
			}
			result += "---------------\n\n\n";
		}
//		log(result);
		System.out.println(result);
//		System.out.println("fertig!");
	}

	public static void log(String message) throws Exception {
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy_HH");
		String heutigesDatum = formatter.format(date);
		String fileName = "taz_headlines_" + heutigesDatum + ".txt";
		PrintWriter out = new PrintWriter(new FileWriter(fileName, false), true);
		out.write(message);
		out.close();
	}

	public static String formatieren(String x) {
		x = x.replace("„", "\"");
		x = x.replace("“", "\"");
		x = x.replace("Ä", "�");
		x = x.replace("Ü", "�");
		x = x.replace("ü", "�");
		x = x.replace("ä", "�");
		x = x.replace("ö", "�");
		x = x.replace("–", "-");
		x = x.replace("ß", "�");
		x = x.replace("­", "");
		x = x.replace("é", "�");
		x = x.replace("…", "");
		
		x = "\n--------> " + x;
		
		if (x.contains("</h4><h3>")) {
			x = x.replace("</h4><h3>", ": ");
		}
		if (x.contains("</h3><p>")) {
			x = x.replace("</h3><p>", "\n    ");
		}
		if (x.contains("  <span class=\"author\">")) {
			x = x.replace("  <span class=\"author\">", "\n    Von: ");
		}
		if (x.contains("</span>")) {
			x = x.replace("</span>", "\n");
		}

		return x;
	}

}