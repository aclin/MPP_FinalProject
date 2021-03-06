package ntu.professor.rating;

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

import ntu.professor.rating.R;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleAdapter.ViewBinder;
import android.widget.TextView;

public class ListProfessor extends Activity implements View.OnClickListener, ServerCall {
	private static final String TAG = "ListProfessor";
	private static final int INDIVIDUAL_REQUEST = 0;
	
	private static final String[] deptsInAbbr = new String[] { "cl", "forex",
		"history", "philo", "anthro", "libs", "japan", "theatre", "ce",
		"me", "che", "esoe", "mse", "lifescience", "bst", "ee", "cs",
		"dod", "ph", "med", "rx", "nurse", "clsmb", "ot", "pt", "politics",
		"econ", "sociology", "sw", "math", "phys", "ch", "gl", "psy",
		"geog", "as", "agron", "ae", "ac", "ppm", "fo", "ansc", "agec",
		"hort", "bicd", "bime", "entomol", "dvm", "ba", "acc", "fin", "ib",
		"im", "law" };
	
	private final String listURL = "http://ee.hac.tw:2323/insertdb.php";
	private String responseString;
	private int queryLength;
	
	private int chosenPosition;
	
	ListView list;
	TextView tvLoading;
	ArrayList<HashMap<String, Object>> listItem;
	SimpleAdapter listItemAdapter;
	String depts;
	
	private String[] arrImage;
	private String[] arrName;
	private String[] arrDepart;
	private int[] arrFor;
	private int[] arrAgainst;

	/*
	 * public Bitmap getBitmap(int i) { Bitmap mBitmap = null; try { URL url =
	 * new URL(arrImage[i]); HttpURLConnection conn = (HttpURLConnection)
	 * url.openConnection(); InputStream is = conn.getInputStream(); mBitmap =
	 * BitmapFactory.decodeStream(is);
	 * 
	 * } catch (MalformedURLException e) { e.printStackTrace(); } catch
	 * (IOException e) { e.printStackTrace(); }
	 * 
	 * return mBitmap; }
	 */
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.list_professors);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.list_professors);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.my_title);
		TextView mTitleTextView = (TextView) findViewById(R.id.title_txt);
		mTitleTextView.setText("Professor Rating");
		Button mBackBtn = (Button) findViewById(R.id.back_btn);
		mBackBtn.setOnClickListener(this);
		ImageView ivsearch = (ImageView) findViewById(R.id.imageView_Search);
		ivsearch.setOnClickListener(this);
		
		list = (ListView) findViewById(R.id.listView_professor);
		tvLoading = (TextView) findViewById(R.id.tvLoading);
		
		Bundle bData = this.getIntent().getExtras();
		depts = bData.getString("dept");
		Log.i(TAG, "dept thingy: " + depts);
		
		listItem = new ArrayList<HashMap<String, Object>>();
		listItemAdapter = new SimpleAdapter(this,
				listItem, R.layout.listview_professor, new String[] {
						"ItemImage", "ItemName", "ItemDepart", "ItemFor",
						"ItemAgainst" }, new int[] {
						R.id.imageView_ItemImage, R.id.textView_ItemName,
						R.id.textView_ItemDepart, R.id.textView_ItemFor,
						R.id.textView_ItemAgainst });
		
		list.setAdapter(listItemAdapter);
		
		for (int i = 0; i < deptsInAbbr.length; i++) {
			if (deptsInAbbr[i].equals(depts))
					new AsyncListTask().execute(ServerCall.POST_READ);
		}
		
		/*if (depts.compareTo("cs") == 0) {
			new AsyncListTask().execute(ServerCall.POST_READ);
		}*/
	}
	
