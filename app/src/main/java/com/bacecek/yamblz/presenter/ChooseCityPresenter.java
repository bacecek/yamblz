package com.bacecek.yamblz.presenter;

import android.support.annotation.NonNull;

import com.bacecek.yamblz.App;
import com.bacecek.yamblz.data.presentation.City;
import com.bacecek.yamblz.data.repository.places.PlacesRepository;
import com.bacecek.yamblz.data.repository.settings.SettingsManager;
import com.bacecek.yamblz.ui.BaseView;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

/**
 * Created by vandrikeev on 26.07.17.
 */
public class ChooseCityPresenter extends BasePresenter<ChooseCityPresenter.ChooseCityView> {

    public interface ChooseCityView extends BaseView {

        void showLoading();

        void hideLoading();

        void addSuggestions(List<City> suggestions);

        void clearSuggestions();

        void closeDialog();
    }

    private Disposable subscription;

    @Inject
    PlacesRepository repository;

    @Inject
    SettingsManager settingsManager;

    public ChooseCityPresenter() {
        App.getAppComponent().inject(this);
    }

    public void onChooseCity(String cityId) {
        ChooseCityView view = getView();
        if (view != null) {
            view.closeDialog();
        }
        repository.loadAndSaveCoordsForCity(cityId);
    }

    public void onTextChanged(@NonNull Observable<CharSequence> textChangeObservable) {
        ChooseCityView view = getView();
        subscription = textChangeObservable
                .doOnNext((it) -> {
                    if (view != null) {
                        view.clearSuggestions();
                        view.showLoading();
                    }
                })
                .flatMapSingle((it) -> repository.getSuggestions(it.toString()))
                .subscribe(
                        suggestions -> {
                            if (view != null) {
                                view.addSuggestions(suggestions);
                                view.hideLoading();
                            }
                        },
                        error -> {
                            if (view != null) {
                                view.hideLoading();
                            }
                        }
                );
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (subscription != null) {
            subscription.dispose();
            subscription = null;
        }
    }
}
