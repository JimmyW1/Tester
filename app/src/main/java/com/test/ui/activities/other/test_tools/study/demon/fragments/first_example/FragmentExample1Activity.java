package com.test.ui.activities.other.test_tools.study.demon.fragments.first_example;

import android.app.Fragment;
import android.app.FragmentManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.test.ui.activities.R;
import com.test.ui.activities.other.test_tools.study.demon.fragments.first_example.dummy.DummyContent;

public class FragmentExample1Activity extends AppCompatActivity implements BookListFragment.OnSelectedBookChangeListener, BookTitleFragment.OnListFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_example1);
    }

    @Override
    public void onSelectedBookChanged(int bookId) {
        FragmentManager fragmentManager = getFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.fragmentDescriptions);

    }

    @Override
    public void onListFragmentInteraction(DummyContent.DummyItem item) {

    }
}
