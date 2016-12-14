package uk.co.ribot.androidboilerplate.injection.module;

import android.content.Context;
import android.support.v4.app.Fragment;

import dagger.Module;
import dagger.Provides;
import uk.co.ribot.androidboilerplate.injection.ActivityContext;

@Module
public class FragmentModule {

    private Fragment mFragment;

    public FragmentModule(Fragment fragment) {
        mFragment = fragment;
    }

    @Provides
    Fragment provideFragment() {
        return mFragment;
    }

    @Provides
    @ActivityContext
    Context providesContext() {
        return mFragment.getActivity();
    }
}
