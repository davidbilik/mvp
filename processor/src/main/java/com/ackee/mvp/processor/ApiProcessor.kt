package com.ackee.mvp.processor

import com.ackee.mvp.core.PresenterBinder
import com.ackee.mvp.core.PresenterClass
import com.google.auto.service.AutoService
import java.io.IOException
import java.util.*
import javax.annotation.processing.*
import javax.lang.model.SourceVersion
import javax.lang.model.element.Element
import javax.lang.model.element.ElementKind
import javax.lang.model.element.TypeElement
import javax.lang.model.util.Elements
import javax.lang.model.util.Types
import javax.tools.Diagnostic
import javax.tools.JavaFileManager
import javax.tools.StandardLocation

/**
 * TODO add class description

 * @author David Bilik [david.bilik@ackee.cz]
 * @since 14/11/16
 **/

@AutoService(Processor::class)
@SupportedSourceVersion(SourceVersion.RELEASE_7)
class ApiProcessor : AbstractProcessor() {
    lateinit private var typeUtils: Types

    lateinit private var elementUtils: Elements

    lateinit private var filer: Filer

    lateinit private var messager: Messager

    override fun init(processingEnv: ProcessingEnvironment) {
        super.init(processingEnv)
        typeUtils = processingEnv.typeUtils
        elementUtils = processingEnv.elementUtils
        filer = processingEnv.filer
        messager = processingEnv.messager
    }

    override fun getSupportedAnnotationTypes(): Set<String> {
        val annotataions = LinkedHashSet<String>()
        annotataions.add(PresenterClass::class.java.canonicalName)
        return annotataions
    }

    override fun getSupportedSourceVersion(): SourceVersion {
        return SourceVersion.latestSupported()
    }

    override fun process(annotations: MutableSet<out TypeElement>, roundEnv: RoundEnvironment): Boolean {
//        messager.printMessage(Diagnostic.Kind.ERROR, "IN PROCESS")
        var annotatedClasses: List<PresenterCreatorClass> = mutableListOf()
        for (elem in roundEnv.getElementsAnnotatedWith(PresenterClass::class.java)) {
            if (elem.kind != ElementKind.CLASS) {
                error(elem, "Only classes can be annotated with @%s", PresenterClass::class.java.simpleName)
                return true
            }
            annotatedClasses += PresenterCreatorClass(elem as TypeElement)
        }
        generateCode(annotatedClasses)
        return true // no further processing of this annotation type
    }

    private fun generateCode(annotatedClasses: List<PresenterCreatorClass>) {
        for (a in annotatedClasses) {
//            messager.printMessage(Diagnostic.Kind.ERROR, a.toString())

            try {
                val f = processingEnv.filer.createResource(StandardLocation.SOURCE_OUTPUT, a.viewPackageName, "${a.viewName}\$\$PresenterBinder.kt")

                val w = f.openWriter()
                w.write("import ${a.viewClassName}\n")
                w.write("import ${a.presenterClassName}\n")
                w.write("import ${PresenterBinder::class.java.canonicalName}\n\n")

                w.write("fun ${a.viewName}.get${a.presenterSimpleTypeName}() : ${a.presenterSimpleTypeName} = getPresenter() as ${a.presenterSimpleTypeName}\n")

                w.write("class ${a.viewName}PresenterBinder : PresenterBinder() {\n")
                w.write("override fun createPresenter() : ${a.presenterSimpleTypeName} { return ${a.presenterSimpleTypeName}() }\n")
                w.write("}")

                w.flush()
                w.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    private fun error(e: Element, msg: String, vararg args: Any) {
        messager.printMessage(
                Diagnostic.Kind.ERROR,
                String.format(msg, *args),
                e)
    }

    /**
     * Get the package name of a certain clazz

     * @param clazz The class you want the packagename for
     * *
     * @return The package name
     */
    private fun getPackageName(clazz: PresenterCreatorClass): String {
        val pkg = elementUtils.getPackageOf(clazz.elem)
        return if (pkg.isUnnamed) "" else pkg.qualifiedName.toString()
    }


}