package whc.com.whc_wallet

import android.content.Intent
import android.databinding.DataBindingUtil
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import com.google.gson.Gson
import com.goyourfly.tabviewpager.TabViewPager
import com.zhy.autolayout.utils.AutoUtils
import whc.com.whc_wallet.adapter.FundDetailAdapter
import whc.com.whc_wallet.databinding.FundDetailHeaderBinding
import wormhole.net.response.PropertyData
import java.util.*

class MainActivity : BaseActivity() {
    private var mRecyclerView: RecyclerView? = null
    private var mAdapter: FundDetailAdapter? = null
    private var mFunding: PropertyData? = null
    private var mShowOpera: Boolean = false;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fund_detail_new)
        setTilte(getString(R.string.fund_detail))
        getDatas()

    }

    private fun getDatas() {
        val fundingJson = intent.getStringExtra(FundingDetailActivity.EXTRA_FUNDING)
        mShowOpera = intent.getBooleanExtra(FundingDetailActivity.EXTRA_SHOW_OPERALY, true)
        mFunding = Gson().fromJson(fundingJson, PropertyData::class.java)
        mAdapter = FundDetailAdapter(ArrayList(), this)

        val tabViewPager = findViewById<TabViewPager>(R.id.tabViewPager)
        val binding = DataBindingUtil.inflate<FundDetailHeaderBinding>(LayoutInflater.from(this), R.layout.fund_detail_header, null, false)
        binding.propertyData = mFunding;
        val holder = HeaderViewHolder(binding, mShowOpera)
        val colors = resources.getColorStateList(R.color.viewpager_tab)
        TabViewPager.Builder
                .with(tabViewPager)
                .header(binding.root)
                .parallax(false)
                .dispatchTouch(true)
                .adapter(DefaultViewPagerAdapter(supportFragmentManager, mFunding ?: PropertyData()))
                .build()
        val tab = tabViewPager.getTabLayout();
        tab.setTabTextColors(-0x595144, -0xf39901)
        tab.setSelectedTabIndicatorColor(-0xf39901);
    }

    fun fetchNet(funding: PropertyData) {

//        val call = Retrofit.getRetrofit().service.txOfFunding(funding.propertyid, 0, 20)
//        call.enqueue(object : Callback<BaseResponse<FundingTxData>> {
//            override fun onResponse(call: Call<BaseResponse<FundingTxData>>, response: Response<BaseResponse<FundingTxData>>) {
//                if (!Utils.dealCommonNetRes(response)) return
//                val fundingList = response.body()!!.result.transactions
//                mAdapter.setHeaderData(mFunding)
//                mAdapter.updateData(fundingList)
//                mRecyclerView.setAdapter(mAdapter)
//            }
//
//            override fun onFailure(call: Call<BaseResponse<FundingTxData>>, t: Throwable) {
//
//            }
//        })

    }


    inner class HeaderViewHolder(val binding: FundDetailHeaderBinding, showOperaLy: Boolean) : RecyclerView.ViewHolder(binding.root) {

        private val mChangeUserBtn: ImageButton
        private val mTxBtn: ImageButton
        private val mAirDropBtn: ImageButton
        private val mSeoBtn: ImageButton
        private val mDistroyBtn: ImageButton
        private val mCloseBtn: ImageButton
        private val mJoinCrowdBtn: Button
        private val mTypeTv: TextView
        private val mDeadlineTip: TextView
        private val mDeadlineTv: TextView
        private val mPerUnitTip: TextView
        private val mPerUnitTv: TextView
        private val mEarlyBonusTip: TextView
        private val mEarlyBonusTv: TextView
        private val mSoldTip: TextView
        private val mSoldTv: TextView

        init {
            AutoUtils.autoSize(binding.root)
            val operateLy: View = binding.operateLy ?: binding.root;
            operateLy.setVisibility(if (showOperaLy) View.VISIBLE else View.GONE)
            mChangeUserBtn = operateLy.findViewById<View>(R.id.changeUserBtn) as ImageButton
            mTxBtn = operateLy.findViewById<View>(R.id.txBtn) as ImageButton
            mAirDropBtn = operateLy.findViewById<View>(R.id.airDropBtn) as ImageButton
            mSeoBtn = operateLy.findViewById<View>(R.id.seoBtn) as ImageButton
            mDistroyBtn = operateLy.findViewById<View>(R.id.distroyBtn) as ImageButton
            mCloseBtn = operateLy.findViewById<View>(R.id.closeBtn) as ImageButton
            mTypeTv = binding.root.findViewById(R.id.typeTv)
            mJoinCrowdBtn = binding.root.findViewById(R.id.joinCrowdBtn)

            mDeadlineTip = binding.root.findViewById<View>(R.id.deadlineTip) as TextView
            mDeadlineTv = binding.root.findViewById<View>(R.id.deadlineTv) as TextView
            mPerUnitTip = binding.root.findViewById<View>(R.id.perUnitTip) as TextView
            mPerUnitTv = binding.root.findViewById<View>(R.id.perUnitTv) as TextView
            mEarlyBonusTip = binding.root.findViewById<View>(R.id.earlyBonusTip) as TextView
            mEarlyBonusTv = binding.root.findViewById<View>(R.id.earlyBonusTv) as TextView
            mSoldTip = binding.root.findViewById<View>(R.id.soldTip) as TextView
            mSoldTv = binding.root.findViewById<View>(R.id.soldTv) as TextView


            binding.urlTv.setOnClickListener { v ->
                val intent = Intent()
                intent.data = Uri.parse("" + (v as TextView).text)
                intent.action = Intent.ACTION_VIEW
                try {
                    this@MainActivity.startActivity(intent)
                } catch (e: Exception) {

                }
            }
            mSeoBtn.setOnClickListener {
                val it = Intent(this@MainActivity, SeoActivity::class.java)
                it.putExtra(FundingDetailActivity.EXTRA_FUNDING, Gson().toJson(mFunding))
                this@MainActivity.startActivity(it)
            }
            mChangeUserBtn.setOnClickListener {
                val it = Intent(this@MainActivity, ChangeOwnerActivity::class.java)
                it.putExtra(FundingDetailActivity.EXTRA_FUNDING, Gson().toJson(mFunding))
                this@MainActivity.startActivity(it)
            }
            mTxBtn.setOnClickListener {
                val it = Intent(this@MainActivity, SendTokenActivity::class.java)
                it.putExtra(FundingDetailActivity.EXTRA_FUNDING, Gson().toJson(mFunding))
                this@MainActivity.startActivity(it)
            }
            mAirDropBtn.setOnClickListener {
                val it = Intent(this@MainActivity, AirDropActivity::class.java)
                it.putExtra(FundingDetailActivity.EXTRA_FUNDING, Gson().toJson(mFunding))
                this@MainActivity.startActivity(it)
            }
            mDistroyBtn.setOnClickListener {
                val it = Intent(this@MainActivity, DestroyFundActivity::class.java)
                it.putExtra(FundingDetailActivity.EXTRA_FUNDING, Gson().toJson(mFunding))
                this@MainActivity.startActivity(it)
            }
            mCloseBtn.setOnClickListener {
                val it = Intent(this@MainActivity, CloseFundActivity::class.java)
                it.putExtra(FundingDetailActivity.EXTRA_FUNDING, Gson().toJson(mFunding))
                this@MainActivity.startActivity(it)
            }
            mJoinCrowdBtn.setOnClickListener {
                val it = Intent(this@MainActivity, JoinCrowdActivityT::class.java)
                it.putExtra(FundingDetailActivity.EXTRA_FUNDING, Gson().toJson(mFunding))
                this@MainActivity.startActivity(it)
            }

            setType(mFunding?.propertyType ?: 1)
        }

        fun setType(type: Int) {
            when (type) {
                PropertyData.CROWD_FUND -> {
                    mSeoBtn.visibility = View.GONE
                    mDistroyBtn.visibility = View.GONE
                    mCloseBtn.visibility = View.VISIBLE
                    mTypeTv.setText(R.string.fund_crowd)
                    if (!this@MainActivity.mShowOpera) mJoinCrowdBtn.visibility = View.VISIBLE
                }
                PropertyData.MANAGABLE_FUND -> {
                    mSeoBtn.visibility = View.VISIBLE
                    mDistroyBtn.visibility = View.VISIBLE
                    mCloseBtn.visibility = View.GONE

                    mDeadlineTip.visibility = View.GONE
                    mDeadlineTv.visibility = View.GONE
                    mPerUnitTip.visibility = View.GONE
                    mPerUnitTv.visibility = View.GONE
                    mEarlyBonusTip.visibility = View.GONE
                    mEarlyBonusTv.visibility = View.GONE
                    mSoldTip.visibility = View.GONE
                    mSoldTv.visibility = View.GONE

                    mTypeTv.setText(R.string.fund_managable)
                }
                PropertyData.FIXED_FUND -> {
                    mSeoBtn.visibility = View.GONE
                    mDistroyBtn.visibility = View.GONE
                    mCloseBtn.visibility = View.GONE
                    mTypeTv.setText(R.string.fund_fixed)

                    mDeadlineTip.visibility = View.GONE
                    mDeadlineTv.visibility = View.GONE
                    mPerUnitTip.visibility = View.GONE
                    mPerUnitTv.visibility = View.GONE
                    mEarlyBonusTip.visibility = View.GONE
                    mEarlyBonusTv.visibility = View.GONE
                    mSoldTip.visibility = View.GONE
                    mSoldTv.visibility = View.GONE
                }
            }

        }

    }


}
