package db.infiniti.server.models;

/**
 * A small POJO for a product.
 * @author intershop1
 *
 */
public class SimpleProduct
{
    String id;
    String productName;
    String longDescription;
    double price;
    
    
    public String getProductName()
    {
        return productName;
    }
    public void setProductName(String productName)
    {
        this.productName = productName;
    }
    public String getLongDescription()
    {
        return longDescription;
    }
    public void setLongDescription(String longDescription)
    {
        this.longDescription = longDescription;
    }
    public double getPrice()
    {
        return price;
    }
    public void setPrice(double price)
    {
        this.price = price;
    }
    public String getId()
    {
        return id;
    }
    public void setId(String id)
    {
        this.id = id;
    }

}