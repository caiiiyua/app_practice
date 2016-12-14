package uk.co.ribot.androidboilerplate.injection.component;

import dagger.Subcomponent;
import uk.co.ribot.androidboilerplate.injection.module.FragmentModule;
import uk.co.ribot.androidboilerplate.ui.main.MainPresenter;

/**
 * This component inject dependencies to all Activities across the application
 */
@Subcomponent(modules = FragmentModule.class)
public interface FragmentComponent {

    void inject(MainPresenter presenter);

}
