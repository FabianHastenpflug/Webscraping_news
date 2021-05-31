import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class TazArtikelUeberschriften {

	public static void main(String[] args) throws Exception {
		BufferedReader br = null;
		InputStreamReader isr = null;
		URL url = null;
		String result = "";
		int countErrorreadingLines = 0;;

		String[] urlList = { "https://taz.de/", "https://taz.de/Politik/!p4615/", "https://taz.de/Oeko/!p4610/",
				"https://taz.de/Gesellschaft/!p4611/", "https://taz.de/Kultur/!p4639/" };
		for (String urlString : urlList) {
			System.out.println("Beginn: " + urlString);
			try {
				url = new URL(urlString);
				isr = new InputStreamReader(url.openStream());
				br = new BufferedReader(isr);
			} catch (IOException e) {
				e.printStackTrace();
			}

			String line = "";
			ArrayList<String> allLinks = new ArrayList<String>();
			
			allLinks.add("\n-----------------------------------------------------------------------------------------------------------------------------");
			allLinks.add(urlString);
			allLinks.add("-----------------------------------------------------------------------------------------------------------------------------");
			
			while ((line = br.readLine()) != null) {
				String y = line;
				while (y.contains("<h3>")) {
					String x = y;
					try {
						x = x.substring(x.indexOf("<h3>") - 50, x.indexOf("</h3>"));

						x = formatieren(x);

						if (!x.contains("Sind Sie schon dabei") && !x.isEmpty()) {
							allLinks.add(x);
						}
					} catch (Exception e) {
						System.err.println("Fehler in Zeile: " + line);
						countErrorreadingLines++;
					}
					y = y.replaceFirst("</h3>", "");
					y = y.replaceFirst("<h3>", "");
				}
			}

			for (int i = 0; i < allLinks.size(); i++) {
				result += allLinks.get(i) + "\n";
			}
		}
		log(result + "\n" + countErrorreadingLines + " Quellcodezeilen nicht lesbar");
		System.out.println("fertig!");
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
		x = x.replace("â€ž", "\"");
		x = x.replace("â€œ", "\"");
		x = x.replace("Ã„", "Ä");
		x = x.replace("Ãœ", "Ü");
		x = x.replace("Ã¼", "ü");
		x = x.replace("Ã¤", "ä");
		x = x.replace("Ã¶", "ö");
		x = x.replace("â€“", "-");
		x = x.replace("ÃŸ", "ß");
		x = x.replace("Â­", "");
		x = x.replace("Ã©", "é");
		x = x.replace("â€¦", "");

		if (x.contains("<h4>")) {
			x = x.substring(x.indexOf("<h4>") + 4, x.length());
			x = x.replace("</h4><h3>", ": ");
		} else {
			x = x.substring(x.indexOf("<h3>") + 4, x.length());
		}

		return x;
	}

}