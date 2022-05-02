package com.example.hunter_game.activities;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.hunter_game.R;
import com.example.hunter_game.utils.MyLocationManager;
import com.example.hunter_game.objects.TopTen.User;
import com.example.hunter_game.objects.enums.KeysToSaveEnums;
import com.example.hunter_game.utils.BackGround;
import com.example.hunter_game.utils.MySharedPreferences;
import com.example.hunter_game.utils.MySignal;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

/**
INTENT:
      "BUNDLE":  BUNDLE:
                        "NAME": playerName (String),
                        "GAME_SCREEN": SENSORS/BUTTONS (String)
                        "LATITUDE": latitude (Double)
                        "LONGITUDE": longitude (DOUBLE)
 */
public class MainActivity extends AppCompatActivity {
    static final String BUTTONS = "BUTTONS";
    static final String SENSORS = "SENSORS";

    private MaterialButton main_BTN_play,
                           main_BTN_topTen;
    private TextInputEditText main_EDT_playerName;
    private ImageView main_IMG_backGround;
    private String playerName;
    private Intent intent;
    private Bundle bundle;
    //Location
    private MyLocationManager myLocationManager;
    private Location theLocation;
    private int LOCATION_REQUEST_CODE = 10001;
    public FusedLocationProviderClient fusedLocationProviderClient;
    public LocationRequest locationRequest;

    //TODO: Make icon and put it above the text input
    //TODO: Text style for all the app
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        new BackGround(this, main_IMG_backGround).setBackGround();

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        locationRequest = LocationRequest.create();
        locationRequest.setInterval(4000);
        locationRequest.setFastestInterval(2000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        myLocationManager = new MyLocationManager(this, fusedLocationProviderClient, locationRequest);


        initializeIntentBundleAndPlayerName();

        /**
         * PLAY & TOP TEN BUTTONS
         */
        main_BTN_play.setOnClickListener(view -> {
            if(!validatePlayerName(main_EDT_playerName.getText().toString())){//Checks the user name before starting the game
                MySignal.getMe().makeToastMessage("Not Valid Name. Use: A-Z / a-z / -");
            }
            else {
                moveToPageWithBundle(GameActivity.class);
                setPopUp().show();
            }
        });
        main_BTN_topTen.setOnClickListener(view -> { moveToTopTenPage(); });
    }

    private void findViews() {
        main_BTN_play = findViewById(R.id.main_BTN_play);
        main_BTN_topTen = findViewById(R.id.main_BTN_topTen);
        main_EDT_playerName = findViewById(R.id.main_EDT_playerName);
        main_IMG_backGround = findViewById(R.id.main_IMG_backGround);
    }

    public void putInBundlePlayerNameAndLocation(){
        playerName = main_EDT_playerName.getText().toString();
        bundle.putString(KeysToSaveEnums.NAME.toString(), (String) playerName);
        bundle.putDouble(KeysToSaveEnums.LATITUDE.toString(), myLocationManager.getLocation().getLatitude());
        bundle.putDouble(KeysToSaveEnums.LONGITUDE.toString(), myLocationManager.getLocation().getLongitude());
    }

    public void moveToPageWithBundle(Class activity){
        intent = new Intent(MainActivity.this, activity);
        putInBundlePlayerNameAndLocation();
    }

    /**
     * Checks if there is any player that get in the Top Ten list
     * If there isn't -> there is nothing to see
     */
    public void moveToTopTenPage(){
        TypeToken token = new TypeToken<ArrayList<User>>() {};
        if(MySharedPreferences.getMe().getArray(KeysToSaveEnums.LIST_USERS.toString(), token).size() == 0)
            MySignal.getMe().makeToastMessage("You are the first player, lets set the bar!");
        else{
            moveToPageWithBundle(TopTenActivity.class);
            bundle.putString(KeysToSaveEnums.PAGE.toString(), KeysToSaveEnums.MAIN_PAGE.toString());
            intent.putExtra(KeysToSaveEnums.BUNDLE.toString(), bundle);
            startActivity(intent);
        }
    }

