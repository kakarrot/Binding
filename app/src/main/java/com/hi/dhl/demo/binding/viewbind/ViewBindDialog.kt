package com.hi.dhl.demo.binding.viewbind

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.Window
import androidx.lifecycle.Lifecycle
import com.hi.dhl.binding.ext.viewbind
import com.hi.dhl.demo.binding.R
import com.hi.dhl.demo.binding.databinding.DialogAppBinding

/**
 * <pre>
 *     author: dhl
 *     date  : 2020/12/12
 *     desc  :
 * </pre>
 */
class ViewBindDialog(context: Context, lifecycle: Lifecycle) : Dialog(context, R.style.AppDialog) {

    val binding: DialogAppBinding by viewbind(lifecycle)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        binding.apply {
            result.setText("ViewBindDialog")
            result.setOnClickListener {
                Log.e(TAG, "绑定 OnClickListener")
            }

            customView.onDialogClickListener = object : ViewBindCustomView.OnDialogClickListener {
                override fun onClick() {
                    Log.e(TAG, "绑定自定义的 OnDialogClickListener")
                }

            }
        }
    }

    companion object {
        private val TAG: String = "ViewBindDialog"
    }
}