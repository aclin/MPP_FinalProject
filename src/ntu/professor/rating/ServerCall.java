package ntu.professor.rating;

import org.json.JSONArray;
import org.json.JSONException;

public interface ServerCall {
	public static final int POST_READ = 0;
	public static final int POST_TOP5 = 1;
	public static final int POST_GOOD = 2;
	public static final int POST_BAD = 3;
	public static final int POST_ADD = 4;
	public static final int POST_SEARCH = 5;
	
	public void postData(int action, String url) throws JSONException;
	
	public void parseJSON(JSONArray ja);
}