    /**
     * Presenting the name of the player if its not the first time in the main page
     * So that the player does not have to rewrite the name
     */
    public void initializeIntentBundleAndPlayerName(){
        intent = getIntent();
        if (intent.getBundleExtra(KeysToSaveEnums.BUNDLE.toString()) != null) {
            bundle = intent.getBundleExtra(KeysToSaveEnums.BUNDLE.toString());
            main_EDT_playerName.setText(bundle.getString(KeysToSaveEnums.NAME.toString()));
        } else {
            bundle = new Bundle();
        }
    }

    //POP-UP FOR BUTTON / SENSORS
    public MaterialAlertDialogBuilder setPopUp(){
        MaterialAlertDialogBuilder selectGameScreen = new MaterialAlertDialogBuilder(this)
                .setIcon(R.drawable.ic_play)
                .setTitle("Let's play")
                .setMessage("Choose how you want to play")
                .setPositiveButton("Sensors", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        bundle.putString(KeysToSaveEnums.GAME_SCREEN.toString(), SENSORS);
                        intent.putExtra(KeysToSaveEnums.BUNDLE.toString(), bundle);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("Buttons", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        bundle.putString(KeysToSaveEnums.GAME_SCREEN.toString(), BUTTONS);
                        intent.putExtra(KeysToSaveEnums.BUNDLE.toString(), bundle);
                        startActivity(intent);
                    }
                });
        return selectGameScreen;
    }

    /**
     * [A-Za-z]+([ '-][a-zA-Z])
     * @param name String
     * @return Boolean
     */
    public Boolean validatePlayerName(String name){
        return name.matches( "[A-Za-z]+([ '-][a-zA-Z]+)*" );
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            myLocationManager.getLastLocation();
            myLocationManager.checkSettingsAndStartLocationUpdates();
        } else {
            myLocationManager.askLocationPermission();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        myLocationManager.stopLocationUpdates();
    }

    LocationCallback locationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            if (locationResult == null) {
                return;
            }
        }
    };

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted
                myLocationManager.getLastLocation();
                myLocationManager.checkSettingsAndStartLocationUpdates();
            } else {
                //Permission not granted
                myLocationManager.showLocationAlert("Application must use device location.\nPlease allow it.\n");
            }
        }
    }
}
/*
 ///////////////////////////
/*
    private void checkSettingsAndStartLocationUpdates() {
        LocationSettingsRequest request = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest).build();
        SettingsClient client = LocationServices.getSettingsClient(this);

        Task<LocationSettingsResponse> locationSettingsResponseTask = client.checkLocationSettings(request);
        locationSettingsResponseTask.addOnSuccessListener(locationSettingsResponse -> {
            //Settings of device are satisfied and we can start location updates
            startLocationUpdates();
        });
        locationSettingsResponseTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (e instanceof ResolvableApiException) {
                    ResolvableApiException apiException = (ResolvableApiException) e;
                    try {
                        apiException.startResolutionForResult(MainActivity.this, 1001);
                    } catch (IntentSender.SendIntentException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
    }


    private void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());
    }

    private void stopLocationUpdates() {
        fusedLocationProviderClient.removeLocationUpdates(locationCallback);
    }

    private void getLastLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Task<Location> locationTask = fusedLocationProviderClient.getLastLocation();
        locationTask.addOnSuccessListener(location -> {
            if (location != null) {
                //We have a location
                theLocation = location;
            }
        });
    }

    private void askLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST_CODE);
            } else {
                ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST_CODE);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted
                getLastLocation();
                checkSettingsAndStartLocationUpdates();
            } else {
                //Permission not granted
                showLocationAlert("Application must use device location.\nPlease allow it.\n");
            }
        }
    }

    private void showLocationAlert(String msg) {
        new AlertDialog.Builder(this)
                .setTitle("Device location")
                .setMessage(msg)
                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton("Exit", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                // A null listener allows the button to dismiss the dialog and take no further action.
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

*/
////////////////
