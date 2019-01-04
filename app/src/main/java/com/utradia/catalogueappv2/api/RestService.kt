package com.utradia.catalogueappv2.api

import com.utradia.catalogueappv2.constants.ApiConstants
import com.utradia.catalogueappv2.model.*
import okhttp3.RequestBody
import retrofit2.http.*
import rx.Observable
import java.util.*

interface RestService {

    //LOGIN API
    @FormUrlEncoded
    @POST(ApiConstants.LOGIN)
    fun login(@Field("email") email: String, @Field("password") pass: String): Observable<LoginResponse>


    @FormUrlEncoded
    @POST(ApiConstants.SIGN_UP)
    fun loginUserSocially(@Field("signup_type") signup_type: String
                          , @Field("fb_id") fb_id: String
                          , @Field("google_id") google_id: String
                          , @Field("email") email: String
                          , @Field("first_name") first_name: String
                          , @Field("device_token") device_token: String
                          , @Field("device_type") device_type: String): Observable<RegisterResponse>


    //email, password, device_token,device_type,login_type
    @FormUrlEncoded
    @POST(ApiConstants.LOGIN)
    fun loginUser(@Field("email") email: String
                  , @Field("password") password: String
                  , @Field("device_token") device_token: String
                  , @Field("device_type") device_type: String
                  , @Field("login_type") login_type: String): Observable<LoginResponse>


    //email, password, device_token,device_type,login_type
    @FormUrlEncoded
    @POST(ApiConstants.SIGN_UP)
    fun signUpUser(@Field("signup_type") signup_type: String
                   , @Field("first_name") first_name: String
                   , @Field("last_name") last_name: String
                   , @Field("phone_number") phone_number: String
                   , @Field("email") email: String
                   , @Field("password") password: String
                   , @Field("device_type") device_type: String
                   , @Field("device_token") device_token: String): Observable<RegisterResponse>


    //email, user_id
    @FormUrlEncoded
    @POST(ApiConstants.UPDATE_EMAIL)
    fun updateEmail(@Field("email") email: String
                    , @Field("user_id") user_id: String): Observable<LoginResponse>

    @FormUrlEncoded
    @POST(ApiConstants.PROFILE)
    fun getProfile(@Field("user_id") user_id: String): Observable<ProfileResponse>


    @FormUrlEncoded
    @POST(ApiConstants.UPDATE_USER)
    fun updateUser(@Field("user_id") user_id: String, @Field("first_name") first_name: String
                   , @Field("last_name") last_name: String
                   , @Field("phone_number") phone_number: String
                   , @Field("email") email: String
                   , @Field("dob") date_of_birth: String
                   , @Field("gender") gender: String): Observable<RegisterResponse>

    @GET(ApiConstants.GET_SHOP_LISTING)
    fun getShops(): Observable<ShopListResponse>

    @GET(ApiConstants.GET_EVENTS)
    fun getEvents(): Observable<EventsResponse>

    @FormUrlEncoded
    @POST(ApiConstants.GET_EVENT_DETAILS)
    fun getEventsDetails(@Field("id") id: String, @Field("user_id") user_id: String): Observable<EventDetailResponse>

    @FormUrlEncoded
    @POST(ApiConstants.GOING_EVENT)
    fun goingEvent(@Field("event_id") id: String, @Field("user_id") user_id: String): Observable<EventDetailResponse>

    @FormUrlEncoded
    @POST(ApiConstants.UNGOING_EVENT)
    fun unGoingEvent(@Field("event_id") id: String, @Field("user_id") user_id: String): Observable<EventDetailResponse>

    @FormUrlEncoded
    @POST(ApiConstants.HOMESCREEN)
    fun getHomeData(@Field("user_id") user_id: String): Observable<HomeResponse>

    @FormUrlEncoded
    @POST(ApiConstants.GET_OFFERS)
    fun getOffers(@Field("category_id") category_id: String
                  , @Field("page_number") page_number: String): Observable<ProductListResponse>

    @POST(ApiConstants.GET_TWO_LEVEL_CATEGORIES)
    fun getTwoLevelCategories(): Observable<CategoriesResponse>

    @FormUrlEncoded
    @POST(ApiConstants.GET_OFFER_DETAILS)
    fun getOfferDetails(@Field("id") id: String
                        , @Field("user_id") user_id: String): Observable<ProductDetailResponse>


