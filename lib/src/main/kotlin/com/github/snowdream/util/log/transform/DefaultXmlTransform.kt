package com.github.snowdream.util.log.transform

import java.io.StringReader
import java.io.StringWriter
import javax.xml.transform.OutputKeys
import javax.xml.transform.TransformerException
import javax.xml.transform.TransformerFactory
import javax.xml.transform.stream.StreamResult
import javax.xml.transform.stream.StreamSource


/**
 * from: http://stackoverflow.com/a/1264912
 *
 * Created by snowdream on 17/4/29.
 */
class DefaultXmlTransform : AbstractLogTransform() {

    override fun transform(obj: Any): String {
        if (obj !is String) {
            throw IllegalArgumentException()
        }

        try {
            val xmlInput = StreamSource(StringReader(obj))
            val xmlOutput = StreamResult(StringWriter())
            val transformer = TransformerFactory.newInstance().newTransformer()
            transformer.setOutputProperty(OutputKeys.INDENT, "yes")
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2")
            transformer.transform(xmlInput, xmlOutput)

            return xmlOutput.getWriter().toString()
        } catch (e: TransformerException) {
            e.printStackTrace()
        }

        return ""
    }
}