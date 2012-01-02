package ntu.professor.rating;

import ntu.professor.rating.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class Departments extends Activity implements View.OnClickListener{
	/** Called when the activity is first created. */

	private static final String[] totalCollegeName = new String[] {
			"wengshueh", "gongshueh", "shenko", "dianchi", "yaiyi", "gongwei",
			"ishueh", "sheiko", "lisheuh", "shengnong", "guanli", "fashueh" };

	private static final String[] deptsInAbbr = new String[] { "cl", "forex",
			"history", "philo", "anthro", "libs", "japan", "theatre", "ce",
			"me", "che", "esoe", "mse", "lifescience", "bst", "ee", "cs",
			"dod", "ph", "med", "rx", "nurse", "clsmb", "ot", "pt", "politics",
			"econ", "sociology", "sw", "math", "phys", "ch", "gl", "psy",
			"geog", "as", "agron", "ae", "ac", "ppm", "fo", "ansc", "agec",
			"hort", "bicd", "bime", "entomol", "dvm", "ba", "acc", "fin", "ib",
			"im", "law" };

	private static final String[] deptsInChinese = new String[] { "中國文學系",
        "外國語文學系", "歷史學系", "哲學系", "人類學系", "圖書資訊學系", "日本語文學系", "戲劇學系",
        "土木工程學系", "機械工程學系", "化學工程學系", "工程科學及海洋工程學系", "材料科學與工程學系", "生命科學系",
        "生化科技學系", "電機工程學系", "資訊工程學系", "牙醫學系 ", "公共衛生學系 ", "醫學系 ", "藥學系",
        "護理學系", "醫學檢驗暨生物技術學系", "職能治療學系", "物理治療學系", "政治學系", "經濟學系", "社會學系",
        "社會工作學系", "數學系", "物理學系", "化學系", "地質學系", "心理學系", "地理環境資源學系",
        "大氣科學系", "農藝學系", "生物環境系統工程學系", "農業化學系", "植物病理與微生物學系", "森林環境暨資源學系",
        "動物科學技術學系", "農業經濟學系", "園藝暨景觀學系", "生物產業傳播暨發展學系", "生物產業機電工程學系",
        "昆蟲學系", "獸醫學系", "工商管理學系 ", "會計學系", "財務金融學系", "國際企業學系", "資訊管理學系",
        "法律學系" };

	private static final int[] lengthOfDepts = new int[] { 8, 5, 2, 2, 1, 1, 6,
			4, 7, 12, 5, 1 };

	ListView mListView;
	ArrayAdapter<String> mArrayAdapter;
	int deptPtr = 0;

	private void setDepts(String collegeName) {
		for (int i = 0; i < 12; i++) {
			if (collegeName.compareTo(totalCollegeName[i]) == 0) {
				int collegeLength = lengthOfDepts[i];
				mListView = (ListView) findViewById(R.id.collegeList);
				mArrayAdapter = new ArrayAdapter<String>(this,
						android.R.layout.simple_list_item_1);
				mListView.setAdapter(mArrayAdapter);

				for (int j = 0; j < collegeLength; j++) {
					mArrayAdapter.add(deptsInChinese[j+deptPtr]);
				}
				mArrayAdapter.notifyDataSetChanged();
				break;
			}
			deptPtr += lengthOfDepts[i];
		}
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				final int whichDept = position;
				Intent newAct;
				Bundle bData = new Bundle();
				String deptName=deptsInAbbr[deptPtr+whichDept];
				
				newAct = new Intent();
				newAct.setClass(Departments.this, ListProfessor.class);
				bData.putString("dept", deptName);
				newAct.putExtras(bData);
				startActivity(newAct);
			}
		});
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.departments2);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.departments);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.my_title);
		TextView mTitleTextView = (TextView) findViewById(R.id.title_txt);
		mTitleTextView.setText("Professor Rating");
		Button mBackBtn = (Button) findViewById(R.id.back_btn);
		mBackBtn.setOnClickListener(this);
		ImageView ivsearch = (ImageView) findViewById(R.id.imageView_Search);
		ivsearch.setOnClickListener(this);
		
		Bundle bData = this.getIntent().getExtras();
		String collegeName = bData.getString("depts");
		//Toast.makeText(Departments.this,collegeName ,Toast.LENGTH_SHORT).show();
		deptPtr = 0;
		setDepts(collegeName);

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
