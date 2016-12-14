package uk.co.ribot.androidboilerplate.ui.main;

import uk.co.ribot.androidboilerplate.ui.base.BasePresenter;

/**
 * Created by pp on 14/12/16.
 */
public class DevicePresenter extends BasePresenter<DeviceUi> {
    public DevicePresenter() {
    }

    public void showUi(String text) {
        getMvpView().showText(text);
    }
}
