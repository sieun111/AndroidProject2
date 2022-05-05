package com.example.project2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private GridAdapter gridAdapter;
    private ArrayList<String> dayList;
    private GridView gridView;

    private Calendar mCal;
    private Calendar sCal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent= getIntent(); //시작 정보

        gridView = (GridView)findViewById(R.id.gridview);
        mCal = Calendar.getInstance();

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        myToolbar.setTitle(mCal.get(Calendar.YEAR) + "년" +mCal.get(Calendar.MONTH)+ "월"); //툴바 날짜

        dayList = new ArrayList<String>();

        int year= Calendar.getInstance().get(Calendar.YEAR);
        int month= Calendar.getInstance().get(Calendar.MONTH);
        int dayNum= Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
        int dayofmonth= Calendar.getInstance().getActualMaximum(Calendar.DAY_OF_MONTH);

        sCal= Calendar.getInstance();
        sCal.set(mCal.get(Calendar.YEAR), mCal.get(Calendar.MONTH) , 1);

        //공백을 넣어주기 위해 생성
        for (int i = 1; i < dayNum; i++) {
            dayList.add("");
        }

        for (int i = 0; i < sCal.getActualMaximum(Calendar.DAY_OF_MONTH); i++) {
            dayList.add("" + (i + 1));
        }

        gridAdapter = new GridAdapter(getApplicationContext(), dayList);
        gridView.setAdapter(gridAdapter);

    }

    private class GridAdapter extends BaseAdapter {
        private final List<String> list;
        private final LayoutInflater inflater;


        public GridAdapter(Context context, List<String> list) {
            this.list = list;
            this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public String getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;

            GridView gridView = (GridView)findViewById(R.id.gridview);

            int gridviewH = gridView.getHeight() / 6;

            if (convertView == null) {
                convertView = inflater.inflate(R.layout.day, parent, false);
                holder = new ViewHolder();
                holder.tvItemGridView = (TextView)convertView.findViewById(R.id.day);
                convertView.setTag(holder);
            }

            else {
                holder = (ViewHolder)convertView.getTag();
            }

            holder.tvItemGridView.setText("" + getItem(position));

            mCal = Calendar.getInstance(); //오늘 day 가져옴

            Integer today = mCal.get(Calendar.DAY_OF_MONTH);
            String sToday = String.valueOf(today);

            return convertView;
        }
    }

    private class ViewHolder {
        TextView tvItemGridView;
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }//앱바에 오버플로우 메뉴 추가

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_month:
                startActivity(new Intent(this,SubActivity.class));
                return true;
            case R.id.action_week:
                startActivity(new Intent(this,SubActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}