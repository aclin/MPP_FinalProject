package ntu.professor.rating;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
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
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.widget.SimpleAdapter.ViewBinder;
import android.widget.TextView;

public class ListProfessor extends Activity implements View.OnClickListener,
		ServerCall {
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
	private AsyncListTask listTask = new AsyncListTask();

	ListView list;
	TextView tvLoading;
	ArrayList<HashMap<String, Object>> listItem;
	//SimpleAdapter listItemAdapter;
	myListAdapter mListAdapter;
	String depts;
	private String query;

	private String[] arrImage;
	private String[] arrName;
	private String[] arrDepart;
	private int[] arrFor;
	private int[] arrAgainst;
	private float[] arrRate;

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
		
		Intent intent = this.getIntent();
		if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
			if (intent.getStringExtra(SearchManager.QUERY).equals(null)) {
				query = intent.getStringExtra("query");
			} else {
				query = intent.getStringExtra(SearchManager.QUERY);
			}
			try {
				query = new String(query.getBytes(), "ISO8859_1");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			Log.i(TAG, "Query: " + query);
			listTask.execute(ServerCall.POST_SEARCH);
			listItem = new ArrayList<HashMap<String, Object>>();
		} else {
			Bundle bData = intent.getExtras();
			depts = bData.getString("dept");
			Log.i(TAG, "dept thingy: " + depts);
	
			listItem = new ArrayList<HashMap<String, Object>>();
	
			if (depts.equals("top5")) {
				listTask.execute(ServerCall.POST_TOP5);
			} else {
				for (int i = 0; i < deptsInAbbr.length; i++) {
					if (deptsInAbbr[i].equals(depts))
						listTask.execute(ServerCall.POST_READ);
				}
			}
		}

		mListAdapter= new myListAdapter(listItem, this);
		list.setAdapter(mListAdapter);
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		if(listTask.getStatus() == AsyncTask.Status.RUNNING) {
			listTask.cancel(true);
		}
	}

	private class myListAdapter extends BaseAdapter {
		private ArrayList<HashMap<String, Object>> myListItem;
		private LayoutInflater mInflater;

		public myListAdapter(ArrayList<HashMap<String, Object>> mylistitem,
				Context context) {
			myListItem = mylistitem;
			mInflater = LayoutInflater.from(context);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return myListItem.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return myListItem.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			// A ViewHolder keeps references to children views to avoid
			// unneccessary calls
			// to findViewById() on each row.
			ViewHolder holder;

			// When convertView is not null, we can reuse it directly, there is
			// no need
			// to reinflate it. We only inflate a new View when the convertView
			// supplied
			// by ListView is null
			if (convertView == null) {
				convertView = mInflater.inflate(R.layout.listview_professor,
						null);
				// Creates a ViewHolder and store references to the two children
				// views
				// we want to bind data to.
				holder = new ViewHolder();
				holder.iv = (ImageView) convertView
						.findViewById(R.id.imageView_ItemImage);
				holder.tvName = (TextView) convertView
						.findViewById(R.id.textView_ItemName);
				holder.tvDept = (TextView) convertView
						.findViewById(R.id.textView_ItemDepart);
				holder.tvFor = (TextView) convertView
						.findViewById(R.id.textView_ItemFor);
				holder.tvAgainst = (TextView) convertView
						.findViewById(R.id.textView_ItemAgainst);
				holder.rtBar = (RatingBar) convertView
						.findViewById(R.id.ratingBar1);
				convertView.setTag(holder);

			} else {
				// Get the ViewHolder back to get fast access to the TextView
				// and the ImageView.
				holder = (ViewHolder) convertView.getTag();
			}
			// Bind the data with the holder.
			
			holder.iv.setImageBitmap((Bitmap) myListItem.get(position).get("ItemImage"));
			holder.tvName.setText((String) myListItem.get(position).get("ItemName"));
			holder.tvDept.setText((String) myListItem.get(position).get("ItemDepart"));
			holder.tvFor.setText((String) myListItem.get(position).get("ItemFor").toString());
			holder.tvAgainst.setText((String) myListItem.get(position).get("ItemAgainst").toString());
			
			holder.rtBar.setRating((Float) myListItem.get(position).get("ItemRating"));

			return convertView;
		}

		class ViewHolder {
			/*
			 * R.id.imageView_ItemImage, R.id.textView_ItemName,
			 * R.id.textView_ItemDepart, R.id.textView_ItemFor,
			 * R.id.textView_ItemAgainst
			 */
			ImageView iv;
			TextView tvName;
			TextView tvDept;
			TextView tvFor;
			TextView tvAgainst;
			RatingBar rtBar;
		}

	}

	public void postData(int action, String url) throws JSONException {
		// Create a new HttpClient and Post Header
		Log.i(TAG, "URL: " + url);
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
			case POST_TOP5:
				j.put("Command", "Top5");
				break;
			case POST_SEARCH:
				Log.i(TAG, "Post for search");
				j.put("Command", "Search");
				j.put("Query", query);
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
			if (response != null) {
				InputStream is = response.getEntity().getContent();

				BufferedReader reader = new BufferedReader(
						new InputStreamReader(is, "UTF8"));
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
					// tv.setText("Cannot parse response from server; not a JSON Object");
				}
			}
			// tv.setText(reply);

		} catch (ClientProtocolException e) {
			Log.e(TAG, "Client Protocol Exception:");
			e.printStackTrace();
		} catch (IOException e) {
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
		arrRate = new float[queryLength];
		try {
			for (int i = 0; i < ja.length(); i++) {
				arrImage[i] = ja.getJSONObject(i).getString("Picture");
				arrName[i] = ja.getJSONObject(i).getString("Name");
				arrDepart[i] = ja.getJSONObject(i).getString("Dept");
				arrFor[i] = ja.getJSONObject(i).getInt("Good");
				arrAgainst[i] = ja.getJSONObject(i).getInt("Bad");
				arrRate[i] = ja.getJSONObject(i).getInt("Rate");
			}
		} catch (JSONException je) {
			Log.e(TAG, "Parse JSON Error:");
			je.printStackTrace();
		}
	}

	public Bitmap getBitmap(int i) {
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

	public float getRating(int forNum, int againstNum){
		float rtNum = 0; 
		if ((forNum+againstNum)>0)
			rtNum = 5*forNum/(forNum+againstNum);			
		return rtNum;
	}
	
	private class AsyncListTask extends
			AsyncTask<Integer, HashMap<String, Object>, Void> {

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
				map.put("ItemRating", getRating(arrFor[i],arrAgainst[i]));
				publishProgress(map);
			}
			return null;
		}
		
		@Override
		protected void onProgressUpdate(HashMap<String, Object>... map) {
			listItem.add(map[0]);
			mListAdapter.notifyDataSetChanged();

			list.setOnItemClickListener(new OnItemClickListener() {
				// String bImage = "";
				String bName = "";
				String bDepart = "";
				int bFor = 0;
				int bAgainst = 0;
				Bitmap bImage;
				float bRate = 0;

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1,
						int position, final long id) {
					HashMap<String, Object> itemAtPosition = (HashMap<String, Object>) listItem
							.get(position);

					chosenPosition = position;

					bName = itemAtPosition.get("ItemName").toString();
					setTitle("選取了" + bName);
					// bDepart = itemAtPosition.get("ItemDepart").toString();
					bFor = (Integer) itemAtPosition.get("ItemFor");
					bAgainst = (Integer) itemAtPosition.get("ItemAgainst");
					bImage = (Bitmap) itemAtPosition.get("ItemImage");
					bRate = (Float) itemAtPosition.get("ItemRating");

					// Toast.makeText(ListProfessor.this, "rate= "+bRate,
					// Toast.LENGTH_LONG).show();

					Intent newAct = new Intent();
					newAct.setClass(ListProfessor.this, Induvidual.class);
					Bundle bData = new Bundle();

					bData.putString("bName", bName);
					// bData.putString("bDepart", bDepart);
					bData.putString("bDepart", depts);
					bData.putInt("bFor", bFor);
					bData.putInt("bAgainst", bAgainst);
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
			hm.put("ItemRating", getRating(b.getInt("bFor"),b.getInt("bAgainst")));
			mListAdapter.notifyDataSetChanged();
		}
	}

	@Override
	protected void onRestart() {
		super.onRestart();
	}

	@Override
	public void onClick(View v) {
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
