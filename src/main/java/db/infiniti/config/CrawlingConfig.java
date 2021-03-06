package db.infiniti.config;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import db.infiniti.server.rest.CrawlerRestRequest;
import db.infiniti.sitedescription.DetailedPageDS;
import db.infiniti.sitedescription.WebsiteDS;
import db.infiniti.sitedescription.WebsiteDescReader;
import db.infiniti.surf.Browser;

//import db.infiniti.webtools.WebTools;

public class CrawlingConfig {
	String currentCollectionName;
	ArrayList<String> queries ;
	LinkedHashMap<String, String> queryNumberofResults;
	private String openDescFilePath = "";
	private String openDescDirPath = "";
	private String queriesPath = "";
	private String linkContentSavePath = "";
	Thread crawlingTh;
	String sourceURL;
	ArrayList<String> listOfSourcesFolders;
	WebsiteDescReader descripReader = new WebsiteDescReader();
	WebsiteDS currentSiteDescription;
	String currentSRPageURL;
	// WebTools webTools;
	ArrayList<WebsiteDS> listOfWebsites;
	int queryIndex = 0;
	HashSet<String> listOfStopWords;
	Browser scrShots;
	HashMap<Browser, Boolean> detailedPagesBrowsers = new HashMap<Browser, Boolean>();
	HashMap<String, Integer> querySet = new HashMap<String, Integer>();
	List<String> sentQueries = new ArrayList<String>();
	
	boolean extractTextFromSRPages;
	boolean extractTextFromAllVisitedPages; 
	
	boolean firstQuery = true;
	public String query;
	public List<String> initialQuery = Arrays.asList(
			  "vitol", 
			  "company" 
			);
	String initialQueryProcesses = "";

	int querySelectionApproach;
	public int crawledTextBased = 1;
	public int mostFrequentWebWords = 2;
	public int browsing = 3;

	public boolean extractDataFromDPageS = true;

	public DetailedPageDS detailedPageDS = new DetailedPageDS();
	public String tableName;
	public String outputDataBase;
	public String dataModelTable;

	ArrayList<Browser> poolOfBrowsers;

	public String pathToAllDOwnloadedPages;
	public String pathToVisitedPagesDoc;
	public String pathToSentQueriesDoc;
	public String pathToCoveredCollections;
	public String pathToLastLinkDoc;
	public String pathToURLCrawlList;
	public String pathToLastSearchResultPage;
	public String pathToNumberOfSearchResults;
	public String pathToNumberOfRepetitions;
	public String pathToVisitedPagesPerQuery;
	public String pathToNumberOfSentQueries;
	public boolean unPauseCrawl = true;
	public String crawlStatusPath;
	
	
	//Databaseinfo regarding the website datamodel
	public static String DEFAULT_WEBSITEDATAMODEL_DBUSERNAME = "mohammad";
	public static String DEFAULT_WEBSITEDATAMODEL_DBPASSWORD = "4249324";
	public static String DEFAULT_WEBSITEDATAMODEL_DBURL = "jdbc:postgresql://10.1.0.23:5432/Vacancies";
	
	private String websiteDatamodel_databaseUsername;
	private String websiteDatamodel_databasePassword;
	private String websiteDatamodel_databaseURL;
	
	//Databaseinfo regarding the item datamodel
	public static String DEFAULT_ITEMDATAMODEL_DBUSERNAME = "mohammad";
	public static String DEFAULT_ITEMDATAMODEL_DBPASSWORD = "4249324";
	public static String DEFAULT_ITEMDATAMODEL_DBURL = "jdbc:postgresql://10.1.0.23:5432/mydatafactory";
	
	private String itemDatamodel_databaseUsername;
	private String itemDatamodel_databasePassword;
	private String itemDatamodel_databaseURL;
	
	//Crawlmodes
	
	//Use a database to determine our datamodel
	public static int CRAWLMODE_USE_DATBASE_DATAMODELS = 0x01;
	
	//Use a file to determine our datamodel
	public static int CRAWLMODE_USE_FILE_DATAMODELS = 0x02;
	
	//Use a datamodel received via REST determine our datamodel
	public static int CRAWLMODE_USE_REST_DATAMODELS = 0x03;
	
	//REST is the default datamodel determiner
	private int crawlmode = CRAWLMODE_USE_DATBASE_DATAMODELS;
	
	
	
	public int getCrawlmode() {
		return crawlmode;
	}

	public void setCrawlmode(int crawlmode) {
		this.crawlmode = crawlmode;
	}

	public String getOutputDataBase() {
		return outputDataBase;
	}

	public void setOutputDataBase(String outputDataBase) {
		this.outputDataBase = outputDataBase;
	}

	public boolean isExtractTextFromSRPages() {
		return extractTextFromSRPages;
	}

	public void setExtractTextFromSRPages(boolean extractTextFromSRPages) {
		this.extractTextFromSRPages = extractTextFromSRPages;
	}

	public boolean isExtractTextFromAllVisitedPages() {
		return extractTextFromAllVisitedPages;
	}

	public void setExtractTextFromAllVisitedPages(
			boolean extractTextFromAllVisitedPages) {
		this.extractTextFromAllVisitedPages = extractTextFromAllVisitedPages;
	}
	
