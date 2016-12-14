package uk.co.ribot.androidboilerplate.ui.main;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Toast;

import butterknife.ButterKnife;
import uk.co.ribot.androidboilerplate.R;
import uk.co.ribot.androidboilerplate.data.SyncService;
import uk.co.ribot.androidboilerplate.ui.base.BaseActivity;

public class MainActivity extends BaseActivity implements View.OnClickListener{

    private static final String EXTRA_TRIGGER_SYNC_FLAG =
            "uk.co.ribot.androidboilerplate.ui.main.MainActivity.EXTRA_TRIGGER_SYNC_FLAG";
    private boolean mPeakFlow = false;

//    @Inject RibotsAdapter mRibotsAdapter;

    private View mContainerView;
    private Snackbar mSnackBar;

    private ViewPager mViewPager;
    private MyFragmentPagerAdapter mFragmentPagerAdapter;

    private Handler mHandler = new Handler() {

        /**
         * Subclasses must implement this to receive messages.
         *
         * @param msg
         */
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1: {
                    togglePeakFlow();
                    Snackbar.make(mContainerView, "Snackbar test..." + mPeakFlow, Snackbar.LENGTH_SHORT).show();
                    break;
                }
            }
            super.handleMessage(msg);
        }
    };

    /**
     * Return an Intent to start this Activity.
     * triggerDataSyncOnCreate allows disabling the background sync service onCreate. Should
     * only be set to false during testing.
     */
    public static Intent getStartIntent(Context context, boolean triggerDataSyncOnCreate) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(EXTRA_TRIGGER_SYNC_FLAG, triggerDataSyncOnCreate);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityComponent().inject(this);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mContainerView = findViewById(R.id.container_view);
        mSnackBar = Snackbar.make(mContainerView, "Snackbar test...", Snackbar.LENGTH_SHORT);

        if (getIntent().getBooleanExtra(EXTRA_TRIGGER_SYNC_FLAG, true)) {
            startService(SyncService.getStartIntent(this));
        }

        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mFragmentPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mFragmentPagerAdapter);
        mViewPager.setCurrentItem(0);
        FragmentManager.enableDebugLogging(true);

        new TempAsyncTask().execute();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        Toast.makeText(this, "Delete " + v.getId() + " " + v.getTag(), Toast.LENGTH_SHORT).show();

    }

    class TempAsyncTask extends AsyncTask<Void, Void, Void> {

        /**
         * Override this method to perform a computation on a background thread. The
         * specified parameters are the parameters passed to {@link #execute}
         * by the caller of this task.
         * <p>
         * This method can call {@link #publishProgress} to publish updates
         * on the UI thread.
         *
         * @param params The parameters of the task.
         * @return A result, defined by the subclass of this task.
         * @see #onPreExecute()
         * @see #onPostExecute
         * @see #publishProgress
         */
        @Override
        protected Void doInBackground(Void... params) {
            try {
                while (true) {
                    Thread.sleep(10000);
                    Message m = Message.obtain();
                    m.what = 1;
                    mHandler.sendMessageDelayed(m, 200);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        /**
         * <p>Runs on the UI thread after {@link #doInBackground}. The
         * specified result is the value returned by {@link #doInBackground}.</p>
         * <p>
         * <p>This method won't be invoked if the task was cancelled.</p>
         *
         * @param aVoid The result of the operation computed by {@link #doInBackground}.
         * @see #onPreExecute
         * @see #doInBackground
         * @see #onCancelled(Object)
         */
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }

    public void togglePeakFlow() {
        mPeakFlow = !mPeakFlow;
        mFragmentPagerAdapter.togglePeakFlow(mViewPager);
    }
}
