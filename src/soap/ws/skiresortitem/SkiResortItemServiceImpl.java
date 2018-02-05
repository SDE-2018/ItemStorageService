package soap.ws.skiresortitem;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import javax.jws.WebService;

import com.recombee.api_client.RecombeeClient;
import com.recombee.api_client.api_requests.AddItemProperty;
import com.recombee.api_client.api_requests.AddRating;
import com.recombee.api_client.api_requests.ListItems;
import com.recombee.api_client.api_requests.ListUserRatings;
import com.recombee.api_client.bindings.Item;
import com.recombee.api_client.bindings.Rating;
import com.recombee.api_client.exceptions.ApiException;

import soap.model.SkiResortItem;

@WebService(endpointInterface = "soap.ws.skiresortitem.ISkiResortItemService", 
									serviceName="SkiResortItemService")
public class SkiResortItemServiceImpl implements ISkiResortItemService{
	
	private static RecombeeClient client;
	
	private static String RECOMBEE_TOKEN;
	
	private static String DB_NAME;
	
	private static final Logger logger = Logger.getLogger(SkiResortItemServiceImpl.class.getName());
	
	private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss+hh:MM");
	
	static {
	    FileHandler fh;  
	    try {  
	        fh = new FileHandler("server-items-logs.log");  
	        logger.addHandler(fh);
	        SimpleFormatter formatter = new SimpleFormatter();  
	        fh.setFormatter(formatter);  
	    } catch (SecurityException e) {  
	        e.printStackTrace();  
	    } catch (IOException e) {  
	        e.printStackTrace();  
	    }  
	}
	
	static {
		Properties properties = new Properties();
        try {
			properties.load(new FileInputStream("local.properties"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
        
        // init Recombee client
        RECOMBEE_TOKEN = properties.getProperty("RECOMBEE_TOKEN");
        DB_NAME = properties.getProperty("DB_NAME");
        client = new RecombeeClient(DB_NAME, RECOMBEE_TOKEN);
        
        // setup properties for ski resort items
//        try {
//	      	client.send(new AddItemProperty("name", "string"));
//	      	client.send(new AddItemProperty("lat", "string"));
//	      	client.send(new AddItemProperty("lng", "string"));
//	      	client.send(new AddItemProperty("officialWebsite", "string"));
//	      	client.send(new AddItemProperty("terrainPark", "boolean"));
//	      	client.send(new AddItemProperty("nightSkiing", "boolean"));
//	      	client.send(new AddItemProperty("skiMapUrl", "string"));
//	      	client.send(new AddItemProperty("region", "string"));
//	      	client.send(new AddItemProperty("lastUpdated", "timestamp"));
//	      	client.send(new AddItemProperty("top", "int"));
//	      	client.send(new AddItemProperty("longestRun", "int"));
//	        client.send(new AddItemProperty("liftCount", "int"));
//		} catch (ApiException e) {
//			e.printStackTrace();
//			logger.info("properties have been already setup!");
//		}
	}
	
	@Override
	public boolean addItem(SkiResortItem item) throws ApiException {
		// TODO Auto-generated method stub
		return false;
	}


	/**
	 * TODO BUG: fix Recombee timestamp to Date conversion
	 */
	@Override
	public SkiResortItem getItemById(String itemId) {
		logger.info("getItemById + " + itemId);
		SkiResortItem item = null;
		try {
			List<Item> result = Arrays.asList(client.send(new ListItems()
					  .setFilter("\"" + itemId + "\" in 'itemId'")
					  .setReturnProperties(true)));
			if (result.size() == 0) {
				logger.info("Item " + itemId + " is not found!");
				return null;
			}
			
			item = new SkiResortItem();
			for (Item i: result) {
				String id = i.getItemId().toString();
				if (id.equals(itemId)) {
					Map<String, Object> v = i.getValues();
					logger.info(v.toString());
					item.setId(Integer.parseInt(v.get("itemId").toString()));
					item.setName(v.get("name").toString());
					if (v.get("liftCount") != null) {
						item.setLiftCount(Integer.parseInt(v.get("liftCount").toString()));
					}
					
					if (v.get("officialWebsite") != null) {
						item.setOfficialWebsite(v.get("officialWebsite").toString());
					}
					if (v.get("longestRun") != null) {
						item.setLongestRun(Integer.parseInt(v.get("longestRun").toString()));
					}
					if (v.get("top") != null) {
						item.setTop(Integer.parseInt(v.get("top").toString()));
					}
					if (v.get("lat") != null) {
						item.setLat(v.get("lat").toString());
					}
					if (v.get("lng") != null) {
						item.setLng(v.get("lng").toString());
					}
					if (v.get("region") != null) {
						item.setRegion(v.get("region").toString());
					}
//					if (v.get("lastUpdated") != null) {
//						item.setLastUpdated(new Date(Long.parseLong(v.get("lastUpdated").toString())));
//					}
					if (v.get("terrainPark") != null) {
						item.setTerrainPark(Boolean.parseBoolean(v.get("terrainPark").toString()));
					}
					if (v.get("nightSkiing") != null) {
						item.setNightSkiing(Boolean.parseBoolean(v.get("nightSkiing").toString()));
					}
					if (v.get("skiMapUrl") != null) {
						item.setSkiMapUrl(v.get("skiMapUrl").toString());
					}
					logger.info(item.toString());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		return item;
	}
	
	/**
	 * Scale transform rating [1..5] (min..max) to range -1.0 ... +1.0  (a..b)
	 * as required by Recombee documentation
	 * [min,max]to [a,b] -- > 
	 *        (b-a)(x - min)
	 * f(x) = -------------  + a
	 *          max - min
	 * @return
	 */
	// 
	private static double scale(int x, int min, int max, double a, double b) {
		return ((b-a) * (x - min) /(max - min) ) + a;
	}
	
	private static double unscale(double x, double min, double max, int a, int b) {
		return ((b-a) * (x - min) /(max - min) ) + a;
	}
	
	@Override
	public boolean addSkiItemRatingById(String userId, String itemId, int rating) {
		try {
			double b = 1.0;
			double a = -1.0;
			int min = 1;
			int max = 5;
			double r = scale(rating, min, max, a, b);
			logger.info("Rating: " + itemId + " " + Double.toString(r));
			client.send(new AddRating(userId, itemId, r)
					  .setTimestamp(new Date()));
			return true;
		}catch (Exception e) {
    		logger.info(e.getMessage());
    		e.printStackTrace();
    	}
		return false;
	}


	
	@Override
	public List<SkiResortItem> getEvaluatedItemsWithRating(String userId, int rating) {
		try {
			List<Rating> ratings = Arrays.asList(
								client.send(new ListUserRatings(userId)));
			List<SkiResortItem> items = new ArrayList<>();
			for (Rating r: ratings ) {
				int itemRating = (int) unscale(r.getRating(), -1.0, 1.0, 1, 5 );
				if ( itemRating  == rating ) {
					String itemId = r.getItemId();
					items.add(getItemById(itemId));
				}
			}
			return items;
		} catch (ApiException e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}

		return null;
	}
	
	
	
	

}
