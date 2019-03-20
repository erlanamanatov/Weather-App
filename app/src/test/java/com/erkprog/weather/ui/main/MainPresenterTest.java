package com.erkprog.weather.ui.main;

import android.location.Location;

import com.erkprog.weather.data.LocationHelper;
import com.erkprog.weather.data.entity.CityResponse;
import com.erkprog.weather.data.entity.Country;
import com.erkprog.weather.data.entity.DailyForecast;
import com.erkprog.weather.data.entity.ForecastDetailed;
import com.erkprog.weather.data.entity.GeoPosition;
import com.erkprog.weather.data.weatherRepository.ApiInterface;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.schedulers.ExecutorScheduler;
import io.reactivex.plugins.RxJavaPlugins;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class MainPresenterTest {

  @Rule
  public MockitoRule mockitoRule = MockitoJUnit.rule();

  @Mock
  MainActivityContract.View view;

  @Mock
  LocationHelper locationHelper;

  @Mock
  ApiInterface api;

  MainPresenter presenter;

  private static String TAG = "Testing";

  @Before
  public void setUp() {
    presenter = new MainPresenter(api, locationHelper);
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
  public void loadData_WhenOnSuccessAndViewIsAttached_ShouldShowData() {
    ForecastDetailed forecastDetailed = mock(ForecastDetailed.class);
    List<DailyForecast> listDaily = mock(List.class);
    when(forecastDetailed.getDailyForecasts()).thenReturn(listDaily);
    when(api.get5dayForecast(anyString(), anyString(), anyBoolean(), anyBoolean()))
        .thenReturn(Single.just(forecastDetailed));

    presenter.loadData("some key");
    InOrder inOrder = Mockito.inOrder(view);
    inOrder.verify(view).showProgress();
    inOrder.verify(view).dismissProgress();
    inOrder.verify(view).showData(anyList());
  }

  @Test
  public void loadData_WhenOnErrorAndViewIsAttached_ShouldShowError() {
    when(api.get5dayForecast(anyString(), anyString(), anyBoolean(), anyBoolean()))
        .thenReturn(Single.error(new Exception("some error")));

    presenter.loadData("some key");
    InOrder inOrder = Mockito.inOrder(view);
    inOrder.verify(view).showProgress();
    inOrder.verify(view).dismissProgress();
    inOrder.verify(view).displayError();
  }

  @Test
  public void getCurrentLocation_WhenCityFoundByLocationAndViewIsAttached_ShouldAddNewCity() {
    ArgumentCaptor<LocationHelper.OnLocationChangedListener> locationChangedListenerArgumentCaptor =
        ArgumentCaptor.forClass(LocationHelper.OnLocationChangedListener.class);
    Location location = mock(Location.class);
    when(location.getLatitude()).thenReturn(123.123);
    when(location.getLongitude()).thenReturn(123.123);
    when(api.getCityByGeoposition(anyString(), anyString())).thenReturn(Single.just(getFakeCityResponse()));

    presenter.getCurrentLocation();
    verify(locationHelper).getLocation(locationChangedListenerArgumentCaptor.capture());
    locationChangedListenerArgumentCaptor.getValue().onLocationChanged(location);

    InOrder inOrder = Mockito.inOrder(view);
    inOrder.verify(view).onGettingLocation();
    inOrder.verify(view).onLocationFound();
    inOrder.verify(view).addNewCity(any());
  }

  @Test
  public void getCurrentLocation_WhenOnError_ShouldShowGeopositionError() {
    ArgumentCaptor<LocationHelper.OnLocationChangedListener> locationChangedListenerArgumentCaptor =
        ArgumentCaptor.forClass(LocationHelper.OnLocationChangedListener.class);
    Location location = mock(Location.class);
    when(location.getLatitude()).thenReturn(123.123);
    when(location.getLongitude()).thenReturn(123.123);
    when(api.getCityByGeoposition(anyString(), anyString())).thenReturn(Single.error(new Exception("some error")));

    presenter.getCurrentLocation();
    verify(locationHelper).getLocation(locationChangedListenerArgumentCaptor.capture());
    locationChangedListenerArgumentCaptor.getValue().onLocationChanged(location);

    InOrder inOrder = Mockito.inOrder(view);
    inOrder.verify(view).onGettingLocation();
    inOrder.verify(view).onLocationFound();
    inOrder.verify(view).showGeopositionError(anyString());
  }

  private CityResponse getFakeCityResponse() {
    CityResponse c1 = new CityResponse();
    c1.setCountry(getFakeCountry());
    c1.setEnglishName("name");
    c1.setGeoPosition(getFakeGeoposition());
    c1.setKey("key");
    c1.setVersion(new Random().nextInt());
    return c1;
  }

  private GeoPosition getFakeGeoposition() {
    return new GeoPosition(new Random().nextDouble(), new Random().nextDouble());
  }

  private Country getFakeCountry() {
    return new Country(String.valueOf(new Random().nextInt()), "some name");
  }
}