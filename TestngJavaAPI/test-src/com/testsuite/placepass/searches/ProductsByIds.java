package com.testsuite.placepass.searches;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.parser.ParseException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.api.category.Search;
import com.base.BaseSetup;
import com.datamanager.ConfigManager;
import com.datamanager.JSONManager;
import com.testsuite.dataprovider.UnitTests_TestData_Provider;

public class ProductsByIds extends BaseSetup {
	
//	Declaration of respective API Parts instances
	public ConfigManager api;
	JSONManager jsonObject = new JSONManager();
	List<String> headers = new ArrayList<String>();
	public String current_TestName;
	Search search;
	
	/**
	 * Purpose - Initializes the API parts instances
	 */
	@BeforeMethod(alwaysRun = true)
	public void SetUp(Method method) {
		
		api = new ConfigManager("Api");
		current_TestName = method.getName();
		search = new Search();
	}
	
	/**
	 * @throws ParseException
	 * 
	 */
	@Test(dataProviderClass=UnitTests_TestData_Provider.class, dataProvider="TC001_Get_ProductsByIds")
	public void tc_API_001_getProductsByIdsUsingGetMethod(String endPoint, String partnerId,
			String expectedProductIds, String status, String message) throws ParseException{
		
		search.requestProductsByIds(current_TestName, endPoint, partnerId);
		
		//	Verify response code and message
		search.verifyStatusCode(status);
		search.verifyStatusMessage(message);
		
		search.verifyProductsByIds(endPoint, expectedProductIds, "products", "objectID");		
	}

}
