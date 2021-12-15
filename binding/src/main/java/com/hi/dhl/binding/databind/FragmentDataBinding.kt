package com.hi.dhl.binding.databind

import android.util.Log
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import com.hi.dhl.binding.base.FragmentDelegate
import com.hi.dhl.binding.ext.inflateMethod
import java.lang.Exception
import kotlin.reflect.KProperty

/**
 * <pre>
 *     author: dhl
 *     date  : 2020/12/10
 *     desc  :
 * </pre>
 */

class FragmentDataBinding<T : ViewDataBinding>(
    classes: Class<T>,
    private val fragment: Fragment,
    private var block: (T.() -> Unit)? = null
) : FragmentDelegate<T>(fragment) {

    private val layoutInflater = classes.inflateMethod()

    override fun getValue(thisRef: Fragment, property: KProperty<*>): T {
        return viewBinding?.run {
            return this

        } ?: let {

            val lifecycle = fragment.viewLifecycleOwner.lifecycle
            if (!lifecycle.currentState.isAtLeast(Lifecycle.State.INITIALIZED)) {
                Log.e("Binding Error", "Should not attempt to get bindings when Fragment views are destroyed.")
                //throw IllegalStateException("Should not attempt to get bindings when Fragment views are destroyed.")
            }

            val bind: T = if (thisRef.view == null) {
                try {
                    // 这里为了兼容在 navigation 中使用 Fragment
                    layoutInflater.invoke(null, thisRef.layoutInflater) as T
                } catch (e: Exception) {
                    e.printStackTrace()
                    // 兼容 thisRef.layoutInflater 特殊情况下为 null 抛出异常的情况
                    layoutInflater.invoke(null, LayoutInflater.from(thisRef.activity)) as T
                }
            } else {
                DataBindingUtil.bind(thisRef.requireView())!!
            }

            return bind.apply {
                viewBinding = this
                lifecycleOwner = fragment.viewLifecycleOwner
                block?.invoke(this)
                block = null
            }
        }
    }
}