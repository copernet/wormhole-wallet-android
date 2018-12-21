package whc.com.whc_wallet;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.blankj.utilcode.util.KeyboardUtils;

public class OverviewParentFragment extends Fragment {

    private RadioGroup mTabRadioGroup;
    private RadioButton mOverAllBtn;
    private RadioButton mOverCrowdBtn;
    private EditText mSearchbox;
    private View mSearchIv;

    private OverviewFragment fragmentA;
    private OverviewValiadCrowdFragment fragmentB;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_overview_parent, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        fragmentA = new OverviewFragment();
        fragmentB = new OverviewValiadCrowdFragment();

        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.add(R.id.linearLayout2, fragmentA, fragmentA.getClass().getName());
        transaction.add(R.id.linearLayout3, fragmentB, fragmentB.getClass().getName());
        transaction.addToBackStack(null);
        transaction.commit();

        mTabRadioGroup = (RadioGroup) getActivity().findViewById(R.id.tabRadioGroup);
        mOverAllBtn = (RadioButton) getActivity().findViewById(R.id.overAllBtn);
        mOverCrowdBtn = (RadioButton) getActivity().findViewById(R.id.overCrowdBtn);
        mSearchbox = getActivity().findViewById(R.id.searchBox);
        mSearchIv = getView().findViewById(R.id.searchIv);

        mTabRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.overAllBtn:
                        mOverAllBtn.setTextColor(0xffffffff);
                        mOverCrowdBtn.setTextColor(0xff0C66FF);
                        showFragment(fragmentA);
                        break;
                    case R.id.overCrowdBtn:
                        mOverCrowdBtn.setTextColor(0xffffffff);
                        mOverAllBtn.setTextColor(0xff0C66FF);
                        showFragment(fragmentB);
                        break;

                }
            }
        });

        mSearchIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KeyboardUtils.hideSoftInput(v);
                if (fragmentA == mCurrentFragment) {
                    fragmentA.setSearchWord("" + mSearchbox.getText());
                } else {
                    fragmentB.setSearchWord("" + mSearchbox.getText());
                }
            }
        });

        mSearchbox.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_SEARCH || event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                    KeyboardUtils.hideSoftInput(v);
                    if (fragmentA == mCurrentFragment) {
                        fragmentA.setSearchWord("" + mSearchbox.getText());
                    } else {
                        fragmentB.setSearchWord("" + mSearchbox.getText());
                    }
                }
                return false;
            }
        });

        mTabRadioGroup.check(R.id.overAllBtn);

    }

    private Fragment mCurrentFragment;

    /**
     * 使用show() hide()切换页面
     * 显示fragment
     */
    private void showFragment(Fragment fg) {
        getView().findViewById(R.id.linearLayout2).setVisibility(fg == fragmentA ? View.VISIBLE : View.GONE);
        getView().findViewById(R.id.linearLayout3).setVisibility(fg == fragmentA ? View.GONE : View.VISIBLE);
        mCurrentFragment = fg;
    }

}
