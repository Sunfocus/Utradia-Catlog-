package com.utradia.catalogueappv2.module.productdetail.fragments.description

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.utradia.catalogueappv2.R
import com.utradia.catalogueappv2.module.productdetail.ProductDetailActivity
import kotlinx.android.synthetic.main.fragment_description.*


class DescriptionFragment:Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Get the view from fragmenttab1.xml
        return inflater.inflate(R.layout.fragment_description, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        updateUI()

    }

    private fun updateUI(){

        val webSettings = webView.settings
        val res = resources
        val fontSize = res.getInteger(R.integer.webview_font_size)
        webSettings.defaultFontSize = fontSize

        val response= ProductDetailActivity.response

        val text = ("<html><head>"
                + "<style type=\"text/css\">body{color: #000000; background-color: #ffffff;}"
                + "</style></head>"
                + "<body>"
                + response?.offer_details?.description
                + "</body></html>")



        webView.loadData(text, "text/html", "UTF-8")


    }


}