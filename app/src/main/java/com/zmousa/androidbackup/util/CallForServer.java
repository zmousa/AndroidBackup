package com.zmousa.androidbackup.util;


import android.content.Context;

import com.github.asifmujteba.easyvolley.ASFJsonObjectRequest;
import com.github.asifmujteba.easyvolley.ASFRequestListener;
import com.github.asifmujteba.easyvolley.EasyVolley;
import com.google.gson.JsonObject;

public class CallForServer {
	private String url;
	private OnServerResultNotifier onServerResultNotifier;
	private boolean isError;
	private String message;

	public CallForServer(String url, OnServerResultNotifier onServerResultNotifier,Context context)
	{
		this.url=url;
		this.onServerResultNotifier=onServerResultNotifier;
		EasyVolley.initialize(context);
	}

	public void callServer()
	{
		ASFJsonObjectRequest asfJsonObjectRequest=EasyVolley.withGlobalQueue().load(url).asJsonObject();
		asfJsonObjectRequest.noCache();
		asfJsonObjectRequest.setCallback(new ASFRequestListener<JsonObject>() {
					@Override
					public void onSuccess(JsonObject response) {
						onServerResultNotifier.onServerResult(false, "Receive Data Successfully", response);
					}

					@Override
					public void onFailure(Exception e) {
						onServerResultNotifier.onServerResult(true, e.getMessage(), null);
		}
	})
				.start();
	}

	public interface OnServerResultNotifier {
		public void onServerResult(boolean isError, String message, JsonObject jsonObj);
	}

}
