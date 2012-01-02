package ntu.professor.rating;

import ntu.professor.rating.R;
import android.app.Activity;
import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class Classified extends Activity implements View.OnClickListener {
	/** Called when the activity is first created. */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.classified);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.my_title_home);
		TextView mTitleTextView = (TextView) findViewById(R.id.title_txt);
		mTitleTextView.setText("Professor Rating");
		Button mBackBtn = (Button) findViewById(R.id.top5_btn);
		mBackBtn.setOnClickListener(this);
		ImageView ivsearch = (ImageView) findViewById(R.id.imageView_Search);
		ivsearch.setOnClickListener(this);

		Intent intent = getIntent();
		String action = intent.getAction();
		if (action != null) {
			if (action.equals(Intent.ACTION_MAIN)) {
				mBackBtn.setVisibility(View.INVISIBLE);
			}
			if (Intent.ACTION_SEARCH.equals(action)) {
				String query = intent.getStringExtra(SearchManager.QUERY);
				System.out.println(query);
			}
		}

		ImageView ivdepart1 = (ImageView) findViewById(R.id.ImageView_depart1);
		ImageView ivdepart2 = (ImageView) findViewById(R.id.ImageView_depart2);
		ImageView ivdepart3 = (ImageView) findViewById(R.id.ImageView_depart3);
		ImageView ivdepart4 = (ImageView) findViewById(R.id.ImageView_depart4);
		ImageView ivdepart5 = (ImageView) findViewById(R.id.ImageView_depart5);
		ImageView ivdepart6 = (ImageView) findViewById(R.id.ImageView_depart6);
		ImageView ivdepart7 = (ImageView) findViewById(R.id.ImageView_depart7);
		ImageView ivdepart8 = (ImageView) findViewById(R.id.ImageView_depart8);
		ImageView ivdepart9 = (ImageView) findViewById(R.id.ImageView_depart9);
		ImageView ivdepart10 = (ImageView) findViewById(R.id.ImageView_depart10);
		ImageView ivdepart11 = (ImageView) findViewById(R.id.ImageView_depart11);
		ImageView ivdepart12 = (ImageView) findViewById(R.id.ImageView_depart12);

		ivdepart1.setOnClickListener(this);
		ivdepart2.setOnClickListener(this);
		ivdepart3.setOnClickListener(this);
		ivdepart4.setOnClickListener(this);
		ivdepart5.setOnClickListener(this);
		ivdepart6.setOnClickListener(this);
		ivdepart7.setOnClickListener(this);
		ivdepart8.setOnClickListener(this);
		ivdepart9.setOnClickListener(this);
		ivdepart10.setOnClickListener(this);
		ivdepart11.setOnClickListener(this);
		ivdepart12.setOnClickListener(this);
	}

	public void onRestart() {
		super.onRestart();

		ImageView ivdepart1 = (ImageView) findViewById(R.id.ImageView_depart1);
		ImageView ivdepart2 = (ImageView) findViewById(R.id.ImageView_depart2);
		ImageView ivdepart3 = (ImageView) findViewById(R.id.ImageView_depart3);
		ImageView ivdepart4 = (ImageView) findViewById(R.id.ImageView_depart4);
		ImageView ivdepart5 = (ImageView) findViewById(R.id.ImageView_depart5);
		ImageView ivdepart6 = (ImageView) findViewById(R.id.ImageView_depart6);
		ImageView ivdepart7 = (ImageView) findViewById(R.id.ImageView_depart7);
		ImageView ivdepart8 = (ImageView) findViewById(R.id.ImageView_depart8);
		ImageView ivdepart9 = (ImageView) findViewById(R.id.ImageView_depart9);
		ImageView ivdepart10 = (ImageView) findViewById(R.id.ImageView_depart10);
		ImageView ivdepart11 = (ImageView) findViewById(R.id.ImageView_depart11);
		ImageView ivdepart12 = (ImageView) findViewById(R.id.ImageView_depart12);

		ivdepart1.setBackgroundColor(0x000000);
		ivdepart2.setBackgroundColor(0x000000);
		ivdepart3.setBackgroundColor(0x000000);
		ivdepart4.setBackgroundColor(0x000000);
		ivdepart5.setBackgroundColor(0x000000);
		ivdepart6.setBackgroundColor(0x000000);
		ivdepart7.setBackgroundColor(0x000000);
		ivdepart8.setBackgroundColor(0x000000);
		ivdepart9.setBackgroundColor(0x000000);
		ivdepart10.setBackgroundColor(0x000000);
		ivdepart11.setBackgroundColor(0x000000);
		ivdepart12.setBackgroundColor(0x000000);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v.getId() != R.id.imageView_Search && v.getId() != R.id.top5_btn)
			v.setBackgroundColor(0xFF94E938);
		
		Intent newAct;
		Bundle bData = new Bundle();
		newAct = new Intent();	
		Boolean activity = true;
		
		if (v.getId()==R.id.top5_btn)
			newAct.setClass(Classified.this, ListTop5.class);	
		else 
			newAct.setClass(Classified.this, Departments.class);
		

		switch (v.getId()) {
		case R.id.ImageView_depart1:
			bData.putString("depts", "wengshueh");
			break;

		case R.id.ImageView_depart2:
			bData.putString("depts", "gongshueh");
			break;

		case R.id.ImageView_depart5:
			bData.putString("depts", "shenko");
			break;

		case R.id.ImageView_depart3:
			bData.putString("depts", "dianchi");
			break;

		case R.id.ImageView_depart4:
			bData.putString("depts", "yaiyi");
			break;

		case R.id.ImageView_depart6:
			bData.putString("depts", "gongwei");
			break;

		case R.id.ImageView_depart7:
			bData.putString("depts", "ishueh");
			break;

		case R.id.ImageView_depart8:
			bData.putString("depts", "sheiko");
			break;

		case R.id.ImageView_depart10:
			bData.putString("depts", "lisheuh");
			break;

		case R.id.ImageView_depart9:
			bData.putString("depts", "shengnong");
			break;

		case R.id.ImageView_depart11:
			bData.putString("depts", "guanli");
			break;

		case R.id.ImageView_depart12:
			bData.putString("depts", "fashueh");
			break;
			
		case R.id.imageView_Search:
			onSearchRequested();
			activity = false;
			break;
			
		}
		if (activity != false) {
			newAct.putExtras(bData);
			startActivity(newAct);
		}
	}
}
