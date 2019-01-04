package com.utradia.catalogueappv2.module.productdetail.fragments.overview

import android.content.Context
import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.utradia.catalogueappv2.R
import com.utradia.catalogueappv2.base.UtradiaApplication
import com.utradia.catalogueappv2.constants.ValueConstants
import com.utradia.catalogueappv2.model.HomeResponse
import com.utradia.catalogueappv2.model.ProductDetailResponse
import com.utradia.catalogueappv2.model.Thumbnails
import com.utradia.catalogueappv2.module.loginsignup.login.LoginActivity
import com.utradia.catalogueappv2.module.productdetail.optioncolors.OptionColorsActivity
import com.utradia.catalogueappv2.module.productdetail.optionsizes.OptionSizeActivity
import com.utradia.catalogueappv2.module.productdetail.ProductDetailActivity
import com.utradia.catalogueappv2.module.productdetail.fragments.description.OnYouMayClicked
import com.utradia.catalogueappv2.module.productdetail.fragments.description.YouMayAdapter
import com.utradia.catalogueappv2.module.productdetail.productoptions.ProductOptionsActivity
import com.utradia.catalogueappv2.utils.*
import org.greenrobot.eventbus.EventBus
import javax.inject.Inject
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.SnapHelper
import com.utradia.catalogueappv2.model.FavouriteResponse
import com.utradia.catalogueappv2.module.imageZoomView.ZoomImageActivity
import jp.wasabeef.recyclerview.animators.ScaleInAnimator
import kotlinx.android.synthetic.main.fragment_overview.*


class OverViewFragment : Fragment(), OverView, OnThumbClicked, OnYouMayClicked {

    override fun onYouMayItemClicked(id: String, name: String) {
        startActivity(ProductDetailActivity.createIntent(activity!!.applicationContext, id, name, false))
    }

    override fun onAddedToWishList(response: FavouriteResponse) {
        ProductDetailActivity.response!!.offer_details.favourite = 1
        imgNotFavourite.visibility = View.GONE
        imgFavourite.visibility = View.VISIBLE
        listener!!.itemFav(1)
    }

    override fun onRemovedWishList(response: FavouriteResponse) {
        ProductDetailActivity.response!!.offer_details.favourite = 0
        imgNotFavourite.visibility = View.VISIBLE
        imgFavourite.visibility = View.GONE
        listener!!.itemFav(0)
    }

    override fun onWishListError(error: String) {
        mAppUtils.showErrorToast(error)
    }

    override fun showProgress() {
        mProgressBarHandler.showProgress()
    }

    override fun hideProgress() {
        mProgressBarHandler.hideProgress()
    }

    override fun onError(t: Throwable) {

    }

    override fun onException(message: String) {

    }


    @Inject
    lateinit var mAppUtils: AppUtils
    @Inject
    lateinit var mProgressBarHandler: ProgressBarHandler
    @Inject
    lateinit var mPrefs: PreferenceManager
    @Inject
    lateinit var mPresenter: OverViewPresenter

    private lateinit var mProductId: String
    private val mImageList = ArrayList<Thumbnails>()
    private lateinit var mImagesAdapter: ImagesAdapter

    private val mZoomImageList = ArrayList<String>()

