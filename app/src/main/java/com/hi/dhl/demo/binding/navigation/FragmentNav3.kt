package com.hi.dhl.demo.binding.navigation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.hi.dhl.binding.ext.viewbind
import com.hi.dhl.demo.binding.R
import com.hi.dhl.demo.binding.databinding.FragmentNav3Binding

/**
 * <pre>
 *     author: dhl
 *     date  : 2020/12/21
 *     desc  :
 * </pre>
 */
class FragmentNav3 : Fragment(R.layout.fragment_nav_3) {

    val binding: FragmentNav3Binding by viewbind()

//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//        return binding.root
//    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnJump.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_fragmentNav3_to_fragmentNav1)
        }
    }

}