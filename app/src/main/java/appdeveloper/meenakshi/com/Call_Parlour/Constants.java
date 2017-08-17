/*
 * Copyright (c) 2015 Karpa IT Solutions LLP
 * Distributed under the GNU GPL v2 with additional terms. For full terms see the file doc/LICENSE.txt
 */

package appdeveloper.meenakshi.com.Call_Parlour;

/**
 * Updated by karpa on 22/08/2016
 */

public class Constants {

    public final static String URL_BASE = "https://inetclean.com/scripts/16.10.27/vpn/";
    public final static String LOGIN_URL = "https://inetclean.com/scripts/16.10.27/vpn/login.php";

    public final static long TRAFFIC_THRESHOLD = 10 * 1000 * 1000; // 1 MB lead to high load on server

    public final static String FAQ_URL = "https://www.inetclean.com/faq/";

    public final static int TRIAL_DAYS = 3; // days

    public final static int POPUP_TIME_DELAY = 4000; // millisec

    public static final String URL_ON_INSTALL = URL_BASE + "on_install.php";
    public static final String URL_ON_APP_UPGRADE = URL_BASE + "on_app_upgrade.php";

    public static final String URL_ON_EMAIL = URL_BASE + "on_email.php";
    public static final String URL_ON_EMAIL_VERIFY = URL_BASE + "on_email_verify.php";
    public final static String URL_INSTALL = URL_BASE + "install.php";

    public static final String URL_ON_PIN_CHANGE = URL_BASE + "on_pin_change.php";
    public static final String URL_PASSWORD_VERIFY = URL_BASE + "on_pin_verify.php";

    public static final String URL_OVPN_FILE = URL_BASE + "ovpn.php";
    public static final String URL_ALERTS = URL_BASE + "alerts.php";
    public static final String URL_DEBUG_LOG = URL_BASE + "debuglog.php";
    public static final String URL_SMS_ALERT = URL_BASE + "sms_alert.php";

    public static final String URL_ON_SETUP_COMPLETE = URL_BASE + "on_setup_complete.php";
    public static final String URL_NO_SHUT_DOWN_FILE = URL_BASE + "on_no_shutdown_file.php";
    public static final String URL_ON_CONNECT = URL_BASE + "on_connect.php";
    public static final String URL_BYTE_INFO = URL_BASE + "on_byte_info.php";
    public static final String URL_UPDATE_LOCATION = URL_BASE + "on_location.php";
    public static final String URL_ON_SYNC = URL_BASE + "on_sync.php";
    public static final String URL_ON_SMS_ALERT = URL_BASE + "sms_alert.php";

    public static final String URL_ON_FEEDBACK = URL_BASE + "on_feedback.php";
}
