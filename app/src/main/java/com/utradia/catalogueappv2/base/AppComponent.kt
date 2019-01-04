package com.utradia.catalogueappv2.base

import com.utradia.catalogueappv2.api.NetModule
import com.utradia.catalogueappv2.module.account.MyAccountActivity
import com.utradia.catalogueappv2.module.account.MyAccountPresenter
import com.utradia.catalogueappv2.module.addresses.addaddress.AddAddressActivity
import com.utradia.catalogueappv2.module.addresses.addaddress.AddAddressPresenter
import com.utradia.catalogueappv2.module.addresses.addaddress.CitiesAdapter
import com.utradia.catalogueappv2.module.addresses.addaddress.RegionsAdapter
import com.utradia.catalogueappv2.module.addresses.alladdresses.AddressesActivity
import com.utradia.catalogueappv2.module.addresses.alladdresses.AddressesAdapter
import com.utradia.catalogueappv2.module.addresses.alladdresses.AddressesPresenter
import com.utradia.catalogueappv2.module.allcategories.*
import com.utradia.catalogueappv2.module.buynowcheckout.BuyCheckoutActivity
import com.utradia.catalogueappv2.module.buynowcheckout.BuyCheckoutAdapter
import com.utradia.catalogueappv2.module.buynowcheckout.BuyCheckoutPresenter
import com.utradia.catalogueappv2.module.cart.CartActivity
import com.utradia.catalogueappv2.module.cart.CartAdapter
import com.utradia.catalogueappv2.module.cart.CartPresenter
import com.utradia.catalogueappv2.module.categoryproductlist.CatProListActivity
import com.utradia.catalogueappv2.module.categoryproductlist.CatProListAdapter
import com.utradia.catalogueappv2.module.categoryproductlist.CatProPresenter
import com.utradia.catalogueappv2.module.checkout.CheckoutActivity
import com.utradia.catalogueappv2.module.checkout.CheckoutAdapter
import com.utradia.catalogueappv2.module.checkout.CheckoutPresenter
import com.utradia.catalogueappv2.module.checkout.ShipMethodsAdapter
import com.utradia.catalogueappv2.module.discovery.DiscoveryAdapter
import com.utradia.catalogueappv2.module.discovery.tabListScreen.TabListActivity
import com.utradia.catalogueappv2.module.discovery.tabListScreen.TabScreenPresenter
import com.utradia.catalogueappv2.module.eventdetail.EventDetailActivity
import com.utradia.catalogueappv2.module.eventdetail.EventDetailPresenter
import com.utradia.catalogueappv2.module.events.EventsActivity
import com.utradia.catalogueappv2.module.events.EventsAdapter
import com.utradia.catalogueappv2.module.events.EventsPresenter
import com.utradia.catalogueappv2.module.filter.FilterActivity
import com.utradia.catalogueappv2.module.filter.FilterPresenter
import com.utradia.catalogueappv2.module.flashsales.FlashSalesActivity
import com.utradia.catalogueappv2.module.flashsales.FlashSalesPresenter
import com.utradia.catalogueappv2.module.flashsales.current.CurrentSaleAdapter
import com.utradia.catalogueappv2.module.flashsales.next.NextSaleAdapter
import com.utradia.catalogueappv2.module.home.*
import com.utradia.catalogueappv2.module.imageZoomView.ZoomImageActivity
import com.utradia.catalogueappv2.module.imageZoomView.ZoomImagePresenter
import com.utradia.catalogueappv2.module.loginsignup.login.LoginActivity
import com.utradia.catalogueappv2.module.loginsignup.login.LoginPresenter
import com.utradia.catalogueappv2.module.loginsignup.signup.RegisterActivity
import com.utradia.catalogueappv2.module.loginsignup.signup.RegisterPresenter
import com.utradia.catalogueappv2.module.mostpopularlist.MostPopularActivity
import com.utradia.catalogueappv2.module.mostpopularlist.MostPopularAdapterList
import com.utradia.catalogueappv2.module.mostpopularlist.MostPopularPresenter
import com.utradia.catalogueappv2.module.mtnmoney.MTNActivity
import com.utradia.catalogueappv2.module.notifications.NotificationListAdapter
import com.utradia.catalogueappv2.module.notifications.NotificationPresenter
import com.utradia.catalogueappv2.module.notifications.NotificationsActivity
import com.utradia.catalogueappv2.module.notifications.settings.NotiSettingsActivity
import com.utradia.catalogueappv2.module.notifications.settings.NotiSettingsPresenter
import com.utradia.catalogueappv2.module.orderdetail.OrderDetailActivty
import com.utradia.catalogueappv2.module.orderdetail.OrderProductsListAdapter
import com.utradia.catalogueappv2.module.orderdetail.OrdersDetailPresenter
import com.utradia.catalogueappv2.module.orderdetail.item_review.RateReviewItemActivity
import com.utradia.catalogueappv2.module.orderdetail.item_review.RateReviewPresenter
import com.utradia.catalogueappv2.module.orderplaced.OrderPlacedActivity
import com.utradia.catalogueappv2.module.orders.OrdersActivity
import com.utradia.catalogueappv2.module.orders.OrdersListAdapter
import com.utradia.catalogueappv2.module.orders.OrdersPresenter
import com.utradia.catalogueappv2.module.payment.PaymentActivity
import com.utradia.catalogueappv2.module.payment.PaymentPresenter
import com.utradia.catalogueappv2.module.productdetail.optioncolors.ColorsOptionsAdapter
import com.utradia.catalogueappv2.module.productdetail.optioncolors.OptionColorsActivity
import com.utradia.catalogueappv2.module.productdetail.optionsizes.OptionSizeActivity
import com.utradia.catalogueappv2.module.productdetail.ProductDetailActivity
import com.utradia.catalogueappv2.module.productdetail.ProductDetailPresenter
import com.utradia.catalogueappv2.module.productdetail.fragments.description.DescriptionFragment
import com.utradia.catalogueappv2.module.productdetail.fragments.overview.ImagesAdapter
import com.utradia.catalogueappv2.module.productdetail.fragments.overview.OverViewFragment
import com.utradia.catalogueappv2.module.productdetail.fragments.description.YouMayAdapter
import com.utradia.catalogueappv2.module.productdetail.fragments.overview.OverViewPresenter
import com.utradia.catalogueappv2.module.productdetail.fragments.ratingreview.RatingReviewFragment
import com.utradia.catalogueappv2.module.productdetail.fragments.ratingreview.RatingReviewListAdapter
import com.utradia.catalogueappv2.module.productdetail.fragments.ratingreview.RatingReviewPresenter
import com.utradia.catalogueappv2.module.productlist.ProductListActivity
import com.utradia.catalogueappv2.module.productlist.ProductListAdapter
import com.utradia.catalogueappv2.module.productlist.ProductListPresenter
import com.utradia.catalogueappv2.module.productdetail.productoptions.ColorsAdapter
import com.utradia.catalogueappv2.module.productdetail.productoptions.ProductOptionsActivity
import com.utradia.catalogueappv2.module.productdetail.productpresenter.ProductOptionsPresenter
import com.utradia.catalogueappv2.module.productdetail.productoptions.SizeAdapter
import com.utradia.catalogueappv2.module.profile.ProfileActivity
import com.utradia.catalogueappv2.module.profile.ProfilePresenter
import com.utradia.catalogueappv2.module.recomendedItems.RecomendedActivity
import com.utradia.catalogueappv2.module.recomendedItems.RecomendedAdapter
import com.utradia.catalogueappv2.module.recomendedItems.RecomendedPresenter
import com.utradia.catalogueappv2.module.search.SearchProduct
import com.utradia.catalogueappv2.module.search.SearchProductPresenter
import com.utradia.catalogueappv2.module.shops.ShopsActivity
import com.utradia.catalogueappv2.module.shops.ShopsAdapter
import com.utradia.catalogueappv2.module.shops.ShopsPresenter
import com.utradia.catalogueappv2.module.splash.SplashActivity
import com.utradia.catalogueappv2.module.storedetail.StoreDetailActivity
import com.utradia.catalogueappv2.module.storedetail.StoreDetailPresenter
import com.utradia.catalogueappv2.module.storedetail.more_options.catalogue.CatalogueListActivity
import com.utradia.catalogueappv2.module.storedetail.more_options.catalogue.CatalogueListAdapter
import com.utradia.catalogueappv2.module.storedetail.more_options.catalogue.CataloguePresenter
import com.utradia.catalogueappv2.module.storedetail.more_options.shop_products.ShopProductFragment
import com.utradia.catalogueappv2.module.storedetail.more_options.shop_products.ShopProductPresenter
import com.utradia.catalogueappv2.module.storedetail.more_options.shop_products.ShopProductAdapter
import com.utradia.catalogueappv2.module.storedetail.more_options.openhours.OpenHoursActivity
import com.utradia.catalogueappv2.module.storedetail.more_options.location.LocationActivity
import com.utradia.catalogueappv2.module.storedetail.more_options.location.LocationPresenter
import com.utradia.catalogueappv2.module.storedetail.more_options.openhours.OpenHoursAdapter
import com.utradia.catalogueappv2.module.subcategory.SubCategoriesAdapter
import com.utradia.catalogueappv2.module.subcategory.SubCategoryActivity
import com.utradia.catalogueappv2.module.subcategory.SubCategoryPresenter
import com.utradia.catalogueappv2.module.wishlist.WishListActivity
import com.utradia.catalogueappv2.module.wishlist.WishListAdapter
import com.utradia.catalogueappv2.module.wishlist.WishListPresenter
import com.utradia.catalogueappv2.notification.SampleAppFirebaseInstanceIDService
import com.utradia.catalogueappv2.utils.LocationTracker
import com.utradia.catalogueappv2.utils.UtilsModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(UtilsModule::class, NetModule::class))
interface AppComponent {
    fun inject(locationTracker: LocationTracker)

