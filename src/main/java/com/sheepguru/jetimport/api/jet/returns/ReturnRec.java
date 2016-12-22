/**
 * This file is part of the JetImport package, and is subject to the 
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

package com.sheepguru.jetimport.api.jet.returns;

import com.sheepguru.jetimport.api.jet.AddressRec;
import com.sheepguru.jetimport.api.jet.IJetDate;
import com.sheepguru.jetimport.api.jet.JetDate;
import com.sheepguru.jetimport.api.jet.Jsonable;
import com.sheepguru.jetimport.api.jet.JsonableList;
import com.sheepguru.jetimport.api.jet.ShippingCarrier;
import com.sheepguru.jetimport.api.jet.Utils;
import com.sheepguru.utils.Money;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;


/**
 * Represents a return detail response from jet.
 * 
 * @author John Quinn
 */
public class ReturnRec implements Jsonable
{  
  /**
   * This field set by order refund message. Specifies if the merchant 
   * agrees to the return charge for the return notification.
   */
  private final boolean agreeToReturnCharge;
  
  /**
   * Optional merchant supplied order ID that should have been established in 
   * OrderAcknowledgement in order to use in this message.
   */
  private final String altOrderId;
  
  /**
   * This field is set by return complete message. Optional merchant supplied 
   * return number, allows the merchant to use this number in future messages 
   * instead of return_authorization_id. This ID will be included on the Return 
   * Documentation that Jet gives to the customer.
   */
  private final String altReturnAuthId;
  
  /**
   * Jet's unique ID for a given merchant order
   */
  private final String merchantOrderId;
  
  /**
   * Jet generated return authorization ID that is used in URL
   */
  private final String merchantReturnAuthId;
  
  /**
   * The charge that the merchant is required to pay Jet for the return.
   */
  private final Money merchantReturnCharge;
  
  /**
   * Jet's human readable order ID number that may have a small chance of 
   * collision overtime
   */
  private final String referenceOrderId;
  
  /**
   * Jet's human readable return authorization number that may have a small 
   * chance of collision overtime
   */
  private final String referenceReturnAuthId;
  
  /**
   * If this is true, Jet has deemed that this item should not be returned, 
   * but the customer still should be refunded.
   */
  private final boolean refundWithoutReturn;
  
  /**
   * The date that the customer requested a return authorization.
   */
  private final IJetDate returnDate;
  
  /**
   * Current status of the return.
   */
  private final ReturnStatus status;
  
  /**
   * Shipping method used for the given return shipment.
   */
  private final ShippingCarrier carrier;
  
  /**
   * Tracking number for the given return shipment
   */
  private final String trackingNumber;
  
  /**
   * Returns locations list
   */
  private final List<AddressRec> returnLocations;
  
  /**
   * merchant skus 
   */
  private final List<ReturnMerchantSkuRec> returnMerchantSkus;
  
  /**
   * This is included if status is inprogress or completed.
   */
  private final List<ReturnItemRec> items;

  
  /**
   * ReturnRec object builder 
   */
  public static class Builder
  {
    private boolean agreeToReturnCharge;
    private String altOrderId;
    private String altReturnAuthId;
    private String merchantOrderId;
    private String merchantReturnAuthId;
    private Money merchantReturnCharge;
    private String referenceOrderId;
    private String referenceReturnAuthId;
    private boolean refundWithoutReturn;
    private IJetDate returnDate;
    private ReturnStatus status;
    private ShippingCarrier carrier;
    private String trackingNumber;
    private List<AddressRec> returnLocations;
    private List<ReturnMerchantSkuRec> returnMerchantSkus;    
    private List<ReturnItemRec> items;

    
    /**
     * This field set by order refund message. Specifies if the merchant agrees 
     * to the return charge for the return notification.
     * Possible Values:
     * 'false' - The merchant disagrees with the return charge and will enter a 
     * disputed charge with Jet.com
     * 'true' - The merchant agrees to wholly pay the return charge to Jet.com 
     * from the return notification
     * @param agreeToReturnCharge the agreeToReturnCharge to set
     * @return this
     */
    public Builder setAgreeToReturnCharge( final boolean agreeToReturnCharge )
    {
      this.agreeToReturnCharge = agreeToReturnCharge;
      return this;
    }

