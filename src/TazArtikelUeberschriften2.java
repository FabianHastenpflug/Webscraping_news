import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TazArtikelUeberschriften2 {
	public static void main(String[] args) throws Exception {
		BufferedReader br = null;
		InputStreamReader isr = null;
		URL url = null;
		String result = "";
		
		List<String> arr_li_li_h3_span_h3p = new ArrayList<String>();

//		String[] urlList = { "https://taz.de/" };
		String[] urlList = { "https://taz.de/", "https://taz.de/Politik/!p4615/", "https://taz.de/Oeko/!p4610/",
				"https://taz.de/Gesellschaft/!p4611/", "https://taz.de/Kultur/!p4639/" };

		for (String urlString : urlList) {
			arr_li_li_h3_span_h3p.add(urlString + "\n");
			try {
				url = new URL(urlString);
				isr = new InputStreamReader(url.openStream());
				br = new BufferedReader(isr);
			} catch (IOException e) {
				e.printStackTrace();
			}

			String line = "";
			ArrayList<String> allLinks = new ArrayList<String>();
			allLinks.add(urlString + "\n");
			while ((line = br.readLine()) != null) {
				if (line.contains("<li")) {
					String[] arr_li = line.split("<li ");

					List<String> arr_li_li = new ArrayList<String>();
					for (String str : arr_li) {
						if (str.contains("</li>")) {
							String[] tmp = str.split("</li>");
							arr_li_li.add(tmp[0]);
						}
					}

					List<String> arr_li_li_h3 = new ArrayList<String>();
					for (String str : arr_li_li) {
						if (str.contains("<h3>")) {
							String[] tmp = str.split("<h3>");
							arr_li_li_h3.add(tmp[1]);
						}
					}
					
					List<String> arr_li_li_h3_span = new ArrayList<String>();
					for (String str : arr_li_li_h3) {
						if (str.contains("</span")) {
							String[] tmp = str.split("</span");
							arr_li_li_h3_span.add(tmp[0]);
						}
					}
					
					
					for (String str : arr_li_li_h3_span) {
						if (str.contains("</h3><p>")) {
							str += "\n";
							String[] tmp = str.split("</h3><p>");
							arr_li_li_h3_span_h3p.add(tmp[0]);
							arr_li_li_h3_span_h3p.add(tmp[1]);
						}
					}
					
					
					
					
					
				}

			}
			arr_li_li_h3_span_h3p.add("-----------------------------\n\n");
		}
		for (String x : arr_li_li_h3_span_h3p) {
			System.out.println(formatieren(x));
		}
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
		
		if(x.contains("  <span class=\"author\">")){
			x = x.replace("  <span class=\"author\">", "\nVon: ");
		}
		
		return x;
	}

}