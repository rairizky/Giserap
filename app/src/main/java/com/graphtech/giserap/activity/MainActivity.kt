package com.graphtech.giserap.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.graphtech.giserap.R
import com.graphtech.giserap.adapter.ListUserAdapter
import com.graphtech.giserap.model.User
import com.graphtech.giserap.model.UserData
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity

class MainActivity : AppCompatActivity() {

    private lateinit var rvUser: RecyclerView
    private var list: ArrayList<User> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // setup action bar
        setupNavigation()
        // load recyclerView Function
        loadItemUserRecycler()

        // searchView
        searchViewAction()

        // intent favorite
        intentFavoriteActivity.setOnClickListener {
            startActivity<FavoriteActivity>()
        }
    }

    private fun setupNavigation() {
        val actionBar = supportActionBar
        actionBar?.hide()
    }

    private fun loadItemUserRecycler() {
        rvUser = rvGitUser
        rvUser.setHasFixedSize(true)
        list.addAll(UserData.listData)
        rvUser.layoutManager = LinearLayoutManager(this)
        val listUserAdapter = ListUserAdapter(this, list)
        rvUser.adapter = listUserAdapter
    }

    private fun searchViewAction() {
        svUsers.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                startActivity<SearchActivity>("query" to query.toString())
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }
}