    fun inject(homeActivity: LoginActivity)
    fun inject(homePresenter: LoginPresenter)

    fun inject(baseActivity: BaseActivity)
    fun inject(baseFragment: BaseFragment)


    fun inject(sampleAppFirebaseInstanceIDService: SampleAppFirebaseInstanceIDService)
    fun inject(homePresenter: HomePresenter)

    fun inject(myListActivity: HomeActivity)
    fun inject(splashActivity: SplashActivity)

    fun inject(registerActivity: RegisterActivity)
    fun inject(registerPresenter: RegisterPresenter)

    fun inject(myAccountActivity: MyAccountActivity)
    fun inject(myAccountPresenter: MyAccountPresenter)

    fun inject(mShopsPresenter: ShopsPresenter)
    fun inject(mShopsActivity: ShopsActivity)
    fun inject(mShopsAdapter: ShopsAdapter)

    fun inject(profilePresenter: ProfilePresenter)
    fun inject(profileActivity: ProfileActivity)

    fun inject(eventsActivity: EventsActivity)
    fun inject(eventsPresenter: EventsPresenter)
    fun inject(eventsAdapter: EventsAdapter)

    fun inject(eventDetailPresenter: EventDetailPresenter)
    fun inject(eventDetailActivity: EventDetailActivity)
    fun inject(viewPagerAdapter: ViewPagerAdapter)

