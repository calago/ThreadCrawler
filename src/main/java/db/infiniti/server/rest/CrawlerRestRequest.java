package db.infiniti.server.rest;

import java.util.ArrayList;

/**
 * A POJO for the REST request
 * @author root
 *
 */
public class CrawlerRestRequest
{
    
    //Basic variables
    int ID;
    String name;
    //A base/main url of the website.
    String url;
    //The url to our "list"
    String templateurl;
    //XPath to the next Page
    String nextpage_xpath;
    
    //Item specific variables
    //XPath to an item in the list
    String item_xpath;
    //Xpath to the link that opens the detailpage
    String item_link_xpath;
    //Xpath to the title of this item (not used?)
    String item_title_xpath;
    
    //XPath to the description of this item
    String item_description_xpath;
    //XPath to the thumbnail of this item
    String item_thumbnail_xpath;
    
    //The Itemdatamodel
    ArrayList<ItemData> item_datamodel;
    
    
    public int getID()
    {
        return ID;
    }

    public void setID(int iD)
    {
        ID = iD;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }

    public String getTemplateurl()
    {
        return templateurl;
    }

    public void setTemplateurl(String templateurl)
    {
        this.templateurl = templateurl;
    }

    public String getNextpage_xpath()
    {
        return nextpage_xpath;
    }

    public void setNextpage_xpath(String nextpage_xpath)
    {
        this.nextpage_xpath = nextpage_xpath;
    }

    public String getItem_xpath()
    {
        return item_xpath;
    }

    public void setItem_xpath(String item_xpath)
    {
        this.item_xpath = item_xpath;
    }

    public String getItem_link_xpath()
    {
        return item_link_xpath;
    }

    public void setItem_link_xpath(String item_link_xpath)
    {
        this.item_link_xpath = item_link_xpath;
    }

    public String getItem_title_xpath()
    {
        return item_title_xpath;
    }

    public void setItem_title_xpath(String item_title_xpath)
    {
        this.item_title_xpath = item_title_xpath;
    }

    public String getItem_description_xpath()
    {
        return item_description_xpath;
    }

    public void setItem_description_xpath(String item_description_xpath)
    {
        this.item_description_xpath = item_description_xpath;
    }

    public String getItem_thumbnail_xpath()
    {
        return item_thumbnail_xpath;
    }

    public void setItem_thumbnail_xpath(String item_thumbnail_xpath)
    {
        this.item_thumbnail_xpath = item_thumbnail_xpath;
    }

    public ArrayList<ItemData> getItem_datamodel()
    {
        return item_datamodel;
    }

    public void setItem_datamodel(ArrayList<ItemData> item_datamodel)
    {
        this.item_datamodel = item_datamodel;
    }
    
    
}
