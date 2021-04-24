package com.dicoding.azam.submission2_githubuser

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.dicoding.azam.submission2_githubuser.adapter.SectionPagerAdapter
import com.dicoding.azam.submission2_githubuser.databinding.ActivityDetailBinding
import com.dicoding.azam.submission2_githubuser.models.User
import com.dicoding.azam.submission2_githubuser.viewmodels.DetailUserViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_USERNAME = "extra_username"
        const val EXTRA_ID = "extra_id"
        const val EXTRA_AVATAR = "extra_avatar"
        const val EXTRA_HTML_URL = "extra_html_url"
    }

    private lateinit var binding: ActivityDetailBinding
    private lateinit var viewModel: DetailUserViewModel

    private fun shareUserDetail(sharedUser: Int) {
        if (sharedUser == R.id.share) {
            val login = viewModel.getUsersUsers().value?.login
            val id = viewModel.getUsersUsers().value?.id
            val avatarUrl = viewModel.getUsersUsers().value?.avatar_url
            val htmlUrl = viewModel.getUsersUsers().value?.html_url
            val name = viewModel.getUsersUsers().value?.name
            val company = viewModel.getUsersUsers().value?.company
            val location = viewModel.getUsersUsers().value?.location
            val publicRepos = viewModel.getUsersUsers().value?.public_repos
            val followers = viewModel.getUsersUsers().value?.followers
            val following = viewModel.getUsersUsers().value?.following
            val shareUser = resources.getString(R.string.github_user) +":"+ "\n" +
                    resources.getString(R.string.login_share) + ":" + "\t" + login + "\n" +
                    resources.getString(R.string.id_share) + ":" + "\t" + id.toString() + "\n" +
//                    resources.getString(R.string.avatar_url_share) + ":" + "\t" + avatarUrl + "\n" +
                    resources.getString(R.string.html_url_share)  + ":" + "\t" + htmlUrl + "\n" +
                    resources.getString(R.string.name_share) + ":" + "\t" + name + "\n" +
                    resources.getString(R.string.company_share) + ":" + "\t" + company + "\n" +
                    resources.getString(R.string.location_share) + ":" + "\t" + location + "\n" +
                    resources.getString(R.string.public_repos_share) + ":" + "\t" + publicRepos.toString() + "\n" +
                    resources.getString(R.string.followers_share) + ":" + "\t" + followers.toString() + "\n" +
                    resources.getString(R.string.following_share)  + ":" + "\t" + following.toString() + "\n" +
                    Glide.with(this)
                        .load(avatarUrl)
                        .centerCrop()
                        .into(gambar_user)
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareUser)
            shareIntent.type = "text/html"
            startActivity(Intent.createChooser(shareIntent, getString(R.string.using_share)))

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra(EXTRA_USERNAME)
        val id = intent.getIntExtra(EXTRA_ID, 0)
        val avatarUrl = intent.getStringExtra(EXTRA_AVATAR)
        val htmlUrl = intent.getStringExtra(EXTRA_HTML_URL)
        val bundle = Bundle()
        bundle.putString(EXTRA_USERNAME, username)
        bundle.putInt(EXTRA_ID, id)
        bundle.putString(EXTRA_AVATAR, avatarUrl)
        bundle.putString(EXTRA_HTML_URL, htmlUrl)

        val sectionPagerAdapter = SectionPagerAdapter(this, supportFragmentManager, bundle)
        binding.apply {
            viewPager.adapter = sectionPagerAdapter
            tabs.setupWithViewPager(viewPager)
        }

        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(DetailUserViewModel::class.java)

        viewModel.setUsersDetail(username.toString())
        viewModel.getUsersUsers().observe(this, {
            if (it != null) {
                binding.apply {
                    nameUser.text = if (it.name != null) { it.name } else (it.login)
                    usernameUser.text = getString(R.string.simbol_at, if (it.login != null) { it.login } else getString(R.string.kosong))
                    companyUser.text = if (it.company != null) { it.company } else getString(R.string.kosong)
                    locationUser.text = if (it.location != null) { it.location } else getString(R.string.kosong)
                    repositoryUser.text = it.public_repos.toString()
                    followersUser.text = it.followers.toString()
                    followingUser.text = it.following.toString()
                    urlUser.setOnClickListener { view ->
                        val url = htmlUrl
                        val intent = Intent(Intent.ACTION_VIEW)
                        intent.data = Uri.parse(url)
                        startActivity(intent)
                    }

                    Glide.with(this@DetailActivity)
                        .load(it.avatar_url)
                        .centerCrop()
                        .into(gambarUser)

                    if (supportActionBar != null) {
                        title = it.login
                        supportActionBar?.setDisplayHomeAsUpEnabled(true)
                        supportActionBar?.elevation = 0f
                    }
                }
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu_detail, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.share -> {
                onNavigateUp()
                shareUserDetail(item.itemId)

                return super.onOptionsItemSelected(item)
            }

            R.id.setting -> {
                Intent(this@DetailActivity, SettingActivity::class.java).also {
                    startActivity(it)
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}