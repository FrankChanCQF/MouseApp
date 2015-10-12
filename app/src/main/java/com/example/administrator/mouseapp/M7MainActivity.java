package com.example.administrator.mouseapp;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class M7MainActivity extends AppCompatActivity {

    private TestAdapter mAdapter;
    private List<String> mDataSet = new ArrayList<>(Arrays.asList("1", "2", "3"));
    private static final String TAG = "Recycler";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.m7_main);
        mAdapter = new TestAdapter();
        RecyclerView recycler;
        recycler = ((RecyclerView) findViewById(R.id.recycler));
//        recycler.setLayoutManager(new LinearLayoutManager(M7MainActivity.this,LinearLayoutManager.HORIZONTAL,false));
//        recycler.addItemDecoration(new M7LinearDecoration(M7MainActivity.this, M7LinearDecoration.HORIZONTAL, R.color.recycle_divider_color));


        recycler.setLayoutManager(new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL));
        recycler.addItemDecoration(new M7GridDecoration(M7MainActivity.this, R.color.recycle_divider_color));

        recycler.setItemAnimator(new DefaultItemAnimator());
        recycler.setAdapter(mAdapter);

    }

    public class TestAdapter extends RecyclerView.Adapter<TestHolder> {

        public void addData(int position, String data) {
            mDataSet.add(position, data);
            notifyItemInserted(position);
        }

        public void removeData(int position) {
            if (mDataSet.size() > 0) {
                mDataSet.remove(position);
                notifyItemRemoved(position);
            }
        }

        @Override
        public TestHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            return new TestHolder(LayoutInflater.from(M7MainActivity.this).inflate(R.layout.m7_recycle_item, viewGroup, false));
        }

        @Override
        public void onBindViewHolder(TestHolder testHolder, int i) {
            testHolder.setItemText(mDataSet.get(i));
        }

        @Override
        public int getItemCount() {
            return mDataSet.size();
        }
    }

    public class TestHolder extends RecyclerView.ViewHolder {
        boolean isRandom;
        TextView itemText;
        final String[] colors = {"#7da9e4", "#cf8f5c", "#ff5a60", "#999999", "#b2ff6f"};

        public TestHolder(View itemView) {
            super(itemView);
            itemText = ((TextView) itemView.findViewById(R.id.item_text));
        }

        public void setItemText(String txt) {
            onPrepare();
            itemText.setText(txt);
        }

        private void onPrepare() {
            if (!isRandom) {
                Log.e("Frankchan","onPreset");
                int r = new Random().nextInt(colors.length);
                itemText.getLayoutParams().height = r * 30 + 100;
                itemText.setBackgroundColor(Color.parseColor(colors[r % (colors.length - 1)]));
            }
            isRandom = true;
        }

    }

    public void onAddData(View view) {
        mAdapter.addData(mDataSet.size(), TAG);
    }

    public void onDelData(View view) {
        mAdapter.removeData(mDataSet.size() - 1);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_m7_main, menu);
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