    /**
     * Optional merchant supplied order ID that should have been established 
     * in OrderAcknowledgement in order to use in this message.
     * @param altOrderId the altOrderId to set
     * @return this
     */
    public Builder setAltOrderId( final String altOrderId )
    {
      Utils.checkNull( altOrderId, "altOrderId" );
      this.altOrderId = altOrderId;
      return this;
    }

    /**
     * This field is set by return complete message. Optional merchant supplied 
     * return number, allows the merchant to use this number in future messages 
     * instead of return_authorization_id. This ID will be included on the 
     * Return Documentation that Jet gives to the customer.
     * @param altReturnAuthId the altReturnAuthId to set
     * @return this
     */
    public Builder setAltReturnAuthId( final String altReturnAuthId )
    {
      Utils.checkNull( altReturnAuthId, "altReturnAuthId" );
      this.altReturnAuthId = altReturnAuthId;
      return this;
    }

    /**
     * Jet's unique ID for a given merchant order
     * @param merchantOrderId the merchantOrderId to set
     * @return this
     */
    public Builder setMerchantOrderId( final String merchantOrderId )
    {
      Utils.checkNull( merchantOrderId, "merchantOrderId" );
      this.merchantOrderId = merchantOrderId;
      return this;
    }

    /**
     * Jet generated return authorization ID that is used in URL
     * @param merchantReturnAuthId the merchantReturnAuthId to set
     * @return this
     */
    public Builder setMerchantReturnAuthId( final String merchantReturnAuthId )
    {
      Utils.checkNull( merchantReturnAuthId, "merchantReturnAuthId" );
      this.merchantReturnAuthId = merchantReturnAuthId;
      return this;
    }

    /**
     * The charge that the merchant is required to pay Jet for the return.
     * @param merchantReturnCharge the merchantReturnCharge to set
     * @return this
     */
    public Builder setMerchantReturnCharge( final Money merchantReturnCharge )
    {
      Utils.checkNull( merchantReturnCharge, "merchantReturnCharge" );
      this.merchantReturnCharge = merchantReturnCharge;
      return this;
    }

    /**
     * Jet's human readable order ID number that may have a small chance of 
     * collision overtime
     * @param referenceOrderId the referenceOrderId to set
     * @return this
     */
    public Builder setReferenceOrderId( final String referenceOrderId )
    {
      Utils.checkNull( referenceOrderId, "referenceOrderId" );
      this.referenceOrderId = referenceOrderId;
      return this;
    }

    /**
     * Jet's human readable return authorization number that may have a small 
     * chance of collision overtime

     * @param referenceReturnAuthId the referenceReturnAuthId to set
     * @return this
     */
    public Builder setReferenceReturnAuthId( 
      final String referenceReturnAuthId )
    {
      Utils.checkNull( referenceReturnAuthId, "referenceReturnAuthId" );
      this.referenceReturnAuthId = referenceReturnAuthId;
      return this;
    }

    /**
     * If this is true, Jet has deemed that this item should not be returned,
     * but the customer still should be refunded.
     * @param refundWithoutReturn the refundWithoutReturn to set
     * @return this
     */
    public Builder setRefundWithoutReturn( final boolean refundWithoutReturn )
    {
      this.refundWithoutReturn = refundWithoutReturn;
      return this;
    }

    /**
     * The date that the customer requested a return authorization.
     * @param returnDate the returnDate to set
     * @return this
     */
    public Builder setReturnDate( final IJetDate returnDate )
    {
      Utils.checkNull( returnDate, "returnDate" );
      this.returnDate = returnDate;
      return this;
    }

    /**
     * Current status of the return.
     * @param status the status to set
     * @return this
     */
    public Builder setStatus( final ReturnStatus status )
    {
      Utils.checkNull( status, "status" );
      this.status = status;
      return this;
    }

    /**
     * Shipping method used for the given return shipment.
     * @param carrier the carrier to set
     * @return this
     */
    public Builder setCarrier( final ShippingCarrier carrier )
    {
      Utils.checkNull( carrier, "carrier" );
      this.carrier = carrier;
      return this;
    }

    /**
     * Tracking number for the given return shipment
     * @param trackingNumber the trackingNumber to set
     * @return this
     */
    public Builder setTrackingNumber( final String trackingNumber )
    {
      Utils.checkNull( trackingNumber, "trackingNumber" );
      this.trackingNumber = trackingNumber;
      return this;
    }