    fun inject(homeCatAdapter: HomeCatAdapter)
    fun inject(allCategoriesPresenter: AllCategoriesPresenter)
    fun inject(allCategoriesActivity: AllCategoriesActivity)
    fun inject(categoriesAdapter: CategoriesAdapter)
    fun inject(subCategoryAdapter: SubCategoryAdapter)
    fun inject(flashAdapter: FlashAdapter)
    fun inject(recommendedAdapter: RecommendedAdapter)
    fun inject(mostPopularAdapter: MostPopularAdapter)

    fun inject(productListPresenter: ProductListPresenter)
    fun inject(productListActivity: ProductListActivity)
    fun inject(productListAdapter: ProductListAdapter)

    fun inject(productDetailActivity: ProductDetailActivity)
    fun inject(productDetailPresenter: ProductDetailPresenter)
    fun inject(imagesAdapter: ImagesAdapter)
    fun inject(overViewFragment: OverViewFragment)
    fun inject(youMayAdapter: YouMayAdapter)
    fun inject(overViewPresenter: OverViewPresenter)

    fun inject(descriptionFragment: DescriptionFragment)

    fun inject(subCategoryPresenter: SubCategoryPresenter)
    fun inject(subCategoryActivity: SubCategoryActivity)
    fun inject(subCategoriesAdapter: SubCategoriesAdapter)

    fun inject(wishListActivity: WishListActivity)
    fun inject(wishListPresenter: WishListPresenter)
    fun inject(wishListAdapter: WishListAdapter)


    fun inject(storeDetailPresenter: StoreDetailPresenter)
    fun inject(storeDetailActivity: StoreDetailActivity)

    fun inject(productOptions: ProductOptionsActivity)
    fun inject(productOptionsPresenter: ProductOptionsPresenter)
    fun inject(colorsAdapter: ColorsAdapter)
    fun inject(sizeAdapter: SizeAdapter)

    fun inject(optionColorsActivity: OptionColorsActivity)
    fun inject(optionSizeActivity: OptionSizeActivity)
    fun inject(colorsOptionsAdapter: ColorsOptionsAdapter)

