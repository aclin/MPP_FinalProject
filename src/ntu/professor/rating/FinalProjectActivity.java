package ntu.professor.rating;

import ntu.professor.rating.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
//import android.widget.ImageView;

public class FinalProjectActivity extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.main);

		//ImageView ivStart = (ImageView) findViewById(R.id.imageView_Start);

		Handler handler = new Handler();
		handler.postDelayed(new Runnable() {
			public void run() {
				Intent newAct2 = new Intent();
				newAct2.setClass(FinalProjectActivity.this, Classified.class);
				startActivity(newAct2);
				finish();
			}
		}, 2000);
	}
}