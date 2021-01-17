package com.rynn.apps.view


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.rynn.apps.R
import com.rynn.apps.model.UserFollowing
import com.rynn.apps.model.UserItem
import com.rynn.apps.viewmodel.FollowingViewModel
import com.rynn.apps.viewmodel.ListUserFollowingAdapter
import kotlinx.android.synthetic.main.fragment_following.*


class FollowingFragment : Fragment() {

    companion object {
        val TAG = FollowingFragment::class.java.simpleName
        const val EXTRA_DETAIL = "extra_detail"
    }

    private var listUser: ArrayList<UserFollowing> = ArrayList()
    private lateinit var adapter: ListUserFollowingAdapter
    private lateinit var followingViewModel: FollowingViewModel
    private var bool: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_following, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = ListUserFollowingAdapter(listUser)
        followingViewModel = ViewModelProvider(
            this, ViewModelProvider.NewInstanceFactory()
        ).get(FollowingViewModel::class.java)

        val userFollowing = activity?.intent?.getParcelableExtra<UserItem>(EXTRA_DETAIL) as UserItem
        config()

        activity?.applicationContext?.let {
            followingViewModel.getGitApiFollowing(
                it,
                userFollowing.username.toString()
            )
        }
        showLoading(true)

        followingViewModel.getListFollowing().observe(requireActivity(), Observer { listDataUser ->
            if (listDataUser != null) {
                adapter.setData(listDataUser)
                showLoading(false)
            }
        })
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            progressBarFollowing.visibility = View.VISIBLE
        } else {
            progressBarFollowing.visibility = View.INVISIBLE
        }
    }

    private fun config() {
        recyclerViewFollowing.layoutManager = LinearLayoutManager(activity)
        recyclerViewFollowing.setHasFixedSize(true)
        recyclerViewFollowing.adapter = adapter
    }

}