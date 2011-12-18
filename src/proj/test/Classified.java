package proj.test;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class Classified extends Activity implements View.OnClickListener {
	/** Called when the activity is first created. */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.classified);
		ImageView ivdepart1 = (ImageView) findViewById(R.id.ImageView_depart1);
		ivdepart1.setOnClickListener(this);
		ImageView ivdepart2 = (ImageView) findViewById(R.id.ImageView_depart2);
		ivdepart2.setOnClickListener(this);
		ImageView ivdepart3 = (ImageView) findViewById(R.id.ImageView_depart3);
		ivdepart3.setOnClickListener(this);
		ImageView ivdepart4 = (ImageView) findViewById(R.id.ImageView_depart4);
		ivdepart4.setOnClickListener(this);
		ImageView ivdepart5 = (ImageView) findViewById(R.id.ImageView_depart5);
		ivdepart5.setOnClickListener(this);
		ImageView ivdepart6 = (ImageView) findViewById(R.id.ImageView_depart6);
		ivdepart6.setOnClickListener(this);
		ImageView ivdepart7 = (ImageView) findViewById(R.id.ImageView_depart7);
		ivdepart7.setOnClickListener(this);
		ImageView ivdepart8 = (ImageView) findViewById(R.id.ImageView_depart8);
		ivdepart8.setOnClickListener(this);
		ImageView ivdepart9 = (ImageView) findViewById(R.id.ImageView_depart9);
		ivdepart9.setOnClickListener(this);
		ImageView ivdepart10 = (ImageView) findViewById(R.id.ImageView_depart10);
		ivdepart10.setOnClickListener(this);
		ImageView ivdepart11 = (ImageView) findViewById(R.id.ImageView_depart11);
		ivdepart11.setOnClickListener(this);
		ImageView ivdepart12 = (ImageView) findViewById(R.id.ImageView_depart12);
		ivdepart12.setOnClickListener(this);
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		Intent newAct;
		Bundle bData = new Bundle();
		switch (arg0.getId()) {
		case R.id.ImageView_depart1:
			newAct = new Intent();
			newAct.setClass(Classified.this, Departments.class);
			bData.putString("depts", "dept1");
			newAct.putExtras(bData);
			startActivity(newAct);
			break;
		case R.id.ImageView_depart2:
			newAct = new Intent();
			newAct.setClass(Classified.this, Departments.class);

			bData.putString("depts", "dept");
			newAct.putExtras(bData);
			startActivity(newAct);
			break;
		case R.id.ImageView_depart3:
			newAct = new Intent();

			bData.putString("depts", "dianchi");
			newAct.putExtras(bData);

			newAct.setClass(Classified.this, Departments.class);
			startActivity(newAct);
			break;
		case R.id.ImageView_depart4:
			newAct = new Intent();
			newAct.setClass(Classified.this, Departments.class);

			bData.putString("depts", "dept");
			newAct.putExtras(bData);
			startActivity(newAct);
			break;
		case R.id.ImageView_depart5:
			newAct = new Intent();
			newAct.setClass(Classified.this, Departments.class);
			bData.putString("depts", "dept");
			newAct.putExtras(bData);
			startActivity(newAct);
			break;
		case R.id.ImageView_depart6:
			newAct = new Intent();
			newAct.setClass(Classified.this, Departments.class);
			bData.putString("depts", "dept");
			newAct.putExtras(bData);
			startActivity(newAct);
			break;
		case R.id.ImageView_depart7:
			newAct = new Intent();
			newAct.setClass(Classified.this, Departments.class);
			bData.putString("depts", "dept");
			newAct.putExtras(bData);
			startActivity(newAct);
			break;
		case R.id.ImageView_depart8:
			newAct = new Intent();
			newAct.setClass(Classified.this, Departments.class);
			bData.putString("depts", "dept");
			newAct.putExtras(bData);
			startActivity(newAct);
			break;
		case R.id.ImageView_depart9:
			newAct = new Intent();
			newAct.setClass(Classified.this, Departments.class);
			bData.putString("depts", "dept");
			newAct.putExtras(bData);
			startActivity(newAct);
			break;
		case R.id.ImageView_depart10:
			newAct = new Intent();
			newAct.setClass(Classified.this, Departments.class);
			bData.putString("depts", "dept");
			newAct.putExtras(bData);
			startActivity(newAct);
			break;
		case R.id.ImageView_depart11:
			newAct = new Intent();
			newAct.setClass(Classified.this, Departments.class);
			bData.putString("depts", "dept");
			newAct.putExtras(bData);
			startActivity(newAct);
			break;
		case R.id.ImageView_depart12:
			newAct = new Intent();
			newAct.setClass(Classified.this, Departments.class);
			bData.putString("depts", "dept");
			newAct.putExtras(bData);
			startActivity(newAct);
			break;
		}
	}

}
