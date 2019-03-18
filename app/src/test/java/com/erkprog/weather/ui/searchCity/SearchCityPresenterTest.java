package com.erkprog.weather.ui.searchCity;

import com.erkprog.weather.data.entity.CityResponse;
import com.erkprog.weather.data.entity.Country;
import com.erkprog.weather.data.entity.GeoPosition;
import com.erkprog.weather.data.weatherRepository.ApiInterface;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.schedulers.ExecutorScheduler;
import io.reactivex.plugins.RxJavaPlugins;


import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class SearchCityPresenterTest {

  @Rule
  public MockitoRule mockitoRule = MockitoJUnit.rule();

  @Mock
  SearchCityContract.View view;

  @Mock
  ApiInterface api;

  SearchCityPresenter presenter;

  private static String TAG = "Testing";

  @Before
  public void setUp() {
    presenter = new SearchCityPresenter(api);
    presenter.bind(view);
  }

  @BeforeClass
  public static void setUpRxSchedulers() {
    Scheduler immediate = new Scheduler() {
      @Override
      public Disposable scheduleDirect(@NonNull Runnable run, long delay, @NonNull TimeUnit unit) {
        return super.scheduleDirect(run, 0, unit);
      }

      @Override
      public Scheduler.Worker createWorker() {
        return new ExecutorScheduler.ExecutorWorker(Runnable::run);
      }
    };

    RxJavaPlugins.setInitIoSchedulerHandler(scheduler -> immediate);
    RxJavaPlugins.setInitComputationSchedulerHandler(scheduler -> immediate);
    RxJavaPlugins.setInitNewThreadSchedulerHandler(scheduler -> immediate);
    RxJavaPlugins.setInitSingleSchedulerHandler(scheduler -> immediate);
    RxAndroidPlugins.setInitMainThreadSchedulerHandler(scheduler -> immediate);
  }

  @Test
  public void searchCityByName_WhenOnSuccessResponseAndViewIsAttached_ShouldShowListOfCities() {
    when(api.getCityByName(anyString(), anyString())).thenReturn(Observable.just(getMockList()));
    presenter.searchCityByName("bb");

    InOrder inOrder = Mockito.inOrder(view);
    inOrder.verify(view).onLoadingData();
    inOrder.verify(view).onDataLoaded();
    inOrder.verify(view).showFoundCities(anyList());
  }

  @Test
  public void searchCityByName_WhenResultIsEmpty_ShouldClearList() {
    when(api.getCityByName(anyString(), anyString())).thenReturn(Observable.just(Collections.EMPTY_LIST));
    presenter.searchCityByName("bb");

    InOrder inOrder = Mockito.inOrder(view);
    inOrder.verify(view).onLoadingData();
    inOrder.verify(view).onDataLoaded();
    inOrder.verify(view).clearList();
  }

  @Test
  public void searchCityByName_WhenOnErrorAndViewIsAttached_ShouldShowError() {
    when(api.getCityByName(anyString(), anyString())).thenReturn(Observable.error(new Exception("some exception")));
    presenter.searchCityByName("bb");

    InOrder inOrder = Mockito.inOrder(view);
    inOrder.verify(view).onLoadingData();
    inOrder.verify(view).onDataLoaded();
    inOrder.verify(view).showError();
  }

  private List<CityResponse> getMockList() {
    List<CityResponse> response = new ArrayList<>();
    CityResponse c1 = new CityResponse();
    c1.setCountry(getFakeCountry());
    c1.setEnglishName("name");
    c1.setGeoPosition(getFakeGeoposition());
    c1.setKey("key");
    c1.setVersion(new Random().nextInt());
    response.add(c1);
    return response;
  }

  private GeoPosition getFakeGeoposition() {
    return new GeoPosition(new Random().nextDouble(), new Random().nextDouble());
  }

  private Country getFakeCountry() {
    return new Country(String.valueOf(new Random().nextInt()), "some name");
  }

}