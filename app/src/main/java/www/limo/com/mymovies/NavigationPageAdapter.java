package www.limo.com.mymovies;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class NavigationPageAdapter extends FragmentPagerAdapter {

    public int tabcount;

    public NavigationPageAdapter(FragmentManager fm,int tabcount) {
        super(fm);
        this.tabcount=tabcount;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0:
                return new MyList();
            case 1:
                return new WatchList();
            case 2:
                return new NewShows();
                default:
                    return new MyList();
        }
    }

    @Override
    public int getCount() {
        return tabcount;
    }
}
