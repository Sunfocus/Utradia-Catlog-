package com.utradia.catalogueappv2.module.filter

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.utradia.catalogueappv2.R
import com.utradia.catalogueappv2.base.BaseActivity
import com.utradia.catalogueappv2.base.UtradiaApplication
import com.utradia.catalogueappv2.model.*
import com.utradia.catalogueappv2.model.custom.FilterDataModel
import com.utradia.catalogueappv2.model.custom.FilterListModel
import com.utradia.catalogueappv2.model.custom.PriceListModel
import com.utradia.catalogueappv2.model.custom.RatingListModel
import com.utradia.catalogueappv2.module.filter.adapter.FilterAdapter
import com.utradia.catalogueappv2.module.filter.module.brand.BrandFrag
import com.utradia.catalogueappv2.module.filter.module.category.CategoryFragment
import com.utradia.catalogueappv2.module.filter.module.price.PriceFrag
import com.utradia.catalogueappv2.module.filter.module.product_rating.Product_Rating_Frag
import com.utradia.catalogueappv2.module.filter.module.shop.ShopFrag
import jp.wasabeef.recyclerview.animators.ScaleInAnimator
import kotlinx.android.synthetic.main.activity_filter.*
import javax.inject.Inject

class FilterActivity : BaseActivity(), FilterView, BrandFrag.onBrandInteraction, ShopFrag.ShopInteraction, FilterAdapter.FilterCallback, Product_Rating_Frag.OnProductInteraction, PriceFrag.OnPriceInteraction, CategoryFragment.OnCategoryClick {
    @Inject
    lateinit var mFilterPresenter: FilterPresenter

    private var shopBundle = Bundle()
    private var brandBundle = Bundle()
    private var priceBundle = Bundle()
    private var categoryBundle = Bundle()
    private var ratingBundle = Bundle()

    private var shopFrag: ShopFrag? = null
    private var brandFrag: BrandFrag? = null
    private var priceFrag: PriceFrag? = null
    private var categoryFrag: CategoryFragment? = null
    private var ratingFrag: Product_Rating_Frag? = null

    private var mcatList = mutableListOf<Category>()

    private val mFilterBrand = mutableListOf<ProductsByCatResponse.BrandsBean>()
    private val mFilterShop = mutableListOf<ProductsByCatResponse.ShopsBean>()
    private val mFilterCategory = mutableListOf<String>()


    private val brandCount= mutableSetOf<String>()
    private val shopCount= mutableSetOf<String>()


    private var priceModel: PriceListModel? = null
    private var rateModel: RatingListModel? = null


    private lateinit var filterListModel: FilterDataModel


    private var filterModel: FilterListModel? = null

    private lateinit var mAdapter: FilterAdapter

    private var filterList = mutableListOf<FilterDataModel>()
    private var parentCatId = ""


    override val layout: Int
        get() = R.layout.activity_filter

    override fun showToolbar(): Boolean {
        return true
    }

    override val views: List<View>?
        get() = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /* dependency injection */
        (application as UtradiaApplication).appComponent?.inject(this)
        /*attaching view{@link LoginView} to our presenter*/
        mFilterPresenter.injectDependencies(this)
        mFilterPresenter.attachView(this)
        mAppUtils.changeStatusBarColor(this)

        setToolbar()
        settingLayout()

        if (intent!!.extras!!.containsKey("mCatId")) {
            parentCatId = intent!!.extras!!.getString("mCatId")!!
        }

        inttalizeData()

        if (intent!!.extras!!.containsKey("resultData")) {
            if (intent!!.extras!!.getParcelable<FilterListModel>("resultData") != null) {
                filterModel = intent!!.extras!!.getParcelable("resultData")!!

                settingFilterData(filterModel!!)
            }
        }

        btn_clear.setOnClickListener {
            clearBrands()
            clearCategory()
            clearPrice()
            clearRating()
            clearShop()
        }

        btn_close.setOnClickListener {
            setFilterData()
        }

