package com.bacecek.yamblz.presenter;

import android.arch.lifecycle.ViewModel;

import com.bacecek.yamblz.ui.BaseView;

import javax.annotation.Nullable;

/**
 * Created by Denis Buzmakov on 14.07.2017.
 * <buzmakov.da@gmail.com>
 */

public class BasePresenter<T extends BaseView> extends ViewModel {

    private T view;

    public void onAttach(T view) {
        this.view = view;
    }

    public void onDetach() {
        this.view = null;
    }

    public void onDestroy() {

    }

    protected @Nullable T getView() {
        return this.view;
    }

}
