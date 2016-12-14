package uk.co.ribot.androidboilerplate.ui.main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;
import android.view.ViewGroup;

/**
 * Created by pp on 14/12/16.
 */
public class MyFragmentPagerAdapter extends FragmentStatePagerAdapter {
    private static final int HOME = 0;
    private static final int ADH = 1;
    private static final int DEVICE = 2;
    private static final int PEAK = 3;
    private FragmentManager mFragmentManager;
    private boolean mIsPeakFlow = false;
    private boolean mIsLite = false;
    private DeviceFragment mDeviceFragment = new DeviceFragment();
    private DeviceFragment mHomeFragment = new HomeFragment();
    private DeviceFragment mAdhFragment = new AdhFragment();
    private DeviceFragment mPeakFlowFragment = new PeakFlowFragment();

    public MyFragmentPagerAdapter(FragmentManager supportFragmentManager) {
        super(supportFragmentManager);
        mFragmentManager = supportFragmentManager;
    }

    public MyFragmentPagerAdapter(FragmentManager fm, final boolean isLite) {
        this(fm);
        mIsLite = isLite;
    }

    public MyFragmentPagerAdapter(FragmentManager fm, final boolean isLite, final boolean isPeakFlow) {
        this(fm, isLite);
        mIsPeakFlow = isPeakFlow;
    }

    /**
     * Return the Fragment associated with a specified position.
     *
     * @param position
     */
    @Override
    public Fragment getItem(int position) {
        if (mIsLite) return mDeviceFragment;
        switch (position) {
            case HOME: return mHomeFragment;
            case ADH: return mAdhFragment;
            case DEVICE: return mDeviceFragment;
            case PEAK: {
                if (mIsPeakFlow) return mPeakFlowFragment;
            }
        }

        return null;
    }

    /**
     * Return the number of views available.
     */
    @Override
    public int getCount() {
        return mIsLite ? 1
                : mIsPeakFlow ? 4
                : 3;
    }

    public void togglePeakFlow(ViewGroup container) {
        mIsPeakFlow = !mIsPeakFlow;
        if (!mIsPeakFlow) {
            destroyItem(container, PEAK, mPeakFlowFragment);
        } else if (mPeakFlowFragment.isRemoving()) {
            instantiateItem(container, PEAK);
        }
        Log.d("FragmentManager", "isAdded:" + mPeakFlowFragment.isAdded()
                + " isResumed:" + mPeakFlowFragment.isResumed()
                + " isRemoving:" + mPeakFlowFragment.isRemoving()
                + " isDetached:" + mPeakFlowFragment.isDetached()
                + " isHidden:" + mPeakFlowFragment.isHidden()
                + " isVisible:" + mPeakFlowFragment.isVisible());
        notifyDataSetChanged();
    }
}
