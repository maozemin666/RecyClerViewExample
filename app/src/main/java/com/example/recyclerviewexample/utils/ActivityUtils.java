package com.example.recyclerviewexample.utils;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import static com.google.common.base.Preconditions.checkNotNull;

public class ActivityUtils {

    public static void addFragmentToActivity(FragmentManager fragmentManager,
                                             Fragment fragment, int frameId) {
        checkNotNull(fragment);
        checkNotNull(fragmentManager);
        FragmentTransaction fragmentTransaction =  fragmentManager.beginTransaction();
        fragmentTransaction.add(frameId,fragment);
        fragmentTransaction.commit();
    }
}