    @FormUrlEncoded
    @POST(ApiConstants.GET_SUB_CATEGORIES_BY_PARENT_ID)
    fun getSubCategories(@Field("parent_id") parent_id: String): Observable<SubCategoryData>


    @FormUrlEncoded
    @POST(ApiConstants.ADD_FAVOURITE)
    fun addToWishList(@Field("user_id") user_id: String
                      , @Field("offer_id") offer_id: String): Observable<FavouriteResponse>

    @FormUrlEncoded
    @POST(ApiConstants.REMOVE_FAVOURITE)
    fun removeFromWishList(@Field("user_id") user_id: String
                           , @Field("offer_id") offer_id: String): Observable<FavouriteResponse>

    @FormUrlEncoded
    @POST(ApiConstants.FAVOURITE_LIST)
    fun getWishListItems(@Field("user_id") user_id: String): Observable<WishListResponse>

    @FormUrlEncoded
    @POST(ApiConstants.ADD_TO_CART)
    fun addToCart(@Field("user_id") user_id: String
                  , @Field("product_id") product_id: String
                  , @Field("quantity") quantity: String
                  , @Field("variant_group_type") variant_group_type: String
                  , @Field("variant_group_id") variant_group_id: String
                  , @Field("color") color: String
                  , @Field("size") size: String
                  , @Field("price") price: String
    ): Observable<AddToCartResponse>

    @FormUrlEncoded
    @POST(ApiConstants.BUY_NOW_CART)
    fun buyNowCart(@Field("user_id") user_id: String
                   , @Field("product_id") product_id: String
                   , @Field("quantity") quantity: String
                   , @Field("variant_group_type") variant_group_type: String
                   , @Field("variant_group_id") variant_group_id: String
                   , @Field("color") color: String
                   , @Field("size") size: String
                   , @Field("price") price: String
    ): Observable<AddToCartResponse>

    @FormUrlEncoded
    @POST(ApiConstants.GET_CLIENT_DATA)
    fun getClientData(@Field("client_id") id: String, @Field("user_id") user_id: String): Observable<StoreDetailResponse>

    @FormUrlEncoded
    @POST(ApiConstants.FOLLOW_CLIENT)
    fun followClient(@Field("user_id") id: String, @Field("shop_id") shop_id: String): Observable<StoreDetailResponse>

    @FormUrlEncoded
    @POST(ApiConstants.UNFOLLOW_CLIENT)
    fun unFollowClient(@Field("user_id") id: String, @Field("shop_id") shop_id: String): Observable<StoreDetailResponse>

    @FormUrlEncoded
    @POST(ApiConstants.GET_CLIENT_OFFERS)
    fun getClientOffers(@Field("client_id") id: String
                        , @Field("category_id") category_id: String
                        , @Field("page_number") page_number: String): Observable<ClientProductResponse>

    @FormUrlEncoded
    @POST(ApiConstants.GET_OFFER_BY_CATEGORY_ID)
    fun getOffersByCategoryId(@FieldMap param:HashMap<String,String>): Observable<ProductsByCatResponse>

    @FormUrlEncoded
    @POST(ApiConstants.GET_MOST_POPULAR_ITEMS)
    fun getMostPopularItems(@FieldMap param:HashMap<String,String>): Observable<ProductsByCatResponse>

    @GET(ApiConstants.FLASH_SALES)
    fun getFlashSales(): Observable<FlashSalesResponse>

    @FormUrlEncoded
    @POST(ApiConstants.GET_CART)
    fun getCartItems(@Field("user_id") user_id: String): Observable<CartResponse>

    @FormUrlEncoded
    @POST(ApiConstants.REMOVE_ITEM_FROM_CART)
    fun removeItem(@Field("id") id: String, @Field("user_id") user_id: String): Observable<CartResponse>

    @FormUrlEncoded
    @POST(ApiConstants.UPDATE_QUANTITY)
    fun updateQuantity(@Field("id") id: String, @Field("quantity") quantity: String): Observable<CartResponse>

    @FormUrlEncoded
    @POST(ApiConstants.MOVE_TO_WISHLIST)
    fun moveToWishList(@Field("id") id: String, @Field("user_id") user_id: String): Observable<CartResponse>

    @FormUrlEncoded
    @POST(ApiConstants.RECOMMENDED_ITEMS)
    fun getRecommendedItems(@Field("user_id") user_id: String
                            , @Field("page_number") page_number: String): Observable<RecomendedResponse>

