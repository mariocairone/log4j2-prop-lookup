package com.mariocairone.log4j2.lookup.services;

import java.util.Map;
import java.util.Properties;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.status.StatusLogger;
import org.yaml.snakeyaml.Yaml;

@SuppressWarnings({"unchecked"})
public class YamlPropertiesLoader extends AbstractPropertiesLoader {

	public static final Logger LOGGER = StatusLogger.getLogger();

	public YamlPropertiesLoader(String fileName) {
		this.fileName = fileName;
	}
	
	@Override
	public void load() {
		Properties result = new Properties();
       try {	   
           Yaml yaml = new Yaml();
           Map<String,Object> params = yaml.loadAs(getFileAsStream(fileName),Map.class);
           for(Map.Entry<String,Object> entry : params.entrySet()){
               if(entry.getValue() instanceof Map){
            	   result.putAll( eachYaml(entry.getKey(),(Map<String,Object>)entry.getValue()));
               }else{
                   result.put(entry.getKey(),entry.getValue().toString());
               }
           }
           properties.putAll(result);
       }catch (Exception e){
    	   LOGGER.warn("Exception Initializing yaml properties for plugin properties lookup");
       } finally {
    	   loaded = true;
       }
       
   }
    

	private Properties eachYaml(String key,Map<String,Object> map){
		Properties result = new Properties();
        for (Map.Entry<String,Object> entry : map.entrySet()){
            String newKey = "";
            if(!key.isEmpty()){
                newKey = (key + "." + entry.getKey());
            }else{
                newKey = entry.getKey();
            }
            if(entry.getValue() instanceof Map){
                result.putAll(eachYaml(newKey,(Map<String,Object>)entry.getValue()));
            }else{
            	result.put(newKey,entry.getValue().toString());
            }
        }
        return result;
    }


}