    private var listener: OnShareItem? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Get the view from fragmenttab1.xml
        val view = inflater.inflate(R.layout.fragment_overview, container, false)
        /* dependency injection */
        (activity?.application as UtradiaApplication).appComponent?.inject(this)
        mPresenter.injectDependencies(activity!!)
        mPresenter.attachView(this)
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initClicks()
        updateUI()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnShareItem) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    private fun initClicks() {
        imgNotFavourite.setOnClickListener {
            if (mPrefs.isUserLoggedIn) {
                if (mAppUtils.isInternetConnected)
                    mPresenter.addToWishList(mProductId, mPrefs.userId)
                else
                    mAppUtils.showSnackBar(activity!!.window.decorView.rootView, getString(R.string.toast_network_not_available))
            } else
                startActivity(LoginActivity.createIntent(activity!!, ValueConstants.PRODUCT_DETAIL, ValueConstants.HIDE_SKIP))
        }

        imgFavourite.setOnClickListener {
            if (mAppUtils.isInternetConnected)
                mPresenter.removeFromWishList(mProductId, mPrefs.userId)
            else
                mAppUtils.showSnackBar(activity!!.window.decorView.rootView, getString(R.string.toast_network_not_available))
        }

        imgShareProduct.setOnClickListener {
            listener!!.itemShare()
        }

        txtMore.setOnClickListener {
            EventBus.getDefault().post(MessageEvent())
        }


        imgProductImage.setOnClickListener {

            startActivity(Intent(activity, ZoomImageActivity::class.java).putExtra("imageList", mZoomImageList)
                    .putExtra("clsName", "Product Images"))
        }


        txtProductOptions.setOnClickListener {
            when {
                ProductDetailActivity.response?.offer_details?.variant_group_type!!.equals("1", true) -> {
                    startActivity(ProductOptionsActivity.createIntent(activity!!))
                    activity!!.overridePendingTransition(R.anim.slide_in_up, R.anim.do_nothing)
                }
                ProductDetailActivity.response?.offer_details?.variant_group_type!!.equals("2", true) -> {
                    startActivity(OptionColorsActivity.createIntent(activity!!))
                    activity!!.overridePendingTransition(R.anim.slide_in_up, R.anim.do_nothing)
                }
                ProductDetailActivity.response?.offer_details?.variant_group_type!!.equals("3", true) -> {
                    startActivity(OptionSizeActivity.createIntent(activity!!))
                    activity!!.overridePendingTransition(R.anim.slide_in_up, R.anim.do_nothing)
                }
            }
        }
    }

    private fun updateUI() {

        val response = ProductDetailActivity.response
        mProductId = response?.offer_details!!.id

        loadImage(response.offer_details.image)

        txtProductName.text = response.offer_details?.title

        /*Setting prices
        * */
        val text2 = mPrefs.currencySymbol + " " + response.offer_details.discount
        txtQty.text = text2

        if (!response.offer_details.discounted_price.equals("", true)) {
            val text1 = mPrefs.currencySymbol + " " + response.offer_details.discounted_price
            txtPrice.text = text1
            txtQty.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG

            val discount = response.offer_details.discount
            val discounted_price = response.offer_details.discounted_price
            val mPercentage = (discount.toInt() - discounted_price.toInt())
            val mFinalPercentage = (mPercentage * 100) / discount.toInt()
            txtDiscountPercentage.text = mFinalPercentage.toString() + "%"
        } else {
            txtDiscountPercentage.visibility = View.INVISIBLE
        }

        /* if(response.offer_details.discount.isEmpty()){

         }
         else{

         }*/


        txtMakeModel.text = response.offer_details?.model

        val text = "(" + response.offer_details?.raters.toString() + ")"
        txtRaters.text = text

        ratingBar2.rating = response.offer_details?.rating!!.toFloat()

        /*
        checking for favourite
        * */
        if (response.offer_details.favourite == 1) {
            imgNotFavourite.visibility = View.GONE
            imgFavourite.visibility = View.VISIBLE
        } else {
            imgNotFavourite.visibility = View.VISIBLE
            imgFavourite.visibility = View.GONE
        }

        updateThumbnails(response)

        /*updating description*/
        txtDescription.text = Html.fromHtml(response.offer_details?.description)


        if (response.similar_offers != null && response.similar_offers.size > 0) {

            txtYouMayAlsoLike.visibility = View.VISIBLE
            val layoutManager = LinearLayoutManager(activity, LinearLayout.HORIZONTAL, false)
            rvYouMayAlso.layoutManager = layoutManager

            val snapHelper = LinearSnapHelper()
            snapHelper.attachToRecyclerView(rvYouMayAlso)

            val mYouMayAdapter = YouMayAdapter(activity!!.applicationContext, response.similar_offers, this)
            // val animatorAdapter = ScaleInAnimatorAdapter<YouMayAdapter.ViewHolder>(mYouMayAdapter, rvYouMayAlso)
            rvYouMayAlso.itemAnimator = ScaleInAnimator()
            rvYouMayAlso.adapter = mYouMayAdapter
        }

        updateProductOptions(response)
    }

    private fun updateProductOptions(response: ProductDetailResponse) {

        val type: String = response.offer_details.variant_group_type
        when {
            type.equals("1", true) -> txtProductOptions.visibility = View.VISIBLE
            type.equals("2", true) -> txtProductOptions.visibility = View.VISIBLE
            type.equals("3", true) -> txtProductOptions.visibility = View.VISIBLE
            else -> txtProductOptions.visibility = View.GONE
        }
    }

    private fun updateThumbnails(response: ProductDetailResponse) {

        mImageList.clear()
        mZoomImageList.clear()
        if (response.offer_details.image != "") {
            val thumb = Thumbnails()
            thumb.isSelected = true
            thumb.url = response.offer_details.image
            mImageList.add(thumb)
            mZoomImageList.add(response.offer_details.image)
        }

        if (response.offer_details.image2 != "") {
            val thumb = Thumbnails()
            thumb.isSelected = false
            thumb.url = response.offer_details.image2
            mImageList.add(thumb)
            mZoomImageList.add(response.offer_details.image2)
        }


        if (response.offer_details.image3 != "") {
            val thumb = Thumbnails()
            thumb.isSelected = false
            thumb.url = response.offer_details.image3
            mImageList.add(thumb)
            mZoomImageList.add(response.offer_details.image3)
        }


        if (response.offer_details.image4 != "") {
            val thumb = Thumbnails()
            thumb.isSelected = false
            thumb.url = response.offer_details.image4
            mImageList.add(thumb)
            mZoomImageList.add(response.offer_details.image4)
        }

        if (mImageList.size > 0) {
            val layoutManager = LinearLayoutManager(activity, LinearLayout.HORIZONTAL, false)
            rvThumbnails.layoutManager = layoutManager

            mImagesAdapter = ImagesAdapter(activity!!.applicationContext, mImageList, this)
            rvThumbnails.adapter = mImagesAdapter
        }

    }

    override fun onThumbSelected(url: String, pos: Int) {

        loadImage(url)
        for (item in mImageList) {
            item.isSelected = false
        }
        mImageList[pos].isSelected = true


        mImagesAdapter.setItems(mImageList)
        mImagesAdapter.notifyDataSetChanged()


    }

    private fun loadImage(url: String?) {
        Glide.with(activity!!.applicationContext)
                .applyDefaultRequestOptions(RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC))
                .load(url)
                .into(imgProductImage)
    }

    interface OnShareItem {
        fun itemShare()
        fun itemFav(status: Int)
    }


}