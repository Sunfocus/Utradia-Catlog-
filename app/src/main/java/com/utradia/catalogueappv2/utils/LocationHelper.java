package com.utradia.catalogueappv2.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;


import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * contains helper methods to manipulate user location
 */
public class LocationHelper {

    private Context mContext;

    public LocationHelper(Context context) {
        this.mContext = context;
    }

    /**
     * @param latitude  latitude of address
     * @param longitude longitude of address
     * @return simplified address of location
     */

    public String getSimplifiedAddress(double latitude, double longitude) {
        String location = "";
        try {
            Geocoder geocoder = new Geocoder(mContext, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses.size() > 0) {
                Address address = addresses.get(0);
                String admin = address.getAdminArea();
                String subLocality = address.getSubLocality();
                String locality = address.getLocality();
                if (admin.length() > 10) {
                    admin = admin.substring(0, 10) + "..";
                }
                if (locality != null && subLocality != null) {
                    location = subLocality + "," + locality;
                } else if (subLocality != null) {
                    location = subLocality + "," + admin;
                } else {
                    location = locality + "," + admin;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return location;
    }

    /**
     * @param latitude  latitude of address
     * @param longitude longitude of address
     * @return complete address of location
     */

    public String getCompleteAddress(double latitude, double longitude) {
        String location = "";
        try {
            Geocoder geocoder = new Geocoder(mContext, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses.size() > 0) {
                Address address = addresses.get(0);
                String state, city, zip, street,vicinity;
                if (address.getAdminArea() != null) {
                    state = address.getAdminArea();
                } else {
                    state = "";
                }
                if (address.getLocality() != null) {
                    vicinity = address.getLocality();
                } else {
                    vicinity = "";
                }
                if (address.getLocality() != null) {
                    city = address.getLocality();
                } else {
                    city = "";
                }
                if (address.getPostalCode() != null) {
                    zip = address.getPostalCode();
                } else {
                    zip = "";
                }

                if(address.getSubLocality()!=null){
                    if (address.getThoroughfare() != null) {
                        street = address.getSubLocality() + "," + address.getThoroughfare();
                    } else {
                        street = address.getSubLocality() + "," + address.getFeatureName();
                    }
                }
                else {
                    if (address.getThoroughfare() != null) {
                        street = address.getThoroughfare();
                    } else {
                        street = address.getFeatureName();
                    }
                }


                location = street + "," + city + "," + zip + "," + state;

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return location;
    }
    public String getCompleteAddressSlash(double latitude, double longitude) {
        String location = "";
        try {
            Geocoder geocoder = new Geocoder(mContext, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses.size() > 0) {
                Address address = addresses.get(0);
                String state, city, zip, street;
                if (address.getAdminArea() != null) {
                    state = address.getAdminArea();
                } else {
                    state = "";
                }
                if (address.getLocality() != null) {
                    city = address.getLocality();
                } else {
                    city = "";
                }
                if (address.getPostalCode() != null) {
                    zip = address.getPostalCode();
                } else {
                    zip = "";
                }
                if(address.getSubLocality()!=null){
                    if (address.getThoroughfare() != null) {
                        street = address.getSubLocality() + "," + address.getThoroughfare();
                    } else {
                        street = address.getSubLocality() + "," + address.getFeatureName();
                    }
                }
                else {
                    if (address.getThoroughfare() != null) {
                        street = address.getThoroughfare();
                    } else {
                        street = address.getFeatureName();
                    }
                }
                location = street + "/" + city + "/" + zip + "/" + state;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return location;
    }
    /**
     * to get latitude and longitude of an address
     *
     * @param strAddress address string
     * @return lat and lng in comma separated string
     */
    public String getLocationFromAddress(String strAddress) {

        Geocoder coder = new Geocoder(mContext);
        List<Address> address;

        try {
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }
            Address location = address.get(0);
            double lat = location.getLatitude();
            double lng = location.getLongitude();

            return lat + "," + lng;
        } catch (Exception e) {
            return null;
        }
    }
    public String getZipcode(double latitude, double longitude) {
        String location = "";
        try {
            Geocoder geocoder = new Geocoder(mContext, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses.size() > 0) {
                Address address = addresses.get(0);
                String  zip;

                if (address.getPostalCode() != null) {
                    zip = address.getPostalCode();
                } else {
                    zip = "";
                }

                location = zip ;

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return location;
    }

    public BitmapDescriptor getMarkerIconFromDrawable(Drawable drawable) {
        Canvas canvas = new Canvas();
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        canvas.setBitmap(bitmap);
        drawable.setBounds(0, 0, 60, 120);
        drawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }
}