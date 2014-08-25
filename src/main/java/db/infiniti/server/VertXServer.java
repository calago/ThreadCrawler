package db.infiniti.server;

/*
 * Copyright 2011 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import org.vertx.java.core.Handler;
import org.vertx.java.core.buffer.Buffer;
import org.vertx.java.core.http.HttpServerRequest;
import org.vertx.java.platform.Verticle;

import com.fasterxml.jackson.databind.ObjectMapper;

import db.infiniti.config.CrawlingConfig;
import db.infiniti.crawling.CrawlerSellenium;
import db.infiniti.crawling.MainClass;
import db.infiniti.server.models.CrawlResults;
import db.infiniti.server.models.CrawlerRequest;
import db.infiniti.server.models.SimpleProduct;
import db.infiniti.server.rest.CrawlerRestRequest;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

public class VertXServer extends Verticle {

	
	CrawlingConfig crawlingConfig;
	String outputDatabasename = "mydatafactory";
	
	  public void start() {
		  
	    vertx.createHttpServer().requestHandler(new Handler<HttpServerRequest>() {
	     
	    	public void handle(HttpServerRequest req) {
	    	  
	    	//Give us some info on whats in the request.
	        System.out.println("Got request: " + req.uri());
	        System.out.println("Headers are: ");
	        for (Map.Entry<String, String> entry : req.headers()) {
	          System.out.println(entry.getKey() + ":" + entry.getValue());
	        }
	    		
	        //Handle a crawlproduct request
	        if(req.uri().contains("crawlproduct")){
	        	
		    	//Handle the requests' body 	
		        req.bodyHandler(new Handler<Buffer>() {
		
					public void handle(Buffer arg0) {
						
						//Convert the requests' body to a json string
						String json = arg0.getString(0, arg0.length());
						
						//Create a CrawlerRequest using an ObjectMapper
						try{
							ObjectMapper mapper = new ObjectMapper();
							CrawlerRestRequest request = mapper.readValue(json, CrawlerRestRequest.class);
							
							//Crawl this product
	        				crawl(request);
						
						}catch(Exception e){
							System.out.println("Could not process this request.");
							e.printStackTrace();
						}
					}
				});
		        
	        }
	        //else if(req.uri().contains("requestresults")){
	        	
	        	//Add a SimpleProduct
	        	SimpleProduct product = new SimpleProduct();
	        	product.setId("testID");
	        	product.setLongDescription("Longdescription");
	        	product.setProductName("FromCrawler");
	        	product.setPrice(15.00);
	        	ArrayList<SimpleProduct> products = new ArrayList<SimpleProduct>();
	        	products.add(product);
	        	
	        	//Demo products
	        	CrawlResults results = new CrawlResults();
	        	results.setProducts(products);
	        	results.setLast_crawldate(new Date().toString());
	        	
	        	//Translate this object back to json
	        	try{
	        		ObjectMapper mapper = new ObjectMapper();
	        		String json = mapper.writeValueAsString(results);
	        		
	        		req.response().headers().set("Content-Type", "application/json; charset=UTF-8");
	        		req.response().end(json);
	        		
	        	}catch(Exception e){
	        		System.out.println("Could not process this request.");
					e.printStackTrace();
	        	}
	        	
	        	
	       // }
	        
	    		
	        //req.response().headers().set("Content-Type", "text/html; charset=UTF-8");
	        //req.response().end("<html><body><h1>Hello from vert.x!</h1></body></html>");
	      }
	    }).listen(1337);
	    
	  }
	  
	  /**
	   * Crawl using the given Crawl datamodel via REST
	   * @param request
	   */
	  private void crawl(CrawlerRestRequest request){
	
		crawlingConfig = new CrawlingConfig();
		crawlingConfig.setCrawlmode(CrawlingConfig.CRAWLMODE_USE_REST_DATAMODELS);
		crawlingConfig.setScrShotBrowser();
		crawlingConfig.setDetailedPageBrowsers(1);
		crawlingConfig.setUnPauseCrawl(true);// restart crawl
		crawlingConfig.setExtractTextFromAllVisitedPages(false);
		crawlingConfig.setExtractTextFromSRPages(false);	
		crawlingConfig.setExtractDataFromDPageS(true);
		crawlingConfig.setOutputDataBase(outputDatabasename);
		crawlingConfig.setTableName(request.getName());
		crawlingConfig.setDataModel(request);
		
		//Tell the crawler to use the datamodel we are giving via REST
		crawlingConfig.setAllSitesDescription(request);
		crawlingConfig.setQuerySelectionApproach(crawlingConfig.browsing);
		
		crawlingConfig.setCurrentSiteDescription(request);
		
		crawlingConfig.setCollectionName(crawlingConfig.getCurrentSiteDescription().getName());
		
		ArrayList<String> listOfReturnedResults = new ArrayList<String>();
		CrawlerSellenium crawlingThreadTest = new CrawlerSellenium(crawlingConfig, listOfReturnedResults);
		crawlingThreadTest.run();
		crawlingConfig.stopFXDriver();
	  }
	  
	  /**
	   * Start a new crawler in a Thread. We dont neccesary follow vertx guidelines here because the crawler isnt optimized for service use.
	   * @author marijn
	   *
	   */
	  private class CrawlThread extends Thread {
	
		    public void run() {
		    	MainClass.main(new String[]{});
		    }
	
		}
  
}
