package ntu.professor.rating;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleAdapter.ViewBinder;
import android.widget.TextView;

public class ListTop5 extends Activity implements View.OnClickListener {

	private static final int[] arrImage = new int[] { R.drawable.pic1,
		R.drawable.pic2, R.drawable.professor, R.drawable.pic4, R.drawable.pic5};

	private static final String[] arrName = new String[] { "孫維新", "林火旺", "李錫錕", "費昌勇", "李嗣涔"};
	private static final String[] arrDepart = new String[] { "物理系", "哲學系", "政治系", "獸醫系", "電機系"};
	private static String[] arrFor = new String[] { "29", "18", "15", "14", "10"};
	private static String[] arrAgainst = new String[] { "0","0", "1", "0","2" };
	
	ListView list;
	TextView tvLoading;
	ArrayList<HashMap<String, Object>> listItem;
	SimpleAdapter listItemAdapter;
	String depts;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.list_professors);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.list_professors);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.my_title);
		TextView mTitleTextView = (TextView) findViewById(R.id.title_txt);
		mTitleTextView.setText("TOP 5 Professors");
		Button mBackBtn = (Button) findViewById(R.id.back_btn);
		mBackBtn.setOnClickListener(this);
		ImageView ivsearch = (ImageView) findViewById(R.id.imageView_Search);
		ivsearch.setOnClickListener(this);
		
		
		
		list = (ListView) findViewById(R.id.listView_professor);
		// 生成動態陣列，加入資料
		listItem = new ArrayList<HashMap<String, Object>>();
		// 生成Adapter的Item和動態陣列對應的元素

		for (int i = 0; i < 5; i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("ItemImage", arrImage[i]);
			map.put("ItemName", arrName[i]);
			map.put("ItemDepart", arrDepart[i]);
			map.put("ItemFor", arrFor[i]);
			map.put("ItemAgainst", arrAgainst[i]);
			listItem.add(map);
		}
		final SimpleAdapter listItemAdapter = new SimpleAdapter(this,
				listItem, R.layout.listview_professor, new String[] {
						"ItemImage", "ItemName", "ItemDepart", "ItemFor",
						"ItemAgainst" }, new int[] {
						R.id.imageView_ItemImage, R.id.textView_ItemName,
						R.id.textView_ItemDepart, R.id.textView_ItemFor,
						R.id.textView_ItemAgainst });

		listItemAdapter.setViewBinder(new ViewBinder() {

			public boolean setViewValue(View view, Object data,
					String textRepresentation) {
				if (view instanceof ImageView && data instanceof Bitmap) {
					ImageView iv = (ImageView) view;
					iv.setImageBitmap((Bitmap) data);
					return true;
				} else
					return false;
			}
		});

		list.setAdapter(listItemAdapter);
		listItemAdapter.notifyDataSetChanged();
		tvLoading = (TextView) findViewById(R.id.tvLoading);
		tvLoading.setVisibility(View.GONE);
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
