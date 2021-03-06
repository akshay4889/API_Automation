package com.testsuite.southwests.Booking;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.api.category.SouthWestBooking;
import com.base.BaseSetup;
import com.datamanager.ConfigManager;
import com.datamanager.ExcelManager;
import com.datamanager.JSONManager;
import com.testsuite.dataprovider.SouthwestUnitTests_TestData_Provider;

public class SouthWestCreateShoppingCart extends BaseSetup{
	
//	Declaration of respective API Parts instances
	ExcelManager excel_Manager;
	SouthWestBooking booking;
	public ConfigManager api;
	JSONManager jsonObject;
	
	public String currentTestName;
	List<String> headers = new ArrayList<String>();
	
	/**
	 * Purpose - Initializes the API parts instances
	 */
	@BeforeMethod(alwaysRun = true)
	public void SetUp(Method method) {
		
		api = new ConfigManager("Api");
		jsonObject = new JSONManager();
		booking = new SouthWestBooking();
		currentTestName = method.getName();
		
	}
	
	
	/**
	 * @throws ParseException 
	 * 
	 */
	@Test(dataProviderClass=SouthwestUnitTests_TestData_Provider.class, dataProvider="TC001_CreateShoppingCart")
	public void tc_API_001_CreateShoppingCartUsingPostMethod(String endPoint, String partnerId, String createCartJSON, String validateCartJSON, String expectedError) throws ParseException {
		
		HashMap<String, String> response = booking.requestCreateShoppingCart(currentTestName, endPoint, partnerId, createCartJSON);
		
		//	Verify response code and message
		booking.verifyStatusCode("200");
		booking.verifyStatusMessage("OK");
		
		String responseString = response.get("response body");
		
		JSONParser parser = new JSONParser();
		JSONObject object = (JSONObject) parser.parse(responseString);
		String cartId = (String) object.get("cartId");
		
		System.out.println("CartId is - "+cartId);
		
		//	Validate cart
		endPoint = "/bookings/carts/"+cartId+"/validate";
		
		response = booking.requestValidateShoppingCart(currentTestName, endPoint, partnerId, validateCartJSON);
		
		booking.verifyStatusCode("200");
		booking.verifyStatusMessage("OK");
		
		responseString = response.get("response body");
		
		List<ArrayList<String>> actualMessages = jsonObject.getListOfValuesFromSimpleJsonKey1(responseString, "validateMessages");
		Assert.assertTrue(actualMessages.get(0).get(0).contains(expectedError),actualMessages.get(0).get(0)+" is displayed instead of "+expectedError+". Cart ["+cartId+"] validation failed");
		System.out.println("Cart ["+cartId+"] successfully validated");
					
	}
	
	
	/**
	 * 1. Without partner-id header
	 * 2. Invalid partner-id header 
	 */
	@Test(dataProviderClass=SouthwestUnitTests_TestData_Provider.class, dataProvider="TC002_CreateShoppingCart")
	public void tc_API_002_CreateShoppingCartUsingPostMethod(String endPoint, String partnerId, String createCartJSON,
			String status, String message, String ppcode, String ppmessage){
		
		
		HashMap<String, String> response = booking.requestCreateShoppingCart(currentTestName, endPoint, partnerId, createCartJSON);
		
//		Verify response code and message
		booking.verifyStatusCode(status);
		booking.verifyStatusMessage(message);
		
		booking.verifyPPCodePPMessage(ppcode,ppmessage);					
	}
}
	