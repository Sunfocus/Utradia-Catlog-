package com.utradia.catalogueappv2.module.orderdetail.item_review

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.RatingBar
import com.utradia.catalogueappv2.R
import com.utradia.catalogueappv2.base.BaseActivity
import com.utradia.catalogueappv2.base.UtradiaApplication
import com.utradia.catalogueappv2.utils.ImageUtility
import com.utradia.catalogueappv2.utils.PreferenceManager
import kotlinx.android.synthetic.main.activity_rate_review_item.*
import javax.inject.Inject


class RateReviewItemActivity : BaseActivity(), RateReviewView {


    @Inject
    lateinit var mRateReviewPresenter: RateReviewPresenter
    @Inject
    lateinit var mPrefs: PreferenceManager
    @Inject
    lateinit var mImageUtility: ImageUtility

    var product_id: String = ""
    var cart_id: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as UtradiaApplication).appComponent?.inject(this)

        mRateReviewPresenter.injectDependencies(this)
        mRateReviewPresenter.attachView(this)
        mAppUtils.changeStatusBarColor(this)

        setToolbar()

        // setting UI view
        mImageUtility.loadImage(intent.getStringExtra("product_image"), imgProduct)
        textProductName.text = intent.getStringExtra("product_name")
        product_id = intent.getStringExtra("product_id")
        cart_id = intent.getStringExtra("cart_id")




        rb_prod_rating.onRatingBarChangeListener = RatingBar.OnRatingBarChangeListener { ratingBar, rating, fromUser ->
            /*if (rating > 2) {
                text_rating.text = rating.toString()
            }*/

  /*          if(rating>2.0f) {*/
                text_rating.text = rating.toString()
   //         }



        }

/*
        var xDown= 0.0F

        rb_prod_rating.setOnTouchListener(View.OnTouchListener { v, event ->
            when (event?.action) {
                MotionEvent.ACTION_DOWN -> {
                    if(event.action == MotionEvent.ACTION_DOWN) {
                        // save down X coordinate
                        xDown = event.xActivity
                    } else if(event.action == MotionEvent.ACTION_UP) {
                        // if user moves do not move the finger, update RatingBar value
                        if(Math.abs(xDown - event.xActivity) < 5) {
                            return@OnTouchListener false
                        }
                    }
                }
            }

            v?.onTouchEvent(event) ?: true
        })*/





        btnSubmit.setOnClickListener {
            submitProductRating(product_id, cart_id, text_rating.text.toString(), etText.text.toString())
        }
    }





    private fun setToolbar() {
        toolbar.setNavigationIcon(R.drawable.ic_back)
        supportActionBar!!.setDisplayShowTitleEnabled(true)
        supportActionBar!!.title = getString(R.string.rate_review)

        toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    companion object {
        fun createIntent(context: Context, product_id: String, cart_id: String, product_name: String, product_image: String): Intent {
            return Intent(context, RateReviewItemActivity::class.java)
                    .putExtra("cart_id", cart_id)
                    .putExtra("product_id", product_id)
                    .putExtra("product_name", product_name)
                    .putExtra("product_image", product_image)
        }

    }


    private fun submitProductRating(productId: String, cart_id: String, rating: String, review: String) {
        if (mAppUtils.isInternetConnected)
        // mOrdersPresenter.getOrderList(mPrefs.userId,orderId)
            mRateReviewPresenter.addProductReview(mPrefs.userId, productId, cart_id, rating, review)
        else
            mAppUtils.showSnackBar(window.decorView.rootView, getString(R.string.toast_network_not_available))
    }


    override fun onDestroy() {
        super.onDestroy()
        mRateReviewPresenter.cancelAllAsync()
        mRateReviewPresenter.detachView()
    }


    override val layout: Int
        get() = R.layout.activity_rate_review_item

    override fun showToolbar(): Boolean {
        return true
    }

    override val views: List<View>?
        get() = null


    override fun onReviewAdded(sucessMessage: String) {

        mAppUtils.showSuccessToast(sucessMessage)
        finish()
    }

    override fun onErrorOccur(errorMessage: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}


