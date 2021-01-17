package com.rynn.apps.view

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.rynn.apps.R
import com.rynn.apps.model.UserItem
import com.rynn.apps.viewmodel.SectionsPagerAdapter
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity(), View.OnClickListener {
    companion object {
        const val EXTRA_DETAIL = "extra_detail"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        if (supportActionBar != null) {
            supportActionBar?.title = resources.getString(R.string.detail_user)
        }

        viewPagerConfig()
        setUser()
    }

    private fun viewPagerConfig() {
        val viewPagerDetail = SectionsPagerAdapter(this, supportFragmentManager)
        view_pager.adapter = viewPagerDetail
        tabs.setupWithViewPager(view_pager)
        supportActionBar?.elevation = 0f
    }

    @SuppressLint("SetTextI18n")
    private fun setUser() {
        val dataUser = intent.getParcelableExtra<UserItem>(EXTRA_DETAIL) as UserItem
        Glide.with(this)
            .load(dataUser.avatar)
            .apply(RequestOptions().override(150, 150))
            .into(avatar_detail)
        real_name_detail.text = dataUser.name
        username_detail.text = "Github.com/${dataUser.username}"
        company_detail.text = "${getString(R.string.company)} ${dataUser.company}"
        location_detail.text = "${getString(R.string.location)} ${dataUser.location}"
        following_detail.text = "${getString(R.string.following)} ${dataUser.following}"
        follower_detail.text = "${getString(R.string.follower)} ${dataUser.followers}"
        user_repo_detail.text = "${getString(R.string.user_repo)} ${dataUser.repository}"
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_change_settings -> {
                val moveIntent = Intent(this, SetingsActivity::class.java)
                startActivity(moveIntent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onClick(v: View?) {
    }


}