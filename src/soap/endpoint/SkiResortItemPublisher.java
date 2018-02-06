package soap.endpoint;

import java.net.UnknownHostException;

import javax.xml.ws.Endpoint;

import soap.ws.skiresortitem.SkiResortItemServiceImpl;

/**
 * Service publisher endpoint.
 * 
 * @author ivan
 *
 */
public class SkiResortItemPublisher {

    public static void main(String[] args) throws UnknownHostException {
        Endpoint.publish("http://localhost:9093/skiresort", new SkiResortItemServiceImpl());
    }
}