package io

import scala.annotation.compileTimeOnly
import scala.reflect.macros.blackbox

object functionmeta {

  def functionName: String = macro Impls.functionNameImpl

  def arguments: List[Any] = macro Impls.argumentsImpl

  def argumentsMap: Map[String, Any] = macro Impls.argumentsMapImpl

  private object Impls {

    @compileTimeOnly("Enable macro paradise plugin to expand macro annotations or add scalac flag -Ymacro-annotations.")
    def functionNameImpl(c: blackbox.Context): c.Expr[String] = {
      val owner = c.internal.enclosingOwner

      if (owner.isMethod) {
        c.Expr(c.parse(s""""${owner.name.toString}""""))
      } else {
        c.abort(c.enclosingPosition, "functionName can be used only inside function.")
      }
    }

    @compileTimeOnly("Enable macro paradise plugin to expand macro annotations or add scalac flag -Ymacro-annotations.")
    def argumentsImpl(c: blackbox.Context): c.Expr[List[Any]] = {

      val owner = c.internal.enclosingOwner

      if (owner.isMethod || owner.isConstructor) {
        c.Expr(
          c.parse(
            s"List(${owner.asMethod.paramLists.head.map(_.name).mkString(", ")})"
          )
        )
      } else {
        c.abort(c.enclosingPosition, "arguments can be used only inside function.")
      }

    }

    @compileTimeOnly("Enable macro paradise plugin to expand macro annotations or add scalac flag -Ymacro-annotations.")
    def argumentsMapImpl(c: blackbox.Context): c.Expr[Map[String, Any]] = {

      val owner = c.internal.enclosingOwner

      if (owner.isMethod || owner.isConstructor) {
        c.Expr(
          c.parse(
            s"""Map(${owner.asMethod.paramLists.head.map(s => s""""${s.name}" -> ${s.name}""").mkString(", ")})"""
          )
        )
      } else {
        c.abort(c.enclosingPosition, "argumentsMap can be used only inside function.")
      }

    }

  }

}
