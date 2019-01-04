package com.utradia.catalogueappv2.model;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.RealmQuery;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class SearchCategoryModel extends RealmObject {

    public static final String FIELD_ID = "id";


    @PrimaryKey
    private int id;

    @Required
    private String name;

    @Required
    private String productId;

    public static void create(Realm realm, String name, String productId) {
        SearchCatListModel parent = realm.where(SearchCatListModel.class).findFirst();
        assert parent != null;
        RealmList<SearchCategoryModel> items = parent.getItemList();
        SearchCategoryModel counter = realm.createObject(SearchCategoryModel.class, increment(realm));
        counter.setName(name.toLowerCase());
        counter.setProductId(productId);

        if (!checkIfExists(items, name))
        {
            items.add(counter);
        }


    }

    static void delete(Realm realm, long id) {
        SearchCategoryModel item = realm.where(SearchCategoryModel.class).equalTo(FIELD_ID, id).findFirst();
        // Otherwise it has been deleted already.
        if (item != null) {
            item.deleteFromRealm();
        }
    }

    private static int increment(Realm realm) {
        Number currentIdNum = realm.where(SearchCategoryModel.class).max("id");
        int nextId;
        if (currentIdNum == null) {
            nextId = 1;
        } else {
            nextId = currentIdNum.intValue() + 1;
        }
        return nextId;
    }

    private static boolean checkIfExists(RealmList<SearchCategoryModel> realmList, String name) {

       return realmList.where().equalTo("name", name.toLowerCase()).findAll().size()!=0;
    }

    //  create() & delete() needs to be called inside a transaction.

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountString() {
        return Integer.toString(id);
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }
}
