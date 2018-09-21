package com.testsuite.southwests.Booking;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;


import org.json.simple.parser.ParseException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import com.api.category.SouthWestBooking;
import com.base.BaseSetup;
import com.datamanager.ConfigManager;
import com.datamanager.ExcelManager;
import com.testsuite.dataprovider.SouthwestUnitTests_TestData_Provider;

public class SouthWestBookingHistory extends BaseSetup {
	
//	Declaration of respective API Parts instances
	ExcelManager excel_Manager;
	SouthWestBooking booking;
	public ConfigManager api;
	
	public String currentTestName;
	List<String> headers = new ArrayList<String>();
	
	/**
	 * Purpose - Initializes the API parts instances
	 */
	@BeforeMethod(alwaysRun = true)
	public void SetUp(Method method) {
		
		api = new ConfigManager("Api");
		booking = new SouthWestBooking();
		currentTestName = method.getName();
		
	}
	
	/**
	 * @throws ParseException 
	 *  
	 * 
	 */
	@Test(dataProviderClass=SouthwestUnitTests_TestData_Provider.class, dataProvider="TC001_ViewBookingsHistory")
	public void tc_API_001_ViewBookingsHistoryUsingGetMethod(String endPoint, String partnerId, String hitsPerPage, String customerId, String  pageNumber) throws ParseException {
		
		
		booking.requestViewBookingsHistory(currentTestName, endPoint, partnerId, hitsPerPage,customerId, pageNumber);
		
	//	Verify response code and message
		booking.verifyStatusCode("200");
		booking.verifyStatusMessage("OK");
		
	booking.verifyBookingsHistory(hitsPerPage,customerId,pageNumber);
		
	}	}
	