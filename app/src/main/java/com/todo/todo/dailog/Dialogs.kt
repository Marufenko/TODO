package com.todo.todo.dailog

import android.support.v4.app.DialogFragment
import android.support.v4.app.FragmentManager

fun DialogFragment.showAllowingStateLoss(fragmentManager: FragmentManager, tag: String) {
    fragmentManager.beginTransaction()
            .add(this, tag)
            .commitAllowingStateLoss()
}