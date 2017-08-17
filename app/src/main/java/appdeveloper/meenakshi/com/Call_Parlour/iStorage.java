/*
 * Copyright (c) 2012-2015 Arne Schwabe
 * Distributed under the GNU GPL v2 with additional terms. For full terms see the file doc/LICENSE.txt
 */

package appdeveloper.meenakshi.com.Call_Parlour;

import android.content.Context;
import android.util.Log;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


/**
 * Created by karpa on 14/03/16.
 */
public class iStorage {
    public static final String FILE_AGREE = "Agree";

    public static final String FILE_OVPN = "ovpn.ovpn";
    public static final String FILE_OVPN2 = "ovpn2.ovpn";
    public static final String FILE_ID = "id";
    public static final String FILE_PIN = "PIN";
    public static final String FILE_DisableFilter = "DisableFilter";
    public static final String FILE_SETUPCOMPLETE = "SetupComplete";
    public static final String FILE_SHUTDOWN = "SHUTDOWN";
    public static final String FILE_ALERT_VPN_CANCEL = "VPN_CANCELLED";
    public static final String FILE_EXPIRE = "Expire";
    public static final String FILE_DEVICE_ADMIN = "DeviceAdmin";
    public static final String FILE_DEVICE_LOCK_CONSENT = "DeviceLockConsent";
    public static final String FILE_LONGITUDE = "LONGITUDE";
    public static final String FILE_LATITUDE = "LATITUDE";
    public static final String FILE_TRIAL = "TRIAL";
    public static final String FILE_SUBSCRIPTION = "SUBSCRIPTION";
    public static final String FILE_SMS_ALERT = "SMS_Alerts";
    public static final String FILE_EMAIL_CONFIGURED = "EMAIL_CONFIGURED";
    public static final String FILE_NAME = "User_Name";
    public static final String FILE_EMAIL = "Email";
    public static final String FILE_PASSWORD = "Password";
    public static final String FILE_AGE = "Age";
    public static final String FILE_LOCATION = "Location";
    public static final String FILE_CONTACT = "User_Mobile";

    public static final String FILE_PARLOUR_NAME = "Psrlour_Name";
    public static final String FILE_PARLOUR_EMAIL = "Parlour_Email";
    public static final String FILE_PARLOUR_PASSWORD = "Parlour_Password";
    public static final String FILE_PARLOUR_TIMING = "Parlour_Timing";
    public static final String FILE_PARLOUR_LOCATION = "Parlour_Location";
    public static final String FILE_PARLOUR_CONTACT = "Parlour_Mobile";

    //public static final String FILE_GUIDED_SETUP = "GUIDED_SETUP";
    public static final String FILE_FEEDBACK = "FEEDBACK";

    public static final String FILE_COUNTRY_ISO = "COUNTRY_ISO";

    private final static String TAG = "iStorage";

    //saving ovpn file in internal storage
    public static void saveOVPN(Context context, String ovpn_content, String filename) {
        try {
            // internal storage for backup
            FileOutputStream outputStream = context.openFileOutput(filename, Context.MODE_PRIVATE);
            outputStream.write(ovpn_content.getBytes());
            outputStream.close();

            boolean DEBUG = false;
            if (DEBUG) {
                // Read-back for verification
                FileInputStream fin = context.openFileInput(filename);

                int c;
                String file_content = "";
                while ((c = fin.read()) != -1) {
                    file_content = file_content + Character.toString((char) c);
                }
                //Log.v("iCommon", "ovpn file content is " + "\n" + file_content);
            }

            //Log.v("iCommon", "ovpn file content is " + "\n" + file_content);
        } catch (FileNotFoundException e) {
            Log.v(TAG, filename + " : not found");
        } catch (IOException e) {
            e.printStackTrace();
            iCommon.sendDebugLogToCloud("ovpn2file-" + TAG + "-" + e.toString(), context);
        } catch (Exception e) {
            e.printStackTrace();
            iCommon.sendDebugLogToCloud("ovpn2file-" + TAG + "-" + e.toString(), context);
        }

    }

