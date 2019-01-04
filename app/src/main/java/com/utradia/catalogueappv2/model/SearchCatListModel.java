
package com.utradia.catalogueappv2.model;

import io.realm.RealmList;
import io.realm.RealmObject;


public class SearchCatListModel extends RealmObject {
    @SuppressWarnings("unused")
    private RealmList<SearchCategoryModel> itemList;

   public RealmList<SearchCategoryModel> getItemList() {
        return itemList;
    }
}
