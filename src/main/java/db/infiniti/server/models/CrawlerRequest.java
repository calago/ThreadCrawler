package db.infiniti.server.models;

import java.util.ArrayList;

/**
 * A POJO used to request a list of new Prices
 * @author root
 *
 */
public class CrawlerRequest
{

    ArrayList<SimpleProduct> products;
    

    public ArrayList<SimpleProduct> getProducts()
    {
        return products;
    }

    public void setProducts(ArrayList<SimpleProduct> products)
    {
        this.products = products;
    }
    
    
}
