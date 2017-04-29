package com.github.snowdream.util.log.transform

import org.json.JSONArray
import org.json.JSONObject


/**
 * Created by snowdream on 17/4/29.
 */
class DefaultJsonTransform: AbstractLogTransform() {
    /**
     * It is used for json pretty print
     */
    private val JSON_INDENT = 4

    override fun transform(obj: Any): String {
        when(obj){
            is String -> {
                if (obj.startsWith("{")) {
                    val jsonObject = JSONObject(obj)

                    return transform(jsonObject)
                }else if(obj.startsWith("[")){
                    val jsonArray = JSONArray(obj)

                    return transform(jsonArray)
                }else{

                }
                }
            is JSONObject ->{
                return  obj.toString(JSON_INDENT)
            }
            is JSONArray ->{
                return  obj.toString(JSON_INDENT)
            }
        }

        return ""
    }
}