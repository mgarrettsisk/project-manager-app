package proj_FX;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Properties;

public class Utilities {

    public Properties getPropsFile(String file) throws Exception {

        if (file == null || file.length() == 0) return null;

        Properties config = new Properties();
        InputStream is;
        try {
            try {
                is = new BufferedInputStream(new FileInputStream(file));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                throw e;
            }
            config.load(is);
            return config;
        } catch(Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}
