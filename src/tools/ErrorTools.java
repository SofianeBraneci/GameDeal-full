package tools;


import org.json.JSONException;
import org.json.JSONObject;

public class ErrorTools {

	public static JSONObject serviceRefused(String message){
		try {
			return (new JSONObject()).put("KO",message);
		}catch (JSONException e) {
			e.fillInStackTrace();
			return null;
		}
	}

	public static JSONObject serviceAccepted(JSONObject o) throws JSONException {
		try {
			return (new JSONObject()).put("OK", o);
		}catch (JSONException e) {
			e.fillInStackTrace();
			return null;
		}
		
	}
}