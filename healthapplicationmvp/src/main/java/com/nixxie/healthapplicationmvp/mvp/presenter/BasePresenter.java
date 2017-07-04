package com.nixxie.healthapplicationmvp.mvp.presenter;

import com.nixxie.healthapplicationmvp.mvp.view.BaseView;

public interface BasePresenter<T extends BaseView> {

    void onStart();

    void onStop();

    void onPause();

    void onResume();

    void onDestroy();

    void attachView(T view);

    void detachView();
}
