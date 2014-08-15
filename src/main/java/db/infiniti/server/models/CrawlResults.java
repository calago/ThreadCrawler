package db.infiniti.server.models;

import java.util.ArrayList;

/**
 * POJO for Marketingresults
 * @author intershop1
 *
 */
public class CrawlResults
{
    //Number of the resultset.
    int resultset;
    
    //The last crawl date
    String last_crawldate;
    
    //Products
    ArrayList<SimpleProduct> products;
    

    public int getResultset()
    {
        return resultset;
    }

    public void setResultset(int resultset)
    {
        this.resultset = resultset;
    }

    public String getLast_crawldate()
    {
        return last_crawldate;
    }

    public void setLast_crawldate(String last_crawldate)
    {
        this.last_crawldate = last_crawldate;
    }

    public void addProduct(SimpleProduct p){
        products.add(p);
    }
    
    public ArrayList<SimpleProduct> getProducts()
    {
        return products;
    }

    public void setProducts(ArrayList<SimpleProduct> products)
    {
        this.products = products;
    }

}
