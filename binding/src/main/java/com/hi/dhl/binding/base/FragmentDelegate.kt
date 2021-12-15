package com.hi.dhl.binding.base

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.viewbinding.ViewBinding
import com.hi.dhl.binding.ext.observerWhenCreated
import kotlin.properties.ReadOnlyProperty

/**
 * <pre>
 *
 *     author: dhl
 *     date  : 2020/12/15
 *     desc  :
 * </pre>
 */
abstract class FragmentDelegate<T : ViewBinding>(
    fragment: Fragment
) : ReadOnlyProperty<Fragment, T> {

    protected var viewBinding: T? = null

    init {
        /**
         * 感谢 architecture-components-samples 提供的思路
         *
         * 原处理方案是监听 viewLifecycle的生命周期在 onDestroyView时 设置 binding为空，但如果在
         * onDestroyView有释放view的操作，会导致无法找到 binding 报错（或者二次初始化）
         * 详情查看 [issue][https://github.com/android/architecture-components-samples/issues/56]
         */
        fragment.lifecycle.observerWhenCreated {
            val fragmentManager = fragment.parentFragmentManager
            fragmentManager.registerFragmentLifecycleCallbacks(object :
                FragmentManager.FragmentLifecycleCallbacks() {
                override fun onFragmentViewDestroyed(fm: FragmentManager, f: Fragment) {
                    super.onFragmentViewDestroyed(fm, f)
                    if (f == fragment) {
                        destroyed()
                        fragmentManager.unregisterFragmentLifecycleCallbacks(this)
                    }
                }
            }, false)
        }
    }

    private fun destroyed() {
        Log.e("binding", "取消")
        viewBinding = null
    }
}