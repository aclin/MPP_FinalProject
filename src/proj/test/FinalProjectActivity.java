package proj.test;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class FinalProjectActivity extends Activity implements
		View.OnClickListener, View.OnLongClickListener {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		ImageView ivStart = (ImageView) findViewById(R.id.imageView_Start);
		ivStart.setOnClickListener(this);
	}

	public void onClick(View v) {
		// TODO Auto-generated method stub
		String myImgitem = null;
		switch (v.getId()) {
		case R.id.imageView_Search:
			myImgitem = "Search";
			break;
		case R.id.imageView_Add:
			myImgitem = "Add";
			break;
		case R.id.imageView_Home:
			myImgitem = "Home";
			break;
		case R.id.imageView_Top:
			myImgitem = "Top5";
			Intent newAct1 = new Intent();
			newAct1.setClass(FinalProjectActivity.this,
					ListTop5.class);
			startActivity(newAct1);
			break;
		case R.id.imageView_Start:
			myImgitem = "Start";
			Intent newAct2 = new Intent();
			newAct2.setClass(FinalProjectActivity.this,
					Classified.class);
			startActivity(newAct2);
			break;
		default:
			break;
		}
		//Toast.makeText(FinalProjectActivity.this, myImgitem, Toast.LENGTH_SHORT).show();
	}

	public boolean onLongClick(View v) {
		// TODO Auto-generated method stub
		return false;
	}
}