package zadanie2;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

public class TravelData {
	
	private final static String PROP_BUNDLE = "zadanie2.Info";
	private List<Travel> allOffers = new LinkedList<>();
	private static Map<String, Locale> data = new HashMap<>();
    
	public TravelData(File dir) {
		
		Locale[] loc = Locale.getAvailableLocales();
		String country;
		Locale.setDefault(new Locale("en_US"));
		for (int i=0; i<loc.length; ++i) {
			
		      String countryCode = loc[i].getCountry();
		      if (countryCode.equals("")) continue;
		      country =  loc[i].getDisplayCountry();
		      data.put(country, loc[i]);
		      
		}
		
		try {
			
			Files.walk(Paths.get(dir.getAbsolutePath()))
							.filter(Files::isRegularFile)
							.forEach(f -> getOffersFromFile(f));
			
		} catch (IOException e) {
			//e.printStackTrace();
		}
		
	}
	
	private void getOffersFromFile(Path filePath) {
		
		List<String> tmp;
		try {
			
			tmp = Files.readAllLines(filePath, Charset.forName("utf-8"));
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			
			for(String line : tmp) {
				
				String off[] = line.split("\\t+");
				Travel singleOffer = new Travel();
				if(!off[0].contains("en")) {
					
					ResourceBundle msg_1 = ResourceBundle.getBundle(PROP_BUNDLE, new Locale("en"));
					String krajEN = msg_1.getString((off[1].replaceAll(" ", "_")));
					singleOffer.setCountry(krajEN);
					
					ResourceBundle msg_2 = ResourceBundle.getBundle(PROP_BUNDLE, new Locale(off[0].substring(0, 2)));
					String miejsceEN = msg_2.getString(off[4]);
					singleOffer.setPlace(miejsceEN);
					
				} else {
					
					singleOffer.setCountry(off[1]);
					singleOffer.setPlace(off[4]);
					
				}
				
				singleOffer.setFrom(formatter.parse(off[2]));  
				singleOffer.setTo(formatter.parse(off[3]));
				
				NumberFormat initFormat = NumberFormat.getInstance(new Locale(off[0].substring(0, 2)));
				Number number = initFormat.parse(off[5]);
				singleOffer.setPrice(number.doubleValue());
				singleOffer.setCurrency(off[6]);
				
				this.allOffers.add(singleOffer);
				
			}
		} catch (IOException | ParseException e) {
			//e.printStackTrace();
		}
	}
	
	public List<String> getOffersDescriptionsList(String loc, String dateFormat) {
		
		List<String> result = new LinkedList<String>();
		
		Locale locale = new Locale(loc.substring(0, 2));
		SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
		NumberFormat numFormat = NumberFormat.getInstance(locale);
		
		for(Travel offer : allOffers) {
			
			String kraj;
			String miejsce;
			if (loc.contains("en")) {
				
				kraj = offer.getCountry();
				miejsce = offer.getPlace();
				
			} else {
				
				Locale countryLoc = (Locale) data.get(offer.getCountry());
				kraj = countryLoc.getDisplayCountry(locale);
				
				ResourceBundle msgs = ResourceBundle.getBundle(PROP_BUNDLE, locale);
				miejsce = msgs.getString(offer.getPlace());
				
			}
			
			String cena = numFormat.format(offer.getPrice());
			String dataWyj = formatter.format(offer.getFrom());
			String dataPowr = formatter.format(offer.getTo());
			String waluta = offer.getCurrency();
			
			result.add(kraj + " " + dataWyj + " " + dataPowr + " "+ 
					   miejsce + " " +  cena + " " + waluta);
		}
		
		return result;	
	}
	
	
	public List<Travel> getOffersList() {
		return this.allOffers;
	}
	
	public static String[] getOffer(Integer id, Travel offer, String loc, String dateFormat) {
		
		String[] rtab = new String[7];

		Locale locale = new Locale(loc.substring(0, 2));
		SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
		NumberFormat numFormat = NumberFormat.getInstance(locale);

		String kraj;
		String miejsce;
		if (loc.contains("en")) {
			
			kraj = offer.getCountry();
			miejsce = offer.getPlace();
			
		} else {
			
			Locale countryLoc = (Locale) data.get(offer.getCountry());
			kraj = countryLoc.getDisplayCountry(locale);
			ResourceBundle msgs = ResourceBundle.getBundle(PROP_BUNDLE, locale);
			miejsce = msgs.getString(offer.getPlace());
			
		}

		String cena = numFormat.format(offer.getPrice());
		String dataWyj = formatter.format(offer.getFrom());
		String dataPowr = formatter.format(offer.getTo());
		String waluta = offer.getCurrency();

		rtab[0] = id.toString();
		rtab[1] = kraj;
		rtab[2] = dataWyj;
		rtab[3] = dataPowr;
		rtab[4] = miejsce;
		rtab[5] = cena;
		rtab[6] = waluta;

		return rtab;
		
	}

}
