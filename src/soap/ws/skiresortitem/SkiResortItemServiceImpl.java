package soap.ws.skiresortitem;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import javax.jws.WebService;

import com.recombee.api_client.RecombeeClient;
import com.recombee.api_client.api_requests.AddItemProperty;
import com.recombee.api_client.api_requests.ListItems;
import com.recombee.api_client.bindings.Item;
import com.recombee.api_client.exceptions.ApiException;

import soap.model.SkiResortItem;

@WebService(endpointInterface = "soap.ws.skiresortitem.ISkiResortItemService", 
									serviceName="SkiResortItemService")
public class SkiResortItemServiceImpl implements ISkiResortItemService{
	
	private static RecombeeClient client;
	
	private static String RECOMBEE_TOKEN;
	
	private static String DB_NAME;
	
	private static final Logger logger = Logger.getLogger(SkiResortItemServiceImpl.class.getName());
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
	 * TODO: parse properly !!!
	 */
	@Override
	public SkiResortItem getItemById(String itemId) throws ApiException {
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
					item.setId(Integer.parseInt(v.get("itemId").toString()));
					item.setName(v.get("name").toString());
//					item.setLiftCount(Integer.parseInt(v.get("liftCount").toString()));
					item.setOfficialWebsite(v.get("officialWebsite").toString());
					
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		return item;
	}
	
	
	
	
	
	
	

}
