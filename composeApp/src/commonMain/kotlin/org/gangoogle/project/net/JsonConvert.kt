import io.ktor.util.reflect.TypeInfo
import io.ktor.utils.io.core.*
import io.ktor.http.*
import io.ktor.http.content.OutgoingContent
import io.ktor.http.content.TextContent
import io.ktor.serialization.ContentConverter
import io.ktor.utils.io.*
import io.ktor.utils.io.charsets.Charset
import kotlinx.serialization.json.*
import kotlinx.serialization.*
import kotlinx.serialization.serializer  // ← 一定要加这一行

class AutoUnwrapKotlinxConverter(
    private val json: Json = Json { ignoreUnknownKeys = true; prettyPrint = true }
) : ContentConverter {

    override suspend fun serialize(
        contentType: ContentType,
        charset: Charset,
        typeInfo: TypeInfo,
        value: Any?
    ): OutgoingContent? {
        if (value == null) return null
        // 这一行：typeInfo.type 是 KType
        val serializer = json.serializersModule.serializer(typeInfo.kotlinType!!)
        val text = json.encodeToString(serializer as KSerializer<Any>, value)
        return TextContent(text, contentType.withCharset(charset))
    }

    override suspend fun deserialize(
        charset: Charset,
        typeInfo: TypeInfo,
        content: ByteReadChannel
    ): Any? {
        val text = content.readRemaining().readText(charset = charset)
        val element = json.parseToJsonElement(text)

        // 先处理非对象的快速路径
        if (element !is JsonObject) {
            val deserializer = json.serializersModule.serializer(typeInfo.kotlinType!!)
            return json.decodeFromJsonElement(deserializer, element)
        }
        val jsonObject = element
        // 自动识别外壳字段
        val (codeKey, dataKey, msgKey, successCode) = when {
            "StatusCode" in jsonObject -> listOf("StatusCode", "Data", "Errors", "200")
            "resCode" in jsonObject -> listOf("resCode", "data", "resMsg", "200")
            "code" in jsonObject -> listOf("code", "data", "message", "200")
            "errorCode" in jsonObject -> listOf("errorCode", "data", "errorMsg", "0")
            else -> throw ResponseException("无法识别响应结构: $text")
        }
        println("code: $codeKey, data: $dataKey, msg: $msgKey, successCode: $successCode")
        // 判断状态码
        val code = jsonObject[codeKey]?.jsonPrimitive?.content ?: "-1"
        if (code != successCode) {
            val msg = jsonObject[msgKey]?.jsonPrimitive?.content ?: "未知错误"
            throw ResponseException("请求失败: $msg (code=$code)")
        }

        // 如果没有 data 字段，则返回 true
        if (dataKey !in jsonObject) return true
        // 最后反序列化 data 字段
        val dataElement = jsonObject[dataKey]!!
        if (typeInfo.type == String::class) {
            return dataElement.toString()
        }
        val deserializer = json.serializersModule.serializer(typeInfo.kotlinType!!)
        return json.decodeFromJsonElement(deserializer, dataElement)
    }
}

class ResponseException(message: String) : RuntimeException(message)
