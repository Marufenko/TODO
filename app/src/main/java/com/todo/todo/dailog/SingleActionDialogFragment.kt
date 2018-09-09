package com.todo.todo.dailog

import android.app.DialogFragment
import android.os.Bundle

class SingleActionDialogFragment : DialogFragment() {

    interface Listener {

        fun onPositiveDialogButtonClicked(dialogTag: String, extras: Bundle)
    }
}