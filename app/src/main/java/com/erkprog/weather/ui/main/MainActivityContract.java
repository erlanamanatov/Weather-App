package com.erkprog.weather.ui.main;

public interface MainActivityContract {

  interface View {

    void showData();

    void showMessage(String message);

  }

  interface Presenter {
    void loadData();

    void bind(View v);

    void unbind();

    boolean isViewAttached();
  }
}
