package com.jingdong.app.mall.utils;

import android.app.Application;
import android.content.pm.PackageInfo;
import android.os.Build;
import android.view.Display;
import android.view.WindowManager;
import com.hyrt.foshanLaw.b.NavitApplication;
import org.json.JSONException;
import org.json.JSONObject;

public class StatisticsReportUtil
{

  public static String getDeviceInfoStr()
  {
    JSONObject localJSONObject = new JSONObject();
    try
    {
      localJSONObject.put("brand", spilitSubString(Build.MANUFACTURER, 12));
      localJSONObject.put("model", spilitSubString(Build.MODEL, 12));
      Display localDisplay = ((WindowManager)NavitApplication.getInstance().getSystemService("window")).getDefaultDisplay();
      localJSONObject.put("screen", localDisplay.getHeight() + "*" + localDisplay.getWidth());
      localJSONObject.put("clientVersion", getSoftwareVersionName());
      localJSONObject.put("osVersion", Build.VERSION.RELEASE);
    }
    catch (JSONException localJSONException)
    {
        localJSONException.printStackTrace();
    }
    return localJSONObject.toString();
  }


  private static PackageInfo getPackageInfo()
  {
	  PackageInfo localPackageInfo2 = null;
    try
    {
      Application localMyApplication = NavitApplication.getInstance();
      localPackageInfo2 = localMyApplication.getPackageManager().getPackageInfo(localMyApplication.getPackageName(), 0);
      
    }
    catch (Exception localException)
    {
    	localException.printStackTrace();
    }
    return localPackageInfo2;
  }


  public static int getSoftwareVersionCode()
  {
    PackageInfo localPackageInfo = getPackageInfo();
    if(localPackageInfo == null)
    	return 0;
    return localPackageInfo.versionCode;
  }

  public static String getSoftwareVersionName()
  {
    PackageInfo localPackageInfo = getPackageInfo();
    if(localPackageInfo == null)
    	return "0";
    return localPackageInfo.versionName;
  }

  private static String spilitSubString(String paramString, int paramInt)
  {
    if ((paramString != null) && (paramString.length() > paramInt))
      paramString = paramString.substring(0, paramInt);
    return paramString;
  }
}