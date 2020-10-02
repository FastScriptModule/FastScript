package me.scoretwo.fastscript.api.yaml

import com.alibaba.fastjson.JSONArray

open class YAMLArray: JSONArray {

    constructor(jsonArray: JSONArray): super(jsonArray)

    constructor(): super()


}