	public String getPathToAllDOwnloadedPages() {
		return pathToAllDOwnloadedPages;
	}

	public void setPathToAllDOwnloadedPages(String pathToAllDOwnloadedPages) {
		this.pathToAllDOwnloadedPages = pathToAllDOwnloadedPages;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getDataModelTable() {
		return dataModelTable;
	}

	public void setDataModelTable(String dataModelTable) {
		this.dataModelTable = dataModelTable;
	}
	
	//Set the datamodel using the datamodel from a REST request
	public void setDataModel(CrawlerRestRequest request){	
		this.detailedPageDS.readItemsXpathFromRequest(request);
	}

	public void setDetailedPageDS(String DBName) {
		
		if(itemDatamodel_databaseUsername == null){
			this.detailedPageDS.readItemsXpathDB(DEFAULT_ITEMDATAMODEL_DBURL, DEFAULT_ITEMDATAMODEL_DBUSERNAME, DEFAULT_ITEMDATAMODEL_DBPASSWORD, this.getDataModelTable());
		}else{
			this.detailedPageDS.readItemsXpathDB(itemDatamodel_databaseURL, itemDatamodel_databaseUsername, itemDatamodel_databasePassword, this.getDataModelTable());
		}
	}

	public boolean isExtractDataFromDPageS() {
		return extractDataFromDPageS;
	}

	public void setExtractDataFromDPageS(boolean extractDataFromDPageS) {
		this.extractDataFromDPageS = extractDataFromDPageS;
	}

	public String getCrawlStatusPath() {
		return crawlStatusPath;
	}

	public String getPathToNumberOfRepetitions() {
		return pathToNumberOfRepetitions;
	}

	public void setPathToNumberOfRepetitions(String pathToNumberOfRepetitions) {
		this.pathToNumberOfRepetitions = pathToNumberOfRepetitions;
	}

	public String getPathToLastSearchResultPage() {
		return pathToLastSearchResultPage;
	}

	public void setPathToLastSearchResultPage(String pathToLastSearchResultPage) {
		this.pathToLastSearchResultPage = pathToLastSearchResultPage;
	}

	public String getPathToVisitedPagesPerQuery() {
		return pathToVisitedPagesPerQuery;
	}

	public void setPathToVisitedPagesPerQuery(String pathToVisitedPagesPerQuery) {
		this.pathToVisitedPagesPerQuery = pathToVisitedPagesPerQuery;
	}

	@Deprecated
	public void setAllSitesDescription() throws Exception {
		
		//If this Config contains a null username, use the default
		if(websiteDatamodel_databaseUsername == null){
			listOfWebsites = descripReader.readDB(DEFAULT_WEBSITEDATAMODEL_DBURL,DEFAULT_WEBSITEDATAMODEL_DBUSERNAME, DEFAULT_WEBSITEDATAMODEL_DBPASSWORD);
		}else{
			listOfWebsites = descripReader.readDB(websiteDatamodel_databaseURL,websiteDatamodel_databaseUsername, websiteDatamodel_databasePassword);
		}
	}
	
	/**
	 * Set our sites description / website datamodel using info from a restrequest
	 * @param request
	 */
	public void setAllSitesDescription(CrawlerRestRequest request){
		
		//Begin a new collection
		ArrayList<WebsiteDS> websiteDatamodels = new ArrayList<WebsiteDS>();
		
		//Create a new websitedescription using the given Rest Request
		WebsiteDS siteDes = new WebsiteDS();
		siteDes.setEngineid(request.getID());
		siteDes.setName(request.getName());
		siteDes.setAddress(request.getUrl());
		siteDes.setDescription("REST website Crawler");
		siteDes.setComments("Datamodel made using REST");
		siteDes.setTemplate(request.getTemplateurl());
		siteDes.setItemXPath(request.getItem_xpath());
		siteDes.setLinkXPath(request.getItem_link_xpath());
		siteDes.setTitleXPath(request.getItem_title_xpath());
		siteDes.setDescXPath(request.getItem_description_xpath());
		siteDes.setThumbNXPath(request.getItem_thumbnail_xpath());
		siteDes.setStatus("");
		siteDes.setNextPageXP(WebsiteDescReader.refineString(request.getNextpage_xpath()));
		
		//Add the websitedatamodel to our collection
		websiteDatamodels.add(siteDes);
		
		//Return the list of website datamodels to our crawlingconfig
		listOfWebsites = websiteDatamodels;
	}

	@Deprecated
	public void setCurrentSiteDescription(boolean readFromDB, int index){
		if (readFromDB) {
			currentSiteDescription = descripReader.readOneDSFromDB(index);
		} else {
			currentSiteDescription = descripReader.readOpenDSFile(
					openDescFilePath + currentCollectionName, index); // from
			// openFileDescription
		}
	}
	
	/**
	 * Gets the current WebsiteDescripttion (Datamodel)
	 * @param request
	 */
	public void setCurrentSiteDescription(CrawlerRestRequest request){
		//Currently, yoou can only request one website datamodel at a time using REST
		currentSiteDescription = listOfWebsites.get(0);
	}
	
	

	public String getCollectionName() {
		return currentCollectionName;
	}

	public void setCollectionName() {
		String collectionName = null;
		File file = new File(openDescFilePath);
		if (file.isDirectory()) {
			String[] list = file.list();
			if (list.length > 1) {// to remove effect of .svn
				collectionName = list[1];
			} else {
				collectionName = list[0];
			}
			// there is only one openDescFile in each directory
		}
		this.currentCollectionName = collectionName;
	}

	public void setCollectionName(String name) {
		this.currentCollectionName = name;
	}

//	public ArrayList<String> getQueries() {
//		return queries;
//	}
//
//	public void setQueries(ArrayList<String> queries) {
//		this.queries = queries;
//	}

	public LinkedHashMap<String, String> getQueryNumberofResults() {
		return queryNumberofResults;
	}

	public void setQueryNumberofResults(
			LinkedHashMap<String, String> queryNumberofResults) {
		this.queryNumberofResults = queryNumberofResults;
	}

	public String getOpenDescFilePath() {
		return openDescFilePath;
	}

	public void setOpenDescFilePath(int numberOfCrawledSoerces) {
		this.openDescFilePath = this.openDescDirPath
				+ this.getListOfSourcesFolders().get(numberOfCrawledSoerces)
				+ "/";
	}

	public String getQueriesPath() {
		return queriesPath;
	}

	public void setQueriesPath(String queriesPath) {
		this.queriesPath = queriesPath;
	}

	public String getLinkContentSavePath() {
		return linkContentSavePath;
	}

	public void setLinkContentSavePath(String linkContentSavePath) {
		String filePath = linkContentSavePath;

		File file = new File(filePath);
		if (file.exists()) {
			this.linkContentSavePath = linkContentSavePath;
		} else if (file.mkdir()) {
			this.linkContentSavePath = linkContentSavePath;
			System.out.println("Save link path is set.");
		} else {
			System.err
					.println("Could not create the path to save the content of links returned by serch engine.");
		}
	}

	public void setCrawlStatusPath(String linkContentSavePath) {
		String filePath = linkContentSavePath;

		File file = new File(filePath);
		if (file.exists()) {
			this.crawlStatusPath = linkContentSavePath;
		} else if (file.mkdir()) {
			this.crawlStatusPath = linkContentSavePath;
			System.out.println("Crawl status path is set.");
		} else {
			System.err
					.println("Could not create the path to save the crawl status.");
		}
	}

	public Thread getCrawlingTh() {
		return crawlingTh;
	}

	public void setCrawlingTh(Thread crawlingTh) {
		this.crawlingTh = crawlingTh;
	}

	public String getSourceURL() {
		return sourceURL;
	}

	public void setSourceURL(String sourceURL) {
		this.sourceURL = sourceURL;
	}

	public void setQueries(String queriesFilePath) {
		if (this.getQuerySelectionApproach() == this.mostFrequentWebWords) {
			this.queriesPath = queriesFilePath;
			queries = new ArrayList<String>();
			queries.add("vitol");
			queries.add("vitol+oil");
			queries.add("vitol+trading");
			queries.add("vitol+foundation");
			queries.add("vitol+group");
			// //hazf-> queries = readQueriesFromFile(queries, queriesFilePath);
		} else if (this.getQuerySelectionApproach() == this.crawledTextBased) {
			// do nothing
		}
	}

	// [the, of, on, and, in, content, to, as, have, not, is, will, home, from,
	// by, on, wikipedia, for, was, site]
	private ArrayList<String> readQueriesFromFile(ArrayList<String> queries,
			String filePath) {
		try {
			File file = new File(filePath);
			FileReader fstream = new FileReader(file);
			BufferedReader in = new BufferedReader(fstream);
			for (int i = 0; i < 100; i++) {
				String line = in.readLine();
				String query = line.replaceAll("[0-9]*", "").trim();
				if (!queries.contains(query)) {
					queries.add(query);
				}
			}
			in.close();
			fstream.close();
		} catch (Exception e) {// Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}
		return queries;
	}

	public ArrayList<String> getListOfSourcesFolders() {
		return listOfSourcesFolders;
	}

	public ArrayList<String> setListOfSourcesFolders() {
		listOfSourcesFolders = new ArrayList<String>();
		File file = new File(openDescDirPath);
		if (file.isDirectory()) {
			String[] list = file.list();
			for (String fileDir : list) {
				listOfSourcesFolders.add(fileDir);
			}
			if (listOfSourcesFolders.contains(".svn")) {
				listOfSourcesFolders.remove(".svn");
			}
		}
		return listOfSourcesFolders;
	}

	public void updateQueryList(String pageContent) {
		pageContent = this.refineText(pageContent);
		String[] tokens = tokenizer(pageContent);

		for (String token : tokens) {
			if (!token.equalsIgnoreCase("") && !this.isStopWord(token) && !initialQuery.contains(token)
					&& !sentQueries.contains(token)) {//it is not used before
				//!initialQuery.contains(token) to avoid having "vitol+company+vitol"
				synchronized (querySet) {
					if (querySet.containsKey(token)) {
						querySet.put(token, querySet.get(token) + 1);

					} else {
						querySet.put(token, 1);

					}
				}
			}
		}
	}

	public String refineText(String pageContent) {

		if (pageContent.contains("\n")) {
			pageContent = pageContent.replaceAll("\n", " ");
		}
		/*
		 * if (pageContent.matches("[.,!?:;'\"-]")){ pageContent =
		 * pageContent.replaceAll("\\p{punct}+", " "); } if
		 * (pageContent.contains("\\p{Punct}")){ pageContent =
		 * pageContent.replaceAll("\\p{Punct}", " "); }
		 */
		if (pageContent.contains("\\") || pageContent.contains(":")
				|| pageContent.contains(";") || pageContent.contains(".")) {
			pageContent = pageContent.replaceAll("\\p{Punct}", " ");
		}
		return pageContent.trim();
	}

	private String[] tokenizer(String content) {
		String delims = "[+\\-*/\\^ .,?!:;=()]+";
		String[] tokens = content.split(delims);
		return tokens;
	}

	public String setNextQuery() {
		String url = null;
		if (this.querySelectionApproach == this.crawledTextBased) {
			if (firstQuery) {// set first query
				query = "";
				for(String part : initialQuery){
					initialQueryProcesses = initialQueryProcesses+"+"+ part;
				}
				initialQueryProcesses = initialQueryProcesses.replaceFirst("\\+", "");
				query = initialQueryProcesses; //only for vitol website
				sentQueries.add(query);
				querySet.put(query, 0); // not to use later
				firstQuery = false;
			} else {
				query = getMostFreqQuery();// getLeastFreqQuery();//
				sentQueries.add(query);
				querySet.put(query, 0); // not to use later//it grows again
				query = initialQueryProcesses+"+"+query; // for vitol 
				saveMostFreqTable();
			}
		//	querySet.put(query, 0); // not to use later
			String tempUrl = currentSiteDescription.getTemplate();
			
			if (tempUrl.contains("{q}")) {
				url = currentSiteDescription.getTemplate()
						.replace("{q}", query);
			} else if (tempUrl.contains("{searchTerms}")) {
				url = currentSiteDescription.getTemplate().replace(
						"{searchTerms}", query);
			} else if (tempUrl.contains("{query}")) {
				url = currentSiteDescription.getTemplate().replace("{query}",
						query);
			}else{//in case of browsing
				url = currentSiteDescription.getTemplate();
			}
			/*
			 * currentSiteDescription.getTemplate().replace( "{searchTerms}",
			 * query);
			 */
			url = url.replace("amp;", "");
			System.out.println("New query, number " + queryIndex + " : "
					+ query);
			queryIndex++;
			return url;
		} else if (this.querySelectionApproach == this.mostFrequentWebWords){
			if (queryIndex < queries.size()) {
				query = queries.get(queryIndex);
				if (!currentSiteDescription.isAcceptsStopWords()) {
					if (isStopWord(query)) {
						queryIndex++;
						return setNextQuery();
					} /*
			if (url == null){
				url = currentSiteDescription.getTemplate();
			}
		
					 * else if(query is used before){list does not have repeated
					 * queries queryIndex++; return setNextQuery(); }
					 */
					{
						String tempUrl = currentSiteDescription.getTemplate();
						if (tempUrl.contains("{q}")) {
							url = currentSiteDescription.getTemplate().replace(
									"{q}", query);
						} else if (tempUrl.contains("{searchTerms}")) {
							url = currentSiteDescription.getTemplate().replace(
									"{searchTerms}", query);
						} else if (tempUrl.contains("{query}")) {
							url = currentSiteDescription.getTemplate().replace(
									"{query}", query);
						}
						/*
						 * currentSiteDescription.getTemplate().replace(
						 * "{searchTerms}", query);
						 */
						url = url.replace("amp;", "");
						System.out.println("New query, number " + queryIndex
								+ " : " + query);
						queryIndex++;
						return url;
					}
				} else {
					String tempUrl = currentSiteDescription.getTemplate();
					if (tempUrl.contains("{q}")) {
						url = currentSiteDescription.getTemplate().replace(
								"{q}", query);
					} else if (tempUrl.contains("{searchTerms}")) {
						url = currentSiteDescription.getTemplate().replace(
								"{searchTerms}", query);
					} else if (tempUrl.contains("{query}")) {
						url = currentSiteDescription.getTemplate().replace(
								"{query}", query);
					}
					/*
					 * currentSiteDescription.getTemplate().replace(
					 * "{searchTerms}", query);
					 */
					url = url.replace("amp;", "");
					System.out.println("New query, number " + queryIndex
							+ " : " + query);
					queryIndex++;
					return url;
				}
			} else {
				System.out.println("End of Query List");
				return null;
			}
		}else if(this.querySelectionApproach == this.browsing){
			if (url == null){
				url = currentSiteDescription.getTemplate();
			}
		}
		return url;
	}

	private void saveMostFreqTable() {
		try {
			File file = new File(this.getCrawlStatusPath() + "tableofmostfreq");
			FileWriter fstream = new FileWriter(file, false);
			BufferedWriter out = new BufferedWriter(fstream);
			Iterator<String> keyIt = querySet.keySet().iterator();
			while (keyIt.hasNext()) {
				String key = (String) keyIt.next();
				int comp1 = querySet.get(key);
				out.write(key + "\t" + comp1 + "\n");
				out.flush();

			}
			out.close();
			fstream.close();
		} catch (Exception e) {// Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}

	}

	/*
	 * private void readMostFreqTableFromFile() { try { File file = new
	 * File(this.getCrawlStatusPath()+"tableofmostfreq"); Reader fstream = new
	 * FileW(file, false); BufferedReader in = new BufferedReader(fstream);
	 * Iterator<String> keyIt = querySet.keySet().iterator(); while
	 * (keyIt.hasNext()) { String key = (String) keyIt.next(); int comp1 =
	 * querySet.get(key); out.write(key+"\t"+comp1+"\n"); out.flush();
	 * 
	 * } out.close(); fstream.close(); } catch (Exception e) {// Catch exception
	 * if any System.err.println("Error: " + e.getMessage()); }
	 * 
	 * }
	 */

	private String getMostFreqQuery() {

		List<String> mapKeys = new ArrayList<String>(querySet.keySet());
		List<Integer> mapValues = new ArrayList<Integer>(querySet.values());
		Collections.sort(mapValues, Collections.reverseOrder());

		Iterator<Integer> valueIt = mapValues.iterator();
		// TODO check if this is the most frequent and not the least frequent
		int val = (Integer) valueIt.next();
		Iterator<String> keyIt = mapKeys.iterator();
		while (keyIt.hasNext()) {
			String key = (String) keyIt.next();
			int comp1 = querySet.get(key);

			if (comp1 == val && !initialQuery.contains(key)) {
				return key;
			}
		}

		return null;
	}
	
	private String getLeastFreqQuery() {

		List<String> mapKeys = new ArrayList<String>(querySet.keySet());
		List<Integer> mapValues = new ArrayList<Integer>(querySet.values());
		Collections.sort(mapValues);

		Iterator<Integer> valueIt = mapValues.iterator();
		// TODO check if this is the most frequent and not the least frequent
		//int val = (Integer) valueIt.next();
		Iterator<String> keyIt = mapKeys.iterator();
		while (keyIt.hasNext()) { // we set value to 0 not to use it later
			String key = (String) keyIt.next();
			int comp1 = querySet.get(key);

			if (comp1 != 0 && !initialQuery.contains(key)) {
				return key;
			}
		}

		return null;
	}

	private boolean isStopWord(String query) {
		if (listOfStopWords == null) {
			setListOfStopWords();
		}
		if (query.length() < 2) {
			return true;
		}
		if (query.matches(".*\\d.*")) {
			return true;
		}
		if (listOfStopWords.contains(query)) {
			return true;
		}
		return false;
	}

	public String setTestQuery(String query) {
		String url = currentSiteDescription.getTemplate().replace("{q}", query);
		url = url.replace("amp;", "");
		System.out.println("Test query: " + query);
		return url;
	}

	public void resetForNextCollection() {
		this.queryIndex = 0;
		this.querySet.clear();
		this.firstQuery = true;
	}

	public String getCurrentSRPageURL() {
		return currentSRPageURL;
	}

	public void setCurrentSRPageURL(String currentURL) {
		this.currentSRPageURL = currentURL;
	}

	/*
	 * public WebTools getWebTools() { return webTools; }
	 * 
	 * public void setWebTools(WebTools webTools) { this.webTools = webTools; }
	 */

	public String getOpenDescDirPath() {
		return openDescDirPath;
	}

	public void setOpenDescDirPath(String openDescDirPath) {
		this.openDescDirPath = openDescDirPath;
	}

	public WebsiteDS getCurrentSiteDescription() {
		return currentSiteDescription;
	}

	public void setCurrentSiteDescription(WebsiteDS currentSiteDescription) {
		this.currentSiteDescription = currentSiteDescription;
	}

	public ArrayList<WebsiteDS> getListOfWebsites() {
		return listOfWebsites;
	}

	public int getQueryIndex() {
		return queryIndex;
	}

	public void setQueryIndex(int queryIndex) {
		this.queryIndex = queryIndex;
	}

	public HashSet<String> setListOfStopWords() {
		listOfStopWords = new HashSet<String>();
		listOfStopWords.add("the");
		listOfStopWords.add("a");
		listOfStopWords.add("an");
		listOfStopWords.add("of");
		listOfStopWords.add("in");
		listOfStopWords.add("and");
		listOfStopWords.add("is");
		listOfStopWords.add("to");
		listOfStopWords.add("at");
		listOfStopWords.add("on");
		listOfStopWords.add("as");
		listOfStopWords.add("not");
		listOfStopWords.add("from");
		listOfStopWords.add("by");
		listOfStopWords.add("for");

		listOfStopWords.add("het");
		listOfStopWords.add("de");
		listOfStopWords.add("en");
		listOfStopWords.add("met");
		listOfStopWords.add("andere");
		listOfStopWords.add("tussen");
		listOfStopWords.add("van");
		listOfStopWords.add("een");
		listOfStopWords.add("pagina");
		listOfStopWords.add("deze");
		
		listOfStopWords.add("have");
		listOfStopWords.add("had");
		listOfStopWords.add("will");
		listOfStopWords.add("would");
		listOfStopWords.add("there");
		listOfStopWords.add("with");
		listOfStopWords.add("wikipedia");
		listOfStopWords.add("wikimedia");
		listOfStopWords.add("also");
		listOfStopWords.add("org");
		listOfStopWords.add("here");
		listOfStopWords.add("there");
		listOfStopWords.add("data");
		listOfStopWords.add("that");
		listOfStopWords.add("this");
		listOfStopWords.add("these");
		listOfStopWords.add("those");
		listOfStopWords.add("me");
		listOfStopWords.add("her");
		listOfStopWords.add("his");
		listOfStopWords.add("world");
		listOfStopWords.add("at");
		listOfStopWords.add("was");
		listOfStopWords.add("were");
		listOfStopWords.add("page");
		listOfStopWords.add("new");
		listOfStopWords.add("all");
		listOfStopWords.add("also");
		listOfStopWords.add("public");
		listOfStopWords.add("next");
		listOfStopWords.add("last");
		listOfStopWords.add("book");
		listOfStopWords.add("than");
		listOfStopWords.add("which");
		listOfStopWords.add("when");
		listOfStopWords.add("see");
		listOfStopWords.add("many");
		listOfStopWords.add("has");
		listOfStopWords.add("are");
		listOfStopWords.add("com");
		listOfStopWords.add("or");
		listOfStopWords.add("more");
		listOfStopWords.add("be");
		listOfStopWords.add("its");
		listOfStopWords.add("data");
		listOfStopWords.add("please");
		listOfStopWords.add("http");
		listOfStopWords.add("links");
		listOfStopWords.add("their");
		listOfStopWords.add("page");
		listOfStopWords.add("about");
		listOfStopWords.add("high");
		listOfStopWords.add("must");
		listOfStopWords.add("see");
		listOfStopWords.add("book");
		/*
		 * String[] a = new String[]{"the", "of", "on", "and", "in", "content",
		 * "to", "as", "have","not", "is", "will", "home", "from", "by", "on",
		 * "wikipedia", "for", "was", "site", "this", "contains", "their", "as",
		 * "edit", "string", "with", "there", "page", "his", also, when, org,
		 * here, data, that, wikimedia, me, world, at, video, page, it, powered,
		 * content, than, http, links, work, he, had, article, his, back, many,
		 * state, please, an, free, are, software, after, or, must, january,
		 * cache, centralauth, high, about, be, posted, expires, available, all,
		 * travel, book, also, mail, public, internet, right, retrieved,
		 * private, national, which, media, game, last, en, text, were, store,
		 * new, hotels, search, en, see, changes, has, encyclopedia};
		 */
		return listOfStopWords;
	}

	public void setListOfStopWords(HashSet<String> listOfStopWords) {

		this.listOfStopWords = listOfStopWords;
	}

	public Browser getScrShotBrowser() {
		return scrShots;
	}

	public void setScrShotBrowser() {
		this.scrShots = new Browser();
		this.scrShots.setDriver();
	}

	public void stopFXDriver() {
		this.scrShots.stopFXDriver();
		Iterator<Browser> iter = this.detailedPagesBrowsers.keySet().iterator();
		while (iter.hasNext()) {
			((Browser) iter.next()).stopFXDriver();
		}
	}

	public HashMap<Browser, Boolean> getDetailedPageBrowser() {
		return detailedPagesBrowsers;
	}

	public void setDetailedPageBrowsers(int numberOfBrowsers) {
		for (int i = 0; i < numberOfBrowsers; i++) {
			Browser detailedPages = new Browser();
			detailedPages.setDriver();
			this.detailedPagesBrowsers.put(detailedPages, true); // true means
																	// it is
																	// free
		}

	}

	public int getQuerySelectionApproach() {
		return querySelectionApproach;
	}

	public void setQuerySelectionApproach(int querySelectionApproach) {
		this.querySelectionApproach = querySelectionApproach;
	}

	public void saveCrawlStatus(ArrayList<String> listOfReturnedResultsOfSRPage) {
		synchronized (this) {

			this.saveLists(listOfReturnedResultsOfSRPage,
					this.pathToVisitedPagesDoc);
		}
	}

	public void saveCrawlStatus(String crawledPage) {// ,
		//synchronized (this) {
			this.saveStringInFile(crawledPage, this.pathToVisitedPagesPerQuery,
					true);
			this.saveStringInFile(crawledPage, this.pathToVisitedPagesDoc, true);
			this.saveStringInFile(this.currentSRPageURL,
					this.pathToLastSearchResultPage, false);

			this.saveStringInFile(crawledPage, this.pathToLastLinkDoc, false);
	//	}
	}

	public void saveCrawlStatus(int crawledPage, String path) {// ,
	//	synchronized (this) {
			this.saveStringInFile(crawledPage + "", path, false);
	//	}
	}

	public void saveCrawlStatusCollectionName() {// ,
		this.saveStringInFile(this.currentCollectionName,
				this.pathToCoveredCollections, true);
	}

	public void saveCrawlStatusQuery() {// ,
		if (this.queryIndex == 1) {// To replace if it is repeated for first
									// time
			this.saveStringInFile(query, this.pathToSentQueriesDoc, false);
		} else {
			this.saveStringInFile(query, this.pathToSentQueriesDoc, true);
		}
		this.saveStringInFile("", this.pathToVisitedPagesPerQuery, false);// remove
																			// the
																			// urls
																			// entered
																			// for
																			// previous
																			// query
	}

	public void saveLists(ArrayList<String> list, String filePath) {
		try {
			File file = new File(filePath);
			FileWriter fstream = new FileWriter(file, true);
			BufferedWriter out = new BufferedWriter(fstream);
			for (String element : list) {
				out.write(element + "\n");
				out.flush();
			}
			out.close();
			fstream.close();
		} catch (Exception e) {// Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}

	}

	public void saveListsofQueries(Set<String> list, String filePath) {
		try {
			File file = new File(filePath);
			FileWriter fstream = new FileWriter(file, true);
			BufferedWriter out = new BufferedWriter(fstream);
			for (String element : list) {
				out.write(element + "\n");
				out.flush();
			}
			out.close();
			fstream.close();
		} catch (Exception e) {// Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}

	}

	public void saveStringInFile(String link, String filePath, boolean append) {
		if(filePath != null){
			try {
				File file = new File(filePath);
				FileWriter fstream = new FileWriter(file, append);
				BufferedWriter out = new BufferedWriter(fstream);
				out.write(link + "\n");
				out.flush();
				out.close();
				fstream.close();
			} catch (Exception e) {// Catch exception if any
				System.err.println("Error: " + e.getMessage());
			}
		}

	}

	public void saveStringInFile(String dataPart1, String dataPart2,
			String filePath, boolean append) {
		try {
			File file = new File(filePath);
			FileWriter fstream = new FileWriter(file, append);
			BufferedWriter out = new BufferedWriter(fstream);
			out.write(dataPart1 + "\t" + dataPart2 + "\n");
			out.flush();
			out.close();
			fstream.close();
		} catch (Exception e) {// Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}

	}

	public ArrayList<String> readLinesFromFile(String path) {
		ArrayList<String> visitedPagesInLastCrawl = new ArrayList<String>();
		try {
			File file = new File(path);
			FileReader fstream = new FileReader(file);
			BufferedReader in = new BufferedReader(fstream);
			String line = "";
			while ((line = in.readLine()) != null) {
				if (line.startsWith("http")) {
					visitedPagesInLastCrawl.add(line.trim());
				}
			}
			in.close();
			fstream.close();
		} catch (Exception e) {// Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}
		return visitedPagesInLastCrawl;
	}

	public int readFirstNumberFromFile(String path) {
		String line = "0";
		try {
			File file = new File(path);
			FileReader fstream = new FileReader(file);
			BufferedReader in = new BufferedReader(fstream);
			ArrayList<String> visitedPagesInLastCrawl = new ArrayList<String>();
			line = in.readLine();
			if (line != null) {
				line = line.trim();
			}
			in.close();
			fstream.close();
		} catch (Exception e) {// Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}
		int foo = Integer.parseInt(line);
		return foo;
	}

	public String readLastPageFromFile(String path) {
		String line = "";
		try {
			File file = new File(path);
			FileReader fstream = new FileReader(file);
			BufferedReader in = new BufferedReader(fstream);
			ArrayList<String> visitedPagesInLastCrawl = new ArrayList<String>();
			line = in.readLine();
			if (line != null) {
				line = line.trim();
			}
			in.close();
			fstream.close();
		} catch (Exception e) {// Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}
		return line;
	}

	public void setQueryIndexFromFile() {
		this.queryIndex = this.lastQueryIndex();
		if (queryIndex != 0) {
			this.query = this.queries.get(queryIndex - 1);
		}
	}

	public int lastQueryIndex() {
		String line = "";
		int indexValue = 0;
		ArrayList<String> tempSentQueries = new ArrayList<String>();
		try {
			File file = new File(this.pathToSentQueriesDoc);
			FileReader fstream = new FileReader(file);
			BufferedReader in = new BufferedReader(fstream);
			while ((line = in.readLine()) != null) {
				tempSentQueries.add(line.trim());
			}
			in.close();
			fstream.close();
		} catch (Exception e) {// Catch exception if any
			System.err.println("Error: " + e.getMessage());
		}
		if (!tempSentQueries.isEmpty()) {
			if (this.queries.contains(tempSentQueries.get(tempSentQueries
					.size() - 1))) {
				indexValue = queries.indexOf(tempSentQueries
						.get(tempSentQueries.size() - 1)) + 1;// everytime the
																// index is
																// increased
																// after
																// selecting
																// query. to set
																// it to that
																// situation, +1
																// is done
			}
		} else {
			indexValue = 0;
		}
		return indexValue;
	}

	public String getPathToURLCrawlList() {
		return pathToURLCrawlList;
	}

	public void setPathToURLCrawlList(String pathToURLCrawlList) {
		this.pathToURLCrawlList = pathToURLCrawlList;
	}

	public String getPathToVisitedPagesDoc() {
		return pathToVisitedPagesDoc;
	}

	public void setPathToVisitedPagesDoc(String pathToVisitedPagesDoc) {
		this.pathToVisitedPagesDoc = pathToVisitedPagesDoc;
	}

	public String getPathToSentQueriesDoc() {
		return pathToSentQueriesDoc;
	}

	public void setPathToSentQueriesDoc(String pathToSentQueriesDoc) {
		this.pathToSentQueriesDoc = pathToSentQueriesDoc;
	}

	public String getPathToCoveredCollections() {
		return pathToCoveredCollections;
	}

	public void setPathToCoveredCollections(String pathToCoveredCollections) {
		this.pathToCoveredCollections = pathToCoveredCollections;
	}

	public String getPathToLastLinkDoc() {
		return pathToLastLinkDoc;
	}

	public void setPathToLastLinkDoc(String pathToLastLinkDoc) {
		this.pathToLastLinkDoc = pathToLastLinkDoc;
	}

	public boolean isUnPauseCrawl() {
		return unPauseCrawl;
	}

	public void setUnPauseCrawl(boolean unPauseCrawl) {
		this.unPauseCrawl = unPauseCrawl;
	}

	public void setPathToNumberOfSearchResults(String string) {
		pathToNumberOfSearchResults = string;
	}

	public void setPathToNumberOfSentQueries(String string) {
		// TODO Auto-generated method stub
		pathToNumberOfSentQueries = string;
	}

	public boolean FreeBrowserExists() {
		Iterator<Browser> iter = this.detailedPagesBrowsers.keySet().iterator();
		while (iter.hasNext()) {
			boolean ifFree = this.detailedPagesBrowsers.get((Browser) iter
					.next());
			if (ifFree) {
				return true;
			}
		}
		return false;// non of them is free
	}

	/*
	 * public void waitTillFreeBrowser() { boolean anyFreeBrowser = false; while
	 * (!anyFreeBrowser) { Iterator<Browser> iter =
	 * this.detailedPagesBrowsers.keySet() .iterator(); while (iter.hasNext()) {
	 * boolean ifFree = this.detailedPagesBrowsers.get((Browser) iter .next());
	 * if (ifFree) { anyFreeBrowser = true; } } } }
	 */

	public Browser getFreeBrowser() {
		synchronized (this) {
			Iterator<Browser> iter = detailedPagesBrowsers.keySet().iterator();
			while (iter.hasNext()) {
				Browser tempBrowser = (Browser) iter.next();
				boolean ifFree = detailedPagesBrowsers.get(tempBrowser);
				if (ifFree) {
					detailedPagesBrowsers.put(tempBrowser, false);
					// true means it is free
					return tempBrowser;
				}
			}
			waitTillOneIsFree();
			return getFreeBrowser();
		}
	}

	public void waitTillOneIsFree() {
		boolean oneBrowsersIsFree = false;
		while (!oneBrowsersIsFree) {
			Iterator<Browser> iter = this.detailedPagesBrowsers.keySet()
					.iterator();
			boolean oneBusy = false;
			while (iter.hasNext()) {
				boolean ifFree = this.detailedPagesBrowsers.get((Browser) iter
						.next());
				if (ifFree) {
					oneBusy = true;
					break;
				}
			}
			if (oneBusy == true) {
				oneBrowsersIsFree = true;
			}
		}

	}

	public void freeBrowser(Browser browser) {
		// synchronized(this){
		detailedPagesBrowsers.put(browser, true);// true means it is free
		// }

	}

	public void waitTillAllBrowsersAreFree() {
		boolean allBrowsersAreFree = false;
		while (!allBrowsersAreFree) {
			Iterator<Browser> iter = this.detailedPagesBrowsers.keySet()
					.iterator();
			boolean noBusyBrowser = true;
			while (iter.hasNext()) {
				boolean ifFree = this.detailedPagesBrowsers.get((Browser) iter
						.next());
				if (!ifFree) {
					noBusyBrowser = false;
					break;
				}
			}
			if (noBusyBrowser == true) {
				allBrowsersAreFree = true;
			}
		}

	}

	public String getWebsiteDatamodel_databaseUsername() {
		return websiteDatamodel_databaseUsername;
	}

	public void setWebsiteDatamodel_databaseUsername(
			String websiteDatamodel_databaseUsername) {
		this.websiteDatamodel_databaseUsername = websiteDatamodel_databaseUsername;
	}

	public String getWebsiteDatamodel_databasePassword() {
		return websiteDatamodel_databasePassword;
	}

	public void setWebsiteDatamodel_databasePassword(
			String websiteDatamodel_databasePassword) {
		this.websiteDatamodel_databasePassword = websiteDatamodel_databasePassword;
	}

	public String getWebsiteDatamodel_databaseURL() {
		return websiteDatamodel_databaseURL;
	}

	public void setWebsiteDatamodel_databaseURL(String websiteDatamodel_databaseURL) {
		this.websiteDatamodel_databaseURL = websiteDatamodel_databaseURL;
	}

	public String getItemDatamodel_databaseUsername() {
		return itemDatamodel_databaseUsername;
	}

	public void setItemDatamodel_databaseUsername(
			String itemDatamodel_databaseUsername) {
		this.itemDatamodel_databaseUsername = itemDatamodel_databaseUsername;
	}

	public String getItemDatamodel_databasePassword() {
		return itemDatamodel_databasePassword;
	}

	public void setItemDatamodel_databasePassword(
			String itemDatamodel_databasePassword) {
		this.itemDatamodel_databasePassword = itemDatamodel_databasePassword;
	}

	public String getItemDatamodel_databaseURL() {
		return itemDatamodel_databaseURL;
	}

	public void setItemDatamodel_databaseURL(String itemDatamodel_databaseURL) {
		this.itemDatamodel_databaseURL = itemDatamodel_databaseURL;
	}

}