    /**
     * Set returns locations
     * @param returnLocations the returnLocations to set
     * @return this
     */
    public Builder setReturnLocations( final List<AddressRec> returnLocations )
    {
      if ( returnLocations == null )
        this.returnLocations.clear();
      else      
        this.returnLocations.addAll( returnLocations );
      
      return this;
    }

    /**
     * Set merchant skus 
     * @param returnMerchantSkus the returnMerchantSkus to set
     * @return this
     */
    public Builder setReturnMerchantSkus( 
      final List<ReturnMerchantSkuRec> returnMerchantSkus )
    {
      if ( returnMerchantSkus == null )
        this.returnMerchantSkus.clear();
      else
        this.returnMerchantSkus.addAll( returnMerchantSkus );
      
      return this;
    }
    
    
    /**
     * Add some returned items to the list.
     * @param items items
     * @return this 
     */
    public Builder setReturnItems( final List<ReturnItemRec> items )
    {
      if ( items == null )
        this.items.clear();
      else
        this.items.addAll( items );
      
      return this;
    }
    
    
    /**
     * Build a new ReturnRec instance 
     * @return instance 
     */
    public ReturnRec build()
    {
      return new ReturnRec( this );
    }
  } //..Builder

  
  /**
   * Turn jet json into an instance of this
   * @param json jet json
   * @return instance 
   */
  public static ReturnRec fromJson( final JsonObject json )
  {
    return new Builder()     
      .setAgreeToReturnCharge( json.getBoolean( "agree_to_return_charge", false ))
      .setAltOrderId( json.getString( "alt_order_id", "" ))
      .setAltReturnAuthId( json.getString( "alt_return_authorization_id", "" ))
      .setMerchantOrderId( json.getString( "merchant_order_id", "" ))
      .setMerchantReturnAuthId( json.getString( "merchant_return_authorization_id", "" ))
      .setMerchantReturnCharge( new Money( json.getString( "merchant_return_charge", "0" )))
      .setReferenceOrderId( json.getString( "reference_order_id", "" ))
      .setReferenceReturnAuthId( json.getString( "reference_return_authorization_id", "" ))
      .setRefundWithoutReturn( json.getBoolean( "refund_without_return", false ))
      .setReturnDate( new JetDate( json.getString( "return_date", "" )))
      .setStatus( ReturnStatus.fromText( json.getString( "return_status", "" )))
      .setCarrier( ShippingCarrier.fromText( json.getString( "shipping_carrier", "" )))
      .setTrackingNumber( json.getString( "tracking_number", "" ))
      .setReturnLocations( AddressRec.fromJsonArray( json.getJsonArray( "return_location" )))
      .setReturnMerchantSkus( ReturnMerchantSkuRec.fromJsonArray( json.getJsonArray( "return_merchant_skus" )))
      .setReturnItems( ReturnItemRec.fromJsonArray( json.getJsonArray( "items" )))
      .build();
  }
  
  
  /**
   * Constructor
   * @param b builder instance 
   */
  private ReturnRec( final Builder b )
  {
    this.agreeToReturnCharge = b.agreeToReturnCharge;
    this.altOrderId = b.altOrderId;
    this.altReturnAuthId = b.altReturnAuthId;
    this.merchantOrderId = b.merchantOrderId;
    this.merchantReturnAuthId = b.merchantReturnAuthId;
    this.merchantReturnCharge = b.merchantReturnCharge;
    this.referenceOrderId = b.referenceOrderId;
    this.referenceReturnAuthId = b.referenceReturnAuthId;
    this.refundWithoutReturn = b.refundWithoutReturn;
    this.returnDate = b.returnDate;
    this.status = b.status;
    this.carrier = b.carrier;
    this.trackingNumber = b.trackingNumber;
    this.returnLocations = Collections.unmodifiableList( b.returnLocations );
    this.returnMerchantSkus = Collections.unmodifiableList( b.returnMerchantSkus );
    this.items = Collections.unmodifiableList( b.items );
  }
  
  
  
