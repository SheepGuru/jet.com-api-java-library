/**
 * This file is part of the Aerodrome package, and is subject to the 
 * terms and conditions defined in file 'LICENSE', which is part 
 * of this source code package.
 *
 * Copyright (c) 2016 All Rights Reserved, John T. Quinn III,
 * <johnquinn3@gmail.com>
 *
 * THIS CODE AND INFORMATION ARE PROVIDED "AS IS" WITHOUT WARRANTY OF ANY
 * KIND, EITHER EXPRESSED OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND/OR FITNESS FOR A
 * PARTICULAR PURPOSE.
 */

package com.buffalokiwi.aerodrome.jet.products;

import com.buffalokiwi.api.APIException;
import com.buffalokiwi.api.PostFile;
import com.buffalokiwi.aerodrome.jet.IJetAPIResponse;
import com.buffalokiwi.aerodrome.jet.JetAPI;
import com.buffalokiwi.aerodrome.jet.JetAPIResponse;
import com.buffalokiwi.aerodrome.jet.JetConfig;
import com.buffalokiwi.aerodrome.jet.JetException;
import com.buffalokiwi.aerodrome.jet.Utils;
import com.buffalokiwi.api.IAPIHttpClient;
import java.util.HashMap;
import java.util.Map;
import javax.json.Json;
import javax.json.JsonObject;


/**
 * This should handle bulk product uploads and stuff.....
 * @author john Quinn
 */
public class JetAPIBulkProductUpload extends JetAPI implements IJetAPIBulkProductUpload
{
  public JetAPIBulkProductUpload( final IAPIHttpClient client, 
    final JetConfig config )
  {
    super( client, config );
  }
  
  
  /**
   * Retrieve a token for uploading some file.
   * @return api response
   * @throws APIException
   * @throws JetException 
   */
  @Override
  public IJetAPIResponse sendGetUploadToken()
    throws APIException, JetException
  {
    return get(
      config.getGetBulkUploadTokenUrl(),
      getJSONHeaderBuilder().build()
    );
  }
  
  
  /**
   * Retrieve an upload token for uploading a bulk feed of some sort.
   * @return auth token
   * @throws APIException
   * @throws JetException 
   */
  @Override
  public BulkUploadAuthRec getUploadToken()
    throws APIException, JetException
  {
    return BulkUploadAuthRec.fromJSON( sendGetUploadToken().getJsonObject());
  }
  
  
  /**
   * Once you receive the url to upload to from getUploadToken(), feed that 
   * into the url argument in this method along with the file to upload..
   * @param url Url from getUploadToken()
   * @return response
   * @throws APIException
   * @throws JetException 
   */
  @Override
  public IJetAPIResponse sendAuthorizedFile( final String url, final PostFile file )
    throws APIException, JetException
  {
    final Map<String,String> headers = new HashMap<>();
    headers.put( "x-ms-blob-type", "blockblob" );
    
    return put( url, file, headers );
  }
    
  
  /**
   * Get authorization to add an additional file to an existing uploadToken, 
   * AND/OR I'm pretty sure this is required to tell Jet what type of file
   * was uploaded, and to start the batch import on Jet itself.
   * The documentation on Jet is lacking, well documentation.
   * 
   * @param file File to send 
   * @param filename The basename of the local file sent to jet.
   * If this does not match the name sent via sendAuthorizedFile(), then 
   * jet will say "not found" in the partner portal and nothing will be found.
   * @param uploadType File type 
   * @return
   * @throws APIException
   * @throws JetException 
   */
  @Override
  public IJetAPIResponse sendPostUploadedFiles( final String uploadUrl, 
      final String filename, 
      BulkUploadFileType uploadType ) throws APIException, JetException
  {
    Utils.checkNullEmpty( filename, "filename" );
    Utils.checkNull( uploadType, "uploadType" );
    
    JsonObject o = Json.createObjectBuilder().add("url", uploadUrl)
      .add( "file_type", uploadType.getText())
      .add( "file_name", filename ).build();    
    
    return JetAPIResponse.createFromAPIResponse( post(
      config.getPostBulkUploadedFilesUrl(), o.toString(), getJSONHeaderBuilder().build()
    ));
  }
  
  
  /**
   * Query the status of an uploaded file..
   * @param fileId Jet File id 
   * @return The status 
   * @throws APIException
   * @throws JetException
   */
  @Override
  public IJetAPIResponse sendGetJetFileId( final String fileId )
    throws APIException, JetException 
  {
    return get(
      config.getGetBulkJetFileIdUrl( fileId ),
      getJSONHeaderBuilder().build()
    );
  }
  
  
  /**
   * Query the status of an uploaded file..
   * @param fileId Jet File id 
   * @return The status 
   * @throws APIException
   * @throws JetException
   */
  @Override
  public FileIdRec getJetFileId( final String fileId )
    throws APIException, JetException
  {
    return FileIdRec.fromJSON( sendGetJetFileId( fileId ).getJsonObject());
  }
  
    
}