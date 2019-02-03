package com.guralnya.myvocabulary.ui.main;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.guralnya.myvocabulary.R;
import com.guralnya.myvocabulary.databinding.MainFragmentBinding;
import com.guralnya.myvocabulary.model.dto.DictionaryWord;
import com.guralnya.myvocabulary.ui.adapters.CardsViewPagerAdapter;

import io.realm.Realm;
import io.realm.RealmResults;


public class MainFragment extends Fragment {

    private MainViewModel mViewModel;
    private ViewPager mViewPager;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        MainFragmentBinding binding = DataBindingUtil.inflate(
                inflater, R.layout.main_fragment, container, false);
        ;

        Realm realm = Realm.getDefaultInstance();
        RealmResults<DictionaryWord> realmResults = realm.where(DictionaryWord.class).findAll();

        mViewPager = binding.viewPager;
        mViewPager.setAdapter(new CardsViewPagerAdapter(getActivity(), realmResults));

        return binding.getRoot();

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        // TODO: Use the ViewModel
    }

}
