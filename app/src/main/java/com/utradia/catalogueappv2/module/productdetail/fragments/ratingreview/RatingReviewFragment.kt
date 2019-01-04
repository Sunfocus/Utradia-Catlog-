package com.utradia.catalogueappv2.module.productdetail.fragments.ratingreview

import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import butterknife.ButterKnife
import com.utradia.catalogueappv2.R
import com.utradia.catalogueappv2.base.BaseFragment
import com.utradia.catalogueappv2.base.UtradiaApplication
import com.utradia.catalogueappv2.model.ProductReviewListResponse
import com.utradia.catalogueappv2.module.productdetail.ProductDetailActivity
import com.utradia.catalogueappv2.utils.PreferenceManager
import com.utradia.catalogueappv2.utils.ProgressBarAnimation
import jp.wasabeef.recyclerview.animators.ScaleInAnimator
import kotlinx.android.synthetic.main.fragment_rating_reviews.*
import javax.inject.Inject


class RatingReviewFragment: BaseFragment(),RatingReviewView {


    @Inject
    lateinit var mRatingReviewPresenter: RatingReviewPresenter
    @Inject
    lateinit var mPrefs: PreferenceManager

    private var mAdapter: RatingReviewListAdapter? = null

    @Inject
    lateinit var mprogressAnimation: ProgressBarAnimation

    override val layoutId: Int
        get() = R.layout.fragment_rating_reviews

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        (activity!!.application as UtradiaApplication).appComponent?.inject(this)

        // Get the view from fragmenttab1.xml
        val view = inflater.inflate(R.layout.fragment_rating_reviews, container, false)
        setUnBinder(ButterKnife.bind(this, view))
        mRatingReviewPresenter.injectDependencies(activity!!)
        mRatingReviewPresenter.attachView(this)
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getProductReview()
    }


    private fun getProductReview() {

        val response= ProductDetailActivity.response

        if (mAppUtils.isInternetConnected)
            mRatingReviewPresenter.getReviewList(response!!.offer_details.id)
        else
            mAppUtils.showSnackBar(activity!!.window.decorView.rootView, getString(R.string.toast_network_not_available))
    }


    override fun onDestroyView() {
        super.onDestroyView()
        mRatingReviewPresenter.cancelAllAsync()
    }

    override fun onReviewFound(reviewData: ProductReviewListResponse) {


        val layoutManager1 = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        rvReviewRatings.layoutManager = layoutManager1

        val itemDecoration = DividerItemDecoration(activity, androidx.recyclerview.widget.DividerItemDecoration.VERTICAL)
        rvReviewRatings.addItemDecoration(itemDecoration)

        if (mAdapter == null) {
            mAdapter = RatingReviewListAdapter(this.activity!!, reviewData)
        }


        if (reviewData.data.size > 0) {

            if (mAdapter != null) {
                //val animatorAdapter = ScaleInAnimatorAdapter<androidx.recyclerview.widget.RecyclerView.ViewHolder>(mAdapter!!, rvReviewRatings)
                rvReviewRatings.itemAnimator= ScaleInAnimator()
                rvReviewRatings.adapter = mAdapter

                mAdapter!!.notifyDataSetChanged()
            }
            rvReviewRatings.visibility = View.VISIBLE
            txtNoReviews.visibility = View.GONE

            setRating(reviewData)
            overallRating.visibility=View.VISIBLE

        } else {
            rvReviewRatings.visibility = View.GONE
            txtNoReviews.visibility = View.VISIBLE
            overallRating.visibility=View.GONE

        }

    }

    private fun setRating(reviewData: ProductReviewListResponse) {
        tx_userRating.text = reviewData.offer_data[0].rating
        ratingBar.rating = reviewData.offer_data[0].rating.toFloat()
        txt_ratingCount.text=reviewData.total_ratings+" Ratings"

        txt_star_five_count.text = reviewData.five_star

        setAnimation(progressBar_five, reviewData.five_star.toFloat(),reviewData.total_ratings.toFloat())

        txt_star_four_count.text = reviewData.four_star
        setAnimation(progressBar_four, reviewData.four_star.toFloat(),reviewData.total_ratings.toFloat())

        txt_star_three_count.text = reviewData.three_star
        setAnimation(progressBar_three, reviewData.three_star.toFloat(),reviewData.total_ratings.toFloat())


        txt_star_two_count.text = reviewData.two_star
        setAnimation(progressBar_two, reviewData.two_star.toFloat(),reviewData.total_ratings.toFloat())

        txt_star_one_count.text = reviewData.one_star
        setAnimation(progressBar_one, reviewData.one_star.toFloat(),reviewData.total_ratings.toFloat())
    }

    override fun onErrorOccur(error: String) {

        rvReviewRatings.visibility = View.GONE
        txtNoReviews.visibility = View.VISIBLE
    }

    private fun setAnimation(progressBar: ProgressBar, ratingcount: Float, ratingtotal: Float) {

        val anim = ProgressBarAnimation()
        anim.setAnimation(progressBar, ratingcount, ratingtotal)
        anim.duration = 1000
        progressBar.startAnimation(anim)
    }
}