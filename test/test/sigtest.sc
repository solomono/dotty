package test

import dotty.tools.dotc._
import core._
import Decorators._
import Types._, Symbols._

object sigtest extends DottyTest {
  println("Welcome to the Scala worksheet")       //> Welcome to the Scala worksheet
  val int = ctx.requiredClass("scala.Int")        //> int  : dotty.tools.dotc.core.Symbols.ClassSymbol = class Int
  int.signature                                   //> res0: dotty.tools.dotc.core.Denotations.Signature = List()
  val intmeth = methType("x")(int.symbolicRef)()  //> intmeth  : dotty.tools.dotc.core.Types.MethodType = MethodType(List(x), List
                                                  //| (TypeRef(ThisType(module class scala),Int)), TypeRef(ThisType(module class s
                                                  //| cala),Unit))
  intmeth.signature                               //> res1: dotty.tools.dotc.core.Denotations.Signature = List(Int)
  val arraymeth = methType("x")(defn.ArrayType.appliedTo(int.symbolicRef))()
                                                  //> arraymeth  : dotty.tools.dotc.core.Types.MethodType = MethodType(List(x), Li
                                                  //| st(RefinedType(TypeRef(ThisType(module class scala),Array), scala$Array$$T, 
                                                  //| TypeAlias(TypeRef(ThisType(module class scala),Int)) | hash = -634207123)), 
                                                  //| TypeRef(ThisType(module class scala),Unit))
  arraymeth.signature                             //> res2: dotty.tools.dotc.core.Denotations.Signature = List(Object[])
  val curriedmeth = methType("x", "y")(defn.IntType, defn.BooleanType)(methType("z")(defn.ArrayType.appliedTo(defn.IntType))())
                                                  //> curriedmeth  : dotty.tools.dotc.core.Types.MethodType = MethodType(List(x, y
                                                  //| ), List(TypeRef(ThisType(module class scala),Int), TypeRef(ThisType(module c
                                                  //| lass scala),Boolean)), MethodType(List(z), List(RefinedType(TypeRef(ThisType
                                                  //| (module class scala),Array), scala$Array$$T, TypeAlias(TypeRef(ThisType(modu
                                                  //| le class scala),Int)) | hash = -250095115)), TypeRef(ThisType(module class s
                                                  //| cala),Unit)))
  curriedmeth.signature                           //> res3: dotty.tools.dotc.core.Denotations.Signature = List(Int, Boolean, Objec
                                                  //| t[])
}