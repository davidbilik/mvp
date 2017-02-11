package com.ackee.mvp.processor

import com.ackee.mvp.core.PresenterClass
import javax.lang.model.element.Element
import javax.lang.model.element.TypeElement
import javax.lang.model.type.DeclaredType
import javax.lang.model.type.MirroredTypeException

/**
 * TODO add class description

 * @author David Bilik [david.bilik@ackee.cz]
 * @since 14/11/16
 **/
class PresenterCreatorClass(val elem: TypeElement) {
    companion object {
        val TAG: String = PresenterCreatorClass::class.java.name
    }

    val viewName: String
    val viewClassName : String
    var presenterClassName: String = ""
    var presenterSimpleTypeName: String = ""
    val viewPackageName:  String

    init {
        viewName = elem.simpleName.toString()
        viewClassName = elem.qualifiedName.toString()
        viewPackageName = elem.enclosingElement.toString()
        val annotation = elem.getAnnotation(PresenterClass::class.java)

//        // Get the full QualifiedTypeName
        try {
            val presenterClass = annotation.value.java
            presenterClassName = presenterClass.canonicalName
            presenterSimpleTypeName = presenterClass.simpleName
        } catch (mte: MirroredTypeException) {
            val classTypeMirror = mte.typeMirror as DeclaredType
            presenterClassName = (classTypeMirror.asElement() as TypeElement).qualifiedName.toString()
            presenterSimpleTypeName = classTypeMirror.asElement().simpleName.toString()
        }
    }

    override fun toString(): String {
        return "$viewName, $presenterClassName, $presenterSimpleTypeName"
    }

}