    public static void savePropertyWithValue(Context context, String Filename, String Value) {
        try {
            FileOutputStream outputStream = context.openFileOutput(Filename, Context.MODE_PRIVATE);

            outputStream.write(Value.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
            iCommon.sendDebugLogToCloud("pin2file-" + TAG + "-"+ e.toString(), context);
        }
    }

    public static String readPropertyWithValue(Context context, String filename) {
        try {
            FileInputStream fin = context.openFileInput(filename);

            int c;
            String Value = "";
            while ((c = fin.read()) != -1) {
                Value = Value + Character.toString((char) c);
            }
            //Log.v("iCommon", "content of " + filename +" is " + Value);

            return Value;
        } catch (FileNotFoundException e) {
            Log.v(TAG, filename + " : not found");
        } catch (Exception e) {
            e.printStackTrace();
            iCommon.sendDebugLogToCloud("file2ovpn-" + TAG + "-" + e.toString(), context);
        }

        return null;
    }

    public static boolean readProperty(Context context, String filename) {
        try {
            FileInputStream fin = context.openFileInput(filename);
            //Log.v(TAG, filename + " : found");
            return true;
        } catch (FileNotFoundException e) {
            Log.v(TAG, filename + " : not found");
        } catch (Exception e) {
            e.printStackTrace();
            iCommon.sendDebugLogToCloud("file2ovpn-" + TAG + "-" + e.toString(), context);
        }

        return false;
    }

    public static void saveProperty(Context context, String filename) {
        // avoiding to over-write the file
        if (readProperty(context, filename))
            return;

        try {
            FileOutputStream outputStream = context.openFileOutput(filename, Context.MODE_PRIVATE);

            outputStream.write("1".getBytes());
            outputStream.close();
            Log.v(TAG, "Write " + filename);
        } catch (FileNotFoundException e) {
            Log.v(TAG, filename + " : not found");
        } catch (IOException e) {
            e.printStackTrace();
            iCommon.sendDebugLogToCloud("saveProperty-" + TAG + "-"+ e.toString(), context);
        } catch (Exception e) {
            e.printStackTrace();
            iCommon.sendDebugLogToCloud("saveProperty-" + TAG + "-"+ e.toString(), context);
        }
    }

    public static void write_lat_lon_to_storage(Context context, String filename,double value) {

        try {
            String Filename = filename;
            FileOutputStream outputStream = context.openFileOutput(Filename, Context.MODE_PRIVATE);

            if (filename.equals(iStorage.FILE_LATITUDE)) {
                String Latitude = String.valueOf(value);
                outputStream.write(Latitude.getBytes());
            } else if (filename.equals(iStorage.FILE_LONGITUDE)) {
                String Longitude = String.valueOf(value);
                outputStream.write(Longitude.getBytes());
            }
            outputStream.close();

        } catch (FileNotFoundException e) {
            Log.v(TAG, filename + " : not found");
        } catch (IOException e) {
            e.printStackTrace();
            iCommon.sendDebugLogToCloud("latlon2file-" + TAG + "-"+ e.toString(), context);
        } catch (Exception e) {
            e.printStackTrace();
            iCommon.sendDebugLogToCloud("latlon2file-" + TAG + "-"+ e.toString(), context);
        }
    }

    public static double read_lat_lon_from_storage(Context context, String filename) {
        String file_content = "";
        try {
            FileInputStream fin = context.openFileInput(filename);
            int c;
            while ((c = fin.read()) != -1) {
                file_content = file_content + Character.toString((char) c);
            }
            Log.v(TAG, "content of " + filename + " file is " + file_content);

        } catch (FileNotFoundException e) {
            Log.v(TAG, filename + " : not found");
        } catch (IOException e) {
            e.printStackTrace();
            iCommon.sendDebugLogToCloud("file2latlon-" + TAG + "-"+ e.toString(), context);
        } catch (Exception e) {
            e.printStackTrace();
            iCommon.sendDebugLogToCloud("file2latlon-" + TAG + "-"+ e.toString(), context);
        }
        return Double.parseDouble(file_content);
    }

    public static int read_days_left(Context context, String filename) {
        try {
            FileInputStream fin = context.openFileInput(filename);

            int c;
            String file_content = "";
            while ((c = fin.read()) != -1) {
                file_content = file_content + Character.toString((char) c);
            }
            Log.v(TAG, "content of " + filename + " file is " + file_content);
            int days = Integer.parseInt(file_content); // TODO : BUG : / appeared in content 28-Sept-2016

            return days;
        } catch (FileNotFoundException e) {
            Log.v(TAG, filename + " : not found");
        } catch (IOException e) {
            e.printStackTrace();
            iCommon.sendDebugLogToCloud("read_days_left-" + TAG + "-" + e.toString(), context);
        } catch (Exception e) {
            e.printStackTrace();
            iCommon.sendDebugLogToCloud("read_days_left-" + TAG + "-" + e.toString(), context);
        }
        return -1; // To distinguish subscription was started and now ended
    }

    public static void write_days_left(Context context, String filename, int value) {

        try {
            String Filename = filename;
            FileOutputStream outputStream = context.openFileOutput(Filename, Context.MODE_PRIVATE);

            outputStream.write(String.valueOf(value).getBytes());
            outputStream.close();
        } catch (FileNotFoundException e) {
            Log.v(TAG, filename + " : not found");
        } catch (IOException e) {
            e.printStackTrace();
            iCommon.sendDebugLogToCloud("write_days_left-" + TAG + "-"+ e.toString(), context);
        } catch (Exception e) {
            e.printStackTrace();
            iCommon.sendDebugLogToCloud("write_days_left-" + TAG + "-"+ e.toString(), context);
        }
    }
}
