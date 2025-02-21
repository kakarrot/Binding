package com.hi.dhl.demo.binding.navigation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.hi.dhl.binding.ext.viewbind
import com.hi.dhl.demo.binding.R
import com.hi.dhl.demo.binding.databinding.FragmentNav1Binding

/**
 * <pre>
 *     author: dhl
 *     date  : 2020/12/21
 *     desc  :
 * </pre>
 */
class FragmentNav1 : Fragment() {

    val binding: FragmentNav1Binding by viewbind()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnJump.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_fragmentNav1_to_fragmentNav2)
        }
    }

    override fun onDestroyView() {
        Log.e("FragmentNav1", "onDestroyView前")
        binding.btnJump.background = null
        super.onDestroyView()
        Log.e("FragmentNav1", "onDestroyView后")
    }

    override fun onDestroy() {
        Log.e("FragmentNav1", "onDestroy前")
        super.onDestroy()
        Log.e("FragmentNav1", "onDestroy后")
    }
}