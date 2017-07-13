package com.bacecek.yamblz.ui.widget;

import android.content.Context;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.bacecek.yamblz.R;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Denis Buzmakov on 12.07.2017.
 * <buzmakov.da@gmail.com>
 */

public class SwitchTemperature extends FrameLayout {

    @IntDef
    @Retention(RetentionPolicy.SOURCE)
    public @interface State {
        int CELSIUS = 0;
        int FAHRENHEIT = 1;
    }

    @State
    private int mState;

    @BindView(R.id.txt_celsius)
    TextView mTxtCelsius;
    @BindView(R.id.txt_fahrenheit)
    TextView mTxtFahrenheit;

    public SwitchTemperature(@NonNull Context context) {
        super(context);
        init(context);
    }

    public SwitchTemperature(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SwitchTemperature(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.view_switch_temp, this, true);
        ButterKnife.bind(this);
        setState(State.CELSIUS);
    }

    public void setState(@State int state) {
        mState = state;
        updateViewStates();
    }

    public void switchState() {
        mState = mState == State.CELSIUS ? State.FAHRENHEIT : State.CELSIUS;
        updateViewStates();
    }

    @State
    public int getState() {
        return mState;
    }

    private void updateViewStates() {
        mTxtCelsius.setSelected(mState == State.CELSIUS);
        mTxtFahrenheit.setSelected(mState == State.FAHRENHEIT);
    }
}
