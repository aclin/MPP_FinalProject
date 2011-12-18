package proj.test;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Induvidual extends Activity implements View.OnClickListener {
	private String bImage;
	private String bName;
	private String bDepart;
	private String bFor;
	private String bAgainst;
	private String bDept;
	private ImageView ivImage;
	private TextView tvName;

	private void findView() {
		ImageView ivImage = (ImageView) findViewById(R.id.imageView_ItemImage);
		tvName = (TextView) findViewById(R.id.textView_ItemName);
		Button btFor = (Button) findViewById(R.id.button_For);
		btFor.setOnClickListener(this);
		Button btAgainst = (Button) findViewById(R.id.button_Against);
		btAgainst.setOnClickListener(this);
	}

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.induvisual);
		findView();
		Bundle bData = this.getIntent().getExtras();
		//bImage = bData.getString("bImage");
		bName = bData.getString("bName");
		bDepart = bData.getString("bDepart");
		bFor = bData.getString("bFor");
		bAgainst = bData.getString("bAgainst");
		bDept = bData.getString("dept");
		String strImg = "R.drawable." + bImage;
		// ivImage.setImageResource(Integer.parseInt(strImg));
		tvName.setText(bName + " �б�");
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		String myImgitem = null;
		Bundle bData = new Bundle();
		switch (v.getId()) {
		case R.id.button_For:
			myImgitem = "Good!!!!";
			int forValue = Integer.parseInt(bFor);
			forValue++;
			bFor = forValue + "";
			Intent newAct1 = new Intent();
			newAct1.setClass(Induvidual.this, ListProfessor.class);
			// �إ� Bundle ����
			bData.putString("dept", "cs");
			newAct1.putExtras(bData);
			startActivity(newAct1);
			break;
		case R.id.button_Against:
			myImgitem = "Uhhhhhh";
			int againstValue = Integer.parseInt(bAgainst);
			againstValue++;
			bAgainst = againstValue + "";
			Intent newAct2 = new Intent();
			newAct2.setClass(Induvidual.this, ListProfessor.class);
			// �إ� Bundle ����
			bData.putString("dept", "cs");
			newAct2.putExtras(bData);
			startActivity(newAct2);
			break;
		default:
			break;
		}
		Toast.makeText(Induvidual.this, myImgitem, Toast.LENGTH_SHORT).show();
	}
}
