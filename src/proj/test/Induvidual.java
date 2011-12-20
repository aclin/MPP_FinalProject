package proj.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Induvidual extends Activity implements View.OnClickListener, ServerCall {
	private final static String TAG = "MPP_Individual";
	
	private String responseString;
	private String rateURL = "http://ee.hac.tw:2323/updaterating.php";
	private String bNameUTF8;
	
	//private String bImage;
	private String bName;
	private String bDepart;
	private String bFor;
	private String bAgainst;
	private String bDept;
	private Bitmap bImage;
	
	private Button btFor;
	private Button btAgainst;
	private ImageView ivImage;
	private TextView tvName;

	private void findView() {
		ivImage = (ImageView) findViewById(R.id.imageView_ItemImage);
		tvName = (TextView) findViewById(R.id.textView_ItemName);
		btFor = (Button) findViewById(R.id.button_For);
		btAgainst = (Button) findViewById(R.id.button_Against);
		
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
		setContentView(R.layout.induvisual);
		findView();
		setListeners();
		Bundle bData = this.getIntent().getExtras();
		//bImage = bData.getString("bImage");
		bName = bData.getString("bName");
		Log.i(TAG, "bName: " + bName);
		try {
			bNameUTF8 = new String(bName.getBytes(), "ISO8859_1");
			Log.i(TAG, "bNameUTF8: " + bNameUTF8);
		} catch (UnsupportedEncodingException  e) {
			e.printStackTrace();
		}
		bDepart = bData.getString("bDepart");
		bFor = bData.getString("bFor");
		bAgainst = bData.getString("bAgainst");
		bDept = bData.getString("dept");
		bImage = (Bitmap) this.getIntent().getParcelableExtra("bImage");
		//String strImg = "R.drawable." + bImage;
		// ivImage.setImageResource(Integer.parseInt(strImg));
		tvName.setText(bName + " �б�");
		ivImage.setImageBitmap(bImage);
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		String myImgitem = null;
		Bundle bData = new Bundle();
		switch (v.getId()) {
		case R.id.button_For:
			myImgitem = "Good!!!!";
			int forValue = Integer.parseInt(bFor);
			forValue++;
			bFor = forValue + "";
			try {
				postData(POST_GOOD, rateURL);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			Intent newAct1 = new Intent();
			newAct1.setClass(Induvidual.this, ListProfessor.class);
			// �إ� Bundle ����
			bData.putString("dept", "cs");
			newAct1.putExtras(bData);
			startActivity(newAct1); // Don't start new activity here;
			// finish() and let previous activity handle aftermath
			break;
		case R.id.button_Against:
			myImgitem = "Uhhhhhh";
			int againstValue = Integer.parseInt(bAgainst);
			againstValue++;
			bAgainst = againstValue + "";
			try {
				postData(POST_BAD, rateURL);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			Intent newAct2 = new Intent();
			newAct2.setClass(Induvidual.this, ListProfessor.class);
			// �إ� Bundle ����
			bData.putString("dept", "cs");
			newAct2.putExtras(bData);
			startActivity(newAct2);
			break;
		default:
			break;
		}
		Toast.makeText(Induvidual.this, myImgitem, Toast.LENGTH_SHORT).show();
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
				break;
			case POST_BAD:
				j.put("Command", "Bad");
				j.put("Name", bNameUTF8);
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
}