    fun inject(catProListActivity: CatProListActivity)
    fun inject(catProPresenter: CatProPresenter)
    fun inject(catProListAdapter: CatProListAdapter)

    fun inject(mostPopularActivity: MostPopularActivity)
    fun inject(mostPopularPresenter: MostPopularPresenter)
    fun inject(mostPopularAdapterList: MostPopularAdapterList)


    fun inject(flashSalesPresenter: FlashSalesPresenter)
    fun inject(flashSalesActivity: FlashSalesActivity)
    fun inject(currentSaleAdapter: CurrentSaleAdapter)
    fun inject(nextSaleAdapter: NextSaleAdapter)

    fun inject(cartActivity: CartActivity)
    fun inject(cartPresenter: CartPresenter)
    fun inject(cartAdapter: CartAdapter)


    fun inject(recomendedPresenter: RecomendedPresenter)
    fun inject(recomendedActivity: RecomendedActivity)
     fun inject(recomendedAdapter: RecomendedAdapter)

    fun inject(locationPresenter: LocationPresenter)
    fun inject(locationFragment: LocationActivity)

    fun inject(openHoursFragment: OpenHoursActivity)
    fun inject(openHoursAdapter: OpenHoursAdapter)

    fun inject(catalogueFragment: ShopProductFragment)
    fun inject(shopProductPresenter: ShopProductPresenter)
    fun inject(catalougeAdapter: ShopProductAdapter)

    fun inject(addAddressActivity: AddAddressActivity)
    fun inject(addressesPresenter: AddAddressPresenter)
    fun inject(regionsAdapter: RegionsAdapter)
    fun inject(citiesAdapter: CitiesAdapter)


    fun inject(addressesPresenter: AddressesPresenter)
    fun inject(addressesActivity: AddressesActivity)
    fun inject(addressesAdapter: AddressesAdapter)

    fun inject(notificationsActivity: NotificationsActivity)
    fun inject(notificationPresenter: NotificationPresenter)
    fun inject(notificationListAdapter: NotificationListAdapter)

    fun inject(notiSettingsActivity: NotiSettingsActivity)
    fun inject(notiSettingsPresenter: NotiSettingsPresenter)

    fun inject(checkoutPresenter: CheckoutPresenter)
    fun inject(checkoutActivity: CheckoutActivity)
    fun inject(checkoutAdapter: CheckoutAdapter)
    fun inject(shipMethodsAdapter: ShipMethodsAdapter)

    fun inject(paymentActivity: PaymentActivity)
    fun inject(paymentPresenter: PaymentPresenter)

    fun inject(buyCheckoutAdapter: BuyCheckoutAdapter)
    fun inject(buyCheckoutActivity: BuyCheckoutActivity)
    fun inject(buyCheckoutPresenter: BuyCheckoutPresenter)


    fun inject(orderPlacedActivity: OrderPlacedActivity)
    fun inject(mtnActivity: MTNActivity)

    fun inject(ordersPresenter: OrdersPresenter)
    fun inject(ordersActivity: OrdersActivity)
    fun inject(ordersListAdapter: OrdersListAdapter)

    fun inject(orderProductsListAdapter: OrderProductsListAdapter)
    fun inject(ordersDetailActivity: OrderDetailActivty)
    fun inject(ordersDetailPresenter: OrdersDetailPresenter)

    fun inject(rateReviewPresenter: RateReviewPresenter)
    fun inject(rateReviewItemActivity: RateReviewItemActivity)

    fun inject(ratingReviewListAdapter: RatingReviewListAdapter)
    fun inject(ratingReviewPresenter: RatingReviewPresenter)
    fun inject(ratingReviewFragment: RatingReviewFragment)


    fun inject(searchProduct: SearchProduct)
    fun inject(searchProductPresenter: SearchProductPresenter)

    fun inject(filterPresenter: FilterPresenter)
    fun inject(filterScreen: FilterActivity)

    fun inject(catalogueListActivity: CatalogueListActivity)
    fun inject(catalogueListAdapter: CatalogueListAdapter)
    fun inject(cataloguePresenter: CataloguePresenter)

    fun inject(zoomImageActivity: ZoomImageActivity)
    fun inject(zoomImagePresenter: ZoomImagePresenter)

    fun inject(discoveryAdapter: DiscoveryAdapter)

    fun inject(tabScreenPresenter: TabScreenPresenter)
    fun inject(tabScreenActivity: TabListActivity)

}