//	private void populateList() {
//		
//		for (int i = 0; i < queryLength; i++) {
//			HashMap<String, Object> map = new HashMap<String, Object>();
//			map.put("ItemImage", getBitmap(i));
//			map.put("ItemName", arrName[i]);
//			map.put("ItemDepart", arrDepart[i]);
//			map.put("ItemFor", arrFor[i]);
//			map.put("ItemAgainst", arrAgainst[i]);
//			listItem.add(map);
//		}
//		
//		listItemAdapter.setViewBinder(new ViewBinder() {    
//            
//            public boolean setViewValue(View view, Object data,    
//                    String textRepresentation) {    
//                if (view instanceof ImageView  && data instanceof Bitmap) {    
//                    ImageView iv = (ImageView) view;
//                    iv.setImageBitmap((Bitmap) data);    
//                    return true;    
//                } else {
//                	return false;
//                }
//            }    
//        });
//		listItemAdapter.notifyDataSetChanged();
//
//		list.setOnItemClickListener(new OnItemClickListener() {
//			//String bImage = "";
//			String bName = "";
//			String bDepart = "";
//			String bFor = "";
//			String bAgainst = "";
//			Bitmap bImage;
//
//			@Override
//			public void onItemClick(AdapterView<?> arg0, View arg1,
//					int position, final long id) {
//				// TODO Auto-generated method stub
//				final int peopleToBeShown = position;
//				
//				HashMap<String, Object> itemAtPosition = (HashMap<String, Object>) list
//						.getItemAtPosition(peopleToBeShown);
//				
//				bName = itemAtPosition.get("ItemName").toString();
//				
//				setTitle("選取了"+ bName);
//				
//				bDepart = itemAtPosition.get("ItemDepart").toString();
//				bFor = itemAtPosition.get("ItemFor").toString();
//				bAgainst = itemAtPosition.get("ItemAgainst").toString();
//				bImage = (Bitmap) itemAtPosition.get("ItemImage");
//				
//				Intent newAct = new Intent();
//				newAct.setClass(ListProfessor.this, Induvidual.class);
//				
//				Bundle bData = new Bundle();
//				
//				bData.putString("bName", bName);
//				bData.putString("bDepart", bDepart);
//				bData.putString("bFor", bFor);
//				bData.putString("bAgainst", bAgainst);
//				bData.putString("dept", "cs");
//				newAct.putExtras(bData);
//				newAct.putExtra("bImage", bImage);
//				startActivity(newAct); // Need to change this to startActivityForResult()
//			}
//		});
//	}
	
	public void postData(int action, String url) throws JSONException {
    	// Create a new HttpClient and Post Header
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(url);
		JSONObject j = new JSONObject();
		
		try {
			switch (action) {
			case POST_READ:
				j.put("Command", "Read");
				j.put("Dept", depts);
				Log.i(TAG, "DEPTS: " + depts);
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
    		Log.e(TAG, "Parse JSON Error:");
    		je.printStackTrace();
    	}
    }
    
    public Bitmap getBitmap(int i){
        Bitmap mBitmap = null;    
        try {
            // Get URL of image, make a connection to pull in the image stream
        	URL url = new URL(arrImage[i]);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();    
            InputStream is = conn.getInputStream();
            
            // Set options to just decode the size of original image
            BitmapFactory.Options opts = new BitmapFactory.Options();
            opts.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(is, null, opts);
            
            // Set maximum size
            final int REQUIRED_SZ = 70;
            
            // Find scale value. Should be power of 2
            int origWidth = opts.outWidth;
            int origHeight = opts.outHeight;
            int origDim = Math.max(origWidth, origHeight);
            opts = new BitmapFactory.Options();
            opts.inSampleSize = 1;
            while (origDim > REQUIRED_SZ) {
            	origDim /= 2;
            	opts.inSampleSize *= 2;
            }
            
            // Get stream again
            conn = (HttpURLConnection) url.openConnection();    
            is = conn.getInputStream();
            
            // Decode with new scaled option
            mBitmap = BitmapFactory.decodeStream(is, null, opts);
            is.close();
            is = null;
            System.gc();
        } catch (MalformedURLException e) {    
            e.printStackTrace();
        } catch (IOException e) {    	
            e.printStackTrace();
        }
        
        return mBitmap;	
	}
    
//    private class NetworkTask extends AsyncTask<Integer, Void, Void> {
//    	
//    	ProgressDialog pd;
//    	
//    	@Override
//    	protected void onPreExecute() {
//    		pd = ProgressDialog.show(ListProfessor.this, "", "Loading...Please wait...");
//    	}
//    	
//    	@Override
//    	protected Void doInBackground(Integer... params) {
//    		try {
//    			postData(params[0].intValue(), listURL);
//    		} catch (JSONException e) {
//    			Log.e(TAG, "Network Call Error:");
//    			e.printStackTrace();
//    		}
//    		return null;
//    	}
//    	
//    	@Override
//    	protected void onPostExecute(Void unused) {
//    		pd.dismiss();
//    		populateList();
//    	}
//    }
    
    private class AsyncListTask extends AsyncTask<Integer, HashMap<String, Object>, Void> {

		@Override
		protected Void doInBackground(Integer... params) {
			try {
    			postData(params[0].intValue(), listURL);
    		} catch (JSONException e) {
    			Log.e(TAG, "Network Call Error:");
    			e.printStackTrace();
    		}
			for (int i = 0; i < queryLength; i++) {
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("ItemImage", getBitmap(i));
				map.put("ItemName", arrName[i]);
				map.put("ItemDepart", arrDepart[i]);
				map.put("ItemFor", arrFor[i]);
				map.put("ItemAgainst", arrAgainst[i]);
				publishProgress(map);
			}
			return null;
		}
		
		@Override
		protected void onProgressUpdate(HashMap<String, Object>... map) {
			listItem.add(map[0]);
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
			listItemAdapter.notifyDataSetChanged();

			list.setOnItemClickListener(new OnItemClickListener() {
				//String bImage = "";
				String bName = "";
				String bDepart = "";
				int bFor = 0;
				int bAgainst = 0;
				Bitmap bImage;
				
				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int position, final long id) {
					// TODO Auto-generated method stub
					HashMap<String, Object> itemAtPosition = (HashMap<String, Object>) listItem
							.get(position);
					
					chosenPosition = position;
					
					bName = itemAtPosition.get("ItemName").toString();
					setTitle("選取了" + bName);
					//bDepart = itemAtPosition.get("ItemDepart").toString();
					bFor = (Integer) itemAtPosition.get("ItemFor");
					bAgainst = (Integer) itemAtPosition.get("ItemAgainst");
					bImage = (Bitmap) itemAtPosition.get("ItemImage");
					Intent newAct = new Intent();
					newAct.setClass(ListProfessor.this, Induvidual.class);
					Bundle bData = new Bundle();
					
					bData.putString("bName", bName);
					//bData.putString("bDepart", bDepart);
					bData.putString("bDepart", depts);
					bData.putInt("bFor", bFor);
					bData.putInt("bAgainst", bAgainst);
					//bData.putString("dept", "cs");
					Log.i(TAG, "bDepart: " + bDepart);
					newAct.putExtras(bData);
					newAct.putExtra("bImage", bImage);
					startActivityForResult(newAct, INDIVIDUAL_REQUEST);
				}
			});
		}
		
		@Override
		protected void onPostExecute(Void unused) {
			tvLoading.setVisibility(View.GONE);
		}
    }
    
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == INDIVIDUAL_REQUEST) {
        	Bundle b = data.getExtras();
        	Log.i(TAG, "Name: " + b.getString("bName"));
        	Log.i(TAG, "For: " + b.getInt("bFor"));
        	Log.i(TAG, "Against: " + b.getInt("bAgainst"));
        	HashMap<String, Object> hm = listItem.get(chosenPosition);
        	hm.put("ItemFor", b.getInt("bFor"));
        	hm.put("ItemAgainst", b.getInt("bAgainst"));
        	listItemAdapter.notifyDataSetChanged();
        }
    }
    
	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
//		Toast.makeText(ListProfessor.this, "Restart!!!", Toast.LENGTH_SHORT)
//				.show();

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.imageView_Search:
			onSearchRequested();
			break;
		case R.id.back_btn:
			finish();
			break;
		}
	}
}
