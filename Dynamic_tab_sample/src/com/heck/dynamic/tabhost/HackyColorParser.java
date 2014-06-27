/**
 * This HackyColorParser is an helper class to parse color 
 * of Android Color.parseColor(String str).
 * 
 * Throws internal exception: If argument string's first six(6) 
 * substring contains character/s other than alphanumeric character.
 * ColorStringFormatException example: 66??66, 999{{
 * 
 *
 * @Author : Mohammad Hasan Khan
 * @Milan, Italy
 * @Copyright: 2013
 * **/

package com.heck.dynamic.tabhost;

import org.apache.commons.lang3.StringUtils;

import android.graphics.Color;
import android.text.TextUtils;
import android.util.Log;

public final class HackyColorParser {

	
	private final static int defColor = Color.BLACK;
	
	private final static String defColorString = "000000";
	
	private final static String HASH = "#";
	
	private final static String EMPTY = "";
	
	private final static String WHITE_SPACE = " ";

	/**
	 * @param: Alphanumeric string (containing valid characters)
	 * @supported formats are(uppercase/lowercase and with/without HASH character): 
	 * @examples: #RRGGBB, #RGB, #RR, #R, #RRGG, #RRGGB, #RRGGBBHHHHHH, 
	 *           RRGGBB, RGB, RR, R, RRGG, RRGGB, RRGGBBHHHHHH, RRGGBBHXX
	 * @specific examples: #666666, #6, #66, #666, #6666, #cccccc, #fafafafafafa
	 *                    6, 88, 333, 6633, 666ee, 6688ff, cccc00, ccccc11, fafafafafa22,
	 *                    88886666;;;;;;;, 888866;;>, 
	 * **/
	// Modify
	public static int parseColor(String color) {
		Log.v("HackyColorParser", "COLOR STRING: " + color);
		if (!isEmpty(color)) {
			try {
				if (!isFirt6CharAlphaNumeric(color)) {
					throw new Exception("Color string is not valid");
				}
				return Color.parseColor(HASH.concat(regularizeColorString(color)));
			} catch (NumberFormatException e) {
				e.printStackTrace();
				return defColor;
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
				return defColor;
			} catch (Exception e) {
				e.printStackTrace();
				return defColor;
			}
		}
		return defColor;
	}
	
	// ADD
	private static boolean isEmpty(String color) {
		if (TextUtils.isEmpty(color)) {
			return true;
		}
		if(TextUtils.isEmpty(color.trim())) {
			return true;
		}
		if (color.equalsIgnoreCase("null") || color.equalsIgnoreCase("#null")) {
			return true;
		}
		return false;
	}
	
	private static String regularizeColorString(String color) {
		if (!TextUtils.isEmpty(color)) {
			try {
				if(StringUtils.containsWhitespace(color)){
					trimAllWhiteSpaces(color);
				}
				if(color.contains(HASH)){
					color = removeHashFromString(color);
				}
				int len = color.length();
				if(len == 6){
					return color;
				}
				if(len > 6){
					return color.substring(0, 6);
				}
				if(len < 6){
					if(color.length() == 1){
						return regularizeFromOneChar(color);
					}
					if(len == 2){
						return fillR2RGB(color);
					}
					if(len == 3){
						return padOtherHalfColorString(color);
					}
					if(len == 4){
						return regularizeFromFourChar(color);
					}
					if(len == 5){
						return regularizeFromFiveChar(color);
					}
				}
			} catch(Exception e) {
				e.printStackTrace();
				return defColorString;
			}
		}
		return defColorString;
	}
	
	private static String padOtherHalfColorString(String halfString) {
		String fullString = EMPTY;
		halfString = halfString.trim();
		fullString = halfString + halfString;
		return fullString;
	}
	
	private static String fillR2RGB(String R) {
		String RGB = EMPTY;
		R = R.trim();
		RGB = R + R + R;
		return RGB;
	}
	
	private static String regularizeFromOneChar(String l) {
		String RGB = EMPTY;
		l = l.trim();
		RGB = l + l + l;
		RGB = RGB + RGB;
		return RGB;
	}
	
	private static String regularizeFromFiveChar(String l) {
		String RGB = EMPTY;
		l = l.trim();
		l = RGB + l.charAt(0);
		return regularizeFromOneChar(l);
	}
	
	private static String regularizeFromFourChar(String l) {
		String RGB = EMPTY;
		l = l.trim();
		l = RGB + l.charAt(0);
		return regularizeFromOneChar(l);
	}
	
	protected static boolean isFirt6CharAlphaNumeric(String string) {
		boolean isOnlyAlphanumeric = false;
		string = removeHashFromString(string);
		if(string.length() > 6) {
			string = string.substring(0, 6);
		}
		if (StringUtils.isAlphanumeric(string)) {
			isOnlyAlphanumeric = true;
		}
		return isOnlyAlphanumeric;
	}
	
	protected static String trimAllWhiteSpaces(String color){
		color = color.trim();
	    color = color.replaceAll(WHITE_SPACE, EMPTY);
	    return StringUtils.deleteWhitespace(color);
	}
	
	protected static boolean containsInnerWhiteSpaces(String color){
		color = color.trim();
		return StringUtils.containsWhitespace(color);
	}
	
	protected static String removeHashFromString(String color) {
		if(!TextUtils.isEmpty(color) && color.contains(HASH)) {
			color = color.replaceAll(HASH, EMPTY);
			Log.v("HackyColorParser", "COLOR STRING WITHOUT HASH: " + color);
			return color;
		}
		return defColorString;
	}

}