    @FormUrlEncoded
    @POST(ApiConstants.ADD_ADDRESS)
    fun addAddress(@Field("user_id") user_id: String
                   , @Field("full_name") full_name: String
                   , @Field("mobile_number") mobile_number: String
                   , @Field("houseno") houseno: String
                   , @Field("locality") locality: String
                   , @Field("city") city: String
                   , @Field("state") state: String
                   , @Field("type") type: String): Observable<RecomendedResponse>

    @FormUrlEncoded
    @POST(ApiConstants.UPDATE_ADDRESS)
    fun updateAddress(@Field("address_id") address_id: String
                      , @Field("full_name") full_name: String
                      , @Field("mobile_number") mobile_number: String
                      , @Field("houseno") houseno: String
                      , @Field("locality") locality: String
                      , @Field("city") city: String
                      , @Field("state") state: String
                      , @Field("type") type: String): Observable<RecomendedResponse>

    @GET(ApiConstants.GET_REGIONS)
    fun getRegions(): Observable<RegionsResponse>

    @FormUrlEncoded
    @POST(ApiConstants.GET_CITIES_BY_REGION)
    fun getCities(@Field("region_id") region_id: String): Observable<CitiesResponse>

    @FormUrlEncoded
    @POST(ApiConstants.GET_ADDRESS)
    fun getAddresses(@Field("user_id") user_id: String
                     , @Field("type") type: String): Observable<AddressResponse>

    @FormUrlEncoded
    @POST(ApiConstants.DELETE_ADDRESS)
    fun deleteAddress(@Field("address_id") address_id: String): Observable<CitiesResponse>

    @FormUrlEncoded
    @POST(ApiConstants.GET_ADDRESS_DETAILS)
    fun getAddressDetails(@Field("id") address_id: String): Observable<AddressDetailResponse>


    @FormUrlEncoded
    @POST(ApiConstants.MAKE_DEFAULT_ADDRESS)
    fun makeDefault(@Field("address_id") address_id: String, @Field("user_id") user_id: String): Observable<CitiesResponse>

    @FormUrlEncoded
    @POST(ApiConstants.GET_NOTIFICATION_SETTINGS)
    fun getNotificationSettings(@Field("user_id") user_id: String): Observable<SettingsResponse>

    // user_id, notifications, order_and_logistics, system_messages
    @FormUrlEncoded
    @POST(ApiConstants.UPDATE_NOTIFICATION_SETTINGS)
    fun updateNotificationSettings(@FieldMap map: Map<String, String>)
            : Observable<SettingsResponse>

    @FormUrlEncoded
    @POST(ApiConstants.CHECKOUT_DATA)
    fun checkoutData(@Field("user_id") user_id: String): Observable<CheckoutData>

    @FormUrlEncoded
    @POST(ApiConstants.BUY_NOW_CHECKOUT_DATA)
    fun buyNowCheckoutData(@Field("user_id") user_id: String): Observable<CheckoutData>

    @FormUrlEncoded
    @POST(ApiConstants.GET_SHIPMENT_PRICES)
    fun getShipmentCharges(@Field("region_id") region_id: String
                           , @Field("city") city: String
                           , @Field("weight") weight: String
                           , @Field("client_id") client_id: String
                           , @Field("product_id") product_id: String): Observable<ShipMethodsResponse>

    //user_id, promocode, items_total, shipping_total
    @FormUrlEncoded
    @POST(ApiConstants.APPLY_PROMOCODE)
    fun applyPromocode(@Field("user_id") user_id: String
                       , @Field("promocode") promocode: String
                       , @Field("items_total") items_total: String
                       , @Field("shipping_total") shipping_total: String): Observable<DiscountResponse>

    //Required: user_id, product_id, shipment_charges, shipment_id, billing_address_id, shipping_type, price, total_shipping_charges, total_price, payment_mode, payment_method
    // Optional: payment_token, payment_common_id, promocode_id, discount, discount_type,

