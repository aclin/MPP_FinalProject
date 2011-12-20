package proj.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleAdapter.ViewBinder;

public class ListProfessor extends Activity implements ServerCall {
	private static final String TAG = "ListProfessor";
	
	private final String listURL = "http://ee.hac.tw:2323/insertdb.php";
	private String responseString;
	private int queryLength;
	
	ListView list;
	
	private String[] arrImage;
	private String[] arrName;
	private String[] arrDepart;
	private int[] arrFor;
	private int[] arrAgainst;
	

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_professors);
		
		Bundle bData = this.getIntent().getExtras();
		String depts = bData.getString("dept");
		
		if (depts.compareTo("cs") == 0) {
			list = (ListView) findViewById(R.id.listView_professor);
			
			new NetworkTask().execute(ServerCall.POST_READ);
		}
	}
	
	private void populateList() {
		// 嚙談佗蕭嚙褊態嚙罷嚙瘠嚙璀嚙稼嚙皚嚙踝蕭嚙�
		final ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();
		// 嚙談佗蕭Adapter嚙踝蕭Item嚙瞎嚙褊態嚙罷嚙瘠嚙踝蕭嚙踝蕭嚙踝蕭嚙踝蕭嚙踝蕭
		final SimpleAdapter listItemAdapter = new SimpleAdapter(this,
				listItem, R.layout.listview_professor, new String[] {
						"ItemImage", "ItemName", "ItemDepart", "ItemFor",
						"ItemAgainst" }, new int[] {
						R.id.imageView_ItemImage, R.id.textView_ItemName,
						R.id.textView_ItemDepart, R.id.textView_ItemFor,
						R.id.textView_ItemAgainst });
		
		for (int i = 0; i < queryLength; i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("ItemImage", getBitmap(i));
			map.put("ItemName", arrName[i]);
			map.put("ItemDepart", arrDepart[i]);
			map.put("ItemFor", arrFor[i]);
			map.put("ItemAgainst", arrAgainst[i]);
			listItem.add(map);
		}
		
		listItemAdapter.setViewBinder(new ViewBinder() {    
            
            public boolean setViewValue(View view, Object data,    
                    String textRepresentation) {    
                if (view instanceof ImageView  && data instanceof Bitmap) {    
                    ImageView iv = (ImageView) view;
                    iv.setImageBitmap((Bitmap) data);    
                    return true;    
                } else {
                	return false;
                }
            }    
        });
		list.setAdapter(listItemAdapter);
		//listItemAdapter.notifyDataSetChanged();

		list.setOnItemClickListener(new OnItemClickListener() {
			//String bImage = "";
			String bName = "";
			String bDepart = "";
			String bFor = "";
			String bAgainst = "";
			Bitmap bImage;

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, final long id) {
				// TODO Auto-generated method stub
				final int peopleToBeShown = position;
				// Toast.makeText(ListProfessor.this, "No. " +
				// peopleToBeShown,
				// Toast.LENGTH_SHORT).show();
				HashMap<String, Object> itemAtPosition = (HashMap<String, Object>) list
						.getItemAtPosition(peopleToBeShown);
				//bImage = itemAtPosition.get("ItemImage").toString();
				// Toast.makeText(ListProfessor.this, "No. " + bImage,
				// Toast.LENGTH_SHORT).show();
				bName = itemAtPosition.get("ItemName").toString();
				setTitle("嚙踝蕭嚙瘤" + bName);
				bDepart = itemAtPosition.get("ItemDepart").toString();
				bFor = itemAtPosition.get("ItemFor").toString();
				bAgainst = itemAtPosition.get("ItemAgainst").toString();
				bImage = (Bitmap) itemAtPosition.get("ItemImage");
				Intent newAct = new Intent();
				newAct.setClass(ListProfessor.this, Induvidual.class);
				// 嚙諍伐蕭 Bundle 嚙踝蕭嚙踝蕭
				Bundle bData = new Bundle();

				// 嚙篇嚙皚嚙踝蕭嚙�Bundle 嚙踝蕭
				//bData.putString("bImage", bImage);
				bData.putString("bName", bName);
				bData.putString("bDepart", bDepart);
				bData.putString("bFor", bFor);
				bData.putString("bAgainst", bAgainst);
				bData.putString("dept", "cs");
				newAct.putExtras(bData);
				newAct.putExtra("bImage", bImage);
				startActivity(newAct); // Need to change this to startActivityForResult()
			}
		});
	}
	
	public void postData(int action, String url) throws JSONException {
    	// Create a new HttpClient and Post Header
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(url);
		JSONObject j = new JSONObject();
		
		try {
			switch (action) {
			case POST_READ:
				j.put("Command", "Read");
				break;
			}
			
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

				BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF8"));
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
			JSONTokener tokener = new JSONTokener(responseString);
			Object o;
			while (tokener.more()) {
				o = tokener.nextValue();
				if (o instanceof JSONArray) {
					JSONArray result = (JSONArray) o;
					parseJSON(result);
				} else {
					//tv.setText("Cannot parse response from server; not a JSON Object");
				}
			}
			//tv.setText(reply);
			
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			Log.e(TAG, "Client Protocol Exception:");
			e.printStackTrace();
		} catch (IOException e) {
    		// TODO Auto-generated catch block
			Log.e(TAG, "IO Exception:");
			e.printStackTrace();
		}
    }
    
    public void parseJSON(JSONArray ja) {
    	queryLength = ja.length();
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
    		Log.e(TAG, "Parse JSON Errror:");
    		je.printStackTrace();
    	}
    }
    
    public Bitmap getBitmap(int i){
        Bitmap mBitmap = null;    
        try {
            URL url = new URL(arrImage[i]);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();    
            InputStream is = conn.getInputStream();    
            mBitmap = BitmapFactory.decodeStream(is);
        } catch (MalformedURLException e) {    
            e.printStackTrace();
        } catch (IOException e) {    	
            e.printStackTrace();
        }
        
        return mBitmap;	
	}
    
    private class NetworkTask extends AsyncTask<Integer, Void, Void> {
    	
    	ProgressDialog pd;
    	
    	protected void onPreExecute() {
    		pd = ProgressDialog.show(ListProfessor.this, "", "Loading...Please wait...");
    	}
    	
    	protected Void doInBackground(Integer... x) {
    		try {
    			postData(x[0].intValue(), listURL);
    		} catch (JSONException e) {
    			Log.e(TAG, "Network Call Error:");
    			e.printStackTrace();
    		}
    		return null;
    	}
    	
    	protected void onPostExecute(Void nada) {
    		pd.dismiss();
    		populateList();
    	}
    }
}
