package soap.ws.skiresortitem;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import javax.jws.soap.SOAPBinding.Use;

import com.recombee.api_client.exceptions.ApiException;

import soap.model.SkiResortItem;


@WebService
@SOAPBinding(style = Style.DOCUMENT, use=Use.LITERAL) 
public interface ISkiResortItemService {
	
    @WebMethod(operationName="createSkiResortItem") 
    @WebResult(name="createdSkiResortItem") 
    public boolean addItem(@WebParam(name="user") SkiResortItem item) throws ApiException;
    
 
    // get Item by id
    
    // remove item
    
    // update item
}




