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
import kotlinx.android.synthetic.main.fragment_follower.*
import org.jetbrains.anko.support.v4.toast

/**
 * A simple [Fragment] subclass.
 */
class FollowerFragment : Fragment(), FollowView {

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

    private lateinit var followPresenter: FollowPresenter
    private lateinit var username: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (arguments != null) {
            username = arguments?.getString(ARG_USERNAME) as String
        }

        // request
        followPresenter = FollowPresenter(this)
        followPresenter.getFollower(username)
    }

    override fun onShowLoading() {
        shimmerFollower.visibility = View.VISIBLE
        shimmerFollower.startShimmer()
    }

    override fun onHideLoading() {
        shimmerFollower.stopShimmer()
        shimmerFollower.visibility = View.GONE
    }

    override fun onSuccessFollow(data: List<FollowResponse?>?) {
        rvFollower.layoutManager = LinearLayoutManager(activity)
        rvFollower.adapter = FollowAdapter(activity, data)
    }

    override fun onErrorFollower(message: String) {
        toast(message)
    }

}
