package com.example.administrator.mouseapp;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

/**
 * First mouse-contributor for view measure
 * Conclusion:what do view->measure(0,0) can do
 * 1.Those whose size is wrap_content can be measured
 * 2.Only those added to parent can be measured when they are sized with fill_parent
 */
public class M1MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.m1_activity_main);
        View header = getLayoutInflater().inflate(R.layout.m1_header_layout, null);
        measureViewHeight(header);
        Log.e("FrankChan", String.format("width=%d;height=%d", header.getMeasuredWidth(), header.getMeasuredWidth()));
        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = findViewById(R.id.header);
                Log.e("FrankChan", String.format("width=%d;height=%d", view.getWidth(), view.getHeight()));
            }
        });
    }

    private void measureViewHeight(View view){
        ViewGroup.LayoutParams p = view.getLayoutParams();
        if(p==null){
            p = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        int widthSpec = ViewGroup.getChildMeasureSpec(0,0,p.width);
        int heightSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        view.measure(widthSpec,heightSpec);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