    @FormUrlEncoded
    @POST(ApiConstants.PLACE_ORDER)
    fun placeOrder(@Field("user_id") user_id: String
                   , @Field("product_id") product_id: String
                   , @Field("shipment_charges") shipment_charges: String
                   , @Field("shipment_id") shipment_id: String
                   , @Field("billing_address_id") billing_address_id: String
                   , @Field("shipping_type") shipping_type: String
                   , @Field("price") price: String
                   , @Field("total_shipping_charges") total_shipping_charges: String
                   , @Field("total_price") total_price: String
                   , @Field("payment_mode") payment_mode: String
                   , @Field("payment_token") payment_token: String
                   , @Field("payment_common_id") payment_common_id: String
                   , @Field("promocode_id") promocode_id: String
                   , @Field("discount") discount: String
                   , @Field("discount_type") discount_type: String
                   , @Field("payment_method") payment_method: String
                   , @Field("order_type") order_type: String
                   , @Field("order_status") order_status: String
    ): Observable<OrderPlacedResponse>


    @FormUrlEncoded
    @POST(ApiConstants.ORDER_LIST)
    fun getOrderList(@Field("user_id") user_id: String): Observable<OrderListResponse>


    //order_id
    @FormUrlEncoded
    @POST(ApiConstants.ORDER_DETAIL)
    fun getOrderDetail(@Field("user_id") user_id: String
                       , @Field("order_id") order_id: String): Observable<OrderDetailResponse>

    //user_id, product_id, order_item_id, rating, review
    @FormUrlEncoded
    @POST(ApiConstants.PRODUCT_RATE_REVIEW)
    fun addProductReview(@Field("user_id") user_id: String
                         , @Field("product_id") product_id: String
                         , @Field("order_item_id") order_item_id: String
                         , @Field("rating") rating: String
                         , @Field("review") review: String): Observable<AddProductReviewResponse>


    @FormUrlEncoded
    @POST(ApiConstants.RATE_REVIEW_LIST)
    fun getReviewList(@Field("product_id") product_id: String): Observable<ProductReviewListResponse>


    @FormUrlEncoded
    @POST(ApiConstants.CANCEL_ORDER)
    fun cancelOrder(@Field("order_id") order_id: String,@Field("user_id") user_id: String): Observable<SucessResponse>


    @Multipart
    @POST(ApiConstants.UPLOAD_IMAGE)
    fun uploadImage(@Part("profile_pic\"; filename=\"image.jpg\" ") image: RequestBody, @Part("user_id") user_id: RequestBody): Observable<SucessResponse>


    @GET(ApiConstants.SEARCH_OFFERS)
    fun getSearchProduct(@Query("keyword") keyword: String): Observable<SearchProductModel>


    @GET(ApiConstants.GET_SHOP_LISTING)
    fun getShops_list(): Observable<ShopListModel>


    @GET(ApiConstants.GET_BRAND_LIST)
    fun getBrandList(): Observable<BrandListModel>


    @FormUrlEncoded
    @POST(ApiConstants.GET_CATEGORY_LIST)
    fun getCategoryList(@Field("category_id") category_id: String): Observable<CategoryListModel>


    @FormUrlEncoded
    @POST(ApiConstants.SEARCH_OFFER_LIST)
    fun searchOfferList(@FieldMap param: HashMap<String, String>): Observable<ProductsByCatResponse>


    @FormUrlEncoded
    @POST(ApiConstants.SEARCH_OFFER_BY_ID)
    fun searchOfferID(@FieldMap param: HashMap<String, String>): Observable<ProductsByCatResponse>


    @FormUrlEncoded
    @POST(ApiConstants.ORDER_PAYNOW)
    fun orderPayment(@FieldMap param: HashMap<String, String>): Observable<SucessResponse>


    @FormUrlEncoded
    @POST(ApiConstants.ADD_DEVICE)
    fun addDevice(@Field("device_token") device_token: String,@Field("device_type") device_type: String): Observable<SucessResponse>


    @FormUrlEncoded
    @POST(ApiConstants.LOGOUT)
    fun logout(@Field("user_id") user_id: String): Observable<SucessResponse>


    @FormUrlEncoded
    @POST(ApiConstants.NOTIFICATION_LIST)
    fun notificationList(@Field("user_id") user_id: String): Observable<NotificationResponse>


    @FormUrlEncoded
    @POST(ApiConstants.SHOP_CATALOGUE)
    fun shopCatalogue(@Field("client_id") user_id: String): Observable<CatalogueListModel>


    @FormUrlEncoded
    @POST(ApiConstants.SHOP_CATALOGUE_ITEM)
    fun shopCatalogueItem(@Field("catalogue_id") catalogue_id: String): Observable<CatalogueItemDetailResponse>

}
