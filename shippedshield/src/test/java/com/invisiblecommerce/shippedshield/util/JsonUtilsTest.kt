package com.invisiblecommerce.shippedshield.util

import org.json.JSONArray
import org.json.JSONObject
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class JsonUtilsTest {

    @Test
    fun optStringTest() {
        val jsonObject = JSONObject().put("key", "value")
        assertEquals("value", JsonUtils.optString(jsonObject, "key"))

        jsonObject.put("key", "null")
        assertNull(JsonUtils.optString(jsonObject, "key"))

        jsonObject.put("key", "value")
        val ob = JsonUtils.optString(jsonObject, "nokeyshere")
        assertNull(ob)
    }


    @Test
    fun jsonObjectToMapTest() {
        assertNull(JsonUtils.jsonObjectToMap(null))

        val expectedMap = mapOf(
            "a" to "a",
            "b" to "b",
            "c" to true,
            "d" to 123
        )

        val mappedObject = JsonUtils.jsonObjectToMap(TEST_JSON_OBJECT)
        assertEquals(expectedMap, mappedObject)
    }

    @Test
    fun jsonArrayToListTest() {
        assertNull(JsonUtils.jsonArrayToList(null))

        val expectedList = listOf("a", "b", "c", "d", true)
        val convertedJsonArray = JsonUtils.jsonArrayToList(TEST_JSON_ARRAY)
        assertEquals(expectedList, convertedJsonArray)
    }

    private companion object {

        private val TEST_JSON_ARRAY = JSONArray(
            """
            ["a", "b", "c", "d", true]
            """.trimIndent()
        )

        private val TEST_JSON_OBJECT = JSONObject(
            """
            {
                "a": "a",
                "b": "b",
                "c": true,
                "d": 123
            }
            """.trimIndent()
        )
    }
}