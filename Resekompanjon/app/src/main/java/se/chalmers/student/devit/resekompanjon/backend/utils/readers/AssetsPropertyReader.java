package se.chalmers.student.devit.resekompanjon.backend.utils.readers;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by Marcus on 2015-10-07.
 * Used for getting passwords out of properties file
 */
public class AssetsPropertyReader {
    private Properties prop;
    private Resources res;

    public AssetsPropertyReader(Context context) {
        res = context.getResources();
        prop = new Properties();
    }

    private Properties getProperties(String FileName) {

        try {
            /**
             * getAssets() Return an AssetManager instance for your
             * application's package. AssetManager Provides access to an
             * application's raw asset files;
             */
            AssetManager assetManager = res.getAssets();
            /**
             * Open an asset using ACCESS_STREAMING mode. This
             */
            InputStream inputStream = assetManager.open(FileName);
            /**
             * Loads properties from the specified InputStream,
             */

            prop.load(inputStream);

        } catch (IOException e) {
            // TODO Auto-generated catch block
            Log.e("AssetsPropertyReader", e.toString());
        }
        return prop;
    }

    public String getPassword(String s) {
        return getProperties("config.properties").getProperty(s) + "";
    }
}
