package com.pax.printersetting.password;

import android.text.TextUtils;
import android.util.Log;

import java.util.Calendar;
import java.util.regex.Pattern;

public class PaxPassWordUtils {
	private static final String TAG = PaxPassWordUtils.class.getSimpleName();
	private static final int PAXMINLEN = 7;
	private static final int MINLEN = 10;
	private static final int MAXLEN  = 20;
	
	public static final long LOCK_TIME = 180 * 1000;

	public static boolean PASS = false;
    public static boolean PASSWORD_EXIT = false;
    public static boolean MAINACTIVITY_EXIT = false;

    //storage password
    public static final String PREFERENCE_NAME = "settings_password";
	public static final String PASSWORD_NAME = "password";
	public static final String DISPLAY_PASSWORD = "display_password";
	private static final String DEFAULT_PASSWORD = "pax9876@@";
    public static final String MANAGE_APPLICATIONS_SETTINGS_ACTION = "android.settings.MANAGE_APPLICATIONS_SETTINGS";

    public static final String PASSWORD_RETURN_REQUEST_CODE = "finish_password";
    public static final String PASSWORD_START_REQUEST_CODE = "start_password";

    public static String getDefaultSettingsPassword() {
        return DEFAULT_PASSWORD;
    }

	public static boolean isValidPassWord(String str){
		boolean result = false;
		if(isValidPasWordLen(str) && ((isVaildAlpChar(str) && isVaildNumChar(str)) && isSpecialChar(str))){
			result = true;
		}		
		return result;
	}

    public static boolean isPaxValidPassWord(String str){
        boolean result = false;
        if(isPaxValidPasWordLen(str) && ((isPaxVaildAlpChar(str) && isPaxVaildNumChar(str)) && isPaxSpecialChar(str))){
            result = true;
        }
        return result;
    }
	

	public static boolean isValidPasWordLen(String str){
		return (!TextUtils.isEmpty(str)  && str.length() >= MINLEN  && str.length() <= MAXLEN);
	}


    public static boolean isPaxValidPasWordLen(String str){
        return (!TextUtils.isEmpty(str)  && str.length() >= PAXMINLEN);
    }


	public static boolean  isVaildAlpChar(String str){
		Pattern mPattern = Pattern.compile("^(?=.*[a-zA-Z].*[a-zA-Z].*[a-zA-Z].*[a-zA-Z].*[a-zA-Z].*[a-zA-Z]).*$");
		boolean result = mPattern.matcher(str).matches();
		Log.d(TAG,"isVaildAlpChar result ="+ result);
		return result;
	}

    public static boolean  isPaxVaildAlpChar(String str){
        Pattern mPattern = Pattern.compile("^(?=.*[a-zA-Z].*[a-zA-Z].*[a-zA-Z]).*$");
        boolean result = mPattern.matcher(str).matches();
        Log.d(TAG,"isPaxVaildAlpChar result ="+ result);
        return result;
    }


	public static boolean isVaildNumChar(String str){
		Pattern mPattern = Pattern.compile("^(?=.*[0-9].*[0-9]).*$");
		boolean result = mPattern.matcher(str).matches();
		Log.d(TAG,"isVaildNumChar  result ="+ result);
		return result;
	}

    public static boolean isPaxVaildNumChar(String str){
        Pattern mPattern = Pattern.compile("^(?=.*[0-9].*[0-9].*[0-9]).*$");
        boolean result = mPattern.matcher(str).matches();
        Log.d(TAG,"isPaxVaildNumChar  result ="+ result);
        return result;
    }


    public static boolean isSpecialChar(String str) {
        String regEx = "[ _`~!@#$%^&*()+[-]=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]|\n|\r|\t";
        Pattern mPattern = Pattern.compile("^(?=.*["+regEx+"].*["+regEx+"]).*$");
        boolean result = mPattern.matcher(str).matches();
        Log.d(TAG,"isSpecialChar result ="+ result);
        return result;
    }

    public static boolean isPaxSpecialChar(String str) {
        String regEx = "[ _`~!@#$%^&*()+[-]=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]|\n|\r|\t";
        Pattern mPattern = Pattern.compile("^(?=.*["+regEx+"]).*$");
        boolean result = mPattern.matcher(str).matches();
        Log.d(TAG,"isPaxSpecialChar result ="+ result);
        return result;
    }


    public static String getSuperPasswd(){
        String result = "";
        Calendar calendar= Calendar.getInstance();  //获取当前时间，作为图标的名字
        String year=calendar.get(Calendar.YEAR)+"";
        String month= String.format("%02d", calendar.get(Calendar.MONTH)+1);
        String day= String.format("%02d",calendar.get(Calendar.DAY_OF_MONTH));
        result = year + month + day;
        result = result + "9876";
        Log.e(TAG,"The passwd="+result);
        return result;
    }

}
