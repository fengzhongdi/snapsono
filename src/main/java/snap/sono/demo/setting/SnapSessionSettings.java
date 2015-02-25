package snap.sono.demo.setting;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import snap.sono.demo.data.Constants;


public class SnapSessionSettings {
	private static HashMap<String,String> snapSetting = null;
	private static Logger logger = LoggerFactory.getLogger("SnapSessionSettings");

	public static HashMap<String,String> getSettings() throws Exception{
		if(snapSetting!=null){
			logger.info("Already got settings, return");
			return snapSetting;
		}
		snapSetting = new HashMap<String,String>();
		logger.info("Try to populate snapSetting!");
		Scanner scan = new Scanner(new File(Constants.DB_SETTING_PATH));
		while(scan.hasNext()){
			String[] strArr = scan.nextLine().split(" ");
			snapSetting.put(strArr[0], strArr[1]);
		}
		logger.info("Finish populating snapSetting!");
		return snapSetting;
	}
	
}
