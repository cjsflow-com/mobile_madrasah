package com.example.man2superapp.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import com.example.man2superapp.R
import org.w3c.dom.Text

class SplashPagerAdapter(
    private val context: Context,
    private val images: List<Int>,
    private val titles: List<String>,
    private val descriptions: List<String>
) : PagerAdapter() {

    override fun getCount(): Int = images.size

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.splash_screen, container, false)

        // Temukan elemen elemen UI
        val imageView = view.findViewById<ImageView>(R.id.gambarSs)
        val titleText = view.findViewById<TextView>(R.id.tvJudul)
        val descText = view.findViewById<TextView>(R.id.tvDesc)

        // Ubah konten berdasarkan posisi
        imageView.setImageResource(images[position])
        titleText.text = titles[position]
        descText.text = descriptions[position]

        // Tambahkan view ke ViewPager
        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }
}