  /**
   * This field set by order refund message. Specifies if the merchant agrees 
   * to the return charge for the return notification.
   * Possible Values:
   * 'false' - The merchant disagrees with the return charge and will enter a 
   * disputed charge with Jet.com
   * 'true' - The merchant agrees to wholly pay the return charge to Jet.com 
   * from the return notification
   * @return the agreeToReturnCharge
   */
  public boolean isAgreeToReturnCharge() 
  {
    return agreeToReturnCharge;
  }

  
  /**
   * Optional merchant supplied order ID that should have been established in 
   * OrderAcknowledgement in order to use in this message.
   * @return the altOrderId
   */
  public String getAltOrderId() 
  {
    return altOrderId;
  }

  
  /**
   * This field is set by return complete message. Optional merchant supplied 
   * return number, allows the merchant to use this number in future messages 
   * instead of return_authorization_id. This ID will be included on the Return 
   * Documentation that Jet gives to the customer.
   * @return the altReturnAuthId
   */
  public String getAltReturnAuthId() 
  {
    return altReturnAuthId;
  }

  
  /**
   * Jet's unique ID for a given merchant order
   * @return the merchantOrderId
   */
  public String getMerchantOrderId() 
  {
    return merchantOrderId;
  }

  
  /**
   * Jet generated return authorization ID that is used in URL
   * @return the merchantReturnAuthId
   */
  public String getMerchantReturnAuthId() 
  {
    return merchantReturnAuthId;
  }

  
  /**
   * The charge that the merchant is required to pay Jet for the return.
   * @return the merchantReturnCharge
   */
  public Money getMerchantReturnCharge() 
  {
    return merchantReturnCharge;
  }

  
  /**
   * Jet's human readable order ID number that may have a small chance of 
   * collision overtime
   * @return the referenceOrderId
   */
  public String getReferenceOrderId() 
  {
    return referenceOrderId;
  }

  
  /**
   * Jet's human readable return authorization number that may have a small 
   * chance of collision overtime
   * @return the referenceReturnAuthId
   */
  public String getReferenceReturnAuthId() 
  {
    return referenceReturnAuthId;
  }

  
  /**
   * If this is true, Jet has deemed that this item should not be returned, 
   * but the customer still should be refunded
   * @return the refundWithoutReturn
   */
  public boolean isRefundWithoutReturn() 
  {
    return refundWithoutReturn;
  }

  
  /**
   * The date that the customer requested a return authorization.
   * @return the returnDate
   */
  public IJetDate getReturnDate() 
  {
    return returnDate;
  }

  
  /**
   * Current status of the return.
   * @return the status
   */
  public ReturnStatus getStatus() 
  {
    return status;
  }

  
  /**
   * Shipping method used for the given return shipment.
   * @return the carrier
   */
  public ShippingCarrier getCarrier() 
  {
    return carrier;
  }

  
  /**
   * Tracking number for the given return shipment
   * @return the trackingNumber
   */
  public String getTrackingNumber() 
  {
    return trackingNumber;
  }

  
  /**
   * 
   * @return the returnLocations
   */
  public List<AddressRec> getReturnLocations() 
  {
    return returnLocations;
  }

  
  /**
   * 
   * @return the returnMerchantSkus
   */
  public List<ReturnMerchantSkuRec> getReturnMerchantSkus() 
  {
    return returnMerchantSkus;
  }
  
  
  /**
   * Access the items returned if in inprogress or complete state.
   * @return items
   */
  public List<ReturnItemRec> getReturnItems()
  {
    return items;
  }
  
  
  /**
   * Convert this object into JSON 
   * @return json object
   */
  @Override 
  public JsonObject toJSON()
  {
    return Json.createObjectBuilder()
      .add( "agree_to_return_charge", agreeToReturnCharge )
      .add( "alt_order_id", altOrderId )
      .add( "alt_return_authorization_id", altReturnAuthId )
      .add( "merchant_order_id", merchantOrderId )
      .add( "merchant_return_authorization_id", merchantReturnAuthId )
      .add( "merchant_return_charge", merchantReturnCharge.asBigDecimal())
      .add( "reference_order_id", referenceOrderId )
      .add( "reference_return_authorization_id", referenceReturnAuthId )
      .add( "refund_without_return", refundWithoutReturn )
      .add( "return_date", returnDate.getDateString())
      .add( "return_status", status.getText())
      .add( "shipping_carrier", carrier.getText())
      .add( "tracking_number", trackingNumber )
      .add( "return_location", Utils.jsonableToArray( returnLocations ))
      .add( "return_merchant_skus", Utils.jsonableToArray( returnMerchantSkus ))
      .add( "items", Utils.jsonableToArray( items ))
      .build();
  }
}
