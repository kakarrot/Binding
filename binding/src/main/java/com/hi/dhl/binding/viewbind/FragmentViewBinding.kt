package com.hi.dhl.binding.viewbind

import android.util.Log
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.viewbinding.ViewBinding
import com.hi.dhl.binding.base.FragmentDelegate
import com.hi.dhl.binding.ext.bindMethod
import com.hi.dhl.binding.ext.inflateMethod
import java.lang.Exception
import kotlin.reflect.KProperty

/**
 * <pre>
 *     author: dhl
 *     date  : 2020/12/11
 *     desc  :
 * </pre>
 */
class FragmentViewBinding<T : ViewBinding>(
    classes: Class<T>,
    private val fragment: Fragment
) : FragmentDelegate<T>(fragment) {

    private val layoutInflater = classes.inflateMethod()
    private val bindView = classes.bindMethod()

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
                    Log.e("fragment-view-binding", e.message ?: "layoutInflater error!!!")
                    // 兼容 thisRef.layoutInflater 特殊情况下为 null 抛出异常的情况
                    layoutInflater.invoke(null, LayoutInflater.from(thisRef.activity)) as T
                }
            } else {
                bindView.invoke(null, thisRef.view) as T
            }

            return bind.apply { viewBinding = this }
        }
    }
}