package soap.ws.skiresortitem;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import javax.jws.soap.SOAPBinding.Use;

import com.recombee.api_client.exceptions.ApiException;

import soap.model.SkiResortItem;

/**
 * Service interface for item CRUD and rating search methods.
 *  
 * @author ivan
 *
 */
@WebService
@SOAPBinding(style = Style.DOCUMENT, use=Use.LITERAL) 
public interface ISkiResortItemService {
	
	/**
	 * Add item ski resort to Recombee database.
	 * 
	 * @return true if the operation was successful, false otherwise
	 * @throws ApiException
	 */
    @WebMethod(operationName="createSkiResortItem") 
    @WebResult(name="createdSkiResortItem") 
    public boolean addItem(@WebParam(name="user") SkiResortItem item) throws ApiException;
    
 
    /**
     *  Get Ski Resort Item by id
     * @param itemId
     * @return SkiResortItem
     * @throws ApiException
     */
    @WebMethod(operationName="getSkiResortItem") 
    @WebResult(name="skiResortItemById") 
    public SkiResortItem getItemById(@WebParam(name="itemId") String itemId);
    
    // TODO: remove item
    
    // TODO: update item

    /**
     * Add a rating for a certain item evaluated by the user to Recombee database.
     * 
     * @param userId user evaluated the item
     * @param itemId id of the item
     * @param rating rating in integer scale from 1 to 5 (will be transformed to -1.0..+1.0)
     * @return true if rating was added successfully, false otherwise
     * @throws ApiException
     */
    @WebMethod(operationName="addSkiItemRating") 
    @WebResult(name="createdRating") 
    public boolean addSkiItemRatingById(@WebParam(name="userId") String userId,
			    		@WebParam(name="itemId") String itemId, 
			    		@WebParam(name="rating") int rating);
 
    
    /**
     * Get all items with the rating for a given user.
     * 
     * @param userId target user id
     * @param rating rating of all items should be equal to this 
     * 			rating that user have askedfor
     * @return items with rating equal to the given
     */
    public List<SkiResortItem> getEvaluatedItemsWithRating(
    						@WebParam(name="userId") String userId,
				    		@WebParam(name="rating") int rating);
    
    
}




