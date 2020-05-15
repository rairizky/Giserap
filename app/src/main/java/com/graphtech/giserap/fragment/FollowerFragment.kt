package com.graphtech.giserap.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.graphtech.giserap.R
import kotlinx.android.synthetic.main.fragment_follower.*
import org.jetbrains.anko.support.v4.toast

/**
 * A simple [Fragment] subclass.
 */
class FollowerFragment : Fragment() {

    private val ARG_USERNAME = "username"

    fun newInstance(username: String) : FollowerFragment {
        val fragment = FollowerFragment()
        val bundle = Bundle()
        bundle.putString(ARG_USERNAME, username)
        fragment.arguments = bundle
        return fragment
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_follower, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var username = "Can't get Username! Please contact Developer, Thank You."
        if (arguments != null) {
            username = arguments?.getString(ARG_USERNAME) as String
        }

        tvFollower.text = username
    }

}
