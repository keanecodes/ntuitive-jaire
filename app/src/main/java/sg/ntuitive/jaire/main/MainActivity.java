package sg.ntuitive.jaire.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import sg.ntuitive.jaire.R;
import sg.ntuitive.jaire.viewpager.CardItemExercise;
import sg.ntuitive.jaire.viewpager.PagerAdapterExercise;
import sg.ntuitive.jaire.viewpager.ShadowTransformer;

//import com.androidplot.xy.XYPlot;
//import com.shimmerresearch.android.Shimmer;
//import com.shimmerresearch.android.guiUtilities.ShimmerBluetoothDialog;
//import com.shimmerresearch.android.guiUtilities.ShimmerDialogConfigurations;
//import com.shimmerresearch.android.guiUtilities.supportfragments.ConnectedShimmersListFragment;
//import com.shimmerresearch.android.guiUtilities.supportfragments.DeviceConfigFragment;
//import com.shimmerresearch.android.guiUtilities.supportfragments.PlotFragment;
//import com.shimmerresearch.android.guiUtilities.supportfragments.SensorsEnabledFragment;
//import com.shimmerresearch.android.guiUtilities.supportfragments.SignalsToPlotFragment;
//import com.shimmerresearch.android.shimmerService.ShimmerService;
//import com.shimmerresearch.bluetooth.ShimmerBluetooth;
//import com.shimmerresearch.driver.CallbackObject;
//import com.shimmerresearch.driver.ObjectCluster;
//import com.shimmerresearch.driver.ShimmerDevice;
//import com.shimmerresearch.managers.bluetoothManager.ShimmerBluetoothManager;

//public class MainActivity extends AppCompatActivity implements ConnectedShimmersListFragment.OnShimmerDeviceSelectedListener, SensorsEnabledFragment.OnSensorsSelectedListener{
public class MainActivity extends AppCompatActivity {

    //private Context context = getApplicationContext();
    private ViewPager exercisePager;
    private PagerAdapterExercise mCardAdapter;
    private ShadowTransformer mCardShadowTransformer;
    private Button bluetoothBtn;

//    ShimmerDialogConfigurations dialog;
    BluetoothAdapter btAdapter;
//    ShimmerService mService;

    final static String LOG_TAG = "Shimmer";
    final static String SERVICE_TAG = "ShimmerService";
    final static int REQUEST_CONNECT_SHIMMER = 2;
    //Extra for intent from ShimmerBluetoothDialog
    public static final String EXTRA_DEVICE_ADDRESS = "device_address";

    boolean isServiceStarted = false;
    public String selectedDeviceAddress, selectedDeviceName;

    //SensorsEnabledFragment sensorsEnabledFragment;
    //ConnectedShimmersListFragment connectedShimmersListFragment;
    //DeviceConfigFragment deviceConfigFragment;
    //PlotFragment plotFragment;
    //SignalsToPlotFragment signalsToPlotFragment;

//    XYPlot dynamicPlot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        exercisePager = findViewById(R.id.exercisePager);
        bluetoothBtn = findViewById(R.id.btn_bluetooth);

        /****************** ViewPager ***********************/
        mCardAdapter = new PagerAdapterExercise(this);
        mCardAdapter.addCardItem(new CardItemExercise(
                R.drawable.shoulder_press,
                "FOLLOW ALONG",
                "Shoulder\nPress",
                "+  SENSORS\n  ")
        );
        mCardAdapter.addCardItem(new CardItemExercise(
                R.drawable.shoulder_press_track,
                "AUTO TRACKING",
                "Shoulder\nPress",
                //"+  CAMERA\n+  SENSORS")
                "+  CAMERA")
        );

        mCardShadowTransformer = new ShadowTransformer(exercisePager, mCardAdapter);
        mCardShadowTransformer.enableScaling(true);

        exercisePager.setAdapter(mCardAdapter);
        exercisePager.setPageTransformer(false, mCardShadowTransformer);
        exercisePager.setOffscreenPageLimit(3);

