package com.haiprj.android_app_lib.mvp.presenter;


import com.haiprj.android_app_lib.mvp.model.DataModel;
import com.haiprj.android_app_lib.mvp.model.DataResult;
import com.haiprj.android_app_lib.mvp.view.ViewResult;

public class DataPresenter implements DataResult {

    protected ViewResult viewResult;
    protected DataModel dataModel;
    public DataPresenter(ViewResult viewResult) {
        this.viewResult = viewResult;
    }

    @Override
    public void onDataResultSuccess(String key, Object... objects) {
        viewResult.onViewAvailable(key, objects);
    }

    @Override
    public void onDataResultFailed(String mess) {
        viewResult.onViewNotAvailable(mess);
    }


}
