package com.demo.taipeizoo


import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.demo.taipeizoo.databinding.ActivityMainBinding
import com.demo.taipeizoo.ui.view.AreaListFragment


/**
 * MainActivity
 *
 * Created by TeyaHong on 2021/8/4
 */
class MainActivity : AppCompatActivity() {

    private var backPressed: Long = 0

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        showFragment(AreaListFragment.newInstance(), AreaListFragment::class.java.simpleName)
    }

    /**
     * 顯示Fragment（沒動畫）
     *
     * @param fra fragment to be shown
     * @param tag fragment tag
     */
    private fun showFragment(fra: Fragment, tag: String) {
        val fragmentManager: FragmentManager = supportFragmentManager
        // check if the fragment is in back stack
        val fragmentPopped: Boolean = fragmentManager.popBackStackImmediate(tag, 0)
        if (fragmentPopped) {
            // fragment is pop from backStack
        } else {
            fragmentManager.beginTransaction()
                .replace(R.id.container, fra, tag)
                .addToBackStack(tag)
                .commit()
        }
    }

    /**
     * 顯示Fragment（有動畫）
     *
     * @param current current visible fragment
     * @param new     became to show fragment
     * @param tag     fragment tag
     */
    fun showFragmentWithTransition(
        current: Fragment,
        new: Fragment,
        tag: String
    ) {
        val fragmentManager = supportFragmentManager
        // check if the fragment is in back stack
        val fragmentPopped = fragmentManager.popBackStackImmediate(tag, 0)
        if (!fragmentPopped) {
            // fragment was not pop from backStack
            fragmentManager.beginTransaction()
                .setCustomAnimations(
                    R.anim.slide_in,
                    R.anim.fade_out,
                    R.anim.fade_in,
                    R.anim.slide_out
                )
                .hide(current)
                .add(R.id.container, new, tag)
                .addToBackStack(tag)
                .commit()
        }
    }

    /**
     * 回前一個fragment
     */
    private fun oneStepBack() {
        val fragmentManager = supportFragmentManager
        if (fragmentManager.backStackEntryCount >= 2) {
            fragmentManager.popBackStackImmediate()
            fragmentManager.beginTransaction().commit()
            if (fragmentManager.backStackEntryCount == 1) {
                binding.toolbar.navigationIcon = null
            }
        } else {
            doubleClickToExit()
        }
    }

    /**
     * 連續點擊back鍵
     */
    private fun doubleClickToExit() {
        if (backPressed + 2000 > System.currentTimeMillis())
            finish()
        else
            Toast.makeText(
                this@MainActivity,
                "Click again to exit",
                Toast.LENGTH_SHORT
            ).show()
        backPressed = System.currentTimeMillis()
    }

    override fun onBackPressed() {
        oneStepBack()
    }
}