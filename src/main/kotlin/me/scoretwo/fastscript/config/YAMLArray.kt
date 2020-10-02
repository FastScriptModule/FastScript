package me.scoretwo.fastscript.config

import com.alibaba.fastjson.JSONArray

class YAMLArray: JSONArray {

    constructor(jsonArray: JSONArray): super(jsonArray)

    constructor(): super()


}