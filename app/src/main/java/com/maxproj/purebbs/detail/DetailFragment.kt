package com.maxproj.purebbs.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.maxproj.purebbs.R
import kotlinx.android.synthetic.main.detail_fragment.*


class DetailFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val safeArgs: DetailFragmentArgs by navArgs()
        val postId = safeArgs.postId

        val v: View = inflater.inflate(R.layout.detail_fragment, container, false)
        val tv = v.findViewById<TextView>(R.id.textView)
        tv.setText("postId: $postId")

        return v
    }

}
