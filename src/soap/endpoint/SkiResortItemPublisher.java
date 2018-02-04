package soap.endpoint;

import java.net.UnknownHostException;

import javax.xml.ws.Endpoint;

import soap.ws.skiresortitem.SkiResortItemServiceImpl;

public class SkiResortItemPublisher {

    public static void main(String[] args) throws UnknownHostException {
//        Endpoint.publish("https://assignment3-chernukha.herokuapp.com/skiresort", new SkiResortItemServiceImpl());
        Endpoint.publish("http://localhost:9091/skiresort", new SkiResortItemServiceImpl());
    }
}