package com.Reporting;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryFlakyTests implements IRetryAnalyzer {
	
	int count=0;
	int maxTry=1;

	@Override
	public boolean retry(ITestResult result) {
		if(count<maxTry) {
			count++;
			return true;
			
		}
		// TODO Auto-generated method stub
		return false;
	}

}
