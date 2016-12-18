package com.sheepguru.jetimport.api.jet.product;

import com.sheepguru.jetimport.api.jet.Utils;
import java.math.BigDecimal;
import java.util.Date;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;


/**
 * A record that contains a response from the get upload token endpoint..
 * @author John Quinn
 */
public class BulkUploadAuth 
{
  /**
   * The url where the file should be uploaded
   */
  private final String url;
  
  /**
   * The jet defined id associated with the file upload
   */
  private final String jetFileId;
  
  /**
   * The amount of time in seconds the available URL will be open to put a file
   */
  private final int expiresInSeconds;
  
  /**
   * A Date when this will expire 
   */
  private final Date expiresAt;
  
  
  /**
   * Create an instance of this from Jet json 
   * @param json Json to parse
   * @return instance 
   */
  public static BulkUploadAuth fromJSON( final JsonObject json )
  {
    return new BulkUploadAuth(
      json.getString( "url", "" ),
      json.getString( "jet_file_id", "" ),
      json.getInt( "expires_in_seconds", 0 )
    );
  }
  
  
  /**
   * Create a new BulkUploadAuth instance 
   * @param url Url to upload file
   * @param jetFileId Jet File id 
   * @param expiresInSeconds number of seconds for which the token is valid.
   */
  public BulkUploadAuth( final String url, final String jetFileId, 
    final int expiresInSeconds )
  {
    Utils.checkNullEmpty( url, "url" );
    Utils.checkNullEmpty( jetFileId, "jetFileId" );
    
    this.expiresInSeconds = expiresInSeconds;
    this.url = url;
    this.jetFileId = jetFileId;
    expiresAt = new Date( System.currentTimeMillis() 
      + ( expiresInSeconds * 1000L ));
  }
  

  /**
   * Turn this object into Jet Json 
   * @return jet json 
   */
  public JsonObject toJSON()
  {
    return Json.createObjectBuilder()
      .add( "url", url )
      .add( "jet_file_id", jetFileId )
      .add( "expires_in_seconds", expiresInSeconds )
      .build();    
  }
  
  
  /**
   * Get the url where the file should be uploaded
   * @return url 
   */
  public String getUrl()
  {
    return url;
  }
  
  
  /**
   * Get the Jet defined file id 
   * @return 
   */
  public String getJetFileId()
  {
    return jetFileId;
  }
  
  
  /**
   * Number of seconds the upload token is valid 
   * @return 
   */
  public int getExpiresInSeconds()
  {
    return expiresInSeconds;
  }
  
  
  /**
   * Local time this token expires at.
   * @return 
   */
  public Date getExpiresAt()
  {
    return expiresAt;
  }
}
