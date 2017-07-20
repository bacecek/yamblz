package com.bacecek.yamblz.ui.fragment;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.bacecek.yamblz.R;

/**
 * Created by Denis Buzmakov on 15.07.2017.
 * <buzmakov.da@gmail.com>
 */

public class ChooseUpdateIntervalFragment extends DialogFragment {
    private OnChooseUpdateIntervalListener listener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (OnChooseUpdateIntervalListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnChooseUpdateIntervalListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new AlertDialog.Builder(getActivity())
                .setItems(R.array.array_intervals_titles, (dialog, which) -> {
                    int[] intervalArrayValues = getResources().getIntArray(R.array.array_intervals_values);
                    listener.onChooseInterval(intervalArrayValues[which]);
                    dialog.dismiss();
                })
                .setNegativeButton(R.string.cancel, (dialog, which) -> dialog.dismiss())
                .create();

    }
    public static ChooseUpdateIntervalFragment newInstance() {
        return new ChooseUpdateIntervalFragment();
    }

    public interface OnChooseUpdateIntervalListener {
        void onChooseInterval(int interval);
    }
}