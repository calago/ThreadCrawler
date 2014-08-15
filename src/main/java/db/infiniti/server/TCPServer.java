package db.infiniti.server;


 
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

import db.infiniti.server.models.CrawlerRequest;
import db.infiniti.server.models.CrawlResults;
import db.infiniti.server.models.SimpleProduct;
 
/**
 * A simple TCPServer to launch a crawl with.
 * @author marijn
 *
 */
public class TCPServer {
 
    private static int port;
    private static ServerSocket serverSocket;
 
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("usage: java -jar JavaTCPServer.jar <port>");
            System.exit(1);
        }
 
        port = Integer.parseInt(args[0]);
        System.out.println("Port: " + port);
 
        Socket socket = null;
        try {
            serverSocket = new ServerSocket(port);
 
            socket = serverSocket.accept();
            Scanner scanner = new Scanner(socket.getInputStream());
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
 
           
            //Read all the incoming data
            StringBuilder builder = new StringBuilder();
            while(scanner.hasNext()){
            	
            	String line = scanner.nextLine();
            	if(line.contains("###END###")){
            		break;
            	}else{
            		builder.append(scanner.nextLine());
            	}
            	
            }
 

            //Parse input
            ObjectMapper mapper = new ObjectMapper();
            CrawlerRequest request = mapper.readValue(builder.toString(), CrawlerRequest.class);
            
            //Return the result of the last crawl if available
            File home = new File("results.json");
            if(home.exists()){
            	
            	
            	
            	//printWriter.
            }
            
 
            //Begin our new crawl; Go through each product
            for(SimpleProduct sp : request.getProducts()){
            	
            	//Craw
            	
            }
            
            //
            
            
        } catch (IOException ex) {
            Logger.getLogger(TCPServer.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
 
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException ex) {
                    Logger.getLogger(TCPServer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
 
        }
 
    }
 
}