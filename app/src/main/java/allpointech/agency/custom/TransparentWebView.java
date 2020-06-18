package allpointech.agency.custom;


import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class TransparentWebView extends WebView {

	public TransparentWebView(Context context) {
		super(context);
	}
	public TransparentWebView(Context context, AttributeSet attrs,
							  int defStyle, boolean privateBrowsing) {
		super(context, attrs, defStyle, privateBrowsing);
	}

	public TransparentWebView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public TransparentWebView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	public void loadfilePage(String filename){
		setWebViewClient(new WebViewClient());
		setBackgroundColor(0x01000000);
		setDrawingCacheEnabled(true);
		setDrawingCacheBackgroundColor(0x01000000);
		setAlwaysDrawnWithCacheEnabled(true);
//		setBackgroundResource(R.drawable.patten_delivery_bg);
		loadDataWithBaseURL(
				 null,
				"<html>" + 
			    "<head>" +
 				"<style type=\"text/css\">" +
		        "body {" +
		        "margin-left:0px;" +
		        "margin-top: 0px;" +
		        "margin-right:0px;"+
		        "margin-bottom: 0px;"+
		        "}"+
		        "</style>"+
			    "</head>" +
			    "<body padding=\"0px\">" +
			    "<img src=\"" + filename  +  "\" width=\"100%\" >" +
			    "</body bgcolor=\"#00000000\">" + 
				"</html>"
				, "text/html", "utf-8",null);
	}
}