        btn_apply.setOnClickListener {
            setFilterData()
        }
    }

    private fun setFilterData() {
        val intent = Intent()
        val bundle = Bundle()
        bundle.putParcelable("resultData", applyFilterData())
        intent.putExtras(bundle)

        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    private fun applyFilterData(): FilterListModel {

        showProgress()

        hideProgress()

        return FilterListModel(mFilterBrand, rateModel, mFilterShop, priceModel, mFilterCategory)
    }


    private fun setToolbar() {
        toolbar.setNavigationIcon(R.drawable.ic_back)
        supportActionBar!!.setDisplayShowTitleEnabled(true)
        supportActionBar!!.title = "Filter Screen"

        toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    private fun inttalizeData() {

        shopFrag = ShopFrag()
        brandFrag = BrandFrag()
        priceFrag = PriceFrag()
        categoryFrag = CategoryFragment()
        ratingFrag = Product_Rating_Frag()



        filterModel = null

        priceModel = null

        rateModel = null

        filterList.clear()

        mcatList.clear()

        mFilterBrand.clear()
        mFilterShop.clear()
        mFilterCategory.clear()

        filterList.addAll(getFilterData(resources.getStringArray(R.array.filter_list)))




        priceBundle.putParcelable("priceList", priceModel)
        ratingBundle.putParcelable("ratingList", rateModel)


        priceFrag!!.arguments = priceBundle
        ratingFrag!!.arguments = ratingBundle


        mAdapter = FilterAdapter(filterList)
        mAdapter.setCallback(this)
        rv_category.adapter = mAdapter




        if (parentCatId != "") {
            getCategoryList(parentCatId)
        }

    }

    private fun getFilterData(filterModelArray: Array<String>): MutableList<FilterDataModel> {

        val filterData = mutableListOf<FilterDataModel>()

        for (i in 0 until filterModelArray.size) {

            filterData.add(FilterDataModel(filterModelArray[i], "0"))
        }

        if (parentCatId != "") {
            filterData.add(FilterDataModel("Category", "0"))
        }
        // }
        return filterData as ArrayList<FilterDataModel>

    }

    private fun settingFilterData(filterModel: FilterListModel) {


        if (filterModel.productRating != null) {
            rateModel = filterModel.productRating

            notifyRateProdCount()
        }

        if (filterModel.priceList != null) {
            priceModel = filterModel.priceList

            notifyPriceCount()
        }

        if (filterModel.shopList.isNotEmpty()) {
            onShopsFound(filterModel.shopList)
        }

        if (filterModel.brandList.isNotEmpty()) {
            onBrandList(filterModel.brandList)
        }

    }

    private fun settingLayout() {

        val layoutManager1 = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rv_category.layoutManager = layoutManager1

        rv_category.itemAnimator = ScaleInAnimator()

    }


    private fun getCategoryList(catId: String) {
        if (mAppUtils.isInternetConnected)
            mFilterPresenter.getCategoryList(catId)
        else
            mAppUtils.showSnackBar(window.decorView.rootView, getString(R.string.toast_network_not_available))

    }

    override fun onDestroy() {
        super.onDestroy()
        mFilterPresenter.cancelAllAsync()
        mFilterPresenter.detachView()
    }

    override fun onErrorOccur(error: String) {
    }

    fun onBrandList(brandList: List<ProductsByCatResponse.BrandsBean>) {



        mFilterBrand.addAll(brandList)

        for (brand in mFilterBrand) {

            if (brand.isBrandStatus) {
                brandCount.add(brand.id)
            }
        }

        notifyBrandCount()

        brandBundle.putParcelableArrayList("brandList", mFilterBrand as ArrayList<out Parcelable>)

        brandFrag!!.arguments = brandBundle

        supportFragmentManager.beginTransaction().replace(R.id.fl_category_detail, brandFrag!!, "brand").commit()



    }


    fun onShopsFound(shopList: List<ProductsByCatResponse.ShopsBean>) {


        mFilterShop.addAll(shopList)

        for (shop in mFilterShop) {
            if (shop.isShopStatus) {
                shopCount.add(shop.id)
            }
        }

        notifyShopCount()


        shopBundle.putParcelableArrayList("shopList", mFilterShop as ArrayList<out Parcelable>)

        shopFrag!!.arguments = shopBundle
    }


    override fun onCategoryList(response: CategoryListModel) {
        mcatList.addAll(response.categories)

        if (filterModel != null && filterModel!!.catList!=null) {
            for (category in filterModel!!.catList!!) {
                for (i in 0 until mcatList.size) {
                    if (mcatList[i].id == category) {
                        val catModel: Category = mcatList[i]
                        catModel.check_status = true

                        mcatList[i] = catModel

                        mFilterCategory.add(mcatList[i].id)
                    }
                }
            }
            notifyCategoryCount()
        }

    }


    override fun onBrandClick(item: ProductsByCatResponse.BrandsBean?) {

        val brandData: ProductsByCatResponse.BrandsBean = item!!

        brandData.isBrandStatus = !brandData.isBrandStatus

        mFilterBrand[mFilterBrand.indexOf(item)] = brandData


        if (brandData.isBrandStatus) {
            brandCount.add(item.id)
        } else {
            brandCount.remove(item.id)
        }


        notifyBrandCount()

    }


    override fun onItemShopClick(item: ProductsByCatResponse.ShopsBean?) {

        val shopData: ProductsByCatResponse.ShopsBean = item!!

        shopData.isShopStatus = !shopData.isShopStatus

        mFilterShop[mFilterShop.indexOf(item)] = shopData

        if (shopData.isShopStatus) {
            shopCount.add(shopData.id)
        } else {
            shopCount.remove(shopData.id)
        }

        notifyShopCount()
    }


    override fun onCategory(catModel: Category) {
        val categoryData: Category = catModel

        categoryData.check_status = !categoryData.check_status

        mcatList[mcatList.indexOf(catModel)] = categoryData


        if (categoryData.check_status) {
            mFilterCategory.add(catModel.id)
        } else {
            mFilterCategory.removeAt(mFilterCategory.indexOf(catModel.id))
        }


        notifyCategoryCount()
    }


    override fun onRateProduct(rateText: String, id: Int) {

        val ratingData = RatingListModel(rateText, id, rateText)

        this.rateModel = ratingData

        notifyRateProdCount()

    }


    override fun onPriceChange(price: String, id: Int) {
        val priceModel = PriceListModel(price, id, price.split(",")[0].toInt(), price.split(",")[1].toInt())

        this.priceModel = priceModel

        notifyPriceCount()

    }

    private fun notifyBrandCount() {
        filterListModel = filterList[0]
        filterListModel.count = brandCount.size.toString()

        filterList[0] = filterListModel

        mAdapter.notifyItemChanged(0)
    }


    private fun notifyCategoryCount() {
        filterListModel = filterList[4]
        filterListModel.count = mFilterCategory.size.toString()

        filterList[4] = filterListModel

        mAdapter.notifyItemChanged(4)
    }


    private fun notifyRateProdCount() {
        filterListModel = filterList[1]
        filterListModel.count = "1"

        filterList[1] = filterListModel

        mAdapter.notifyItemChanged(1)
    }

    private fun notifyShopCount() {
        filterListModel = filterList[2]
        filterListModel.count = shopCount.size.toString()

        filterList[2] = filterListModel

        mAdapter.notifyItemChanged(2)
    }


    private fun notifyPriceCount() {
        filterListModel = filterList[3]
        filterListModel.count = "1"

        filterList[3] = filterListModel

        mAdapter.notifyItemChanged(3)
    }


    override fun clearBrands() {

        for ((index, brand) in mFilterBrand.withIndex()) {
            brand.isBrandStatus = false
            mFilterBrand[index] = brand
        }
        brandCount.clear()

        notifyBrandCount()


        if(supportFragmentManager.findFragmentByTag("brand")!=null )
        {
            val brandFrag: BrandFrag = supportFragmentManager.findFragmentByTag("brand") as BrandFrag

            brandFrag.refreshAdapter()
        }

    }

    override fun clearCategory() {

        for ((index, category) in mcatList.withIndex()) {
            category.check_status = false
            mcatList[index] = category
        }

        mFilterCategory.clear()

        notifyCategoryCount()

        if(supportFragmentManager.findFragmentByTag("category")!=null)
        {
            val catFrag: CategoryFragment = supportFragmentManager.findFragmentByTag("category") as CategoryFragment

            catFrag.refreshAdapter()
        }



    }


    override fun clearPrice() {

        priceFrag = PriceFrag()

        priceModel = null

        /*     val priceFrag:PriceFrag= supportFragmentManager.findFragmentByTag("price") as PriceFrag
             priceFrag.refreshPrice()*/

        filterListModel = filterList[3]
        filterListModel.count = "0"
        filterList[3] = filterListModel
        mAdapter.notifyItemChanged(3)



        /*      priceBundle.putParcelable("priceList", priceModel)
              priceFrag!!.arguments = priceBundle
              supportFragmentManager.beginTransaction().replace(R.id.fl_category_detail, priceFrag!!, "price").commit()*/
    }


    override fun clearRating() {

        ratingFrag = Product_Rating_Frag()

        rateModel = null

/*        val ratingFrag:Product_Rating_Frag= supportFragmentManager.findFragmentByTag("rating") as Product_Rating_Frag
        ratingFrag.refreshRating()*/

        filterListModel = filterList[1]
        filterListModel.count = "0"
        filterList[1] = filterListModel
        mAdapter.notifyItemChanged(1)


   /*     ratingBundle.putParcelable("ratingList", rateModel)
        ratingFrag!!.arguments = ratingBundle
        supportFragmentManager.beginTransaction().replace(R.id.fl_category_detail, ratingFrag!!, "rating").commit()*/
    }


    override fun clearShop() {

        for ((index, shop) in mFilterShop.withIndex()) {
            shop.isShopStatus = false
            mFilterShop[index] = shop
        }

        shopCount.clear()

        notifyShopCount()


        if(supportFragmentManager.findFragmentByTag("shop")!=null)
        {
            val shopFrag: ShopFrag = supportFragmentManager.findFragmentByTag("shop") as ShopFrag

            shopFrag.refreshAdapter()
        }


    }


    override fun onViewClick(position: Int) {
        when (position) {
            0 -> {
                brandBundle.putParcelableArrayList("brandList", mFilterBrand as ArrayList<out Parcelable>)
                brandFrag!!.arguments = brandBundle
                supportFragmentManager.beginTransaction().replace(R.id.fl_category_detail, brandFrag!!, "brand").commit()
            }
            1 -> {
                ratingBundle.putParcelable("ratingList", rateModel)
                ratingFrag!!.arguments = ratingBundle
                supportFragmentManager.beginTransaction().replace(R.id.fl_category_detail, ratingFrag!!, "rating").commit()
            }
            2 -> {
                shopBundle.putParcelableArrayList("shopList", mFilterShop as ArrayList<out Parcelable>)
                shopFrag!!.arguments = shopBundle
                supportFragmentManager.beginTransaction().replace(R.id.fl_category_detail, shopFrag!!, "shop").commit()
            }
            3 -> {
                priceBundle.putParcelable("priceList", priceModel)
                priceFrag!!.arguments = priceBundle
                supportFragmentManager.beginTransaction().replace(R.id.fl_category_detail, priceFrag!!, "price").commit()
            }
            4 -> {
                categoryBundle.putParcelableArrayList("catList", mcatList as ArrayList<out Parcelable>)
                categoryFrag!!.arguments = categoryBundle
                supportFragmentManager.beginTransaction().replace(R.id.fl_category_detail, categoryFrag!!, "category").commit()
            }
        }
    }


}

