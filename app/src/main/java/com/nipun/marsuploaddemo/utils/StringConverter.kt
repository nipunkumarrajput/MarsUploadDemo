package com.nipun.marsuploaddemo.utils

import com.google.gson.*
import java.lang.reflect.Type

/*
* Created by Nipun Kumar Rajput on 16-04-2020.
* Copyright (c) 2020 Nipun. All rights reserved.
*/
class StringConverter : JsonSerializer<String>, JsonDeserializer<String> {
    override fun serialize(
        src: String?, typeOfSrc: Type,
        context: JsonSerializationContext
    ): JsonElement {
        return if (src == null) {
            JsonPrimitive("")
        } else {
            JsonPrimitive(src)
        }
    }

    @Throws(JsonParseException::class)
    override fun deserialize(
        json: JsonElement, typeOfT: Type,
        context: JsonDeserializationContext
    ): String {
        return json.asJsonPrimitive.asString
    }
}