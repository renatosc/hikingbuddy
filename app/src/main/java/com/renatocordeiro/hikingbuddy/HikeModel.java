package com.renatocordeiro.hikingbuddy;

/**
 * Created by renatosc on 4/13/18.
 */

public class HikeModel {

    public static HikeModel currHike;

    public Long id;             // id of the hike
    public String name;         // name of the hike
    public Long startEpoch;     // epoch (System.currentTimeMillis() )
    public Integer stepCount;   // number of steps



    public HikeModel(String name) {

        this.name = name;
        this.startEpoch = System.currentTimeMillis();
        this.id = HikingDB.getInstance().newHike(this.name, this.startEpoch);

        PrefUtils.setCurrentHikeId(this.id);
        PrefUtils.setCurrentHikeStartTime(this.startEpoch);

        currHike = this;
    }


    public HikeModel() {
        this.id = PrefUtils.getCurrentHikeId();
        this.name = PrefUtils.getCurrentHikeName();
        this.startEpoch = PrefUtils.getCurrentHikeStartTime();
        this.stepCount = PrefUtils.getCurrentHikeStepCount();

        currHike = this;
    }

    public static HikeModel getCurrentInstance() {
        if (currHike == null) {
            currHike = new HikeModel();
        }
        return currHike;
    }


    // returns hike duration in seconds
    public Long getDuration(){
        Long epoch = System.currentTimeMillis();
        return new Long((epoch - startEpoch)/1000);
    }

    // returns hike steps so far
    public Integer getStepCount(){
        return stepCount;
    }

    // update hike steps
    public void setStepCount(Integer value){
        if (value != null) {
            stepCount = value;
            PrefUtils.setCurrentHikeStepCount(value);
        }
    }

    public void increaseStepCount(Integer amount){
        Integer totalCount = HikingDB.getInstance().increaseStepCountForHiking();
        setStepCount(totalCount);
    };

}
