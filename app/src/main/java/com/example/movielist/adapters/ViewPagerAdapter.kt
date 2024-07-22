package com.example.movielist.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.movielist.fragments.main.PopularMoviesFragment
import com.example.movielist.fragments.main.TopRatedFragment

private const val NUM_TABS = 2
class ViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {
        return NUM_TABS
    }

    override fun createFragment(position: Int): Fragment {
        when(position){
            0 -> return PopularMoviesFragment()
            1 -> return TopRatedFragment()
        }
        return PopularMoviesFragment()
    }
}