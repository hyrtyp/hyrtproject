package com.hyrt.foshanLaw;

import com.hyrt.foshanLaw.pptclient.PPTService;
import com.hyrt.foshanLaw.remoteService.StockQuoteService;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BootUpReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent arg1) {
		Intent i = new Intent(context, StockQuoteService.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startService(i); 
	}
}
