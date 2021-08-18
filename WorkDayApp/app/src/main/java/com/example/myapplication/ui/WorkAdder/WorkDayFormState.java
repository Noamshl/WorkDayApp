package com.example.myapplication.ui.WorkAdder;

import androidx.annotation.Nullable;

public class WorkDayFormState {
    @Nullable
    private Integer jobError;
    @Nullable
    private Integer hourError;
    @Nullable
    private Integer dateError;
    private boolean isDataValid;

    WorkDayFormState(@Nullable Integer jobError, @Nullable Integer hourError,@Nullable Integer dateError) {
        this.jobError = jobError;
        this.hourError = hourError;
        this.dateError = dateError;
        this.isDataValid = false;
    }

    WorkDayFormState(boolean isDataValid) {
        this.jobError = null;
        this.hourError = null;
        this.dateError = null;
        this.isDataValid = isDataValid;
    }

    @Nullable
    public Integer getJobError() {
        return jobError;
    }

    @Nullable
    public Integer getHourError() {
        return hourError;
    }

    @Nullable
    public Integer getDateError() {
        return dateError;
    }

    boolean isDataValid() {
        return isDataValid;
    }
}
