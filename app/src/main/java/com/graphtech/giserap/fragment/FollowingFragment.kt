package com.graphtech.giserap.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager

import com.graphtech.giserap.R
import com.graphtech.giserap.adapter.FollowAdapter
import com.graphtech.giserap.model.FollowResponse
import com.graphtech.giserap.presenter.FollowPresenter
import com.graphtech.giserap.view.FollowView
import kotlinx.android.synthetic.main.fragment_following.*
import org.jetbrains.anko.support.v4.toast

/**
 * A simple [Fragment] subclass.
 */
class FollowingFragment : Fragment(), FollowView {

    private val ARG_USERNAME = "username"

    fun newInstance(username: String) : FollowingFragment {
        val fragment = FollowingFragment()
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
        return inflater.inflate(R.layout.fragment_following, container, false)
    }

    private lateinit var followPresenter: FollowPresenter
    private lateinit var username: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (arguments != null) {
            username = arguments?.getString(ARG_USERNAME) as String
        }

        // request
        followPresenter = FollowPresenter(this)
        followPresenter.getFollowing(username)
    }

    override fun onShowLoading() {
        shimmerFollowing.visibility = View.VISIBLE
        shimmerFollowing.startShimmer()
    }

    override fun onHideLoading() {
        shimmerFollowing.stopShimmer()
        shimmerFollowing.visibility = View.GONE
    }

    override fun onSuccessFollow(data: List<FollowResponse?>?) {
        rvFollowing.layoutManager = LinearLayoutManager(activity)
        rvFollowing.adapter = FollowAdapter(activity, data)
    }

    override fun onErrorFollower(message: String) {
        toast(message)
    }
}
