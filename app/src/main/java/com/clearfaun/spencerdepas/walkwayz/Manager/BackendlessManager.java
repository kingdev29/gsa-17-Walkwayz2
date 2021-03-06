package com.clearfaun.spencerdepas.walkwayz.Manager;

import android.content.Context;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.geo.GeoPoint;
import com.clearfaun.spencerdepas.walkwayz.Activity.WalkWayzApplication;
import com.clearfaun.spencerdepas.walkwayz.Model.User;
import com.clearfaun.spencerdepas.walkwayz.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by SpencerDepas on 5/12/17.
 */

public class BackendlessManager {

    public static final String PHONE = "phone";
    public static final String AGE = "age";
    public static final String HEIGHT = "height";
    public static final String LOCATION = "location";
    public static final String EMAIL = "email";
    public static final String NAME = "name";
    public static final String PASSWORD = "password";
    public static final String IMAGE = "image";
    public static final String EMERGENCY = "Emergencies";
    public static final String EMERGENCY_TYPE = "emergency_type";
    public static final String HYPER_TRACK_USER_ID = "hypertrackID";
    public static final String OBJECT_ID = "objectId";


    public static final String ID = "ownerId";

    private final String USER = "user";


    private static BackendlessManager backendlessManager;

    public static BackendlessManager getInstance() {
        if (backendlessManager == null) {
            backendlessManager = new BackendlessManager();
        }
        return backendlessManager;
    }

    public void init(Context context) {
        Backendless.setUrl(context.getString(R.string.backendless_server_url));
        Backendless.initApp(WalkWayzApplication.getAppContext(),
                context.getString(R.string.backendless_application_id),
                context.getString(R.string.backendless_api_key),
                context.getString(R.string.backendless_version));
    }

    public void logIn(String username, String password, final BackendlessCallback backendlessCallback) {
        Backendless.UserService.login(username,
                password, new AsyncCallback<BackendlessUser>() {
                    public void handleResponse(BackendlessUser user) {
                        backendlessCallback.callbackSuccess(user);
                    }

                    public void handleFault(BackendlessFault fault) {
                        backendlessCallback.callbackFailure(fault);
                    }
                });
    }

    public BackendlessUser getCurrentUser() {
        return Backendless.UserService.CurrentUser();
    }


    public void emergencyCall(String emergencyType, final BackendlessEmergencyCallback backendlessCallback) {
        HashMap emergencyInfo = new HashMap();
        emergencyInfo.put( EMERGENCY_TYPE, emergencyType);
        emergencyInfo.put( HYPER_TRACK_USER_ID, User.getInstance().getHypertrackID());

        BackendlessUser user = getCurrentUser();
        emergencyInfo.put( USER, getCurrentUser());
        GeoPoint geoPoint = new GeoPoint(User.getInstance().getLocation().getLatitude(),
                User.getInstance().getLocation().getLongitude());
        user.setProperty(LOCATION, geoPoint);
        Backendless.Persistence.of( EMERGENCY ).save( emergencyInfo, new AsyncCallback<Map>() {
            public void handleResponse( Map response ) {
                backendlessCallback.callbackSuccess(response);
            }

            public void handleFault( BackendlessFault fault ) {
                backendlessCallback.callbackFailure(fault);
            }
        });
    }

    public void updateUser(BackendlessUser user, final BackendlessCallback backendlessCallback) {
        user.setProperty(AGE, User.getInstance().getAge());
        user.setProperty(PHONE, User.getInstance().getPhone());
        user.setProperty(HEIGHT, User.getInstance().getHeight());
        GeoPoint geoPoint = new GeoPoint(User.getInstance().getLocation().getLatitude(),
                User.getInstance().getLocation().getLongitude());
        user.setProperty(LOCATION, geoPoint);
        //user.setProperty(EMAIL,User.getInstance().getEmail());
        //user.setProperty(PASSWORD,User.getInstance().getPassword());
        //user.setProperty(IMAGE,"");
        Backendless.UserService.update(user, new AsyncCallback<BackendlessUser>() {
            public void handleResponse(BackendlessUser user) {
                backendlessCallback.callbackSuccess(user);
            }

            public void handleFault(BackendlessFault fault) {
                backendlessCallback.callbackFailure(fault);
            }
        });
    }

    public interface BackendlessEmergencyCallback {
        public void callbackSuccess(Map response);
        public void callbackFailure(BackendlessFault fault);
    }




    private void saveGlobleGeoLocation() {
        List<String> categories = new ArrayList<String>();
        categories.add("restaurants");
        categories.add("cool_places");

        Map<String, Object> meta = new HashMap<String, Object>();
        meta.put("name", "Eatzi's");

        Backendless.Geo.savePoint(32.81, -96.80, categories, meta,
                new AsyncCallback<GeoPoint>() {
                    @Override
                    public void handleResponse(GeoPoint geoPoint) {
                        //Log.i( "MYAPP", geoPoint.getObjectId() );
                    }

                    @Override
                    public void handleFault(BackendlessFault backendlessFault) {

                    }
                });
    }


    public void registerUser(HashMap userInfo){
        //userInfo.put("objectId", USER_DATA);
        BackendlessUser user = new BackendlessUser();
        user.setProperty( "email", "james.bond@mi6.co.uk" );
        user.setProperty( "bacon", true );
        user.setPassword( "iAmWatchingU" );

        Backendless.UserService.register( user, new AsyncCallback<BackendlessUser>()
        {
            public void handleResponse( BackendlessUser registeredUser )
            {
                // user has been registered and now can login

            }

            public void handleFault( BackendlessFault fault )
            {
                // an error has occurred, the error code can be retrieved with fault.getCode()
            }
        } );
    }


}
