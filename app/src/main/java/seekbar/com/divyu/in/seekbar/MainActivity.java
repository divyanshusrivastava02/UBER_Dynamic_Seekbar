package seekbar.com.divyu.in.seekbar;

import android.content.res.TypedArray;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SeekBar;
import android.widget.TextView;

import seekbar.com.divyu.in.seekbar.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class MainActivity extends ActionBarActivity {
    TypedArray imgs;
    int currentVal;
    int startVal;
    int centerVal;
    Map<Integer, LimitValues> mLimitSplitter;
    List<Integer> divisionFormula;
    TextView  txt1,txt2,txt3;
    int width;
    int pos=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        ComboSeekBar mSeekBar =  (ComboSeekBar)findViewById(R.id.seekBar);

        /**
         *  ADD NEW UBER CARS AS PER YOUR NEED
         */
        final List<String> seekBarStep = Arrays.asList("UBERX", "UBER GO", "UBER HUB", "UBER BLACK");
        imgs = getResources().obtainTypedArray(R.array.car_images);
        mSeekBar.setAdapter(seekBarStep);
        //mSeekBar.setThumb(getResources().getDrawable(R.drawable.sliderknob));
        // mSeekBar.setColor(getResources().getColor(R.color.white));
        mSeekBar.setProgress(5);
        mSeekBar.setThumb(getResources().getDrawable(imgs.getResourceId(0, -1)));

        txt1 = (TextView)findViewById(R.id.txt1);
        txt2 = (TextView)findViewById(R.id.txt2);
        txt3 = (TextView)findViewById(R.id.txt3);


        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        width = size.x;
        int height = size.y;


       // Map<String, List<String>> map = new HashMap<String, List<String>>();

        int multipleOf=0;
        int finVal=0;
        divisionFormula = new ArrayList<Integer>();
        multipleOf = 100/(seekBarStep.size()-1);
        //finVal = multipleOf;
        for(int i=0; i<seekBarStep.size();i++){
            divisionFormula.add(i,finVal);
            finVal = multipleOf + finVal;
        }


        mLimitSplitter = new HashMap<Integer, LimitValues>();
        currentVal = 100/seekBarStep.size();
        startVal = 100/seekBarStep.size()-currentVal;
        for(int i=1; i<seekBarStep.size();i++) {
            mLimitSplitter.put(i, LimitValues.createLimitValues(i, startVal, divisionFormula.get(i)));
            startVal = divisionFormula.get(i);
        }

        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                txt1.setText("PROGRESS VALUE   "+progress);

                for(int k=1 ; k<divisionFormula.size();k++) {
                    if (progress > divisionFormula.get(k-1)) {
                        pos = k;
                    }
                }
                centerValue(mLimitSplitter.get(pos).startPoint, mLimitSplitter.get(pos).endPoint);

                for(int i=1; i<seekBarStep.size();i++)
                {
                    if(progress>mLimitSplitter.get(pos).startPoint && progress<centerVal){
                        seekBar.setThumb(getResources().getDrawable(imgs.getResourceId(pos-1, -1)));
                    }
                    else if(progress>centerVal && progress<=mLimitSplitter.get(pos).endPoint){
                        seekBar.setThumb(getResources().getDrawable(imgs.getResourceId(pos, -1)));
                    }

                }
           //     imgs.recycle();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int val = 0;
                val = seekBar.getProgress();
                txt2.setText("ON STOP VALUE   " + val);


                System.out.println("MAP VALUE:::::" + pos + " %%% " + val + " ##  " + mLimitSplitter.get(pos).startPoint + " : " + mLimitSplitter.get(pos).endPoint);
                System.out.println("CENTER VALUE:::" + centerVal);

                if (val < centerVal && val <= mLimitSplitter.get(1).endPoint-centerVal) {
                    seekBar.setProgress(mLimitSplitter.get(1).startPoint);
                    seekBar.setThumb(getResources().getDrawable(imgs.getResourceId(0, -1)));
                } else {
                    if (val > centerVal && val <= mLimitSplitter.get(pos).endPoint) {

                        seekBar.setProgress(mLimitSplitter.get(pos).endPoint);
                        seekBar.setThumb(getResources().getDrawable(imgs.getResourceId(pos, -1)));

                    } else {
                        seekBar.setProgress(mLimitSplitter.get(pos).startPoint);
                        seekBar.setThumb(getResources().getDrawable(imgs.getResourceId(pos, -1)));
                    }
                }
            }


        });

    }


    public void centerValue(int startVal, int endVal){
        int val=0;
        val = endVal - startVal;
        int val2 = val/2;
        centerVal = startVal+val2;
        txt3.setText("CenterValue   "+centerVal);
    }

    public int valueOf_i_(int progress, int position){

        int startVAl;
        int endVal;
        if(progress<centerVal)
            startVAl= mLimitSplitter.get(position).startPoint;
        else
            endVal =  mLimitSplitter.get(position).endPoint;


        boolean key1 =  mLimitSplitter.containsValue(mLimitSplitter.get(position).startPoint);
        boolean key2 =mLimitSplitter.containsValue(mLimitSplitter.get(position).endPoint);

        Set<Integer> key = mLimitSplitter.keySet();
        for(int j=0 ; j<key.size();j++) {
            System.out.println("K E Y S :::::" + key.toString());
            if(key.equals(mLimitSplitter.get(position).id)){
                System.out.println("K E Y S VALUE :::::" + mLimitSplitter.get(position).startPoint+" ## "+mLimitSplitter.get(position).endPoint);

            }
        }

        for (Object o : mLimitSplitter.keySet()) {
            if (mLimitSplitter.get(o).equals(mLimitSplitter.get(position).id)) {
                System.out.println("K E Y S VALUE :::::" + mLimitSplitter.get(o).startPoint+" >> "+mLimitSplitter.get(o).endPoint);
            }
        }

//        if(key1 && key2)
//        int keyValue = mLimitSplitter.keySet();
//
        return 0;
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
