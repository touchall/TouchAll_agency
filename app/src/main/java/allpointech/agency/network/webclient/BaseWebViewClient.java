package allpointech.agency.network.webclient;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.tuna.utils.SLog;

public class BaseWebViewClient extends WebViewClient {
	
	@Override
	public boolean shouldOverrideUrlLoading(WebView view, String url) {
		SLog.LogD("" + url);
		return super.shouldOverrideUrlLoading(view, url);
	}

	@Override
	public void onPageStarted(WebView view, String url, Bitmap favicon) {
		super.onPageStarted(view, url, favicon);
	}
	
	@Override
	public void onPageFinished(WebView view, String url) {
		super.onPageFinished(view, url);
		SLog.LogD(""+url);
	}
	
	@Override
	public void onReceivedError(final WebView view, int errorCode, String description, String failingUrl) {
		super.onReceivedError(view, errorCode, description, failingUrl);
		
		showToast(view.getContext(), description);
	}

	@Override
	public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
		//super.onReceivedSslError(view, handler, error);
		handler.proceed(); // Ignore SSL certificate errors
	}

	private Toast mToast = null;
	private void showToast(Context context, String msg) {
		if(mToast == null){
			mToast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
		} else {
			mToast.setText(msg);
		}
		mToast.show();
	}
	
}