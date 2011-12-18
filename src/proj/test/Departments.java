package proj.test;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class Departments extends Activity implements View.OnClickListener {
	/** Called when the activity is first created. */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.departments);

		Bundle bData = this.getIntent().getExtras();
		String depts = bData.getString("depts");

		ImageView ivdepart1 = (ImageView) findViewById(R.id.ImageView_dept1);
		ivdepart1.setOnClickListener(this);
		ImageView ivdepart2 = (ImageView) findViewById(R.id.ImageView_dept2);
		ivdepart2.setOnClickListener(this);
		ImageView ivdepart3 = (ImageView) findViewById(R.id.ImageView_dept3);
		ivdepart3.setOnClickListener(this);
		ImageView ivdepart4 = (ImageView) findViewById(R.id.ImageView_dept4);
		ivdepart4.setOnClickListener(this);
		ImageView ivdepart5 = (ImageView) findViewById(R.id.ImageView_dept5);
		ivdepart5.setOnClickListener(this);
		ImageView ivdepart6 = (ImageView) findViewById(R.id.ImageView_dept6);
		ivdepart6.setOnClickListener(this);
		ImageView ivdepart7 = (ImageView) findViewById(R.id.ImageView_dept7);
		ivdepart7.setOnClickListener(this);
		ImageView ivdepart8 = (ImageView) findViewById(R.id.ImageView_dept8);
		ivdepart8.setOnClickListener(this);
		ImageView ivdepart9 = (ImageView) findViewById(R.id.ImageView_dept9);
		ivdepart9.setOnClickListener(this);
		ImageView ivdepart10 = (ImageView) findViewById(R.id.ImageView_dept10);
		ivdepart10.setOnClickListener(this);
		ImageView ivdepart11 = (ImageView) findViewById(R.id.ImageView_dept11);
		ivdepart11.setOnClickListener(this);
		ImageView ivdepart12 = (ImageView) findViewById(R.id.ImageView_dept12);
		ivdepart12.setOnClickListener(this);
		
		if (depts.compareTo("dianchi")==0){
			ivdepart2.setImageResource(R.drawable.ee);
			ivdepart5.setImageResource(R.drawable.cs);
		}

	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		Intent newAct;
		Bundle bData = new Bundle();
		switch (arg0.getId()) {
		case R.id.ImageView_dept2:
			newAct = new Intent();
			newAct.setClass(Departments.this, ListProfessor.class);
			bData.putString("dept", "ee");
			newAct.putExtras(bData);
			startActivity(newAct);
			break;
		case R.id.ImageView_dept5:
			newAct = new Intent();
			newAct.setClass(Departments.this, ListProfessor.class);
			bData.putString("dept", "cs");
			newAct.putExtras(bData);
			startActivity(newAct);
			break;
/*		case R.id.ImageView_dept3:
			newAct = new Intent();
			newAct.setClass(Departments.this, ListProfessor.class);
			startActivity(newAct);
			break;
		case R.id.ImageView_dept4:
			newAct = new Intent();
			newAct.setClass(Departments.this, ListProfessor.class);
			startActivity(newAct);
			break;
		case R.id.ImageView_dept5:
			newAct = new Intent();
			newAct.setClass(Departments.this, ListProfessor.class);
			startActivity(newAct);
			break;
		case R.id.ImageView_dept6:
			newAct = new Intent();
			newAct.setClass(Departments.this, ListProfessor.class);
			startActivity(newAct);
			break;
		case R.id.ImageView_dept7:
			newAct = new Intent();
			newAct.setClass(Departments.this, ListProfessor.class);
			startActivity(newAct);
			break;
		case R.id.ImageView_dept8:
			newAct = new Intent();
			newAct.setClass(Departments.this, ListProfessor.class);
			startActivity(newAct);
			break;
		case R.id.ImageView_dept9:
			newAct = new Intent();
			newAct.setClass(Departments.this, ListProfessor.class);
			startActivity(newAct);
			break;
		case R.id.ImageView_dept10:
			newAct = new Intent();
			newAct.setClass(Departments.this, ListProfessor.class);
			startActivity(newAct);
			break;
		case R.id.ImageView_dept11:
			newAct = new Intent();
			newAct.setClass(Departments.this, ListProfessor.class);
			startActivity(newAct);
			break;
		case R.id.ImageView_dept12:
			newAct = new Intent();
			newAct.setClass(Departments.this, ListProfessor.class);
			startActivity(newAct);
			break;
*/
		}
	}

}
