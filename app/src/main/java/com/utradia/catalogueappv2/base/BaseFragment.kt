package com.utradia.catalogueappv2.base

import android.content.Context
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import butterknife.Unbinder
import com.utradia.catalogueappv2.mvpbase.MvpView
import com.utradia.catalogueappv2.utils.AppUtils
import javax.inject.Inject

abstract class BaseFragment : Fragment(), MvpView {


    lateinit var mUnBinder: Unbinder

    @Inject
    lateinit var mAppUtils: AppUtils

    @get:LayoutRes
    abstract val layoutId: Int

    private var mActivity: BaseActivity? = null




    fun setUnBinder(unBinder: Unbinder) {
        mUnBinder = unBinder
    }


    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is BaseActivity) {
            val activity = context as BaseActivity?
            this.mActivity = activity
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        mUnBinder.unbind()

    }


    /**
     * handle any error during any operation and display proper message to user
     */
    override fun onError(t: Throwable) {
        t.printStackTrace()
        mAppUtils.showErrorMessage(activity!!.window.decorView.rootView, t)
        //  ButterKnife.apply(mNameViews, mAppUtils.ENABLED, true);
    }

    /**
     * handle any logical error here and display message to user (Maybe in case of invalid credentials)
     *
     * @param message warning message to be displayed to user
     */
    override fun onException(message: String) {
        mAppUtils.showSnackBar(activity!!.window.decorView.rootView, message)
        // ButterKnife.apply(mNameViews, mAppUtils.ENABLED, true);
    }


    override fun showProgress() {
        mActivity!!.mProgressHandler.showProgress()
    }

    override fun hideProgress() {
        mActivity!!.mProgressHandler.hideProgress()
    }
}
