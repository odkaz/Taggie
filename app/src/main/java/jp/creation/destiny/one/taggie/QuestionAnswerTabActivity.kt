package jp.creation.destiny.one.taggie

import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.os.Bundle
import android.os.PersistableBundle
import android.support.design.widget.TabLayout
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup

import kotlinx.android.synthetic.main.activity_question_answer_tab.*
import kotlinx.android.synthetic.main.fragment_question_answer_tab.view.*

class QuestionAnswerTabActivity: AppCompatActivity() {

    private lateinit var mTabAdapter: TabAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question_answer_tab)

        val mTabAdapter = TabAdapter(supportFragmentManager)
        val mViewPager = findViewById<ViewPager>(R.id.container)
        setupViewPager(mViewPager)

        val tabLayout = findViewById<TabLayout>(R.id.tabs)
        tabLayout.setupWithViewPager(mViewPager)
    }

    private fun setupViewPager(viewPager: ViewPager) {
        val adapter = TabAdapter(supportFragmentManager)
        val extras = intent.extras
        val qUid = extras.getString("qUid")
        val bundle = Bundle()
        bundle.putString("qUid", qUid)
        val questionFragment = QuestionListFragment()
        questionFragment.arguments = bundle
        val answerFragment = AnswerListFragment()
        answerFragment.arguments = bundle

        adapter.addFragment(questionFragment, "Question")
        adapter.addFragment(answerFragment, "Answer 1")


        viewPager.adapter = adapter
    }
}