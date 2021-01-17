package com.rynn.apps.view

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.rynn.apps.R
import com.rynn.apps.model.UserItem
import com.rynn.apps.viewmodel.ListUserAdapter
import com.rynn.apps.viewmodel.MainActViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var listDataUser: ArrayList<UserItem> = ArrayList()
    private lateinit var listAdapter: ListUserAdapter
    private lateinit var mainViewModel: MainActViewModel

    companion object {
        val TAG = MainActivity::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listAdapter = ListUserAdapter(listDataUser)
        mainViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(MainActViewModel::class.java)
        viewConfig()
        getDataGit()
        searchUsers()
        configMainActViewModel(listAdapter)
    }

    private fun viewConfig() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        listAdapter.notifyDataSetChanged()
        recyclerView.adapter = listAdapter
    }

    private fun getDataGit() {
        mainViewModel.getGitApi(applicationContext)
        showLoading(true)
    }

    private fun configMainActViewModel(adapter: ListUserAdapter) {
        mainViewModel.listUser().observe(this, Observer { listDataUser ->
            if (listDataUser != null) {
                adapter.setData(listDataUser)
                showLoading(false)
            }
        })
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            loadingProgress.visibility = View.VISIBLE
        } else {
            loadingProgress.visibility = View.INVISIBLE
        }
    }

    private fun searchUsers() {
        find_user.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query?.isNotEmpty()!!) {
                    listDataUser.clear()
                    viewConfig()
                    mainViewModel.searchUser(query, applicationContext)
                    showLoading(true)
                    configMainActViewModel(listAdapter)
                } else {
                    return true
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                return false
            }
        })


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

}