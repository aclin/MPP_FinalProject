package ntu.professor.rating;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import ntu.professor.rating.R;
import ntu.professor.rating.R.drawable;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class Induvidual extends Activity implements View.OnClickListener, ServerCall {
	private final static String TAG = "MPP_Individual";
	
	private String responseString;
	private String rateURL = "http://ee.hac.tw:2323/updaterating.php";
	private String bNameUTF8;
	
	//private String bImage;
	private Bitmap bImage;
	private String bName;
	private String bDepart;
	private SharedPreferences settings;
	private int cntVoted; 
	private int bFor;
	private int bAgainst;
	private float bRate;
	
	private Button btFor;
	private Button btAgainst;
	private ImageView ivImage;
	private TextView tvName;
	private RatingBar rtBar;


	private void findView() {
		ivImage = (ImageView) findViewById(R.id.imageView_ItemImage);
		tvName = (TextView) findViewById(R.id.textView_ItemName);
		btFor = (Button) findViewById(R.id.button_For);
		btAgainst = (Button) findViewById(R.id.button_Against);
		rtBar = (RatingBar) findViewById(R.id.ratingBar1);
	}
	
	private void setListeners() {
		btFor.setOnClickListener(this);
		btAgainst.setOnClickListener(this);
	}
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.induvisual);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.induvisual);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.my_title);
		TextView mTitleTextView = (TextView) findViewById(R.id.title_txt);
		Button mBackBtn = (Button) findViewById(R.id.back_btn);
		ImageView ivsearch = (ImageView) findViewById(R.id.imageView_Search);
		mTitleTextView.setText("Professor Rating");
		mBackBtn.setOnClickListener(this);
		ivsearch.setOnClickListener(this);
		
		findView();
		
		Bundle bData = this.getIntent().getExtras();
		bName = bData.getString("bName");
		Log.i(TAG, "bName: " + bName);
		try {
			bNameUTF8 = new String(bName.getBytes(), "ISO8859_1");
			Log.i(TAG, "bNameUTF8: " + bNameUTF8);
		} catch (UnsupportedEncodingException  e) {
			e.printStackTrace();
		}
		bDepart = bData.getString("bDepart");
		bFor = bData.getInt("bFor");
		bAgainst = bData.getInt("bAgainst");
		if ((bFor+bAgainst)>0)
			bRate=5*bFor/(bFor+bAgainst);
		else 
			bRate=0;
		bImage = (Bitmap) this.getIntent().getParcelableExtra("bImage");
		
		tvName.setText(bName + " 教授");
		ivImage.setImageBitmap(bImage);
		rtBar.setRating(bRate);
		
		// To avoid duplicate voting
		settings = getSharedPreferences("Preference", 0);
		cntVoted = settings.getInt("number", 0);
		int checkVoted = 0;
		String checkNameVoted="";
		
		for(int i=1; i<=cntVoted; ++i){
			checkNameVoted = settings.getString("name"+i, "");
			if(bName.compareTo(checkNameVoted) == 0){
				checkVoted = 1;
				btFor.setBackgroundColor(drawable.white);
				btFor.setTextColor(0x5B5B5B);
				btAgainst.setBackgroundColor(drawable.white);
				btAgainst.setTextColor(0x5B5B5B);
			}
			
		}
		if(checkVoted == 0)
			setListeners();
		
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		String myImgitem = null;
		Bundle bData = new Bundle();
		Intent i_return;
		switch (v.getId()) {
		case R.id.button_For:
			// To avoid duplicate voting
			cntVoted++;
			settings = getSharedPreferences("Preference", 0);
			settings.edit().putInt("number", cntVoted).commit();
			settings.edit().putString("name"+cntVoted, bName).commit();
			//myImgitem = "你推薦了" + cntVoted + "個教授";

			myImgitem = "你推薦了" + bName + "教授";
			int forValue = bFor;
			forValue++;
			bFor = forValue;
			new AsyncRateTask().execute(POST_GOOD);
			
			i_return = new Intent();
			bData.putInt("bFor", bFor);
			bData.putInt("bAgainst", bAgainst);
			bData.putString("bName", bName);
			i_return.putExtras(bData);
			setResult(RESULT_OK, i_return);
			finish();
			break;
		case R.id.button_Against:
			// To avoid duplicate voting
			cntVoted++;
			settings = getSharedPreferences("Preference", 0);
			settings.edit().putInt("number", cntVoted).commit();
			settings.edit().putString("name"+cntVoted, bName).commit();
			//myImgitem = "你不推薦" + cntVoted + "個教授";
			
			myImgitem = "你不推薦" + bName + "教授";
			int againstValue = bAgainst;
			againstValue++;
			bAgainst = againstValue;
			new AsyncRateTask().execute(POST_BAD);
			
			i_return = new Intent();
			bData.putInt("bFor", bFor);
			bData.putInt("bAgainst", bAgainst);
			bData.putString("bName", bName);
			i_return.putExtras(bData);
			setResult(RESULT_OK, i_return);
			finish();
			break;
		case R.id.imageView_Search:
			onSearchRequested();
			break;
		case R.id.back_btn:
			finish();
			break;
		default:
			break;
		}
		if (myImgitem!=null)
			Toast.makeText(Induvidual.this, myImgitem, Toast.LENGTH_LONG).show();
	}
	
	public void postData(int action, String url) throws JSONException {
    	// Create a new HttpClient and Post Header
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(url);
		JSONObject j = new JSONObject();
		
		try {
			switch (action) {
			case POST_GOOD:
				j.put("Command", "Good");
				j.put("Name", bNameUTF8);
				j.put("Dept", bDepart);
				break;
			case POST_BAD:
				j.put("Command", "Bad");
				j.put("Name", bNameUTF8);
				j.put("Dept", bDepart);
				break;
			}
			Log.i(TAG, "JSON Object: " + j.toString());
			JSONArray array = new JSONArray();
			array.put(j);
			
			// Post the data:
			httppost.setHeader("json", j.toString());
			httppost.getParams().setParameter("jsonpost", array);
			
			// Execute HTTP Post Request
			HttpResponse response = httpclient.execute(httppost);
			
			// for JSON:
			if(response != null)
			{
				InputStream is = response.getEntity().getContent();

				BufferedReader reader = new BufferedReader(new InputStreamReader(is));
				StringBuilder sb = new StringBuilder();

				String line = null;
				try {
					while ((line = reader.readLine()) != null) {
						sb.append(line + "\n");
					}
				} catch (IOException e) {
					Log.e(TAG, e.toString());
					e.printStackTrace();
				} finally {
					try {
						is.close();
					} catch (IOException e) {
						Log.e(TAG, e.toString());
						e.printStackTrace();
					}
				}
				responseString = sb.toString();
				Log.i(TAG, "Response: " + responseString);
				
			}
			/*JSONTokener tokener = new JSONTokener(responseString);
			Object o;
			while (tokener.more()) {
				o = tokener.nextValue();
				if (o instanceof JSONArray) {
					JSONArray result = (JSONArray) o;
					parseJSON(result);
				} else {
					//tv.setText("Cannot parse response from server; not a JSON Object");
				}
			}*/
			//tv.setText(reply);
			
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			Log.e(TAG, e.toString());
			e.printStackTrace();
		} catch (IOException e) {
    		// TODO Auto-generated catch block
			Log.e(TAG, e.toString());
			e.printStackTrace();
		}
    }
    
    public void parseJSON(JSONArray ja) {
    	/*queryLength = ja.length();
    	arrImage = new String[queryLength];
    	arrName = new String[queryLength];
    	arrDepart = new String[queryLength];
    	arrFor = new int[queryLength];
    	arrAgainst = new int[queryLength];
    	try {
    		for (int i=0; i < ja.length(); i++) {
    			arrImage[i] = ja.getJSONObject(i).getString("Picture");
		    	arrName[i] = ja.getJSONObject(i).getString("Name");
		    	arrDepart[i] = ja.getJSONObject(i).getString("Dept");
		    	arrFor[i] = ja.getJSONObject(i).getInt("Good");
		    	arrAgainst[i] = ja.getJSONObject(i).getInt("Bad");
    		}
    	} catch (JSONException je) {
    		je.printStackTrace();
    	}*/
    }
    
    private class AsyncRateTask extends AsyncTask<Integer, Void, Void> {
	
		@Override
		protected Void doInBackground(Integer... params) {
			try {
				postData(params[0].intValue(), rateURL);
			} catch (JSONException e) {
				Log.e(TAG, "Network Call Error:");
				e.printStackTrace();
			}
			
			return null;
		}
		
		@Override
		protected void onProgressUpdate(Void... unused) {
			
		}
		
		@Override
		protected void onPostExecute(Void unused) {
			
		}
	}
}