        /****************** Bluetooth ***********************/
        btAdapter = BluetoothAdapter.getDefaultAdapter();
//        dialog = new ShimmerDialogConfigurations();

        //sensorsEnabledFragment = SensorsEnabledFragment.newInstance(null, null);
        //connectedShimmersListFragment = ConnectedShimmersListFragment.newInstance();
        //deviceConfigFragment = DeviceConfigFragment.newInstance();
        //plotFragment = PlotFragment.newInstance();
        //signalsToPlotFragment = SignalsToPlotFragment.newInstance();

        //Check if Bluetooth is enabled
        if (!btAdapter.isEnabled()) {
            int REQUEST_ENABLE_BT = 1;
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        } else {
//            Intent intent = new Intent(this, ShimmerService.class);
//            startService(intent);
//            getApplicationContext().bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
            Log.d(LOG_TAG, "Shimmer Service started");
//            Toast.makeText(this, "Shimmer Service started", Toast.LENGTH_SHORT).show();
        }

        bluetoothBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent pairedDevicesIntent = new Intent(getApplicationContext(), ShimmerBluetoothDialog.class);
//                startActivityForResult(pairedDevicesIntent, REQUEST_CONNECT_SHIMMER);
                //new AlertDialog.Builder(getApplicationContext())
                //        .setSingleChoiceItems(items, 0, null)
                //        .show();

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private ServiceConnection mConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder service) {
            // This is called when the connection with the service has been
            // established, giving us the service object we can use to
            // interact with the service.  Because we have bound to a explicit
            // service that we know is running in our own process, we can
            // cast its IBinder to a concrete class and directly access it.
//            mService = ((ShimmerService.LocalBinder) service).getService();
            isServiceStarted = true;
            //Add this activity's Handler to the service's list of Handlers so we know when a Shimmer is connected/disconnected
//            mService.addHandlerToList(mHandler);
            Log.d(SERVICE_TAG, "Shimmer Service Bound");

            //if there is a device connected display it on the fragment
            //connectedShimmersListFragment.buildShimmersConnectedListView(mService.getListOfConnectedDevices(), getApplicationContext());

        }

        public void onServiceDisconnected(ComponentName className) {
            // This is called when the connection with the service has been
            // unexpectedly disconnected -- that is, its process crashed.
            // Because it is running in our same process, we should never
            // see this happen.
//            mService = null;
            isServiceStarted = false;
            Log.d(SERVICE_TAG, "Shimmer Service Disconnected");
        }
    };

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == 1) { //The system Bluetooth enable dialog has returned a result
//            if (resultCode == RESULT_OK) {
//                Intent intent = new Intent(this, ShimmerService.class);
//                startService(intent);
//                getApplicationContext().bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
//                Log.d(LOG_TAG, "Shimmer Service started");
//                Toast.makeText(this, "Shimmer Service started", Toast.LENGTH_SHORT).show();
//            } else if (resultCode == RESULT_CANCELED) {
//                Toast.makeText(this, "Please enable Bluetooth to proceed.", Toast.LENGTH_LONG).show();
//                int REQUEST_ENABLE_BT = 1;
//                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
//                startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
//            } else {
//                Toast.makeText(this, "Unknown Error! Your device may not support Bluetooth!", Toast.LENGTH_LONG).show();
//            }
//        } else if (requestCode == 2) { //The devices paired list has returned a result
//            if (resultCode == Activity.RESULT_OK) {
//                //Get the Bluetooth mac address of the selected device:
//                String macAdd = data.getStringExtra(EXTRA_DEVICE_ADDRESS);
//                mService.connectShimmer(macAdd);    //Connect to the selected device, and set context to show progress dialog when pairing
//            }
//        }
//    }

//    private Handler mHandler = new Handler() {
//        public void handleMessage(Message msg) {
//            if(msg.what == ShimmerBluetooth.MSG_IDENTIFIER_STATE_CHANGE) {
//                ShimmerBluetooth.BT_STATE state = null;
//                String macAddress = "";
//                String shimmerName = "";
//                if (msg.obj instanceof ObjectCluster){
//                    state = ((ObjectCluster)msg.obj).mState;
//                    macAddress = ((ObjectCluster)msg.obj).getMacAddress();
//                    shimmerName = ((ObjectCluster) msg.obj).getShimmerName();
//                } else if(msg.obj instanceof CallbackObject){
//                    state = ((CallbackObject)msg.obj).mState;
//                    macAddress = ((CallbackObject)msg.obj).mBluetoothAddress;
//                    shimmerName = "";
//                }
//                switch (state) {
//                    case CONNECTED:
//                        //connectedShimmersListFragment.buildShimmersConnectedListView(mService.getListOfConnectedDevices(), getApplicationContext());
//                        break;
//                    case CONNECTING:
//                        break;
//                    case STREAMING:
//                        Toast.makeText(getApplicationContext(), "Device streaming: " + shimmerName + " " + macAddress, Toast.LENGTH_SHORT).show();
//                        if(selectedDeviceAddress.contains(macAddress) && dynamicPlot != null) {
//                            //If the selected device is the one that is now streaming, then show the list of signals available to be plotted
//                            //signalsToPlotFragment.buildSignalsToPlotList(getApplicationContext(), mService, macAddress, dynamicPlot);
//                        }
//                        break;
//                    case STREAMING_AND_SDLOGGING:
//                        if(selectedDeviceAddress.contains(macAddress) && dynamicPlot != null) {
//                            //signalsToPlotFragment.buildSignalsToPlotList(getApplicationContext(), mService, macAddress, dynamicPlot);
//                        }
//                        break;
//                    case SDLOGGING:
//                        //connectedShimmersListFragment.buildShimmersConnectedListView(mService.getListOfConnectedDevices(), getApplicationContext());
//                        break;
//                    case DISCONNECTED:
//                        Toast.makeText(getApplicationContext(), "Device disconnected: " + shimmerName + " " + macAddress, Toast.LENGTH_SHORT).show();
//                        //connectedShimmersListFragment.buildShimmersConnectedListView(mService.getListOfConnectedDevices(), getApplicationContext()); //to be safe lets rebuild this
//                        break;
//                }
//
//            }
//
//            if(msg.arg1 == Shimmer.MSG_STATE_STOP_STREAMING) {
//                //signalsToPlotFragment.setDeviceNotStreamingView();
//            }
//
//

//        }
//    };
//
//    /**
//     * This method is called when the ConnectedShimmersListFragment returns a selected Shimmer
//     * @param macAddress
//     */
//    @Override
//    public void onShimmerDeviceSelected(String macAddress, String deviceName) {
//        Toast.makeText(this, "AAAAASelected Device: " + deviceName + "\n" + macAddress, Toast.LENGTH_SHORT).show();
//        selectedDeviceAddress = macAddress;
//        selectedDeviceName = deviceName;
//
//        //Pass the selected device to the fragments
//        ShimmerDevice device = mService.getShimmer(selectedDeviceAddress);
//
//        //sensorsEnabledFragment.setShimmerService(mService);
//        //sensorsEnabledFragment.buildSensorsList(device, this, mService.getBluetoothManager());
//
//        //deviceConfigFragment.buildDeviceConfigList(device, this, mService.getBluetoothManager());
//
//        //plotFragment.setShimmerService(mService);
//        //plotFragment.clearPlot();
//        //dynamicPlot = plotFragment.getDynamicPlot();
//
//        mService.stopStreamingAllDevices();
//    }

//    @Override
//    public void onSensorsSelected() {
//        ShimmerDevice device = mService.getShimmer(selectedDeviceAddress);
//        //deviceConfigFragment.buildDeviceConfigList(device, this, mService.getBluetoothManager());
//    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        Intent intent=new Intent(this, ShimmerService.class);
//        getApplicationContext().bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
//    }
}