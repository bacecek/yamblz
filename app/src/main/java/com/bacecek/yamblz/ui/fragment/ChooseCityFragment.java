package com.bacecek.yamblz.ui.fragment;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.bacecek.yamblz.R;
import com.bacecek.yamblz.data.presentation.City;
import com.bacecek.yamblz.presenter.ChooseCityPresenter;
import com.bacecek.yamblz.ui.adapter.ChooseCityAdapter;
import com.jakewharton.rxbinding2.widget.RxTextView;

import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Created by vandrikeev on 22.07.17.
 */
public class ChooseCityFragment extends DialogFragment implements ChooseCityPresenter.ChooseCityView {

    private ChooseCityAdapter adapter;

    private ChooseCityPresenter presenter;

    private OnChooseCityListener listener;

    private Unbinder unbinder;

    @BindView(R.id.txt_input_query)
    EditText txtInputQuery;

    @BindView(R.id.rv_suggestions)
    RecyclerView rvSuggestions;

    @BindView(R.id.pb_loading)
    ProgressBar progressBar;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = city -> {
                OnChooseCityListener parentListener = (OnChooseCityListener) context;
                parentListener.onChooseCity(city);
                presenter.onChooseCity(city.getId());
            };
        } catch (ClassCastException e) {
            throw new ClassCastException(String.format("%s must implement OnChooseCityListener", context.toString()));
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_choose_city, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapter = new ChooseCityAdapter(listener);
        rvSuggestions.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvSuggestions.setAdapter(adapter);

        Observable<CharSequence> textChangeObservable = RxTextView.textChanges(txtInputQuery)
                .debounce(500, TimeUnit.MILLISECONDS)
                .filter(text -> text.length() > 0)
                .observeOn(AndroidSchedulers.mainThread());

        presenter = ViewModelProviders.of(this).get(ChooseCityPresenter.class);
        presenter.onAttach(this);
        presenter.onTextChanged(textChangeObservable);
    }

    @Override
    public void onStart() {
        super.onStart();
        Window window = getDialog().getWindow();
        if (window != null) {
            window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
        rvSuggestions.setVisibility(View.GONE);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
        rvSuggestions.setVisibility(View.VISIBLE);
    }

    @Override
    public void addSuggestions(List<City> suggestions) {
        adapter.add(suggestions);
    }

    @Override
    public void clearSuggestions() {
        adapter.clear();
    }

    @Override
    public void closeDialog() {
        dismiss();
    }

    public static ChooseCityFragment newInstance() {
        return new ChooseCityFragment();
    }

    public interface OnChooseCityListener {
        void onChooseCity(@NonNull City city);
    }
}
