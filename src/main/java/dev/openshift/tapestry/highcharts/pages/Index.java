package dev.openshift.tapestry.highcharts.pages;

import org.apache.tapestry5.annotations.AfterRender;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;

public class Index {
	
	@Inject
	private JavaScriptSupport javascript;
	
	
	@AfterRender
	public void afterRender(){
		javascript.addInitializerCall("documentation", new JSONObject());
	}
}
