package es.uma.sportjump.sjm.mobile.test;

import roboguice.activity.RoboActivity;
import roboguice.inject.ContentView;
import roboguice.inject.InjectView;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.inject.Inject;

import es.uma.sportjump.sjm.back.test.FooService;
import es.uma.sportjump.sjm.mobile.R;


@ContentView(R.layout.activity_main)
public class TestActivity extends RoboActivity {

	@InjectView(R.id.text)
	TextView texto;
	@InjectView(R.id.button1)
	Button button;
	
	@Inject
	FooService fooService;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActionBar().setDisplayShowTitleEnabled(true);
		
		final Handler handler = new Handler(Looper.getMainLooper()){

			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				texto.setText(msg.getData().getString("json"));
			}
			
		};
		
		button.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				fooService.apiTest(handler);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
