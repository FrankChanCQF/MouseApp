package com.example.administrator.mouseapp;

import android.app.Activity;
import android.os.Handler;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;


/**
 * Mouse App for ListView and its EmptyView
 */
public class M3MainActivity extends Activity {

    ListView listView;

    View proView;

    Handler handler = new Handler();

    BaseAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.m3_activity_main);

        listView = (ListView)findViewById(R.id.list);

        adapter = new BaseAdapter() {
            @Override
            public int getCount() {
                return 0;
            }

            @Override
            public Object getItem(int i) {
                return null;
            }

            @Override
            public long getItemId(int i) {
                return 0;
            }

            @Override
            public View getView(int i, View view, ViewGroup viewGroup) {
                return null;
            }
        };
        listView.setAdapter(adapter);
        proView = findViewById(R.id.progress);
        View empty = LayoutInflater.from(this).inflate(R.layout.m3_empty,null);
        empty.findViewById(R.id.empty_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.removeCallbacks(runnable);
                handler.post(runnable);
            }
        });
        listView.setEmptyView(empty);
        handler.post(runnable);
    }

    private Runnable runnable =new Runnable() {
        @Override
        public void run() {
            proView.setVisibility(View.VISIBLE);
            listView.setVisibility(View.GONE);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    proView.setVisibility(View.GONE);
                    adapter.notifyDataSetChanged();
                    listView.setVisibility(View.VISIBLE);
                }
            }, 2000);
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_m3_main, menu);
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
