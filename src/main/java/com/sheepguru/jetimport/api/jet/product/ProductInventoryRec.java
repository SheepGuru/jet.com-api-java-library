
package com.sheepguru.jetimport.api.jet.product;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.json.JsonArray;
import javax.json.JsonObject;

/**
 * An object representing the results from a Product Inventory query.
 * 
 * The inventory returned from this endpoint represents the number in the feed, 
 * not the quantity that is currently sellable on Jet.com
 * 
 * @author John Quinn
 */
public class ProductInventoryRec 
{
  /**
   * A format for converting jet dates to a date
   */
  private static final SimpleDateFormat FORMAT = new SimpleDateFormat( 
    "yyyy-MM-dd'T'HH:mm:ss.SSSSSSSX", Locale.ENGLISH );    
  
  /**
   * An array of fulfillment nodes to set inventory
   */
  private final List<FNodeInventory> nodes = new ArrayList<>();
  
  /**
   * The last update
   */
  private final Date lastUpdate;
  
  
  /**
   * Create a ProductInventoryRec instance from Jet JSON
   * @param json Jet Json
   * @return Object 
   * @throws ParseException if the date can't be formatted 
   */
  public static ProductInventoryRec fromJSON( final JsonObject json )
    throws ParseException
  {
    if ( json == null )
      throw new IllegalArgumentException( "json cannot be null" );
    
    final List<FNodeInventory> n = new ArrayList<>();
    
    final JsonArray a = json.getJsonArray( "fulfillment_nodes" );
    if ( a != null )
    {
      for ( int i = 0; i < a.size(); i++ )
      {
        n.add( FNodeInventory.fromJSON( a.getJsonObject( i )));
      }
    }
    
    return new ProductInventoryRec( n, json.getString( "inventory_last_update", "" ));
  }
  
  
  /**
   * Create a new ProductInventoryRec instance 
   * @param nodes Fulfillment nodes
   * @param lastUpdate Last update
   * @throws ParseException If last update can't be parsed 
   */
  public ProductInventoryRec( final List<FNodeInventory> nodes, final String lastUpdate )
    throws ParseException
  {
    if ( nodes == null )
      throw new IllegalArgumentException( "nodes cannot be null" );
    else if ( lastUpdate == null || lastUpdate.isEmpty())
      throw new IllegalArgumentException( "lastUpdate cannot be null or empty" );
    
    this.nodes.addAll( nodes );
    this.lastUpdate = FORMAT.parse( lastUpdate );
  }
  
  
  /**
   * Retrieve the fulfillment nodes
   * @return nodes
   */
  public List<FNodeInventory> getNodes()
  {
    return nodes;
  }
  
  
  /**
   * Retrive the last update
   * @return date
   */
  public Date getLastUpdate()
  {
    return lastUpdate;
  }
  
}
