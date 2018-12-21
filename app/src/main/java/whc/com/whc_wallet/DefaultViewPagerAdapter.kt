package whc.com.whc_wallet;

import android.support.v4.app.FragmentManager
import com.goyourfly.tabviewpager.BaseTabViewPagerFragment
import com.goyourfly.tabviewpager.TabViewPager
import wormhole.net.response.PropertyData


internal class DefaultViewPagerAdapter(fm: FragmentManager, funding: PropertyData)
    : TabViewPager.BaseTabViewPagerAdapter(fm) {
    val tabs = arrayOf("records", "owners")
    val mfunding = funding;
    override fun getItem(position: Int): BaseTabViewPagerFragment {
//        return DefaultViewPagerFragment()
        if (0 == position) {
            val bb = FundingDetailActivity();
            bb.setFunding(mfunding);
            return bb;
        } else{
            val bb = FundingDetailOwnersFragment();
            bb.setFunding(mfunding);
            return bb;
        }
    }

    override fun getCount(): Int {
        return tabs.size
    }

    override fun getPageTitle(position: Int): CharSequence {
        return tabs[position]
    }
}