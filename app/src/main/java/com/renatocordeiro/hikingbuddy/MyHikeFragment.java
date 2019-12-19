package com.renatocordeiro.hikingbuddy;


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

import static com.renatocordeiro.hikingbuddy.Utils.print;
/**
 * Created by renatosc on 4/13/18.
 */

public class MyHikeFragment extends Fragment {
    private String TAG  = Utils.TAG;

    private TextView mDuration;
    private TextView mStepsCount;

    private Button mBtStart;
    private Button mBtStop;

    private Timer currTimer;


    // ------------------------------------------
    // -- View listeners

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.my_hike_fragment, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Utils.setContext(this.getContext());

        // initialize views
        //mContent = view.findViewById(R.id.fragment_my_hike);
        mDuration = (TextView) view.findViewById(R.id.tvDuration);
        mStepsCount = (TextView) view.findViewById(R.id.tvStepsCount);


        mDuration.setText("00:00:00");
        mStepsCount.setText("0");
        //mContent.setBackgroundColor(mColor);

        mBtStart = (Button) view.findViewById(R.id.btStart);
        mBtStart.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                btStartHandler(v);
            }
        });

        mBtStop = (Button) view.findViewById(R.id.btStop);
        mBtStop.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                btStopHandler(v);
            }
        });



        Boolean isHiking = PrefUtils.getCurrentHikeId() == null ? false: true;
        print(isHiking);
        if (isHiking) {
            startScreenRefreshLoopTimer();
            ServiceManager.startStepCounter();
        }

        updateButtonsStatus();
    }


    @Override
    public void onResume() {
        super.onResume();
        print("on Resume");
    }

    @Override
    public void onStop() {
        super.onStop();
        print("on Stop!!");
        stopScreenRefreshLoopTimer();
        updateButtonsStatus();
    }

    public void updateButtonsStatus(){
        boolean isRunning = PrefUtils.getCurrentHikeId() == null ? false : true;
        mBtStart.setEnabled(!isRunning);
        mBtStop.setEnabled(isRunning);
    }


    public void btStartHandler(View vw) {
        Log.d(TAG,"btStartHandler");
        HikeModel hike = new HikeModel("my_hike");
        startScreenRefreshLoopTimer();

        ServiceManager.startStepCounter();
        updateButtonsStatus();

        if (ServiceManager.hasStepSensor() == false) {
            mStepsCount.setText("n/a");
            Toast.makeText(getContext(), "Your phone does not have Step sensor, so that feature will not work", Toast.LENGTH_LONG).show();
        }
    }

    public void btStopHandler(View vw) {
        Log.d(TAG,"btStopHandler");
        stopScreenRefreshLoopTimer();

        ServiceManager.stopStepCounter();

        PrefUtils.clearHikeInfo();

        updateButtonsStatus();
    }

    public void btResetHandler(View vw) {
    }




    private void startScreenRefreshLoopTimer(){
        if(currTimer != null) {
            return;
        }
        currTimer = new Timer();

        TimerTask timerLoopTask = new TimerTask() {

            @Override
            public void run() {
                HikeModel hike = HikeModel.getCurrentInstance();
                final Long duration = hike.getDuration();
                final Integer stepCounter = hike.getStepCount();

                Activity act = getActivity();

                if (act != null ) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Integer hour = (int) Math.floor(duration / 3600);
                            Integer min = (int) Math.floor((duration - hour * 3600) / 60);
                            Integer sec = (int) (duration % 60);

                            String elapsed = String.format("%02d:%02d:%02d", hour, min, sec);
                            mDuration.setText(elapsed);
                            if (stepCounter != null) {
                                mStepsCount.setText(stepCounter.toString());
                            }
                        }
                    });
                }

            }
        };
        currTimer.scheduleAtFixedRate(timerLoopTask, 0, 500);
    }

    private void stopScreenRefreshLoopTimer(){
        if (currTimer != null) {
            currTimer.cancel();
            currTimer = null;
        }
    }

}
