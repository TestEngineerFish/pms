package com.einyun.app.library.workorder.net.request;

public class aaa {


    /**
     * delay_number : 0
     * delay_sum_time : 0
     * delay_time : 5
     * apply_reason  : 申请5天延期
     */

    private int delay_number;
    private int delay_sum_time;
    private String delay_time;
    private String apply_reason;

    public int getDelay_number() {
        return delay_number;
    }

    public void setDelay_number(int delay_number) {
        this.delay_number = delay_number;
    }

    public int getDelay_sum_time() {
        return delay_sum_time;
    }

    public void setDelay_sum_time(int delay_sum_time) {
        this.delay_sum_time = delay_sum_time;
    }

    public String getDelay_time() {
        return delay_time;
    }

    public void setDelay_time(String delay_time) {
        this.delay_time = delay_time;
    }

    public String getApply_reason() {
        return apply_reason;
    }

    public void setApply_reason(String apply_reason) {
        this.apply_reason = apply_reason;
    }
}
