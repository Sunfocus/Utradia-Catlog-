package com.utradia.catalogueappv2.utils;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

/**
 * contains methods related to fragment transactions
 */
public class FragmentUtils {

    // replace fragment in a container
    public void replaceFragment(int container, FragmentManager manager, Fragment fragment,String tag) {
        manager.beginTransaction().replace(container, fragment,tag).commitAllowingStateLoss();
    }

    // add fragment in a container
    public void addFragment(int container, FragmentManager manager, Fragment fragment) {
        manager.beginTransaction().add(container, fragment).commitAllowingStateLoss();
    }
}
